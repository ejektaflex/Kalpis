package io.ejekta.makkit.client.editor.drag.tools

import io.ejekta.makkit.client.editor.EditRegion
import io.ejekta.makkit.client.editor.drag.SingleAxisDragTool
import io.ejekta.makkit.client.editor.input.KeyStateHandler
import io.ejekta.makkit.common.ext.*
import net.minecraft.util.math.Box
import net.minecraft.util.math.Vec3d

internal class ResizeToolAxial(
        region: EditRegion,
        binding: KeyStateHandler
) : SingleAxisDragTool(region, binding) {

    // Constrain to direction
    override fun getCursorOffset(snapped: Boolean): Vec3d? {
        return super.getCursorOffset(snapped)?.dirMask(dragStart.dir)
    }

    override fun getPreviewBox(offset: Vec3d, box: Box): Box {
        val shrinkVec = offset.dirMask(dragStart.dir.opposite)
        return box.shrinkSide(shrinkVec, dragStart.dir)
    }

    override fun onDrawPreview(offset: Vec3d) {
        super.onDrawPreview(offset)
        preview.drawSizeOnFace(dragStart.dir)
    }

}