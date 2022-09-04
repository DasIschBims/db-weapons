package dev.dasischbims.items.staffs

import dev.dasischbims.INSTANCE
import dev.dasischbims.particles.drawSmokeCircle
import org.bukkit.Color
import org.bukkit.Location
import org.bukkit.Particle
import org.bukkit.block.Block
import org.bukkit.entity.AreaEffectCloud
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import org.bukkit.scheduler.BukkitRunnable
import java.util.*

object WindStaff {
    fun handleClick(event: PlayerInteractEvent) {
        val player = event.player
        when (event.action) {
            Action.RIGHT_CLICK_AIR, Action.RIGHT_CLICK_BLOCK -> try {
                val block: Block?
                try {
                    block = Objects.requireNonNull(player.rayTraceBlocks(20.0))!!.hitBlock
                    val loc = block!!.location.add(0.5, 0.5, 0.5)
                    drawSmokeCircle(loc, 5.0)
                    block.world.playSound(block.location, "minecraft:block.powder_snow.place", 1.0f, 1.0f)
                    player.playSound(player.location, "minecraft:block.powder_snow.place", 1.0f, 1.0f)
                } catch (ignored: NullPointerException) {
                    return
                }
                val entities = block.location.getNearbyLivingEntities(5.0)
                for (entity in entities) {
                    entity.addPotionEffect(PotionEffect(PotionEffectType.LEVITATION, 20 * 1, 25))
                }
            } catch (ignored: NullPointerException) {
            }
            Action.LEFT_CLICK_AIR, Action.LEFT_CLICK_BLOCK -> try {
                val block: Block?
                try {
                    block = Objects.requireNonNull(player.rayTraceBlocks(30.0))!!.hitBlock
                    val location = Location(block!!.world, block.x.toDouble(), block.y.toDouble() + 1, block.z.toDouble())
                    var time = 20 * 5
                    // remove one from time for every server tick
                    val task = object : BukkitRunnable() {
                        override fun run() {
                            if (time > 0) {
                                val entities = block.location.getNearbyLivingEntities(2.0)
                                for (entity in entities) {
                                    entity.addPotionEffect(PotionEffect(PotionEffectType.LEVITATION, 5, -1))
                                }
                                time--
                            } else {
                                cancel()
                            }
                        }
                    }
                    task.runTaskTimer(INSTANCE, 0L, 1L)

                    val areaEffectCloudShow = block.location.world!!.spawn(location, AreaEffectCloud::class.java)
                    areaEffectCloudShow.addCustomEffect(PotionEffect(PotionEffectType.SLOW, 20 * 1, 2), true)
                    areaEffectCloudShow.color = Color.fromRGB(255, 255, 255)
                    areaEffectCloudShow.particle = Particle.CLOUD
                    areaEffectCloudShow.reapplicationDelay = 1 / 20
                    areaEffectCloudShow.duration = 20 * 5
                    areaEffectCloudShow.radius = 2f
                    block.world.playSound(block.location, "minecraft:block.powder_snow.break", 1.0f, 1.0f)
                } catch (ignored: NullPointerException) {
                    return
                }
            } catch (ignored: NullPointerException) {
            }
            else -> return
        }
    }
}