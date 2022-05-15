package com.minexd.cosmetics

import com.minexd.cosmetics.category.*
import com.minexd.cosmetics.cosmetic.Cosmetic
import com.minexd.cosmetics.hook.DefaultCosmeticHook
import com.minexd.cosmetics.menu.CategoriesMenu
import com.minexd.cosmetics.thread.CosmeticsThread
import gg.scala.commons.ExtendedScalaPlugin
import gg.scala.commons.annotations.container.ContainerEnable
import gg.scala.commons.config.annotations.ContainerConfig
import me.lucko.helper.Events
import me.lucko.helper.plugin.ap.Plugin
import me.lucko.helper.plugin.ap.PluginDependency
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent

@Plugin(
    name = "Cosmetics",
    depends = [
        PluginDependency("scala-commons"),
        PluginDependency("Lemon"),
        PluginDependency("cloudsync", soft = true)
    ]
)
@ContainerConfig(
    value = "config",
    model = CosmeticsConfig::class
)
class CosmeticsPlugin : ExtendedScalaPlugin()
{
    companion object
    {
        @JvmStatic
        lateinit var instance: CosmeticsPlugin
    }

    var hook = DefaultCosmeticHook

    val categories = arrayListOf(
        OutfitsCategory,
        PetsCategory,
        EffectsCategory,
        EmotesCategory,
        TracksCategory
    )

    val cosmetics = mutableMapOf<String, Cosmetic>()

    @ContainerEnable
    fun containerEnable()
    {
        instance = this

        Events.subscribe(PlayerInteractEvent::class.java)
            .filter {
                it.item != null &&
                        it.action.name.contains("RIGHT") &&
                        it.item.hasItemMeta() &&
                        it.item.itemMeta.displayName.contains("Cosmetics")
            }
            .handler {
                CategoriesMenu().openMenu(it.player)
            }

        for (category in categories)
        {
            if (category is Listener)
            {
                server.pluginManager.registerEvents(category, this)
            }

            for (cosmetic in category.getCosmetics())
            {
                if (cosmetic is Listener)
                {
                    server.pluginManager.registerEvents(cosmetic, this)
                }

                cosmetics[cosmetic.getID().toLowerCase()] = cosmetic
            }
        }

        CosmeticsThread().start()
    }

    fun getCosmetics(): Collection<Cosmetic>
    {
        return cosmetics.values
    }

    fun getCosmeticById(id: String): Cosmetic?
    {
        return cosmetics[id.toLowerCase()]
    }

    fun isGameServer(): Boolean
    {
        return this.config<CosmeticsConfig>().gameServer
    }
}
