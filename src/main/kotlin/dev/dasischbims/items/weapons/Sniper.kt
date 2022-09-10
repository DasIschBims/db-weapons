package dev.dasischbims.items.weapons

import org.bukkit.Material
import org.bukkit.Particle
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import org.bukkit.event.block.Action
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.meta.Damageable
import org.bukkit.potion.PotionEffectType

object Sniper {
    fun handleClick(event: PlayerInteractEvent) {
        val player = event.player
        val durability = (player.inventory.itemInMainHand.itemMeta as Damageable).damage
        if (durability >= player.inventory.itemInMainHand.type.maxDurability) return
        when (event.action) {
            Action.LEFT_CLICK_AIR, Action.LEFT_CLICK_BLOCK -> try {
                if (player.isSneaking && player.hasPotionEffect(PotionEffectType.SPEED)) {
                    val target = player.getTargetEntity(100)
                    if (target != null) {
                        if (target is LivingEntity) {
                            if (target is Player) {
                                target.damage(12.0, player)
                                if (player.gameMode != org.bukkit.GameMode.CREATIVE) {
                                    player.inventory.itemInMainHand.itemMeta =
                                        (player.inventory.itemInMainHand.itemMeta as Damageable).apply { damage += 2 }
                                }
                            } else {
                                target.damage(35.0, player)
                                player.inventory.itemInMainHand.itemMeta =
                                    (player.inventory.itemInMainHand.itemMeta as Damageable).apply { damage += 2 }
                            }
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
                        PotionEffectType.SPEED.createEffect(10, -20)
                    ))
                }
            }
            else -> return
        }
    }
    fun handleDamage(event: EntityDamageByEntityEvent) {
        if (event.damager is Player) {
            val player = event.damager as Player
            if (player.isSneaking && player.hasPotionEffect(PotionEffectType.SPEED)) return
                val item = player.inventory.itemInMainHand
            if (item.itemMeta.customModelData == 1004 && item.type == Material.WOODEN_HOE) {
                item.itemMeta = (item.itemMeta as Damageable).apply {
                    damage -= 2
                }
            }
        }
    }
    fun handleBlock(event: PlayerInteractEvent) {
        val item = event.player.inventory.itemInMainHand
        if (item.itemMeta.customModelData == 1004 && item.type == Material.WOODEN_HOE) {
            event.isCancelled = true
        }
    }
}