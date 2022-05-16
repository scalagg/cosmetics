package gg.scala.cosmetics.profile

import gg.scala.cosmetics.CosmeticsPlugin
import gg.scala.cosmetics.cosmetic.Cosmetic
import gg.scala.cosmetics.event.PlayerToggleCosmeticEvent
import gg.scala.flavor.service.Configure
import gg.scala.flavor.service.Service
import gg.scala.store.controller.DataStoreObjectController
import gg.scala.store.controller.DataStoreObjectControllerCache
import gg.scala.store.storage.type.DataStoreStorageType
import org.bukkit.entity.Player
import java.util.*
import java.util.concurrent.CompletableFuture

@Service
object CosmeticsPlayerHandler
{
    private lateinit var cosmeticsPlayerLayer: DataStoreObjectController<CosmeticsPlayer>

    private val profiles: Map<UUID, CosmeticsPlayer>
        get() = cosmeticsPlayerLayer.localCache()

    @Configure
    fun configure()
    {
        this.cosmeticsPlayerLayer =
            DataStoreObjectControllerCache.create()
    }

    fun getProfiles(): Collection<CosmeticsPlayer>
    {
        return profiles.values
    }

    fun getProfile(player: Player): CosmeticsPlayer?
    {
        return profiles[player.uniqueId]
    }

    fun loadProfile(uuid: UUID)
    {
        this.cosmeticsPlayerLayer
            .load(uuid, DataStoreStorageType.MONGO)
            .exceptionally {
                it.printStackTrace()
                return@exceptionally null
            }
            .thenAccept {
                this.cosmeticsPlayerLayer
                    .localCache()[uuid] = it ?: CosmeticsPlayer(uuid)
            }
    }

    fun forgetProfile(cosmeticsPlayer: CosmeticsPlayer)
    {
        this.cosmeticsPlayerLayer
            .localCache()
            .remove(cosmeticsPlayer.uuid)
    }

    fun saveProfile(cosmeticsPlayer: CosmeticsPlayer): CompletableFuture<Unit>
    {
        return cosmeticsPlayerLayer.save(
            cosmeticsPlayer, DataStoreStorageType.MONGO
        ).exceptionally {
            it.printStackTrace()
            return@exceptionally null
        }.thenApply {
            cosmeticsPlayer.requiresSave = false
        }
    }

    fun isCosmeticEnabled(player: Player, cosmetic: Cosmetic): Boolean
    {
        val profile = profiles[player.uniqueId]
        return profile?.isCosmeticEnabled(cosmetic) ?: false
    }

    fun toggleCosmetic(player: Player, cosmetic: Cosmetic)
    {
        val profile = profiles[player.uniqueId]
        if (profile != null)
        {
            if (!profile.isCosmeticEnabled(cosmetic))
            {
                if (!cosmetic.canEnable(player))
                {
                    return
                }
            }

            profile.toggleCosmetic(cosmetic)

            if (profile.isCosmeticEnabled(cosmetic))
            {
                cosmetic.onEnable(player)
            } else
            {
                cosmetic.onDisable(player)
            }

            PlayerToggleCosmeticEvent(
                player,
                cosmetic,
                profile.isCosmeticEnabled(cosmetic)
            ).call()

            // disable other cosmetics in the same category
            for (category in CosmeticsPlugin.instance.categories)
            {
                if (!category.getCosmetics().contains(cosmetic))
                {
                    continue
                }

                for (enabledCheck in category.getCosmetics())
                {
                    if (enabledCheck != cosmetic && profile.isCosmeticEnabled(enabledCheck))
                    {
                        profile.toggleCosmetic(enabledCheck)
                    }
                }
            }
        }
    }
}
