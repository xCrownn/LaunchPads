package me.xcrownn.launchpads;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public final class LaunchPads extends JavaPlugin {

    FileConfiguration config = getConfig();

    @Override
    public void onEnable() {
        getConfig().options().copyDefaults(true);
        getServer().getPluginManager().registerEvents(new Events(this),this);
        getCommand("launchpads").setExecutor(new Commands(this));

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
