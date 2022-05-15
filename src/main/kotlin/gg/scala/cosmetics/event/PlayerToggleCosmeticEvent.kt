package gg.scala.cosmetics.event

import gg.scala.cosmetics.cosmetic.Cosmetic
import net.evilblock.cubed.event.PluginEvent
import org.bukkit.entity.Player

class PlayerToggleCosmeticEvent(
    val player: Player,
    val cosmetic: Cosmetic,
    val enabled: Boolean
) : PluginEvent()
