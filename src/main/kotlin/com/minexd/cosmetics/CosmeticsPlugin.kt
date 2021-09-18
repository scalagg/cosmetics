package com.minexd.cosmetics

import com.minexd.cosmetics.category.*
import com.minexd.cosmetics.category.emote.command.EmoteCommand
import com.minexd.cosmetics.category.track.TrackHandler
import com.minexd.cosmetics.category.track.command.TracksCommand
import com.minexd.cosmetics.category.track.command.TracksEditorCommand
import com.minexd.cosmetics.profile.listener.ProfileTrackListeners
import com.minexd.cosmetics.command.CosmeticsCommand
import com.minexd.cosmetics.command.CosmeticsListCommand
import com.minexd.cosmetics.command.ReloadCommand
import com.minexd.cosmetics.command.WipeCommand
import com.minexd.cosmetics.hook.Hook
import com.minexd.cosmetics.hook.NoHook
import com.minexd.cosmetics.listener.CosmeticsListeners
import com.minexd.cosmetics.profile.listener.ProfileCosmeticListeners
import com.minexd.cosmetics.profile.ProfileHandler
import com.minexd.cosmetics.profile.listener.ProfileLoadListeners
import com.minexd.cosmetics.thread.CosmeticsThread
import net.evilblock.cubed.command.CommandHandler
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin

class CosmeticsPlugin : JavaPlugin() {

    companion object {
        @JvmStatic
        lateinit var instance: CosmeticsPlugin
    }

    var hook: Hook = NoHook()

    val categories: List<CosmeticCategory> = arrayListOf(
        OutfitsCategory,
        PetsCategory,
        EffectsCategory,
        EmotesCategory,
        TracksCategory
    )

    val cosmetics: MutableMap<String, Cosmetic> = hashMapOf()

    override fun onEnable() {
        instance = this

        saveDefaultConfig()

        CommandHandler.registerClass(ReloadCommand.javaClass)
        CommandHandler.registerClass(WipeCommand.javaClass)
        CommandHandler.registerClass(CosmeticsCommand.javaClass)
        CommandHandler.registerClass(CosmeticsListCommand.javaClass)
        CommandHandler.registerClass(EmoteCommand.javaClass)
        CommandHandler.registerClass(TracksCommand.javaClass)
        CommandHandler.registerClass(TracksEditorCommand.javaClass)

        server.pluginManager.registerEvents(ProfileLoadListeners, this)
        server.pluginManager.registerEvents(ProfileCosmeticListeners, this)
        server.pluginManager.registerEvents(ProfileTrackListeners, this)
        server.pluginManager.registerEvents(CosmeticsListeners, this)

        ProfileHandler.initialLoad()
        TrackHandler.initialLoad()

        for (category in categories) {
            if (category is Listener) {
                server.pluginManager.registerEvents(category, this)
            }

            for (cosmetic in category.getCosmetics()) {
                if (cosmetic is Listener) {
                    server.pluginManager.registerEvents(cosmetic, this)
                }

                cosmetics[cosmetic.getID().toLowerCase()] = cosmetic
            }
        }

        CosmeticsThread().start()
    }

    fun getCosmetics(): Collection<Cosmetic> {
        return cosmetics.values
    }

    fun getCosmeticById(id: String): Cosmetic? {
        return cosmetics[id.toLowerCase()]
    }

    fun getStorageDatabase(): String {
        return config.getString("storage.database", "Cosmetics")
    }

    fun getStorageCollection(): String {
        return config.getString("storage.collection", "profiles")
    }

    fun isGameServer(): Boolean {
        return config.getBoolean("game-server", true)
    }

}