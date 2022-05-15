package gg.scala.cosmetics.category.effect.trail

import gg.scala.cosmetics.cosmetic.category.CosmeticCategory
import gg.scala.cosmetics.category.EffectsCategory
import net.evilblock.cubed.util.nms.MinecraftReflection
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

object WaterRingsEffect : RingsEffect(MinecraftReflection.getEnumParticle("DRIP_WATER")!!) {

    override fun getCategory(): CosmeticCategory
    {
        return EffectsCategory
    }

    override fun getID(): String {
        return "water-rings"
    }

    override fun getName(): String {
        return "Water Rings"
    }

    override fun getDescription(): String {
        return "Surround yourself with rings made of water."
    }

    override fun getPermission(): String {
        return "cosmetics.water-rings"
    }

    override fun getIcon(): ItemStack {
        return ItemStack(Material.WATER_BUCKET)
    }

}
