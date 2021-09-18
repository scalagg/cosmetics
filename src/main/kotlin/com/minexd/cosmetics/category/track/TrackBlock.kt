package com.minexd.cosmetics.category.track

import net.evilblock.cubed.util.bukkit.ColorUtil
import net.evilblock.cubed.util.bukkit.ItemUtils
import org.apache.commons.lang.WordUtils
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import org.bukkit.material.MaterialData

data class TrackBlock(
        val blockData: MaterialData,
        var chance: Double = 0.0
) {

    fun blockName(): String {
        if (blockData.itemType == Material.WOOL) {
            getWoolName(blockData.data) + " Wool"
        }

        val itemName = ItemUtils.getName(ItemStack(blockData.itemType, 1, blockData.data.toShort()))
        return if (blockData.data != 0.toByte()) {
            "$itemName (${blockData.data})"
        } else {
            itemName
        }
    }

    private fun getWoolName(data: Byte): String {
        val color = getChatColorByWoolId(data)
        return WordUtils.capitalizeFully((color?.name ?: "UNKNOWN").replace("_", " "))
    }

    private fun getChatColorByWoolId(data: Byte): ChatColor? {
        for ((key, value) in ColorUtil.CHAT_COLOR_TO_WOOL_DATA) {
            if (value == data.toInt()) {
                return key
            }
        }
        return null
    }

}