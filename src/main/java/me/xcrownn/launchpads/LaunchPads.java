package me.xcrownn.launchpads;

import de.leonhard.storage.Yaml;
import org.bukkit.Sound;
import org.bukkit.plugin.java.JavaPlugin;

public final class LaunchPads extends JavaPlugin {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new Events(),this);
        getCommand("launchpads").setExecutor(new Commands());

        Yaml storage = new Yaml("Config", "plugins/LaunchPads");
        storage.getOrSetDefault("Options.LAUNCH_DISTANCE", 10.0);
        storage.getOrSetDefault("Options.LAUNCH_HEIGHT", 5.0);
        storage.getOrSetDefault("Options.LAUNCH_BLOCK", "WOOD");
        storage.getOrSetDefault("Options.SOUND", Sound.UI_BUTTON_CLICK);


    }
}
