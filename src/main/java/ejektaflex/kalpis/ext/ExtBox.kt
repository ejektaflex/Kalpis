package ejektaflex.kalpis.ext

import ejektaflex.kalpis.data.BoxTraceResult
import ejektaflex.kalpis.mixin.BoxMixin
import net.minecraft.util.math.Box
import net.minecraft.util.math.Direction
import net.minecraft.util.math.Vec3d


fun Box.shrinkSide(off: Vec3d, dir: Direction): Box {

    var vecA = getStart()
    var vecB = getEnd()

    if (dir.direction == Direction.AxisDirection.NEGATIVE) {
        vecA = vecA.subtract(off)
    } else {
        vecB = vecB.subtract(off)
    }

    return Box(vecA, vecB)
}

fun Box.getStart(): Vec3d {
    return Vec3d(x1, y1, z1)
}

fun Box.getSize(): Vec3d {
    return Vec3d(x2 - x1, y2 - y1, z2 - z1)
}

fun Box.getEnd(): Vec3d {
    return Vec3d(x2, y2, z2)
}

fun Box.rayTraceForSide(min: Vec3d, max: Vec3d): BoxTraceResult? {
    val ds = doubleArrayOf(1.0)
    val d = max.x - min.x
    val e = max.y - min.y
    val f = max.z - min.z
    val side = BoxMixin.traceCollisionSide(this, min, ds, null, d, e, f)
    return if (side != null) {
        val g = ds[0]
        BoxTraceResult(min, side, min.add(g * d, g * e, g * f))
    } else {
        null
    }
}
