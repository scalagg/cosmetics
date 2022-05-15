package com.minexd.cosmetics.profile

import com.minexd.cosmetics.CosmeticsPlugin
import com.minexd.cosmetics.cosmetic.Cosmetic
import com.minexd.cosmetics.event.PlayerToggleCosmeticEvent
import gg.scala.flavor.service.Configure
import gg.scala.flavor.service.Service
import gg.scala.store.controller.DataStoreObjectController
import gg.scala.store.controller.DataStoreObjectControllerCache
import gg.scala.store.storage.type.DataStoreStorageType
import org.bukkit.entity.Player
import java.util.*
import java.util.concurrent.CompletableFuture

@Service
object ProfileHandler
{
    private lateinit var profileLayer: DataStoreObjectController<Profile>

    private val profiles: Map<UUID, Profile>
        get() = profileLayer.localCache()

    @Configure
    fun configure()
    {
        this.profileLayer =
            DataStoreObjectControllerCache.create()
    }

    fun getProfiles(): Collection<Profile>
    {
        return profiles.values
    }

    fun getProfile(player: Player): Profile?
    {
        return profiles[player.uniqueId]
    }

    fun loadProfile(uuid: UUID)
    {
        this.profileLayer
            .load(uuid, DataStoreStorageType.MONGO)
            .thenAccept {
                this.profileLayer
                    .localCache()[uuid] = it ?: Profile(uuid)
            }
    }

    fun forgetProfile(profile: Profile)
    {
        this.profileLayer
            .localCache()
            .remove(profile.uuid)
    }

    fun saveProfile(profile: Profile): CompletableFuture<Unit>
    {
        return profileLayer.save(
            profile, DataStoreStorageType.MONGO
        ).thenApply {
            profile.requiresSave = false
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
