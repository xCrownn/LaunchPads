package me.xcrownn.launchpads;

import de.leonhard.storage.Yaml;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;


public class Events implements Listener {

    @EventHandler
    public void pressurePlate(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        Yaml storage = new Yaml("Config", "plugins/LaunchPads");

        Vector v = p.getLocation().getDirection().normalize().multiply(storage.getDouble("Options.LAUNCH_DISTANCE"));

        v.setY(storage.getDouble("Options.LAUNCH_HEIGHT"));

        if (e.getAction().equals(Action.PHYSICAL) && e.getClickedBlock().getRelative(BlockFace.DOWN).getType() == Material.valueOf(storage.getString("Options.LAUNCH_BLOCK"))) {
            p.setVelocity(v);
            p.playSound(p.getLocation(), Sound.valueOf(storage.getString("Options.LAUNCH_SOUND")), 1.0f, 1.0f);
        }

    }
}
