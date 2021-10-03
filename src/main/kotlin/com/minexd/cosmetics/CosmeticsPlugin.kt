package com.minexd.cosmetics

import com.minexd.cosmetics.category.*
import com.minexd.cosmetics.category.emote.Emote
import com.minexd.cosmetics.category.emote.EmotesBoxCosmetic
import com.minexd.cosmetics.category.emote.command.EmoteCommand
import com.minexd.cosmetics.category.track.TrackHandler
import com.minexd.cosmetics.category.track.command.TracksCommand
import com.minexd.cosmetics.command.CosmeticsCommand
import com.minexd.cosmetics.hook.Hook
import com.minexd.cosmetics.hook.NoHook
import com.minexd.cosmetics.listener.CosmeticsListeners
import com.minexd.cosmetics.menu.CategoriesMenu
import com.minexd.cosmetics.profile.ProfileHandler
import com.minexd.cosmetics.profile.listener.ProfileCosmeticListeners
import com.minexd.cosmetics.profile.listener.ProfileLoadListeners
import com.minexd.cosmetics.profile.listener.ProfileTrackListeners
import com.minexd.cosmetics.thread.CosmeticsThread
import gg.scala.lemon.Lemon
import me.lucko.helper.Events
import me.lucko.helper.plugin.ExtendedJavaPlugin
import net.evilblock.cubed.acf.ConditionFailedException
import net.evilblock.cubed.command.manager.CubedCommandManager
import net.evilblock.cubed.util.CC
import org.bukkit.ChatColor
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent

class CosmeticsPlugin : ExtendedJavaPlugin() {

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

    override fun enable() {
        instance = this

        saveDefaultConfig()

        val cubedCommandManager = CubedCommandManager(
            plugin = this,
            primary = ChatColor.valueOf(Lemon.instance.lemonWebData.primary),
            secondary = ChatColor.valueOf(Lemon.instance.lemonWebData.secondary)
        )

        Lemon.instance.registerCompletionsAndContexts(cubedCommandManager)

        cubedCommandManager.commandContexts.registerContext(Emote::class.java) {
            var matchingEmote: Emote? = null
            val firstArg = it.popFirstArg()

            for (emote in EmotesBoxCosmetic.emotes) {
                if (emote.getId().equals(firstArg, ignoreCase = true)) {
                    matchingEmote = emote
                    break
                }
            }

            if (matchingEmote == null) {
                throw ConditionFailedException("No emote matching ${CC.YELLOW}${firstArg}${CC.RED} exists.")
            }

            return@registerContext matchingEmote
        }

        cubedCommandManager.registerCommand(EmoteCommand())
        cubedCommandManager.registerCommand(CosmeticsCommand())

//        cubedCommandManager.registerCommand(TracksCommand())

        server.pluginManager.registerEvents(ProfileLoadListeners, this)
        server.pluginManager.registerEvents(ProfileCosmeticListeners, this)
        server.pluginManager.registerEvents(ProfileTrackListeners, this)
        server.pluginManager.registerEvents(CosmeticsListeners, this)

        Events.subscribe(PlayerInteractEvent::class.java)
            .filter { it.item != null && it.action.name.contains("RIGHT") && it.item.hasItemMeta() && it.item.itemMeta.displayName.contains("Cosmetics") }
            .handler { CategoriesMenu().openMenu(it.player) }

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
