package dev.dasischbims.particles

import org.bukkit.Location
import org.bukkit.Particle
import kotlin.math.cos
import kotlin.math.sin

fun drawSmokeCircle(location: Location, radius: Double) {
    for (t in 0 until 50) {
        val x = radius * (sin(t.toDouble()))
        val z = radius * (cos(t.toDouble()))

        location.world?.spawnParticle(Particle.CLOUD, location.clone().add(x, 1.25, z), 0, 0.5, 0.0, 0.5, 0.0)
    }
}