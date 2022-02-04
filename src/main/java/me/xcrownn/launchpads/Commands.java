package me.xcrownn.launchpads;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.awt.*;
import java.util.ArrayList;

public class Commands implements CommandExecutor {
    private final String prefix = ChatColor.translateAlternateColorCodes('&', "&8(&2LaunchPad&8)&r ");

    public LaunchPads plugin;

    public Commands(LaunchPads plugin) {
        this.plugin = plugin;
    }

    boolean isInt(String s) {
        try {
            Double.parseDouble(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    boolean isBlock(String isBlock) {
        try {
            Material.valueOf(isBlock);
            return true;
        } catch (Exception exp) {
            return false;
        }
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player p = (Player)sender;
        ItemStack handItem = p.getInventory().getItemInMainHand();
        String material = handItem.getType().toString();
        if (p.hasPermission("launchpads.admin")) {
            if (label.equalsIgnoreCase("LaunchPads"))
                if (args.length == 0) {
                    p.sendMessage("----Usage----");
                    p.sendMessage("/launchpads reload");
                    p.sendMessage("/launchpads setmulti <double>");
                    p.sendMessage("/launchpads setheight <double>");
                } else {
                    if (args[0].equalsIgnoreCase("reload")) {
                        plugin.reloadConfig();
                        p.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', " &aConfig files have been reloaded."));
                    }
                    if (args[0].equalsIgnoreCase("setdistance"))
                        if (isInt(args[1])) {
                            plugin.config.set("options.launch_distance", args[1]);
                            plugin.saveConfig();
                            p.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', " &aSet distance to &r" + args[1] + "&a."));
                        } else {
                            p.sendMessage(prefix + ChatColor.RED + " You did not supply a number!");
                        }
                    if (args[0].equalsIgnoreCase("setheight"))
                        if (isInt(args[1])) {
                            plugin.config.set("options.launch_height", args[1]);
                            plugin.saveConfig();
                            p.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', "&aSet height to &r" + args[1] + "&a."));
                        } else {
                            p.sendMessage(prefix + ChatColor.RED + " You did not supply a number!");
                        }
                    if (args[0].equalsIgnoreCase("setblock"))
                        if (p.getInventory().getItemInMainHand().getType() == Material.AIR) {
                            p.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', " &cYou need to be holding a material in your main hand!"));
                        } else if (p.getInventory().getItemInMainHand().getType().isSolid()) {
                            plugin.getConfig().set("options.block_under", material);
                            plugin.saveConfig();
                            p.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', " &aSet the bottom block to &r" + material + "&a!"));
                        } else {
                            p.sendMessage(material + ChatColor.RED + " is not a valid block type!");
                        }
                    if (args[0].equalsIgnoreCase("setplate"))
                        if (p.getInventory().getItemInMainHand().getType().toString().endsWith("PLATE")) {
                            plugin.getConfig().set("options.pressure_plate", material);
                            plugin.saveConfig();
                            p.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', " &aSet pressure plate to &r" + material + "&a!"));
                        } else {
                            p.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', " &cYou must be holding a pressure plate in your hand."));
                        }
                    if (args[0].equalsIgnoreCase("info")) {
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8-------&2LaunchPads Info&8-------"));
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aLaunch Distance: &r" + plugin.getConfig().getDouble("options.launch_distance")));
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aLaunch Height: &r" + plugin.getConfig().getDouble("options.launch_height")));
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aPressure Plate: &r" + plugin.getConfig().getString("options.pressure_plate")));
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aBlock Under: &r" + plugin.getConfig().getString("options.block_under")));
                    }
                    if (args[0].equalsIgnoreCase("useslimejump"))
                        if (args[1].equalsIgnoreCase("true")) {
                            plugin.getConfig().set("options.slime_block_jump_enabled", Boolean.TRUE);
                            plugin.saveConfig();
                            p.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', " Enabled slime boost jumps"));
                        } else if (args[1].equalsIgnoreCase("false")) {
                            plugin.getConfig().set("options.slime_block_jump_enabled", Boolean.FALSE);
                            plugin.saveConfig();
                            p.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', " Disabled slime boost jumps"));
                        } else {
                            p.sendMessage(prefix + ChatColor.RED + " Please enter a value!");
                        }
                    if (args[0].equalsIgnoreCase("usepads"))
                        if (args[1].equalsIgnoreCase("true")) {
                            plugin.getConfig().set("options.launch_pads_enabled", Boolean.TRUE);
                            plugin.saveConfig();
                            p.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', " Enabled launch pads"));
                        } else if (args[1].equalsIgnoreCase("false")) {
                            plugin.getConfig().set("options.launch_pads_enabled", Boolean.FALSE);
                            plugin.saveConfig();
                            p.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', " Disabled launch pads"));
                        } else {
                            p.sendMessage(prefix + ChatColor.RED + " Please enter a value!");
                        }
                    if (!args[0].equalsIgnoreCase("launchsound") ||
                            args[1].equalsIgnoreCase("true"));
                }
        } else {
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou do not have permission to use these commands!"));
        }
        return false;
    }

    public ArrayList<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (command.getName().equalsIgnoreCase("launchpads") && sender.hasPermission("launchpads.admin")) {
            ArrayList<String> autoCompletes = new ArrayList<>();
            if (args.length == 1) {
                autoCompletes.add("reload");
                autoCompletes.add("setheight");
                autoCompletes.add("setdistance");
                autoCompletes.add("setblock");
                autoCompletes.add("setplate");
                autoCompletes.add("useslimejump");
                autoCompletes.add("usepads");
                autoCompletes.add("info");
            }
            if (args.length == 2) {
                autoCompletes.add("true");
                autoCompletes.add("false");
            }
            return autoCompletes;
        }
        return null;
    }
}
