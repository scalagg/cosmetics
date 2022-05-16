package gg.scala.cosmetics.category.track.menu

import com.cryptomorin.xseries.XMaterial
import gg.scala.cosmetics.category.track.Track
import gg.scala.cosmetics.category.track.TrackHandler
import gg.scala.cosmetics.category.track.player.PlayerTrack
import gg.scala.cosmetics.menu.CategoriesMenu
import gg.scala.cosmetics.profile.CosmeticsPlayerHandler
import net.evilblock.cubed.menu.Button
import net.evilblock.cubed.menu.buttons.BackButton
import net.evilblock.cubed.menu.pagination.PaginatedMenu
import net.evilblock.cubed.util.CC
import net.evilblock.cubed.util.bukkit.Constants
import net.evilblock.cubed.util.bukkit.Tasks
import net.evilblock.cubed.util.bukkit.enchantment.GlowEnchantment
import net.evilblock.cubed.util.text.TextSplitter
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import org.bukkit.inventory.InventoryView
import org.bukkit.inventory.ItemStack

class TracksMenu(
    val redirect: Boolean = false
) : PaginatedMenu() {

    companion object {
        private val BUTTON_SLOTS = listOf(
            10, 11, 12, 13, 14, 15, 16,
            19, 20, 21, 22, 23, 24, 25
        )
    }

    init {
        updateAfterClick = true
        placeholdBorders = true
    }

    override fun getPrePaginatedTitle(player: Player): String {
        return "Cosmetics ${Constants.DOUBLE_ARROW_RIGHT} Tracks"
    }

    override fun getAllPagesButtons(player: Player): Map<Int, Button> {
        return hashMapOf<Int, Button>().also { buttons ->
            for (track in TrackHandler.getTracks()) {
                buttons[buttons.size] = TrackButton(track)
            }
        }
    }

    override fun getAllPagesButtonSlots(): List<Int> {
        return BUTTON_SLOTS
    }

    override fun getPageButtonSlots(): Pair<Int, Int>
    {
        return Pair(0, 8)
    }

    override fun size(buttons: Map<Int, Button>): Int {
        return 36
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

    private inner class TrackButton(private val track: Track) : Button() {
        override fun getName(player: Player): String {
            return track.getName()
        }

        override fun getDescription(player: Player): List<String> {
            return arrayListOf<String>().also { desc ->
                desc.addAll(
                    TextSplitter.split(
                        linePrefix = CC.GRAY,
                        text = track.getDescription()
                    )
                )

                desc.add("")

                val playerTrack = CosmeticsPlayerHandler.getProfile(player)?.track()

                when {
                    playerTrack != null && playerTrack.track == track -> {
                        desc.add("${ChatColor.GOLD}Click to disable this track!")
                    }
                    player.hasPermission(track.getPermission()) -> {
                        desc.add("${ChatColor.GREEN}Click to enable this track!")
                    }
                    else -> {
                        desc.add("${ChatColor.RED}This track is locked!")
                    }
                }
            }
        }

        override fun getMaterial(player: Player): XMaterial {
            return XMaterial
                .matchXMaterial(
                    Material.STAINED_GLASS_PANE
                )
        }

        override fun getDamageValue(player: Player): Byte {
            return if (player.hasPermission(track.getPermission())) {
                5
            } else {
                14
            }
        }

        override fun getButtonItem(player: Player): ItemStack {
            return super.getButtonItem(player).also {
                val playerTrack = CosmeticsPlayerHandler.getProfile(player)?.track()
                if (playerTrack != null && playerTrack.track == track) {
                    GlowEnchantment.addGlow(it)
                }
            }
        }

        override fun clicked(player: Player, slot: Int, clickType: ClickType, view: InventoryView) {
            val profile = CosmeticsPlayerHandler.getProfile(player)
            if (profile == null) {
                player.sendMessage("${ChatColor.RED}Whoops! That shouldn't have happened...")
                player.sendMessage("${ChatColor.RED}(We couldn't fetch your cosmetics data)")
                return
            }

            if (!player.hasPermission(track.getPermission())) {
                player.sendMessage("${ChatColor.RED}You haven't unlocked that track!")
                return
            }

            if (clickType.isLeftClick) {
                profile.track()?.clearChanges()

                if (profile.track() != null && profile.track()!!.track == track) {
                    profile.updateTrack(null)
                } else {
                    val track = PlayerTrack(track)
                    track.cachedPlayer = player

                    profile.updateTrack(track)
                }

                profile.requiresSave = true
            }
        }
    }

}
