package com.minexd.cosmetics.cosmetic

import org.bukkit.entity.Player
import java.util.*
import java.util.concurrent.ConcurrentHashMap

abstract class TickableCosmetic : Cosmetic()
{
    internal val ticks =
        ConcurrentHashMap<UUID, Int>()

    open fun tick(player: Player)
    {
    }
}
