package gg.scala.cosmetics.category.track

import gg.scala.flavor.service.Configure
import gg.scala.flavor.service.Service
import gg.scala.store.controller.DataStoreObjectControllerCache
import gg.scala.store.storage.type.DataStoreStorageType
import java.util.*
import java.util.concurrent.CompletableFuture

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

    fun saveData(): CompletableFuture<Void>
    {
        return CompletableFuture.runAsync {
            this.tracks.forEach { (_, u) ->
                DataStoreObjectControllerCache
                    .findNotNull<Track>().save(u)
            }
        }
    }

    fun getTracks(): Collection<Track>
    {
        return tracks.values
    }

    fun getTrackById(id: String): Track?
    {
        return tracks[id.lowercase(Locale.getDefault())]
    }

    fun trackTrack(track: Track)
    {
        tracks[track.getRawID().lowercase(Locale.getDefault())] = track
    }

    fun forgetTrack(track: Track)
    {
        tracks.remove(track.getRawID().lowercase(Locale.getDefault()))
    }
}
