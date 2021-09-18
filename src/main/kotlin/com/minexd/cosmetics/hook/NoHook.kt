package com.minexd.cosmetics.hook

import org.bukkit.entity.Player

class NoHook : Hook {

    override fun inSupportedRegion(player: Player): Boolean {
        return true
    }

    override fun canRenderEffects(player: Player): Boolean {
        return true
    }

    override fun canRenderTracks(player: Player): Boolean {
        return true
    }

}