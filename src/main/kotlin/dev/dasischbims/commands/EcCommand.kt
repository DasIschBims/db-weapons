package dev.dasischbims.commands

import dev.dasischbims.asPlayer
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

class EcCommand : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        val player = sender.asPlayer()
        if (player.isOp || player.hasPermission("dbweapons.ec")) {
            player.openInventory(player.enderChest)
        } else {
            player.sendMessage("§4You can't use /ec!")
        }
        return true
    }
}