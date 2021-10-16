package com.minexd.cosmetics.category.pet

import org.bukkit.inventory.ItemStack

/**
 * @author puugz
 * @since 16/10/2021 20:40
 */
interface Pet {

    fun getId(): String

    fun getDisplayName(): String

    fun getDescription(): List<String>

    fun getIcon(): ItemStack

}