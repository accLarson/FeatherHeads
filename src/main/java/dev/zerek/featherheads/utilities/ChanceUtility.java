package dev.zerek.featherheads.utilities;

import dev.zerek.featherheads.FeatherHeads;
import org.bukkit.configuration.ConfigurationSection;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class ChanceUtility {

    private final FeatherHeads plugin;
    private final double dropChance;

    public ChanceUtility(FeatherHeads plugin) {
        this.plugin = plugin;
        this.dropChance = plugin.getConfig().getDouble("drop-chance");
    }


    public boolean rollForDrop(){
        return new Random().nextDouble() < this.dropChance;
        }


}
