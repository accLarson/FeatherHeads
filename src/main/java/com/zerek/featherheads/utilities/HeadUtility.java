package com.zerek.featherheads.utilities;

import com.destroystokyo.paper.profile.PlayerProfile;
import com.destroystokyo.paper.profile.ProfileProperty;
import com.zerek.featherheads.FeatherHeads;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Base64;
import java.util.Collections;
import java.util.UUID;

public class HeadUtility {

    private final FeatherHeads plugin;

    public HeadUtility(FeatherHeads plugin) {
        this.plugin = plugin;
    }

    public ItemStack makeMobHead(UUID uuid, String name, String textures) {

        ItemStack headStack = new ItemStack(Material.PLAYER_HEAD, 1);

        SkullMeta skullMeta = (SkullMeta) headStack.getItemMeta();

        PlayerProfile skullProfile = plugin.getServer().createProfile(uuid,name);

        skullProfile.getProperties().add(new ProfileProperty("textures", textures));
        skullProfile.getProperties().add(new ProfileProperty("display", name));

        skullMeta.setPlayerProfile(skullProfile);

        skullMeta.lore(Collections.singletonList(Component.text("Verified: " + name)));

        headStack.setItemMeta(skullMeta);

        return headStack;
    }

    public ItemStack makeMobHead(String entityType) {

        ItemStack headStack = new ItemStack(Material.PLAYER_HEAD, 1);

        SkullMeta skullMeta = (SkullMeta) headStack.getItemMeta();

        PlayerProfile skullProfile = plugin.getServer().createProfile(plugin.getTextureUtility().getUUID(entityType), plugin.getTextureUtility().getName(entityType));

        skullProfile.getProperties().add(new ProfileProperty("textures", plugin.getTextureUtility().getTextures(entityType)));
        skullProfile.getProperties().add(new ProfileProperty("display", plugin.getTextureUtility().getName(entityType)));

        skullMeta.setPlayerProfile(skullProfile);

        skullMeta.lore(Collections.singletonList(Component.text("Verified: " + plugin.getTextureUtility().getName(entityType))));

        headStack.setItemMeta(skullMeta);

        return headStack;
    }

    public ItemStack makePlayerHead(String username) {

        ItemStack headStack = new ItemStack(Material.PLAYER_HEAD, 1);

        SkullMeta skullMeta = (SkullMeta) headStack.getItemMeta();

        PlayerProfile skullProfile = plugin.getServer().createProfile(Bukkit.getOfflinePlayer(username).getUniqueId(),username);

        String texturesURL = "{\"textures\":{\"SKIN\":{\"url\":\"" + skullProfile.getTextures().getSkin() + "\"}}}";
        String encodedTextures = Base64.getEncoder().encodeToString(texturesURL.getBytes());

        skullProfile.setProperty(new ProfileProperty("textures", encodedTextures));
        skullProfile.setProperty(new ProfileProperty("display", username));

        skullMeta.setPlayerProfile(skullProfile);

        skullMeta.lore(Collections.singletonList(Component.text("Verified: " + username)));

        headStack.setItemMeta(skullMeta);

        return headStack;
    }


    public void reloreHead(ItemStack headStack){
        SkullMeta skullMeta = (SkullMeta) headStack.getItemMeta();
        skullMeta.lore(Collections.singletonList(Component.text("Verified: " + skullMeta.getPlayerProfile().getName())));
        headStack.setItemMeta(skullMeta);
    }
}
