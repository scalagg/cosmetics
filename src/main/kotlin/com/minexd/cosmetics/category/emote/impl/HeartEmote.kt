package com.minexd.cosmetics.category.emote.impl

import com.minexd.cosmetics.category.emote.Emote
import net.evilblock.cubed.util.nms.MinecraftReflection
import net.evilblock.cubed.util.nms.particle.ParticleMeta
import net.evilblock.cubed.util.nms.particle.ParticleUtil
import org.bukkit.ChatColor
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

object HeartEmote : Emote {

    override fun getId(): String {
        return "heart"
    }

    override fun getDisplayName(): String {
        return "Heart"
    }

    override fun getDescription(): List<String> {
        return arrayListOf("${ChatColor.GOLD}Perform a ${ChatColor.RED}❤ ${ChatColor.GOLD}emote.")
    }

    override fun getIcon(): ItemStack {
        return ItemStack(Material.RED_ROSE)
    }

    override fun playEffect(location: Location) {
        ParticleUtil.sendsParticleToAll(location.world,
            ParticleMeta(location, MinecraftReflection.getEnumParticle("HEART")!!)
        )
    }

}