package ejektaflex.makkit.common.world

import ejektaflex.makkit.common.enum.UndoRedoMode
import ejektaflex.makkit.common.ext.getEnd
import ejektaflex.makkit.common.ext.getStart
import ejektaflex.makkit.common.network.pakkits.client.FocusRegionPacket
import net.minecraft.block.BlockState
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Box
import net.minecraft.util.math.Direction

// TODO lift player out of this and commit/revert in UserActionHistory
data class EditAction(
        val player: ServerPlayerEntity,
        val box: Box,
        val direction: Direction,
        val operation: WorldOperation,
        val palette: List<BlockState>
) {
    // Pos: BeforeState, AfterState
    private var stateMap = mutableMapOf<BlockPos, Pair<BlockState, BlockState>>()

    fun edit(pos: BlockPos, state: BlockState) {
        if (pos !in stateMap) {
            stateMap[pos] = player.world.getBlockState(pos) to state
        } else {
            stateMap[pos] = stateMap[pos]!!.first to state
        }
    }

    fun clear(pos: BlockPos) {
        if (pos in stateMap) {
            stateMap.remove(pos)
        }
    }

    // fun optimize() -> will remove changes which go from equal state to equal state

    fun syncToWorldState(mode: UndoRedoMode) {
        for (entry in stateMap) {
            stateMap[entry.key] = when(mode) {
                UndoRedoMode.UNDO -> entry.value.first to player.world.getBlockState(entry.key)
                UndoRedoMode.REDO -> player.world.getBlockState(entry.key) to entry.value.second
                else -> throw Exception("You cannot sync edit history to world state with a CLEAR mode!")
            }
        }
    }

    fun calcChangeSet() {
        operation.execute(this)
    }

    fun select() {
        FocusRegionPacket(
                BlockPos(box.getStart()),
                BlockPos(box.getEnd())
        ).sendToClient(player)
    }

    fun commit() {
        for (entry in stateMap) {
            player.world.setBlockState(entry.key, entry.value.second)
        }
    }

    fun revertCommit() {
        for (entry in stateMap) {
            player.world.setBlockState(entry.key, entry.value.first)
        }
    }

}