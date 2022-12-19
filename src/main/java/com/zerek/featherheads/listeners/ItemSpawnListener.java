package com.zerek.featherheads.listeners;

import com.destroystokyo.paper.profile.PlayerProfile;
import com.destroystokyo.paper.profile.ProfileProperty;
import com.zerek.featherheads.FeatherHeads;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Base64;
import java.util.Collections;

public class ItemSpawnListener implements Listener {

    private final FeatherHeads plugin;

    public ItemSpawnListener(FeatherHeads plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onItemSpawn(ItemSpawnEvent event){

        if (event.getEntity().getItemStack().getType() == Material.PLAYER_HEAD){

            ItemStack headStack = event.getEntity().getItemStack();
            SkullMeta skullMeta = (SkullMeta) headStack.getItemMeta();

            if (skullMeta.hasOwner() && skullMeta.getPlayerProfile() != null){

                PlayerProfile skullProfile = skullMeta.getPlayerProfile();

                String texturesURL = "{\"textures\":{\"SKIN\":{\"url\":\"" + skullProfile.getTextures().getSkin() + "\"}}}";
                String encodedTextures = Base64.getEncoder().encodeToString(texturesURL.getBytes());

                skullProfile.setProperty(new ProfileProperty("textures", encodedTextures));
                skullProfile.setProperty(new ProfileProperty("display", skullProfile.getName()));

                skullMeta.setPlayerProfile(skullProfile);

                skullMeta.lore(Collections.singletonList(Component.text("Verified: " + skullMeta.getPlayerProfile().getName())));
                headStack.setItemMeta(skullMeta);
            }
        }
    }
}
