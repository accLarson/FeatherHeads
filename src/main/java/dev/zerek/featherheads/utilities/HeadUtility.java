package dev.zerek.featherheads.utilities;

import com.destroystokyo.paper.profile.PlayerProfile;
import com.destroystokyo.paper.profile.ProfileProperty;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dev.zerek.featherheads.FeatherHeads;
import dev.zerek.featherheads.data.HeadData;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;



public class HeadUtility {

    private final FeatherHeads plugin;
    private final Map<String, HeadData> heads;

    public HeadUtility(FeatherHeads plugin) {
        this.plugin = plugin;
        this.heads = plugin.getHeadDataManager().getHeadDataMap();
    }

    public ItemStack createSkull(String entityType) {

        ItemStack skull = new ItemStack(Material.PLAYER_HEAD, 1);
        SkullMeta meta = (SkullMeta) skull.getItemMeta();

        String b64;
        UUID uuid;
        String name;
        String sound;

        if (heads.containsKey(entityType)) {
            HeadData headData = heads.get(entityType);
            b64 = headData.getHeadHash();
            uuid = new UUID(b64.substring(b64.length() - 20).hashCode(), b64.substring(b64.length() - 10).hashCode());
            name = headData.getName();
            sound = headData.getSound();
            PlayerProfile profile = plugin.getServer().createProfile(uuid, name);
            profile.getProperties().add(new ProfileProperty("textures", b64));
            profile.getProperties().add(new ProfileProperty("display", name));
            profile.getProperties().add(new ProfileProperty("verified", entityType));
            meta.setPlayerProfile(profile);
            meta.lore(Collections.singletonList(MiniMessage.miniMessage().deserialize("<GOLD>Verified: " + entityType.toUpperCase())));

        } else {
            meta.setOwningPlayer(Bukkit.getOfflinePlayer(entityType));
            PlayerProfile profile = meta.getPlayerProfile();
            meta.setPlayerProfile(profile);
            sound = "entity.player.burp";
            meta.lore(Collections.singletonList(MiniMessage.miniMessage().deserialize("<GOLD>Verified: " + entityType)));
        }
        meta.setNoteBlockSound(NamespacedKey.minecraft(sound));
        skull.setItemMeta(meta);
        return skull;
    }
}