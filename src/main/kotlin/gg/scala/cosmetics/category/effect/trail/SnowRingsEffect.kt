package gg.scala.cosmetics.category.effect.trail

import gg.scala.cosmetics.cosmetic.category.CosmeticCategory
import gg.scala.cosmetics.category.EffectsCategory
import net.evilblock.cubed.util.nms.MinecraftReflection
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

object SnowRingsEffect : RingsEffect(MinecraftReflection.getEnumParticle("SNOW_SHOVEL")!!) {

    override fun getCategory(): CosmeticCategory
    {
        return EffectsCategory
    }

    override fun getID(): String {
        return "snow-rings"
    }

    override fun getName(): String {
        return "Snow Rings"
    }

    override fun getDescription(): String {
        return "Surround yourself with rings made of snow."
    }

    override fun getPermission(): String {
        return "cosmetics.snow-rings"
    }

    override fun getIcon(): ItemStack {
        return ItemStack(Material.SNOW_BALL)
    }

}
