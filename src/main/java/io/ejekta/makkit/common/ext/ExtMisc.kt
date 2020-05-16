package io.ejekta.makkit.common.ext

import io.ejekta.makkit.client.render.RenderColor
import io.ejekta.makkit.client.render.RenderHelper
import net.minecraft.client.render.VertexConsumer
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.item.ItemStack
import net.minecraft.util.Identifier
import net.minecraft.util.math.*
import net.minecraft.util.registry.Registry

fun MatrixStack.drawOffset(pos: Vec3d, func: RenderHelper.() -> Unit, helper: RenderHelper) {
    translate(-pos.x, -pos.y, -pos.z)
    func(helper)
    translate(pos.x, pos.y, pos.z)
}

val ItemStack.identifier: Identifier
    get() = Registry.ITEM.getId(item)

// Shorthand
val ItemStack.id: Identifier
    get() = identifier


// Inlining here may improve performance simply because this gets called very often

inline fun VertexConsumer.vertex(mat: Matrix4f, x: Double, y: Double, z: Double): VertexConsumer {
    return vertex(mat, x.toFloat(), y.toFloat(), z.toFloat())
}

inline fun VertexConsumer.color(col: RenderColor): VertexConsumer {
    return color(col.r, col.g, col.b, col.a)
}













