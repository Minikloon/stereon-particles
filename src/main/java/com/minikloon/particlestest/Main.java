package com.minikloon.particlestest;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import java.util.logging.Level;

public class Main extends JavaPlugin {
    @Override
    public void onEnable() {
        getLogger().log(Level.INFO, "It works!");

        Bukkit.getScheduler().runTaskTimer(this, () -> {
            Bukkit.getOnlinePlayers().forEach(p -> {
                renderParticles(p);
            });
        }, 0, 1);
    }

    private void renderParticles(Player player) {
        World world = player.getWorld();
        Location playerLoc = player.getLocation();

        float yawDegrees = playerLoc.getYaw();
        float yawRadians = (float) Math.toRadians(yawDegrees);

        Matrix4f mat = new Matrix4f()
                .translate(-0.2f, 0.3f, -0.2f)
                .rotate(yawRadians, 0, -1, 0)
                .rotate((float) Math.PI * 1.5f, 1, 0, 0);

        for (double t = 0; t < Math.PI * 2; t += Math.PI / 100) {
            double dx = Math.sin(t) * (Math.pow(Math.E, Math.cos(t)) - 2*Math.cos(4*t) - Math.pow(Math.sin(t/12), 5));
            double dz = Math.cos(t) * (Math.pow(Math.E, Math.cos(t)) - 2*Math.cos(4*t) - Math.pow(Math.sin(t/12), 5));

            Vector3f particleVec = mat.transformPosition(new Vector3f((float) dx, 0, (float) dz));

            Location particleLoc = playerLoc.clone().add(particleVec.x, particleVec.y, particleVec.z);
            world.spawnParticle(Particle.REDSTONE, particleLoc, 1, new Particle.DustOptions(Color.WHITE, 1f));
        }
    }
}
