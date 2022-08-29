package dev.dasischbims

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.plugin.java.JavaPlugin

lateinit var INSTANCE: Main
class Main : JavaPlugin() {
    override fun onEnable() {
        // Plugin startup logic
        INSTANCE = this
        initServer()
    }

    override fun onLoad() {
        // Plugin load logic
        INSTANCE = this

        server.consoleSender.sendMessage("")
        server.consoleSender.sendMessage("")
        server.consoleSender.sendMessage("${ChatColor.AQUA}DBWeapons")
        server.consoleSender.sendMessage("")
        server.consoleSender.sendMessage("${ChatColor.DARK_AQUA}Version: ${ChatColor.WHITE}${description.version}")
        server.consoleSender.sendMessage("${ChatColor.DARK_AQUA}Author: ${ChatColor.WHITE}${description.authors[0]}")
        server.consoleSender.sendMessage("")
        server.consoleSender.sendMessage("")

        for (player in Bukkit.getOnlinePlayers()) {
            server.consoleSender.sendMessage("$player")
        }
    }

    override fun onDisable() {
        // Plugin shutdown logic
    }
}