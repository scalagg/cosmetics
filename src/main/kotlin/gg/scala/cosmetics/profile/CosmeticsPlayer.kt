package gg.scala.cosmetics.profile

import gg.scala.cosmetics.CosmeticsPlugin
import gg.scala.cosmetics.category.track.TrackHandler
import gg.scala.cosmetics.category.track.player.PlayerTrack
import gg.scala.cosmetics.cosmetic.Cosmetic
import gg.scala.store.controller.annotations.Timestamp
import gg.scala.store.storage.storable.IDataStoreObject
import org.bukkit.Bukkit
import java.util.*

class CosmeticsPlayer(
    val uuid: UUID,
    override val identifier: UUID = uuid
) : IDataStoreObject
{
    private val cosmetics =
        mutableMapOf<String, Boolean>()

    private var track: String? = null

    @Timestamp
    var timestamp = 0L

    fun updateTrack(
        track: PlayerTrack?
    )
    {
        this.track =
            track?.track?.getRawID()
    }

    @Transient
    var transientPlayerTrack: PlayerTrack? = null

    fun track(): PlayerTrack?
    {
        if (this.track == null)
        {
            return null
        }

        if (transientPlayerTrack == null && this.track != null)
        {
            val trackId = TrackHandler
                .getTrackById(this.track!!)
                ?: return null

            this.transientPlayerTrack =
                PlayerTrack(trackId).apply {
                    this.cachedPlayer =
                        Bukkit.getPlayer(uuid)
                }
        }

        return this.transientPlayerTrack
    }

    @Transient
    var requiresSave = false

    @Transient
    var lastMovement = System
        .currentTimeMillis()

    fun getEnabledCosmetics(): List<Cosmetic>
    {
        return cosmetics
            .filter { it.value }
            .mapNotNull {
                CosmeticsPlugin.instance
                    .getCosmeticById(it.key)
            }
    }

    fun isCosmeticEnabled(cosmetic: Cosmetic): Boolean
    {
        return cosmetics
            .getOrDefault(
                cosmetic.getID(), false
            )
    }

    fun toggleCosmetic(cosmetic: Cosmetic)
    {
        if (cosmetics.containsKey(cosmetic.getID()))
        {
            cosmetics[cosmetic.getID()] =
                !cosmetics[cosmetic.getID()]!!
        } else
        {
            cosmetics[cosmetic.getID()] = true
        }

        requiresSave = true
    }
}
