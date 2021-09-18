package com.minexd.cosmetics.menu

import com.minexd.cosmetics.CosmeticsPlugin
import com.minexd.cosmetics.category.*
import net.evilblock.cubed.menu.Button
import net.evilblock.cubed.menu.Menu
import net.evilblock.cubed.util.math.Numbers
import net.evilblock.cubed.util.text.TextSplitter
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.entity.Player

class CategoriesMenu : Menu() {

    init {
        updateAfterClick = true
    }

    override fun getTitle(player: Player): String {
        return "Cosmetics"
    }

    override fun getButtons(player: Player): Map<Int, Button> {
        return hashMapOf<Int, Button>().also { buttons ->
            buttons[49] = InfoButton()

            buttons[10] = CategoryButton(OutfitsCategory)
            buttons[12] = CategoryButton(PetsCategory)
            buttons[14] = CategoryButton(EffectsCategory)
            buttons[16] = CategoryButton(TracksCategory)

//            buttons[29] = CategoryButton(EmotesCategory)
            buttons[31] = CategoryButton(EmotesCategory)
//            buttons[33] = CategoryButton(EmotesCategory)
        }
    }

    override fun size(buttons: Map<Int, Button>): Int {
        return 54
    }

    private inner class InfoButton : Button() {
        override fun getName(player: Player): String {
            return "${ChatColor.GREEN}Cosmetics"
        }

        override fun getDescription(player: Player): List<String> {
            return arrayListOf<String>().also { desc ->
                val collected = CosmeticsPlugin.instance.getCosmetics().filter { player.hasPermission(it.getPermission()) }.size
                val total = CosmeticsPlugin.instance.getCosmetics().size

                desc.add(buildString {
                    append("${ChatColor.GRAY}Collected: ")
                    append("${ChatColor.GREEN}${Numbers.format(collected)}")
                    append("${ChatColor.GRAY}/${Numbers.format(total)}")
                })

                desc.add("")
                desc.addAll(TextSplitter.split(text = "Collect cool and crazy cosmetics to show off in lobbies by winning games, unlocking achievements, and opening crates."))
                desc.add("")
                desc.add("${ChatColor.YELLOW}https://store.minexd.com")
            }
        }

        override fun getMaterial(player: Player): Material {
            return Material.CHEST
        }
    }

}