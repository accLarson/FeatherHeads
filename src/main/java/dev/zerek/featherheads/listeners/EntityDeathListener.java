package dev.zerek.featherheads.listeners;

import dev.zerek.featherheads.FeatherHeads;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.Arrays;
import java.util.List;

public class EntityDeathListener implements Listener {

    private final FeatherHeads plugin;
    private final List<Material> axes = Arrays.asList(Material.WOODEN_AXE,Material.STONE_AXE,Material.IRON_AXE,Material.GOLDEN_AXE,Material.DIAMOND_AXE,Material.NETHERITE_AXE);
    private final List<Material> swords = Arrays.asList(Material.WOODEN_SWORD,Material.STONE_SWORD,Material.IRON_SWORD,Material.GOLDEN_SWORD,Material.DIAMOND_SWORD,Material.NETHERITE_SWORD);

    public EntityDeathListener(FeatherHeads plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event){

        String entityType = String.valueOf(event.getEntityType());

        if (event.getEntity().getKiller() != null) {

            Player killer = event.getEntity().getKiller();
            boolean isAxe = axes.contains(killer.getInventory().getItemInMainHand().getType());
            boolean isSword = swords.contains(killer.getInventory().getItemInMainHand().getType());
            double looting = killer.getInventory().getItemInMainHand().getEnchantmentLevel(Enchantment.LOOT_BONUS_MOBS);

            switch (entityType) {
                case "PLAYER":
                    if (plugin.getChanceUtility().rollForDrop(entityType, looting, isAxe, isSword))
                        event.getDrops().add(plugin.getHeadUtility().makePlayerHead(event.getEntity().getName()));
                    break;
                case "AXOLOTL":
                    entityType = entityType + "-" + ((Axolotl) event.getEntity()).getVariant();
                    break;
                case "CAT":
                    entityType = entityType + "-" + ((Cat) event.getEntity()).getCatType();
                    break;
                case "FOX":
                    entityType = entityType + "-" + ((Fox) event.getEntity()).getFoxType();
                    break;
                case "FROG":
                    entityType = entityType + "-" + ((Frog) event.getEntity()).getVariant();
                    break;
                case "HORSE":
                    entityType = entityType + "-" + ((Horse) event.getEntity()).getColor();
                    break;
                case "LLAMA":
                    entityType = entityType + "-" + ((Llama) event.getEntity()).getColor();
                    break;
                case "MUSHROOM_COW":
                    entityType = entityType + "-" + ((MushroomCow) event.getEntity()).getVariant();
                    break;
                case "PANDA":
                    if (((Panda) event.getEntity()).getMainGene().toString().equals("BROWN"))
                        entityType = entityType + "-BROWN";
                    else entityType = entityType + "-BLACK";
                    break;
                case "PARROT":
                    entityType = entityType + "-" + ((Parrot) event.getEntity()).getVariant();
                    break;
                case "RABBIT":
                    entityType = entityType + "-" + ((Rabbit) event.getEntity()).getRabbitType();
                    break;
                case "SHEEP":
                    entityType = entityType + "-" + ((Sheep) event.getEntity()).getColor();
                    break;
                case "STRIDER":
                    if (((Strider) event.getEntity()).isShivering()) entityType = entityType + "-COLD";
                    else entityType = entityType + "-WARM";
                    break;
                case "TRADER_LLAMA":
                    entityType = entityType + "-" + ((TraderLlama) event.getEntity()).getColor();
                    break;
                case "VILLAGER":
                    entityType = entityType + "-" + ((Villager) event.getEntity()).getVillagerType();
                    break;
                case "CREEPER":
                case "ENDER_DRAGON":
                case "GIANT":
                case "SKELETON":
                case "WITHER_SKELETON":
                case "ZOMBIE":
                    entityType = "VANILLA";
                    break;
            }

            if (!entityType.equals("VANILLA") && !entityType.equals("PLAYER") && plugin.getChanceUtility().rollForDrop(entityType, looting, isAxe, isSword)) {
                event.getDrops().add(plugin.getHeadUtility().makeMobHead(entityType));
                plugin.getLogger().info(killer.getName() + " beheaded a " + entityType);
            }
        }
    }
}













