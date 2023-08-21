package dev.zerek.featherheads.utilities;

import dev.zerek.featherheads.FeatherHeads;
import org.bukkit.configuration.ConfigurationSection;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class ChanceUtility {

    private final FeatherHeads plugin;
    private final Map<String, Double> dropChanceMap = new HashMap<>();

    public ChanceUtility(FeatherHeads plugin) {
        this.plugin = plugin;
        this.init();
    }

    private void init(){

        ConfigurationSection yml = plugin.getConfig().getConfigurationSection("drop-chance");

        yml.getKeys(false).forEach(entityType -> {

            // Current entity does not have variants (Simple)
            if (yml.isDouble(entityType)) dropChanceMap.put(entityType,yml.getDouble(entityType));

            // Current entity has variants (Complex)
            else {
                yml.getConfigurationSection(entityType).getKeys(false).forEach(entityVariant ->{
                    dropChanceMap.put(entityType + "-" + entityVariant,yml.getDouble(entityType + "." + entityVariant));
                });
            }
        });
    }

    public boolean rollForDrop(String entity, double looting, boolean isAxe, boolean isSword){
        if (isAxe) return new Random().nextDouble() < (this.dropChanceMap.get(entity)*10) + (looting/1000);
        else if (isSword) return new Random().nextDouble() < (this.dropChanceMap.get(entity)) + (looting/1000);
        else return false;
        }


}
