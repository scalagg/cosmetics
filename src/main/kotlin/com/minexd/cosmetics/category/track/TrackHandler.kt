package com.minexd.cosmetics.category.track

import com.google.common.io.Files
import com.google.gson.reflect.TypeToken
import com.minexd.cosmetics.CosmeticsPlugin
import net.evilblock.cubed.serializers.Serializers
import java.io.File

object TrackHandler {

    private val dataFile = File(CosmeticsPlugin.instance.dataFolder, "tracks.json")
    private val dataType = object : TypeToken<List<Track>>() {}.type

    private val tracks: MutableMap<String, Track> = hashMapOf()

    fun initialLoad() {
        if (dataFile.exists()) {
            Files.newReader(dataFile, Charsets.UTF_8).use { reader ->
                for (track in Serializers.gson.fromJson(reader,
                    dataType
                ) as List<Track>) {
                    trackTrack(track)
                }
            }
        }
    }

    fun saveData() {
        Files.write(Serializers.gson.toJson(tracks.values),
            dataFile, Charsets.UTF_8)
    }

    fun getTracks(): Collection<Track> {
        return tracks.values
    }

    fun getTrackById(id: String): Track? {
        return tracks[id.toLowerCase()]
    }

    fun trackTrack(track: Track) {
        tracks[track.getRawID().toLowerCase()] = track
    }

    fun forgetTrack(track: Track) {
        tracks.remove(track.getRawID().toLowerCase())
    }

}