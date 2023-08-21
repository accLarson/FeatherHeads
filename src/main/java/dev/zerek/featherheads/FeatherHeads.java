package dev.zerek.featherheads;

import dev.zerek.featherheads.commands.HeadCommand;
import dev.zerek.featherheads.commands.HeadTabCompleter;
import dev.zerek.featherheads.listeners.EntityDeathListener;
import dev.zerek.featherheads.listeners.ItemSpawnListener;
import dev.zerek.featherheads.utilities.ChanceUtility;
import dev.zerek.featherheads.utilities.HeadUtility;
import dev.zerek.featherheads.utilities.TextureUtility;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public final class FeatherHeads extends JavaPlugin {

    private ChanceUtility chanceUtility;
    private HeadUtility headUtility;
    private TextureUtility textureUtility;

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        this.chanceUtility = new ChanceUtility(this);
        this.headUtility = new HeadUtility(this);
        this.textureUtility = new TextureUtility(this);
        this.getCommand("head").setExecutor(new HeadCommand(this));
        this.getCommand("head").setTabCompleter(new HeadTabCompleter(this));

        this.getServer().getPluginManager().registerEvents(new ItemSpawnListener(this),this);
        this.getServer().getPluginManager().registerEvents(new EntityDeathListener(this),this);

    }

    @Override
    public void onDisable() {
    }

    public void reload(CommandSender sender) {
        this.onDisable();
        this.onEnable();
        sender.sendMessage("FeatherHeads reloaded");
    }

    public ChanceUtility getChanceUtility() {
        return this.chanceUtility;
    }

    public HeadUtility getHeadUtility() {
        return this.headUtility;
    }

    public TextureUtility getTextureUtility(){
        return this.textureUtility;
    }
}
