package dev.dasischbims

import dev.dasischbims.commands.EcCommand
import dev.dasischbims.commands.ItemCommand
import dev.dasischbims.items.CraftingRecipes.loadRecipes
import dev.dasischbims.items.CustomItems.loadItems

internal fun initServer() {
    registerCommands()
    loadPluginConfig()
    loadItems()
    loadRecipes()
}

internal fun registerCommands() {
    INSTANCE.getCommand("ec")!!.setExecutor(EcCommand())
    INSTANCE.getCommand("item")!!.setExecutor(ItemCommand())
}