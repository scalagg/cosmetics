package com.minexd.cosmetics.profile

import com.google.gson.reflect.TypeToken
import com.mongodb.client.MongoCollection
import com.mongodb.client.model.ReplaceOptions
import com.minexd.cosmetics.Cosmetic
import com.minexd.cosmetics.CosmeticsPlugin
import com.minexd.cosmetics.event.PlayerToggleCosmeticEvent
import net.evilblock.cubed.Cubed
import net.evilblock.cubed.serializers.Serializers
import org.bson.Document
import org.bson.json.JsonMode
import org.bson.json.JsonWriterSettings
import org.bukkit.entity.Player
import java.lang.reflect.Type
import java.util.*

object ProfileHandler {

    /**
     * The mongo collection where profile documents are stored.
     */
    private lateinit var mongoCollection: MongoCollection<Document>

    /**
     * The cache for loaded profiles.
     */
    private val profiles: MutableMap<UUID, Profile> = hashMapOf()

    fun initialLoad() {
        mongoCollection = Cubed.instance.mongo.client
            .getDatabase(CosmeticsPlugin.instance.getStorageDatabase())
            .getCollection(CosmeticsPlugin.instance.getStorageCollection())
    }

    fun getCollection(): MongoCollection<Document> {
        return mongoCollection
    }

    fun getProfiles(): Collection<Profile> {
        return profiles.values
    }

    fun getProfile(player: Player): Profile? {
        return profiles[player.uniqueId]
    }

    fun loadProfile(uuid: UUID) {
        val profile = fetchProfile(uuid)
        profiles[uuid] = profile
    }

    fun fetchProfile(uuid: UUID): Profile {
        val document = mongoCollection.find(Document("uuid", uuid.toString())).first() ?: return Profile(uuid)

        return deserializeDocument(document).also {
            it.lastMovement = System.currentTimeMillis()
        }
    }

    fun forgetProfile(profile: Profile) {
        profiles.remove(profile.uuid)
    }

    fun saveProfile(profile: Profile) {
        mongoCollection.replaceOne(Document("uuid", profile.uuid.toString()), Document.parse(Serializers.gson.toJson(profile)), ReplaceOptions().upsert(true))
        profile.requiresSave = false
    }

    private val JSON_WRITER_SETTINGS = JsonWriterSettings.builder().outputMode(JsonMode.RELAXED).build()
    private val PROFILE_TYPE: Type = object : TypeToken<Profile>() {}.type

    private fun deserializeDocument(document: Document): Profile {
        return Serializers.gson.fromJson(document.toJson(JSON_WRITER_SETTINGS), PROFILE_TYPE) as Profile
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