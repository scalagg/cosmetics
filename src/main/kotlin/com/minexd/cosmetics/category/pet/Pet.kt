package com.minexd.cosmetics.category.pet

import com.minexd.cosmetics.category.pet.impl.DogPet
import org.bukkit.ChatColor
import org.bukkit.inventory.ItemStack

/**
 * @author puugz
 * @since 16/10/2021 20:40
 */
interface Pet {

    fun getId(): String

    fun getDisplayName(): String

    fun getDescription(): List<String> {
        return arrayListOf("${ChatColor.GOLD}Have a ${ChatColor.RED}${DogPet.getDisplayName()} ${ChatColor.GOLD}as your pet.")
    }

    fun getIcon(): ItemStack

}