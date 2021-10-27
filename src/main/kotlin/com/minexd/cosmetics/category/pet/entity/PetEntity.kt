package com.minexd.cosmetics.category.pet.entity

import com.minexd.cosmetics.category.pet.Pet
import net.evilblock.cubed.entity.Entity
import org.bukkit.Location

/**
 * @author puugz
 * @since 16/10/2021 20:51
 */
class PetEntity(val pet: Pet, location: Location) : Entity(location) {

    // dont use cubed entity (no use in this)
    // just use the normal way of spawning entities

    override fun getTypeName(): String {
        return "Pet"
    }
}