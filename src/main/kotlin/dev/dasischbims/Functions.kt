package dev.dasischbims

import dev.dasischbims.items.CustomItems
import org.bukkit.Material
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

fun CommandSender.asPlayer() : Player = if (this is Player) this else throw Exception("Not a player")

fun itemStringToItem(input: String): ItemStack {
    return when {
        input.startsWith("mc:") -> {
            val item = input.removePrefix("mc:").uppercase()
            ItemStack(Material.valueOf(item))
        }
        input.startsWith("db:") -> {
            val item = input.removePrefix("db:")
            customItemsMap[item]!!
        }
        else -> {
            println("Could not find $input")
            CustomItems.defaultCustomItem(Material.DIRT, "Item not found: $input", mutableListOf())
        }
    }
}