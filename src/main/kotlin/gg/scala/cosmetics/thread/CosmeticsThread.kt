package gg.scala.cosmetics.thread

import gg.scala.cosmetics.cosmetic.TickableCosmetic
import gg.scala.cosmetics.profile.CosmeticsPlayer
import gg.scala.cosmetics.profile.CosmeticsPlayerHandler
import org.bukkit.Bukkit
import org.bukkit.entity.Player

class CosmeticsThread : Thread("Cosmetics - Tick Thread")
{

    private var lastSave = System.currentTimeMillis()

    override fun run()
    {
        while (true)
        {
            try
            {
                runLogic()
            } catch (e: Exception)
            {
                e.printStackTrace()
            }

            try
            {
                sleep(50L)
            } catch (e: InterruptedException)
            {
                e.printStackTrace()
            }
        }
    }

    private fun runLogic()
    {
        if (System.currentTimeMillis() - lastSave >= 30_000L)
        {
            lastSave = System.currentTimeMillis()

            for (profile in CosmeticsPlayerHandler.getProfiles())
            {
                try
                {
                    if (profile.requiresSave)
                    {
                        CosmeticsPlayerHandler.saveProfile(profile)
                    }
                } catch (e: Exception)
                {
                    e.printStackTrace()
                }
            }
        }

        for (player in Bukkit.getOnlinePlayers())
        {
            try
            {
                if (!player.isOnline)
                {
                    continue
                }

                val profile = CosmeticsPlayerHandler.getProfile(player)
                if (profile != null)
                {
                    if (!gg.scala.cosmetics.CosmeticsPlugin.instance.hook.inSupportedRegion(player))
                    {
                        for (cosmetic in profile.getEnabledCosmetics())
                        {
                            CosmeticsPlayerHandler.toggleCosmetic(player, cosmetic)
                        }

                        profile.track()?.clearChanges()
                        profile.updateTrack(null)

                        continue
                    }

                    tickCosmetics(player, profile)
                    tickTrack(profile)
                }
            } catch (e: Exception)
            {
                e.printStackTrace()
            }
        }
    }

    private fun tickCosmetics(player: Player, cosmeticsPlayer: CosmeticsPlayer)
    {
        for (cosmetic in cosmeticsPlayer.getEnabledCosmetics())
        {
            if (cosmetic is TickableCosmetic)
            {
                cosmetic.tick(player)
            }
        }
    }

    private fun tickTrack(cosmeticsPlayer: CosmeticsPlayer)
    {
        if (cosmeticsPlayer.track() != null)
        {
            try
            {
                cosmeticsPlayer.track()!!.tick()
            } catch (e: Exception)
            {
                e.printStackTrace()
            }
        }
    }
}
