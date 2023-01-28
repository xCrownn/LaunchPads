package me.xcrownn.launchpads;

import de.leonhard.storage.Yaml;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Commands implements CommandExecutor {
    private final String prefix = ChatColor.translateAlternateColorCodes('&', "&7[&bLaunchPads&7]&r ");
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        if (!player.hasPermission("launchpads.admin")) {
            player.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
            return true;
        }
        if (label.equalsIgnoreCase("launchpads")) {
            if (args.length > 0) {
                Yaml storage = new Yaml("Config", "plugins/LaunchPads");
                switch (args[0].toLowerCase()) {
                    case "setdistance" -> {
                        double dis;
                        try {
                            dis = Double.parseDouble(args[1]);
                        } catch (NumberFormatException e) {
                            player.sendMessage("You must enter a number! ex. 1.5");
                            return true;
                        }
                        storage.set("Options.LAUNCH_DISTANCE", dis);
                        player.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', " &aSet distance to &r" + dis + "&a."));
                    }
                    case "setheight" -> {
                        double height;
                        try {
                            height = Double.parseDouble(args[1]);
                        } catch (NumberFormatException e) {
                            player.sendMessage("You must enter a number! ex. 1.5");
                            return true;
                        }
                        storage.set("Options.LAUNCH_HEIGHT", height);
                        player.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', "&aSet height to &r" + height + "&a."));
                    }
                    case "setblock" -> {
                        if (player.getInventory().getItemInMainHand().getType() != Material.AIR && player.getInventory().getItemInMainHand().getType().isSolid()) {
                            storage.set("Options.LAUNCH_BLOCK", player.getInventory().getItemInMainHand().getType());
                            player.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', " &aSet the bottom block to &r" + player.getInventory().getItemInMainHand().getType() + "&a!"));
                        } else player.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', " &cYou need to be holding a block in your hand!"));
                    }
                    case "setsound" -> {
                        Sound sound;
                        try {
                            sound = Sound.valueOf(args[1]);
                        } catch (IllegalArgumentException e) {
                            player.sendMessage(ChatColor.RED + "Invalid sound! https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/Sound.html");
                            return true;
                        }
                        storage.set("Options.LAUNCH_SOUND", sound.name());
                        player.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', "&aSet sound to &r" + sound.name() + "&a."));
                    }
                }
            } else {
                player.sendMessage("|--- Launch Pad Commands ---|");
                player.sendMessage("/launchpads setdistance <distance>");
                player.sendMessage("/launchpads setheight <height>");
                player.sendMessage("/launchpads setsound <sound>");
                player.sendMessage("/launchpads setblock");
            }
        }
        return true;
    }
}