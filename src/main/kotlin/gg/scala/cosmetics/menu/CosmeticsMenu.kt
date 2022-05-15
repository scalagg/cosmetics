package gg.scala.cosmetics.menu

import gg.scala.cosmetics.cosmetic.category.CosmeticCategory
import net.evilblock.cubed.menu.Button
import net.evilblock.cubed.menu.buttons.BackButton
import net.evilblock.cubed.menu.pagination.PaginatedMenu
import net.evilblock.cubed.util.bukkit.Constants
import net.evilblock.cubed.util.bukkit.Tasks
import org.bukkit.ChatColor
import org.bukkit.entity.Player

class CosmeticsMenu(private val category: CosmeticCategory) : PaginatedMenu() {

    companion object {
        private val BUTTON_SLOTS = listOf(
            10, 11, 12, 13, 14, 15, 16,
            19, 20, 21, 22, 23, 24, 25,
            28, 29, 30, 31, 32, 33, 34
        )
    }

    init {
        updateAfterClick = true
    }

    override fun getPrePaginatedTitle(player: Player): String {
        return "Cosmetics ${Constants.DOUBLE_ARROW_RIGHT} ${ChatColor.stripColor(category.getName())}" // too lazy to go to each cosmetic and remove color lol
    }

    override fun getGlobalButtons(player: Player): Map<Int, Button> {
        return hashMapOf<Int, Button>().also { buttons ->
            buttons[49] = BackButton("Categories") { CategoriesMenu().openMenu(player) }
        }
    }

    override fun getAllPagesButtons(player: Player): Map<Int, Button> {
        return hashMapOf<Int, Button>().also { buttons ->
            for (cosmetic in category.getCosmetics()) {
                if (cosmetic.hasState()) {
                    buttons[buttons.size] = CosmeticButton(cosmetic)
                }
            }
        }
    }

    override fun getAllPagesButtonSlots(): List<Int> {
        return BUTTON_SLOTS
    }

    override fun getPageButtonSlots(): Pair<Int, Int> {
        return Pair(48, 50)
    }

    override fun size(buttons: Map<Int, Button>): Int {
        return 54
    }

    override fun onClose(player: Player, manualClose: Boolean) {
        if (manualClose) {
            Tasks.delayed(1L) {
                CategoriesMenu().openMenu(player)
            }
        }
    }

}
