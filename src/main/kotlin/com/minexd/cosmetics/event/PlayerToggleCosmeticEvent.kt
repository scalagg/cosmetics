package com.minexd.cosmetics.event

import com.minexd.cosmetics.Cosmetic
import net.evilblock.cubed.plugin.PluginEvent
import org.bukkit.entity.Player

class PlayerToggleCosmeticEvent(val player: Player, val cosmetic: Cosmetic, val enabled: Boolean) : PluginEvent()