package gg.scala.cosmetics.menu

import com.cryptomorin.xseries.XMaterial
import gg.scala.cosmetics.CosmeticsPlugin
import gg.scala.cosmetics.category.*
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
            buttons[1] = InfoButton()

            buttons[3] = CategoryButton(OutfitsCategory)
            buttons[4] = CategoryButton(PetsCategory)
            buttons[5] = CategoryButton(EffectsCategory)
            buttons[6] = CategoryButton(EmotesCategory)
            buttons[7] = CategoryButton(TracksCategory)
        }
    }

    override fun size(buttons: Map<Int, Button>): Int {
        return 9
    }

    private inner class InfoButton : Button() {
        override fun getName(player: Player): String {
            return "${CC.YELLOW}Your Cosmetics"
        }

        override fun getDescription(player: Player): List<String> {
            return arrayListOf<String>().also { desc ->
                val collected = CosmeticsPlugin.instance
                    .getCosmetics()
                    .filter { player.hasPermission(it.getPermission()) }.size
                val total = CosmeticsPlugin.instance.getCosmetics().size

                desc.add(buildString {
                    append("${ChatColor.GRAY}Collected: ")
                    append("${ChatColor.GREEN}${Numbers.format(collected)}")
                    append("${ChatColor.GRAY}/${CC.WHITE}${Numbers.format(total)}")
                })

                desc.add("")
                desc.add("${CC.GRAY}Collect cool and crazy")
                desc.add("${CC.GRAY}cosmetics to show off!")
                desc.add("")
                desc.add("${CC.GRAY}Click one of the following")
                desc.add("${CC.GRAY}categories to view cosmetics.")
            }
        }

        override fun getMaterial(player: Player): XMaterial {
            return XMaterial.NETHER_STAR
        }
    }

}
