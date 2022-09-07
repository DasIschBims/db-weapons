package dev.dasischbims.items.weapons

import org.bukkit.Particle
import org.bukkit.entity.LivingEntity
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.potion.PotionEffectType

object Sniper {
    fun handleClick(event: PlayerInteractEvent) {
        val player = event.player
        when (event.action) {
            Action.LEFT_CLICK_AIR, Action.LEFT_CLICK_BLOCK -> try {
                if (player.isSneaking && player.hasPotionEffect(PotionEffectType.SLOW)) {
                    val target = player.getTargetEntity(100)
                    if (target != null) {
                        if (target is LivingEntity) {
                            target.damage(100.0)
                        }
                    }

                    val eyeLocation = player.eyeLocation
                    val direction = eyeLocation.direction
                    for (i in 0..100) {
                        val location = eyeLocation.add(direction)
                        location.world.spawnParticle(Particle.SMOKE_NORMAL, location, 0, 0.0, 0.0, 0.0, 0.0)
                    }

                    player.world.playSound(player.location, "minecraft:block.anvil.place", 1f, 1f)
                }
            } catch (ignored: NullPointerException) {
            }
            Action.RIGHT_CLICK_AIR, Action.RIGHT_CLICK_BLOCK -> {
                if (player.isSneaking) {
                    player.addPotionEffects(listOf(
                        PotionEffectType.SLOW.createEffect(10, 225),
                    ))
                }
            }
            else -> return
        }
    }
}