package gg.scala.cosmetics.category.effect

import gg.scala.cosmetics.cosmetic.category.CosmeticCategory
import gg.scala.cosmetics.cosmetic.TickableCosmetic
import gg.scala.cosmetics.category.EffectsCategory
import net.evilblock.cubed.util.nms.MinecraftReflection
import net.evilblock.cubed.util.nms.particle.ParticleMeta
import net.evilblock.cubed.util.nms.particle.ParticleUtil
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.Listener
import org.bukkit.inventory.ItemStack
import java.util.concurrent.ThreadLocalRandom

object BubblesEffect : TickableCosmetic(), Listener {

    override fun getCategory(): CosmeticCategory
    {
        return EffectsCategory
    }

    override fun getID(): String {
        return "bubbles"
    }

    override fun getName(): String {
        return "Bubbles"
    }

    override fun getDescription(): String {
        return "Surround yourself with bubbles."
    }

    override fun getPermission(): String {
        return "cosmetics.bubbles"
    }

    override fun getIcon(): ItemStack {
        return ItemStack(Material.CLAY_BALL)
    }

    override fun tick(player: Player) {
        var ticks = ticks.putIfAbsent(player.uniqueId, 0) ?: 0
        ticks++

        val location = player.location.clone()
        location.x = ThreadLocalRandom.current().nextDouble(location.x - 0.33, location.x + 0.33)
        location.y = ThreadLocalRandom.current().nextDouble(location.y, player.eyeLocation.y)
        location.z = ThreadLocalRandom.current().nextDouble(location.z - 0.33, location.z + 0.33)

        ParticleUtil.sendsParticleToAll(location.world,
            ParticleMeta(
                location,
                MinecraftReflection.getEnumParticle("SPELL_MOB_AMBIENT")!!,
                0F,
                0F,
                0F,
                1F,
                10
            )
        )

        if (ticks >= 6) {
            ticks = 0
        }

        this.ticks[player.uniqueId] = ticks
    }

}
