package gg.scala.cosmetics.profile.listener

import gg.scala.cosmetics.CosmeticsPlugin
import gg.scala.cosmetics.cosmetic.TickableCosmetic
import gg.scala.cosmetics.profile.CosmeticsPlayerHandler
import gg.scala.commons.annotations.Listeners
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerPreLoginEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent

@Listeners
object ProfileLoadListeners : Listener
{
    @EventHandler
    fun onAsyncPlayerPreLoginEvent(event: AsyncPlayerPreLoginEvent)
    {
        CosmeticsPlayerHandler.loadProfile(event.uniqueId)
    }

    @EventHandler
    fun onPlayerJoinEvent(event: PlayerJoinEvent)
    {
        val profile = CosmeticsPlayerHandler.getProfile(event.player)
        if (profile != null)
        {
            if (profile.track() != null)
            {
                profile.track()!!.cachedPlayer = event.player
            }

            if (CosmeticsPlugin.instance.hook.inSupportedRegion(event.player))
            {
                for (cosmetic in profile.getEnabledCosmetics())
                {
                    if (profile.isCosmeticEnabled(cosmetic) || !cosmetic.hasState())
                    {
                        cosmetic.onEnable(event.player)
                    }
                }
            }
        }
    }

    @EventHandler
    fun onPlayerQuitEvent(event: PlayerQuitEvent)
    {
        val profile = CosmeticsPlayerHandler.getProfile(event.player)
        if (profile != null)
        {
            for (cosmetic in profile.getEnabledCosmetics())
            {
                cosmetic.onDisable(event.player)

                if (cosmetic is TickableCosmetic)
                {
                    cosmetic.ticks.remove(event.player.uniqueId)
                }

                profile.requiresSave = true
            }

            if (profile.track() != null)
            {
                profile.track()!!.cachedPlayer = null
            }

            CosmeticsPlayerHandler.forgetProfile(profile)

            if (profile.requiresSave)
            {
                CosmeticsPlayerHandler.saveProfile(profile)
            }
        }
    }
}
