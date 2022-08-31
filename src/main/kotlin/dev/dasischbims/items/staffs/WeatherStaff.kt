package dev.dasischbims.items.staffs

import dev.dasischbims.particles.drawSmokeCircle
import org.bukkit.block.Block
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import java.util.*

object WeatherStaff {
    fun handleClick(event: PlayerInteractEvent) {
        val player = event.player
        when (event.action) {
            Action.RIGHT_CLICK_AIR, Action.RIGHT_CLICK_BLOCK -> try {
                val block: Block?
                try {
                    block = Objects.requireNonNull(player.rayTraceBlocks(100.0))!!.hitBlock
                    player.sendMessage("Block is at ${block!!.x}, ${block.y}, ${block.z}")
                    drawSmokeCircle(block.location, 5.0, 1)
                    block.world.playSound(block.location, "minecraft:block.powder_snow.place", 1.0f, 1.0f)
                    player.playSound(player.location, "minecraft:block.powder_snow.place", 1.0f, 1.0f)
                } catch (ignored: NullPointerException) {
                    return
                }
                val entities = block.location.getNearbyLivingEntities(5.0)
                for (entity in entities) {
                    entity.addPotionEffect(PotionEffect(PotionEffectType.LEVITATION, 20 * 5, 2))
                }
            } catch (ignored: NullPointerException) {
            }
            Action.LEFT_CLICK_AIR, Action.LEFT_CLICK_BLOCK -> try {
                val block: Block?
                try {
                    block = Objects.requireNonNull(player.rayTraceBlocks(15.0))!!.hitBlock
                    assert(block != null)
                } catch (ignored: NullPointerException) {
                    return
                }
                val entities = block!!.location.getNearbyLivingEntities(5.0)
                for (entity in entities) {
                    entity.addPotionEffect(PotionEffect(PotionEffectType.LEVITATION, 20 * 5, -1))
                }
            } catch (ignored: NullPointerException) {
            }
            else -> return
        }
    }
}