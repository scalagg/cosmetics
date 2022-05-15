package gg.scala.cosmetics.hook

import org.bukkit.entity.Player

interface CosmeticHook {

    fun inSupportedRegion(player: Player): Boolean

    fun canRenderEffects(player: Player): Boolean

    fun canRenderTracks(player: Player): Boolean

}
