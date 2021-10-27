package com.minexd.cosmetics.category.pet.impl

import com.minexd.cosmetics.category.pet.Pet
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

/**
 * @author puugz
 * @since 16/10/2021 20:45
 */
object DogPet : Pet {

    override fun getId(): String {
        return "dog"
    }

    override fun getDisplayName(): String {
        return "Dog"
    }

    override fun getIcon(): ItemStack {
        return ItemStack(Material.BONE)
    }
}