package dev.dasischbims.listener

import dev.dasischbims.items.staffs.WindStaff
import dev.dasischbims.items.weapons.Saber
import dev.dasischbims.items.weapons.Sniper
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.player.PlayerInteractEvent

class InteractListener: Listener {

    @EventHandler
    fun onPlayerInteract(event: PlayerInteractEvent) {
        val player = event.player

        val itemStack = player.inventory.itemInMainHand
        val type = itemStack.type
        if (itemStack.hasItemMeta() && itemStack.itemMeta.hasCustomModelData()) {
            val cmd = itemStack.itemMeta.customModelData
            when (type) {
                Material.WOODEN_HOE -> {
                    when (cmd) {
                        1001 -> {
                            player.sendMessage("You used a fire staff")
                        }
                        1002 -> {
                            player.sendMessage("You used a water staff")
                        }
                        1003 -> WindStaff.handleClick(event)
                        1004 -> {
                            Sniper.handleClick(event)
                            Sniper.handleBlock(event)
                        }
                    }
                    event.isCancelled = true
                }
                else -> return
            }
        }
    }

    @EventHandler
    fun onEntityDamage(event: EntityDamageByEntityEvent) {
        if (event.damager is Player) {
            val damager = event.damager as Player
            val itemStack = damager.inventory.itemInMainHand
            val type = itemStack.type
            if (itemStack.hasItemMeta() && itemStack.itemMeta.hasCustomModelData()) {
                val cmd = itemStack.itemMeta.customModelData
                when (type) {
                    Material.NETHERITE_SWORD -> {
                        when (cmd) {
                            1001 -> Saber.handleClick(event)
                        }
                    }
                    Material.WOODEN_HOE -> {
                        when (cmd) {
                            1004 -> {
                                Sniper.handleDamage(event)
                            }
                        }
                    }
                    else -> return
                }
            }
        }
    }
}