package gg.scala.cosmetics.category.emote.menu

import gg.scala.cosmetics.category.emote.EmotesBoxCosmetic
import gg.scala.cosmetics.menu.CategoriesMenu
import net.evilblock.cubed.menu.Button
import net.evilblock.cubed.menu.Menu
import net.evilblock.cubed.menu.buttons.BackButton
import net.evilblock.cubed.util.bukkit.Tasks
import org.bukkit.entity.Player

class EmoteBoxMenu(private val redirect: Boolean = false) : Menu() {

    override fun getTitle(player: Player): String {
        return "Emote Box"
    }

    override fun getButtons(player: Player): Map<Int, Button> {
        return hashMapOf<Int, Button>().also { buttons ->
            buttons[0] = BackButton { CategoriesMenu().openMenu(player) }

            for ((index, emote) in EmotesBoxCosmetic.emotes.withIndex()) {
                buttons[index] = EmoteButton(emote)
            }
        }
    }

    override fun onClose(player: Player, manualClose: Boolean) {
        if (manualClose) {
            if (redirect) {
                Tasks.delayed(1L) {
                    CategoriesMenu().openMenu(player)
                }
            }
        }
    }

}
