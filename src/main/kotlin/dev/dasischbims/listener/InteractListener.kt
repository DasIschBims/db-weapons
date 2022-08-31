package dev.dasischbims.listener

import dev.dasischbims.items.staffs.WindStaff
import dev.dasischbims.items.weapons.Sniper
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
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
                    }
                    event.isCancelled = true
                }
                Material.SPYGLASS -> {
                    when (cmd) {
                        1001 -> Sniper.handleClick(event)
                    }
                }
                else -> return
            }
        }
    }
}