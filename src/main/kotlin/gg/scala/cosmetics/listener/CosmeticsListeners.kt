package gg.scala.cosmetics.listener

import gg.scala.cosmetics.profile.ProfileHandler
import gg.scala.commons.annotations.Listeners
import net.evilblock.cubed.util.bukkit.EventUtils
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerMoveEvent
import org.bukkit.event.player.PlayerTeleportEvent

@Listeners
object CosmeticsListeners : Listener
{
    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    fun onPlayerMoveEvent(event: PlayerMoveEvent)
    {
        if (EventUtils.hasPlayerMoved(event))
        {
            ProfileHandler.getProfile(event.player)?.lastMovement = System.currentTimeMillis()
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    fun onPlayerTeleportEvent(event: PlayerTeleportEvent)
    {
        if (EventUtils.hasPlayerMoved(event))
        {
            ProfileHandler.getProfile(event.player)?.lastMovement = System.currentTimeMillis()
        }
    }
}
