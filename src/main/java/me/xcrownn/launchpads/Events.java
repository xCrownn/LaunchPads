package me.xcrownn.launchpads;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

import java.util.Objects;

public class Events implements Listener {

    private final LaunchPads plugin;

    public Events(LaunchPads plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void pressurePlate(PlayerInteractEvent e) {
        boolean eenabled = plugin.getConfig().getBoolean("options.launch_pads_enabled");
        boolean sounds = plugin.getConfig().getBoolean("options.launch_sounds");
        if (eenabled) {
            Player p = e.getPlayer();
            Vector v = p.getLocation().getDirection().normalize().multiply(Double.parseDouble(Objects.requireNonNull(plugin.getConfig().getString("options.launch_distance"))));
            v.setY(Double.parseDouble(Objects.requireNonNull(plugin.getConfig().getString("options.launch_height"))));
            if (e.getAction().equals(Action.PHYSICAL) && e.getClickedBlock().getType() == Material.valueOf(plugin.getConfig().getString("options.pressure_plate")) &&
                    e.getClickedBlock().getRelative(BlockFace.DOWN).getType() == Material.valueOf(plugin.getConfig().getString("options.block_under"))) {
                p.setVelocity(v);
                if (sounds)
                    p.playSound(p.getLocation(), Sound.valueOf(plugin.getConfig().getString("options.launch_sound")), 6.0F, 10.0F);
            }
        }
    }

    @EventHandler
    public void playerMove(PlayerMoveEvent e) {
        boolean enabled = plugin.getConfig().getBoolean("options.slime_block_jump_enabled");
        Player p = e.getPlayer();
        if (enabled && Objects.requireNonNull(e.getTo()).getBlock().getRelative(BlockFace.DOWN).getType() == Material.SLIME_BLOCK) {
            Block block = p.getLocation().getBlock().getRelative(BlockFace.DOWN);
            p.setVelocity(p.getLocation().getDirection().setY(p.getFallDistance() / 2.0F));
        }
    }

}
