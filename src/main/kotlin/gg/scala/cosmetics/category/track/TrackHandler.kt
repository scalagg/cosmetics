package gg.scala.cosmetics.category.track

import gg.scala.flavor.service.Configure
import gg.scala.flavor.service.Service
import gg.scala.store.controller.DataStoreObjectControllerCache
import gg.scala.store.storage.type.DataStoreStorageType

@Service
object TrackHandler
{
    private val tracks = mutableMapOf<String, Track>()

    @Configure
    fun configure()
    {
        val controller =
            DataStoreObjectControllerCache
                .create<Track>()

        controller.loadAll(DataStoreStorageType.MONGO)
            .thenAccept {
                this.tracks.putAll(
                    it.mapKeys { track ->
                        track.value.getRawID()
                    }
                )
            }
    }

    fun saveData()
    {
        this.tracks.forEach { (_, u) ->
            DataStoreObjectControllerCache
                .findNotNull<Track>().save(u)
        }
    }

    fun getTracks(): Collection<Track>
    {
        return tracks.values
    }

    fun getTrackById(id: String): Track?
    {
        return tracks[id.toLowerCase()]
    }

    fun trackTrack(track: Track)
    {
        tracks[track.getRawID().toLowerCase()] = track
    }

    fun forgetTrack(track: Track)
    {
        tracks.remove(track.getRawID().toLowerCase())
    }
}
