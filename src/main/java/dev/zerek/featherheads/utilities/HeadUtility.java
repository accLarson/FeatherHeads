package dev.zerek.featherheads.utilities;

import com.destroystokyo.paper.profile.PlayerProfile;
import com.destroystokyo.paper.profile.ProfileProperty;
import dev.zerek.featherheads.FeatherHeads;
import dev.zerek.featherheads.managers.HeadDataManager;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Collections;
import java.util.UUID;

public class HeadUtility {

    private final FeatherHeads plugin;

    private final HeadDataManager manager;

    public HeadUtility(FeatherHeads plugin) {
        this.plugin = plugin;
        this.manager = plugin.getHeadDataManager();
    }

    public ItemStack makeMobHead(String entityType) {

        ItemStack skull = new ItemStack(Material.PLAYER_HEAD, 1);
        SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();

        String b64 = manager.getHeadDataArray().get(entityType).getHeadHash();
        UUID id = new UUID(b64.substring(b64.length() - 20).hashCode(), b64.substring(b64.length() - 10).hashCode());
        String name = manager.getHeadDataArray().get(entityType).getName();

        PlayerProfile profile = plugin.getServer().createProfile(id, name);
        profile.getProperties().add(new ProfileProperty("textures",b64));
        profile.getProperties().add(new ProfileProperty("display",name));

        skullMeta.setPlayerProfile(profile);
        skullMeta.lore(Collections.singletonList(Component.text("Verified: " + entityType)));
        skull.setItemMeta(skullMeta);

        return skull;
    }

    public ItemStack makePlayerHead(String username) {

        ItemStack skull = new ItemStack(Material.PLAYER_HEAD, 1);
        SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();

        skullMeta.setOwningPlayer(Bukkit.getOfflinePlayer(username));
        skullMeta.lore(Collections.singletonList(Component.text("Verified: " + username)));

        skull.setItemMeta(skullMeta);

        return skull;
    }

    public void reloreHead(ItemStack headStack){
        SkullMeta skullMeta = (SkullMeta) headStack.getItemMeta();
        skullMeta.lore(Collections.singletonList(Component.text("Verified: " + skullMeta.getPlayerProfile().getName())));
        headStack.setItemMeta(skullMeta);
    }
}
