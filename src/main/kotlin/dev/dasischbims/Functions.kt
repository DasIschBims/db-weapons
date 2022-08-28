package dev.dasischbims.dbweapons

import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

fun CommandSender.asPlayer() : Player = if (this is Player) this else throw Exception("Not a player")