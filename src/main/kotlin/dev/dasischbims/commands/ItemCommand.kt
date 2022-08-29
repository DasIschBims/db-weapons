package dev.dasischbims.commands

import dev.dasischbims.dbweapons.asPlayer
import org.bukkit.Material
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.inventory.ItemStack

class ItemCommand : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        val player = sender.asPlayer()
        if (player.isOp || player.hasPermission("dbweapons.item")) {
            if(args.isEmpty()) {
                player.sendMessage("§4Please specify an item!")
                return true
            }
            if (Material.getMaterial(args[0].uppercase()) != null) {
                player.inventory.addItem(ItemStack(Material.getMaterial(args[0].uppercase())!!, 1))
                // turn the first letter of the item to uppercase and the rest to lowercase
                player.sendMessage("§aYou have received the item §6${args[0].lowercase().replaceFirstChar { it.uppercase() }}§a!§r")
            } else {
                player.sendMessage("§4The item §6${args[0].lowercase().replaceFirstChar { it.uppercase() }}§4 does not exist!§r")
            }
        } else {
            player.sendMessage("§4You can't use /item!")
        }
        return true
    }
}