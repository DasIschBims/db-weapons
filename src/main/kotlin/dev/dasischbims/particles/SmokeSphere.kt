package dev.dasischbims.particles

import org.bukkit.Location
import org.bukkit.Particle
import kotlin.math.*

fun drawSmokeSphere(location: Location, radius: Double, amount: Int) {
    // Never set amount too high or it will freeze the server temporarily, 16 is already too much from my experience
    val t = PI / amount
    val h = (amount * 2)
    var loc: Location
    for (i in 0..h) {
        for (j in 0..h) {
            for (k in 0..h) {
                loc = location.clone().add(
                    radius * cos(t * i) * sin(t * j),
                    radius * cos(t * j),
                    radius * sin(t * i) * sin(t * j)
                )
                loc.world!!.spawnParticle(Particle.CLOUD, loc, 0, 0.5, 0.0, 0.5, 0.0)
            }
        }
    }
}