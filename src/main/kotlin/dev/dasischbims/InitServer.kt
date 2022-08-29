package dev.dasischbims

import dev.dasischbims.commands.EcCommand
import dev.dasischbims.commands.ItemCommand

internal fun initServer() {
    registerCommands()
    loadPluginConfig()
}

internal fun registerCommands() {
    INSTANCE.getCommand("ec")!!.setExecutor(EcCommand())
    INSTANCE.getCommand("item")!!.setExecutor(ItemCommand())
}