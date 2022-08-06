package com.zerek.featherheads.utilities;

import com.zerek.featherheads.FeatherHeads;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.*;

public class TextureUtility {

    private final FeatherHeads plugin;
    Map<String,Map<String,String>> textureMap = new HashMap<>();

    public TextureUtility(FeatherHeads plugin) {
        this.plugin = plugin;
        this.init();
    }

    private void init(){
        //establish file connection
        File file = new File(plugin.getDataFolder() + File.separator + "textures.yml");
        if (!file.exists()) plugin.saveResource("textures.yml",false);

        ConfigurationSection yml = YamlConfiguration.loadConfiguration(file).getConfigurationSection("entities");


        yml.getKeys(false).forEach(entityType -> {

            // Current entity does not have variants (Simple)
            if (yml.getConfigurationSection(entityType).getKeys(false).contains("uuid")){

                ConfigurationSection entityYml = yml.getConfigurationSection(entityType);
                Map<String, String> entityTextureMap = new HashMap<>();

                entityYml.getKeys(false).forEach(key -> entityTextureMap.put(key,entityYml.getString(key)));
                textureMap.put(entityType,entityTextureMap);
            }

            // Current entity has variants (Complex)
            else {

                yml.getConfigurationSection(entityType).getKeys(false).forEach(entityVariant ->{

                    ConfigurationSection entityVariantYml = yml.getConfigurationSection(entityType + "." + entityVariant);
                    Map<String, String> entityVariantTextureMap = new HashMap<>();

                    entityVariantYml.getKeys(false).forEach(key -> entityVariantTextureMap.put(key,entityVariantYml.getString(key)));
                    textureMap.put(entityType + "-" + entityVariant, entityVariantTextureMap);

                });
            }
        });
    }

    public Set<String> getEntityTypes(){
        return textureMap.keySet();
    }

    public UUID getUUID(String entity){
        return UUID.fromString(textureMap.get(entity).get("uuid"));
    }

    public String getName(String entity){
        return textureMap.get(entity).get("name");
    }

    public String getTextures(String entity){
        return textureMap.get(entity).get("textures");
    }

}
