package gg.scala.cosmetics

import gg.scala.cosmetics.category.*
import gg.scala.cosmetics.cosmetic.Cosmetic
import gg.scala.cosmetics.hook.DefaultCosmeticHook
import gg.scala.cosmetics.menu.CategoriesMenu
import gg.scala.cosmetics.thread.CosmeticsThread
import gg.scala.commons.ExtendedScalaPlugin
import gg.scala.commons.annotations.container.ContainerEnable
import gg.scala.commons.annotations.custom.CustomAnnotationProcessors
import gg.scala.commons.config.annotations.ContainerConfig
import gg.scala.cosmetics.cosmetic.category.CosmeticCategory
import gg.scala.cosmetics.hook.CosmeticHook
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

    lateinit var hook: CosmeticHook

    val categories = mutableListOf<CosmeticCategory>()
    val cosmetics = mutableMapOf<String, Cosmetic>()

    @ContainerEnable
    fun containerEnable()
    {
        instance = this

        this.packageIndexer
            .getSubTypes<CosmeticCategory>()
            .mapNotNull { it.objectInstance() }
            .forEach {
                this.categories.add(it as CosmeticCategory)
            }

        this.hook = this.contract<CosmeticHook>()!!

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
