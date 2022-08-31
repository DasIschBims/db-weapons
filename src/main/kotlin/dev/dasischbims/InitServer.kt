package dev.dasischbims

import dev.dasischbims.commands.EcCommand
import dev.dasischbims.commands.ItemCommand
import dev.dasischbims.items.CraftingRecipes.loadRecipes
import dev.dasischbims.items.CustomItems.loadItems
import dev.dasischbims.listener.InteractListener
import dev.dasischbims.listener.JoinListener
import org.bukkit.Bukkit

internal fun initServer() {
    registerCommands()
    registerListeners()
    loadPluginConfig()
    loadItems()
    loadRecipes()
}

internal fun registerCommands() {
    INSTANCE.getCommand("ec")!!.setExecutor(EcCommand())
    INSTANCE.getCommand("item")!!.setExecutor(ItemCommand())
}

internal fun registerListeners() {
    val listener = listOf(JoinListener(), InteractListener())

    val pluginManager = Bukkit.getPluginManager()
    listener.forEach { pluginManager.registerEvents(it, INSTANCE) }
}