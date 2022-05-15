package com.minexd.cosmetics.menu

import com.cryptomorin.xseries.XMaterial
import com.minexd.cosmetics.CosmeticsPlugin
import com.minexd.cosmetics.category.*
import net.evilblock.cubed.menu.Button
import net.evilblock.cubed.menu.Menu
import net.evilblock.cubed.util.CC
import net.evilblock.cubed.util.bukkit.Constants
import net.evilblock.cubed.util.math.Numbers
import net.evilblock.cubed.util.text.TextSplitter
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.entity.Player

class CategoriesMenu : Menu() {

    init {
        placeholder = true
        updateAfterClick = true
    }

    override fun getTitle(player: Player): String {
        return "Cosmetics ${Constants.DOUBLE_ARROW_RIGHT} Main"
    }

    override fun getButtons(player: Player): Map<Int, Button> {
        return hashMapOf<Int, Button>().also { buttons ->
            buttons[10] = InfoButton()

            buttons[12] = CategoryButton(OutfitsCategory)
            buttons[13] = CategoryButton(PetsCategory)
            buttons[14] = CategoryButton(EffectsCategory)
            buttons[15] = CategoryButton(EmotesCategory)
            buttons[16] = CategoryButton(TracksCategory)
        }
    }

    override fun size(buttons: Map<Int, Button>): Int {
        return 27
    }

    private inner class InfoButton : Button() {
        override fun getName(player: Player): String {
            return "${CC.B_PRI}Your Cosmetics"
        }

        override fun getDescription(player: Player): List<String> {
            return arrayListOf<String>().also { desc ->
                val collected = CosmeticsPlugin.instance.getCosmetics().filter { player.hasPermission(it.getPermission()) }.size
                val total = CosmeticsPlugin.instance.getCosmetics().size

                desc.add(buildString {
                    append("${ChatColor.GRAY}Collected: ")
                    append("${ChatColor.GREEN}${Numbers.format(collected)}")
                    append("${ChatColor.GRAY}/${CC.WHITE}${Numbers.format(total)}")
                })

                desc.add("")
                desc.add("${CC.GRAY}Collect cool and crazy")
                desc.add("${CC.GRAY}cosmetics to show off in")
                desc.add("${CC.GRAY}lobbies by winning games,")
                desc.add("${CC.GRAY}unlocking achievements,")
                desc.add("${CC.GRAY}and opening crates!")
                desc.add("")
                desc.add("${CC.YELLOW}Click a button to view")
                desc.add("${CC.YELLOW}our available cosmetics.")
            }
        }

        override fun getMaterial(player: Player): XMaterial {
            return XMaterial.NETHER_STAR
        }
    }

}
