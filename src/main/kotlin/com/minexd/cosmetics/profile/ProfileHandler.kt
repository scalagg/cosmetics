package com.minexd.cosmetics.profile

import com.google.gson.reflect.TypeToken
import com.mongodb.client.MongoCollection
import com.mongodb.client.model.ReplaceOptions
import com.minexd.cosmetics.Cosmetic
import com.minexd.cosmetics.CosmeticsPlugin
import com.minexd.cosmetics.event.PlayerToggleCosmeticEvent
import com.mongodb.MongoClient
import com.mongodb.MongoClientURI
import com.solexgames.datastore.commons.connection.impl.mongo.UriMongoConnection
import com.solexgames.datastore.commons.layer.impl.MongoStorageLayer
import com.solexgames.datastore.commons.storage.impl.MongoStorageBuilder
import gg.scala.lemon.Lemon
import gg.scala.lemon.player.LemonPlayer
import gg.scala.lemon.player.grant.Grant
import net.evilblock.cubed.Cubed
import net.evilblock.cubed.serializers.Serializers
import org.bson.Document
import org.bson.json.JsonMode
import org.bson.json.JsonWriterSettings
import org.bukkit.entity.Player
import java.lang.reflect.Type
import java.util.*
import java.util.concurrent.CompletableFuture

object ProfileHandler {

    /**
     * The mongo collection where profile documents are stored.
     */
    lateinit var profileLayer: MongoStorageLayer<Profile>

    /**
     * The cache for loaded profiles.
     */
    private val profiles: MutableMap<UUID, Profile> = hashMapOf()

    fun initialLoad() {
        val mongoConnection = UriMongoConnection(Lemon.instance.mongoConfig.uri)
        val database = Lemon.instance.mongoConfig.database

        this.profileLayer = MongoStorageBuilder<Profile>()
            .setDatabase(database).setCollection("cosmetics_players")
            .setConnection(mongoConnection).setType(Profile::class.java).build()

        this.profileLayer.supplyWithCustomGson(Serializers.gson)
    }

    fun getProfiles(): Collection<Profile> {
        return profiles.values
    }

    fun getProfile(player: Player): Profile? {
        return profiles[player.uniqueId]
    }

    fun loadProfile(uuid: UUID) {
        fetchProfile(uuid).thenAccept {
            profiles[uuid] = it ?: Profile(uuid)
        }
    }

    fun fetchProfile(uuid: UUID): CompletableFuture<Profile?> {
        return profileLayer.fetchEntryByKey(uuid.toString())
    }

    fun forgetProfile(profile: Profile) {
        profiles.remove(profile.uuid)
    }

    fun saveProfile(profile: Profile): CompletableFuture<Unit> {
        return profileLayer.saveEntry(
            profile.uuid.toString(), profile
        ).thenApply {
            profile.requiresSave = false
        }
    }

    fun isCosmeticEnabled(player: Player, cosmetic: Cosmetic): Boolean {
        val profile = profiles[player.uniqueId]
        return profile?.isCosmeticEnabled(cosmetic) ?: false
    }

    fun toggleCosmetic(player: Player, cosmetic: Cosmetic) {
        val profile = profiles[player.uniqueId]
        if (profile != null) {
            if (!profile.isCosmeticEnabled(cosmetic)) {
                if (!cosmetic.canEnable(player)) {
                    return
                }
            }

            profile.toggleCosmetic(cosmetic)

            if (profile.isCosmeticEnabled(cosmetic)) {
                cosmetic.onEnable(player)
            } else {
                cosmetic.onDisable(player)
            }

            PlayerToggleCosmeticEvent(
                player,
                cosmetic,
                profile.isCosmeticEnabled(cosmetic)
            ).call()

            // disable other cosmetics in the same category
            for (category in CosmeticsPlugin.instance.categories) {
                if (!category.getCosmetics().contains(cosmetic)) {
                    continue
                }

                for (enabledCheck in category.getCosmetics()) {
                    if (enabledCheck != cosmetic && profile.isCosmeticEnabled(enabledCheck)) {
                        profile.toggleCosmetic(enabledCheck)
                    }
                }
            }
        }
    }

}
