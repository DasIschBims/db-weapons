package dev.dasischbims.items.weapons

import org.bukkit.entity.Snowball
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent

object Sniper {
    fun handleClick(event: PlayerInteractEvent) {
        val player = event.player
        println(event.action)
        when (event.action) {
            Action.LEFT_CLICK_AIR, Action.LEFT_CLICK_BLOCK -> try {
                val snowball = player.world.spawn(player.eyeLocation, Snowball::class.java)
                snowball.velocity = player.location.direction.multiply(3)
                snowball.shooter = player
                player.world.playSound(player.location, "minecraft:entity.snowball.throw", 1f, 1f)
                // TODO: Finish, this is just a test
            } catch (ignored: NullPointerException) {
            }
            else -> return
        }
    }
}