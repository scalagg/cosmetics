package gg.scala.cosmetics.profile.listener

import gg.scala.cosmetics.category.OutfitsCategory
import gg.scala.cosmetics.profile.CosmeticsPlayerHandler
import gg.scala.commons.annotations.Listeners
import net.evilblock.cubed.util.bukkit.ItemUtils
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryType

@Listeners
object ProfileCosmeticListeners : Listener
{
    /**
     * Prevents players with an enabled armor/costume cosmetic from taking off their armor.
     */
    @EventHandler
    fun onInventoryClickEvent(event: InventoryClickEvent)
    {
        if (event.slotType == InventoryType.SlotType.ARMOR)
        {
            val profile = CosmeticsPlayerHandler.getProfile(event.whoClicked as Player)
            if (profile?.getEnabledCosmetics()?.any { it.getCategory() is OutfitsCategory } == true)
            {
                event.isCancelled = true
            }
        }
    }

    /**
     * Prevents dropping armor/costume cosmetic items when a player dies.
     */
    @EventHandler
    fun onPlayerDeathEvent(event: PlayerDeathEvent)
    {
        val profile = CosmeticsPlayerHandler.getProfile(event.entity)
        if (profile?.getEnabledCosmetics()?.any { it.getCategory() is OutfitsCategory } == true)
        {
            val iterator = event.drops.iterator()
            while (iterator.hasNext())
            {
                val itemStack = iterator.next()
                if (ItemUtils.isArmorEquipment(itemStack.type))
                {
                    iterator.remove()
                }
            }
        }
    }
}
