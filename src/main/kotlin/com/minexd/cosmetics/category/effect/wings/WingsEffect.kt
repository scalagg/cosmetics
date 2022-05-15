package com.minexd.cosmetics.category.effect.wings

import com.minexd.cosmetics.cosmetic.TickableCosmetic
import com.minexd.cosmetics.profile.ProfileHandler
import net.evilblock.cubed.util.nms.MinecraftReflection
import net.evilblock.cubed.util.nms.particle.ParticleMeta
import net.evilblock.cubed.util.nms.particle.ParticleUtil
import org.bukkit.Color
import org.bukkit.entity.Player
import org.bukkit.Location

abstract class WingsEffect(
    private val color1: Color,
    private val color2: Color,
    private val color3: Color
) : TickableCosmetic() {

    private var lastTick: Long = System.currentTimeMillis()

    override fun getDescription(): String {
        return "A fancy wing effect!"
    }

    override fun tick(player: Player) {
        if (System.currentTimeMillis() - lastTick < 500L) {
            return
        }

        val profile = ProfileHandler.getProfile(player) ?: return
        if (System.currentTimeMillis() - profile.lastMovement < 500L) {
            return
        }

        lastTick = System.currentTimeMillis()

        ParticleUtil.sendsParticleToAll(player.world, *(generateParticles(player)).toTypedArray())
    }

    private fun generateParticles(player: Player): List<ParticleMeta> {
        return arrayListOf<ParticleMeta>().also { particles ->
            val ws = 75

            val wp = if (player.isFlying) {
                ws
            } else {
                75
            }

            var loc = player.location.clone()
            loc.pitch = 0.0f
            loc.add(0.0, 0.0, 0.0)
            loc.add(loc.direction.multiply(-0.2))
            loc.yaw = loc.yaw + wp

            var loc2 = loc.clone()

            particles.addAll(render(loc, loc2))

            loc = player.location.clone()
            loc.pitch = 0.0f
            loc.add(0.0, 0.0, 0.0)
            loc.add(loc.direction.multiply(-0.2))
            loc.yaw = loc.yaw - wp

            loc2 = loc.clone()

            particles.addAll(render(loc, loc2))
        }
    }

    private fun render(loc: Location, loc2: Location): List<ParticleMeta> {
        return arrayListOf<ParticleMeta>().also { particles ->
            var loc2 = loc2

            val particle = MinecraftReflection.getEnumParticle("REDSTONE")!!

            particles.add(ParticleMeta(loc2.clone().add(loc2.direction.multiply(-1.2000000000000002)), particle, color1, 0.0F, 0))
            particles.add(ParticleMeta(loc2.clone().add(loc2.direction.multiply(-1.4000000000000001)), particle, color1, 0.0F, 0))

            loc.add(0.0, 0.2, 0.0)
            loc2 = loc.clone()

            particles.add(ParticleMeta(loc2.clone().add(loc2.direction.multiply(-1.0)), particle, color1, 0.0F, 0))
            particles.add(ParticleMeta(loc2.clone().add(loc2.direction.multiply(-1.2000000000000002)), particle, color1, 0.0F, 0))
            particles.add(ParticleMeta(loc2.clone().add(loc2.direction.multiply(-1.4000000000000001)), particle, color1, 0.0F, 0))
            particles.add(ParticleMeta(loc2.clone().add(loc2.direction.multiply(-1.6)), particle, color1, 0.0F, 0))

            loc.add(0.0, 0.2, 0.0)
            loc2 = loc.clone()

            particles.add(ParticleMeta(loc2.clone().add(loc2.direction.multiply(-0.8)), particle, color1, 0.0F, 0))
            particles.add(ParticleMeta(loc2.clone().add(loc2.direction.multiply(-1.0)), particle, color1, 0.0F, 0))
            particles.add(ParticleMeta(loc2.clone().add(loc2.direction.multiply(-1.2000000000000002)), particle, color1, 0.0F, 0))
            particles.add(ParticleMeta(loc2.clone().add(loc2.direction.multiply(-1.4000000000000001)), particle, color1, 0.0F, 0))
            particles.add(ParticleMeta(loc2.clone().add(loc2.direction.multiply(-1.6)), particle, color1, 0.0F, 0))

            loc.add(0.0, 0.2, 0.0)
            loc2 = loc.clone()

            particles.add(ParticleMeta(loc2.clone().add(loc2.direction.multiply(-0.6000000000000001)), particle, color1, 0.0F, 0))
            particles.add(ParticleMeta(loc2.clone().add(loc2.direction.multiply(-0.8)), particle, color1, 0.0F, 0))
            particles.add(ParticleMeta(loc2.clone().add(loc2.direction.multiply(-1.0)), particle, color1, 0.0F, 0))
            particles.add(ParticleMeta(loc2.clone().add(loc2.direction.multiply(-1.2000000000000002)), particle, color2, 0.0F, 0))
            particles.add(ParticleMeta(loc2.clone().add(loc2.direction.multiply(-1.4000000000000001)), particle, color2, 0.0F, 0))
            particles.add(ParticleMeta(loc2.clone().add(loc2.direction.multiply(-1.6)), particle, color2, 0.0F, 0))

            loc.add(0.0, 0.2, 0.0)
            loc2 = loc.clone()

            particles.add(ParticleMeta(loc2.clone().add(loc2.direction.multiply(-0.4)), particle, color1, 0.0F, 0))
            particles.add(ParticleMeta(loc2.clone().add(loc2.direction.multiply(-0.6000000000000001)), particle, color1, 0.0F, 0))
            particles.add(ParticleMeta(loc2.clone().add(loc2.direction.multiply(-0.8)), particle, color1, 0.0F, 0))
            particles.add(ParticleMeta(loc2.clone().add(loc2.direction.multiply(-1.0)), particle, color2, 0.0F, 0))
            particles.add(ParticleMeta(loc2.clone().add(loc2.direction.multiply(-1.2000000000000002)), particle, color2, 0.0F, 0))
            particles.add(ParticleMeta(loc2.clone().add(loc2.direction.multiply(-1.4000000000000001)), particle, color2, 0.0F, 0))

            loc.add(0.0, 0.2, 0.0)
            loc2 = loc.clone()

            particles.add(ParticleMeta(loc2.clone().add(loc2.direction.multiply(-0.4)), particle, color1, 0.0F, 0))
            particles.add(ParticleMeta(loc2.clone().add(loc2.direction.multiply(-0.6000000000000001)), particle, color1, 0.0F, 0))
            particles.add(ParticleMeta(loc2.clone().add(loc2.direction.multiply(-0.8)), particle, color2, 0.0F, 0))
            particles.add(ParticleMeta(loc2.clone().add(loc2.direction.multiply(-1.0)), particle, color2, 0.0F, 0))
            particles.add(ParticleMeta(loc2.clone().add(loc2.direction.multiply(-1.2000000000000002)), particle, color2, 0.0F, 0))
            particles.add(ParticleMeta(loc2.clone().add(loc2.direction.multiply(-1.4000000000000001)), particle, color2, 0.0F, 0))

            loc.add(0.0, 0.2, 0.0)
            loc2 = loc.clone()

            particles.add(ParticleMeta(loc2.clone().add(loc2.direction.multiply(-0.4)), particle, color1, 0.0F, 0))
            particles.add(ParticleMeta(loc2.clone().add(loc2.direction.multiply(-0.6000000000000001)), particle, color1, 0.0F, 0))
            particles.add(ParticleMeta(loc2.clone().add(loc2.direction.multiply(-0.8)), particle, color2, 0.0F, 0))
            particles.add(ParticleMeta(loc2.clone().add(loc2.direction.multiply(-1.0)), particle, color2, 0.0F, 0))
            particles.add(ParticleMeta(loc2.clone().add(loc2.direction.multiply(-1.2000000000000002)), particle, color2, 0.0F, 0))
            particles.add(ParticleMeta(loc2.clone().add(loc2.direction.multiply(-1.4000000000000001)), particle, color2, 0.0F, 0))
            particles.add(ParticleMeta(loc2.clone().add(loc2.direction.multiply(-1.6)), particle, color2, 0.0F, 0))

            loc.add(0.0, 0.2, 0.0)
            loc2 = loc.clone()

            particles.add(ParticleMeta(loc2.clone().add(loc2.direction.multiply(-0.4)), particle, color1, 0.0F, 0))
            particles.add(ParticleMeta(loc2.clone().add(loc2.direction.multiply(-0.6000000000000001)), particle, color2, 0.0F, 0))
            particles.add(ParticleMeta(loc2.clone().add(loc2.direction.multiply(-0.8)), particle, color2, 0.0F, 0))
            particles.add(ParticleMeta(loc2.clone().add(loc2.direction.multiply(-1.0)), particle, color2, 0.0F, 0))
            particles.add(ParticleMeta(loc2.clone().add(loc2.direction.multiply(-1.2000000000000002)), particle, color2, 0.0F, 0))
            particles.add(ParticleMeta(loc2.clone().add(loc2.direction.multiply(-1.4000000000000001)), particle, color3, 0.0F, 0))
            particles.add(ParticleMeta(loc2.clone().add(loc2.direction.multiply(-1.6)), particle, color3, 0.0F, 0))

            loc.add(0.0, 0.2, 0.0)
            loc2 = loc.clone()

            particles.add(ParticleMeta(loc2.clone().add(loc2.direction.multiply(-0.4)), particle, color2, 0.0F, 0))
            particles.add(ParticleMeta(loc2.clone().add(loc2.direction.multiply(-0.6000000000000001)), particle, color2, 0.0F, 0))
            particles.add(ParticleMeta(loc2.clone().add(loc2.direction.multiply(-0.8)), particle, color2, 0.0F, 0))
            particles.add(ParticleMeta(loc2.clone().add(loc2.direction.multiply(-1.0)), particle, color2, 0.0F, 0))
            particles.add(ParticleMeta(loc2.clone().add(loc2.direction.multiply(-1.2000000000000002)), particle, color3, 0.0F, 0))
            particles.add(ParticleMeta(loc2.clone().add(loc2.direction.multiply(-1.4000000000000001)), particle, color3, 0.0F, 0))
            particles.add(ParticleMeta(loc2.clone().add(loc2.direction.multiply(-1.6)), particle, color3, 0.0F, 0))
            particles.add(ParticleMeta(loc2.clone().add(loc2.direction.multiply(-1.8)), particle, color3, 0.0F, 0))

            loc.add(0.0, 0.2, 0.0)
            loc2 = loc.clone()

            particles.add(ParticleMeta(loc2.clone().add(loc2.direction.multiply(-2.0)), particle, color3, 0.0F, 0))
            particles.add(ParticleMeta(loc2.clone().add(loc2.direction.multiply(-0.6000000000000001)), particle, color2, 0.0F, 0))
            particles.add(ParticleMeta(loc2.clone().add(loc2.direction.multiply(-0.8)), particle, color2, 0.0F, 0))
            particles.add(ParticleMeta(loc2.clone().add(loc2.direction.multiply(-1.0)), particle, color3, 0.0F, 0))
            particles.add(ParticleMeta(loc2.clone().add(loc2.direction.multiply(-1.2000000000000002)), particle, color3, 0.0F, 0))
            particles.add(ParticleMeta(loc2.clone().add(loc2.direction.multiply(-1.4000000000000001)), particle, color3, 0.0F, 0))
            particles.add(ParticleMeta(loc2.clone().add(loc2.direction.multiply(-1.6)), particle, color3, 0.0F, 0))
            particles.add(ParticleMeta(loc2.clone().add(loc2.direction.multiply(-1.8)), particle, color3, 0.0F, 0))

            loc.add(0.0, 0.2, 0.0)
            loc2 = loc.clone()

            particles.add(ParticleMeta(loc2.clone().add(loc2.direction.multiply(-1.0)), particle, color3, 0.0F, 0))
            particles.add(ParticleMeta(loc2.clone().add(loc2.direction.multiply(-1.2000000000000002)), particle, color3, 0.0F, 0))
            particles.add(ParticleMeta(loc2.clone().add(loc2.direction.multiply(-1.4000000000000001)), particle, color3, 0.0F, 0))
            particles.add(ParticleMeta(loc2.clone().add(loc2.direction.multiply(-1.6)), particle, color3, 0.0F, 0))
            particles.add(ParticleMeta(loc2.clone().add(loc2.direction.multiply(-1.8)), particle, color3, 0.0F, 0))
            particles.add(ParticleMeta(loc2.clone().add(loc2.direction.multiply(-2.0)), particle, color3, 0.0F, 0))
            particles.add(ParticleMeta(loc2.clone().add(loc2.direction.multiply(-2.2)), particle, color3, 0.0F, 0))

            loc.add(0.0, 0.2, 0.0)
            loc2 = loc.clone()

            particles.add(ParticleMeta(loc2.clone().add(loc2.direction.multiply(-1.4000000000000001)), particle, color3, 0.0F, 0))
            particles.add(ParticleMeta(loc2.clone().add(loc2.direction.multiply(-1.6)), particle, color3, 0.0F, 0))
            particles.add(ParticleMeta(loc2.clone().add(loc2.direction.multiply(-1.8)), particle, color3, 0.0F, 0))
            particles.add(ParticleMeta(loc2.clone().add(loc2.direction.multiply(-2.0)), particle, color3, 0.0F, 0))
            particles.add(ParticleMeta(loc2.clone().add(loc2.direction.multiply(-2.2)), particle, color3, 0.0F, 0))
        }
    }

}
