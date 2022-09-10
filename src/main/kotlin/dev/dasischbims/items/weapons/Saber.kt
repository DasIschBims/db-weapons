package dev.dasischbims.items.weapons

import org.bukkit.Material
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import org.bukkit.event.entity.EntityDamageByEntityEvent

object Saber {
    fun handleClick(event: EntityDamageByEntityEvent) {
        if (event.entity is Player) return
        if (event.damager is Player) {
            val player = event.damager as Player
            val entity = event.entity as LivingEntity
            val item = player.inventory.itemInMainHand
            if (item.itemMeta.customModelData == 1001 && item.type == Material.NETHERITE_SWORD) {
                val damage = event.damage
                entity.damage(damage + 5)
            } else return
        } else return
    }
}