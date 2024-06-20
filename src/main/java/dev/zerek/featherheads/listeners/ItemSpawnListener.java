package dev.zerek.featherheads.listeners;

import com.destroystokyo.paper.profile.PlayerProfile;
import com.destroystokyo.paper.profile.ProfileProperty;
import dev.zerek.featherheads.FeatherHeads;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.*;
import java.util.function.Supplier;

public class ItemSpawnListener implements Listener {

    private final FeatherHeads plugin;

    public ItemSpawnListener(FeatherHeads plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onItemSpawn(ItemSpawnEvent event){

        if (event.getEntity().getItemStack().getType() == Material.PLAYER_HEAD){


            ItemStack skull = event.getEntity().getItemStack();
            SkullMeta meta = (SkullMeta) skull.getItemMeta();

            if (meta.hasOwner() && meta.getPlayerProfile() != null){

                PlayerProfile profile = meta.getPlayerProfile();

                Map<String,String> properties = new HashMap<>();
                meta.getPlayerProfile().getProperties().forEach(pp -> properties.put(pp.getName(),pp.getValue()));
                if (properties.containsKey("verified")) meta.lore(Collections.singletonList(MiniMessage.miniMessage().deserialize("<GOLD>Verified: " + properties.get("verified"))));
                else if (properties.containsKey("textures")) {
                    if (properties.get("textures").length() > 200) meta.lore(Collections.singletonList(MiniMessage.miniMessage().deserialize("<GOLD>Verified: " + profile.getName())));
                    else meta.lore(Collections.singletonList(MiniMessage.miniMessage().deserialize("<GRAY>Verified (old): " + profile.getName())));
                }

                skull.setItemMeta(meta);
            }
        }
    }
}
