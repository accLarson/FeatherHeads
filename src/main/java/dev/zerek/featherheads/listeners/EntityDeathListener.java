package dev.zerek.featherheads.listeners;

import dev.zerek.featherheads.FeatherHeads;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.Arrays;
import java.util.List;

public class EntityDeathListener implements Listener {

    private final FeatherHeads plugin;
    private final List<Material> axes = Arrays.asList(Material.WOODEN_AXE,Material.STONE_AXE,Material.IRON_AXE,Material.GOLDEN_AXE,Material.DIAMOND_AXE,Material.NETHERITE_AXE);

    public EntityDeathListener(FeatherHeads plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event){


        if (event.getEntity().getKiller() != null && axes.contains(event.getEntity().getKiller().getInventory().getItemInMainHand().getType())) {

            Player killer = event.getEntity().getKiller();
            String entityType = String.valueOf(event.getEntityType());

            switch (entityType) {
                case "PLAYER":
                    if (plugin.getChanceUtility().rollForDrop())
                        event.getDrops().add(plugin.getHeadUtility().createSkull(event.getEntity().getName()));
                    break;
                case "AXOLOTL":
                    entityType = entityType + "_" + ((Axolotl) event.getEntity()).getVariant();
                    break;
                case "CAT":
                    entityType = entityType + "_" + ((Cat) event.getEntity()).getCatType();
                    break;
                case "FOX":
                    entityType = entityType + "_" + ((Fox) event.getEntity()).getFoxType();
                    break;
                case "FROG":
                    entityType = entityType + "_" + ((Frog) event.getEntity()).getVariant();
                    break;
                case "HORSE":
                    entityType = entityType + "_" + ((Horse) event.getEntity()).getColor();
                    break;
                case "LLAMA":
                    entityType = entityType + "_" + ((Llama) event.getEntity()).getColor();
                    break;
                case "MUSHROOM_COW":
                    entityType = entityType + "_" + ((MushroomCow) event.getEntity()).getVariant();
                    break;
                case "PANDA":
                    if (((Panda) event.getEntity()).getMainGene().toString().equals("BROWN"))
                        entityType = entityType + "_BROWN";
                    else entityType = entityType + "_BLACK";
                    break;
                case "PARROT":
                    entityType = entityType + "_" + ((Parrot) event.getEntity()).getVariant();
                    break;
                case "RABBIT":
                    entityType = entityType + "_" + ((Rabbit) event.getEntity()).getRabbitType();
                    break;
                case "SHEEP":
                    entityType = entityType + "_" + ((Sheep) event.getEntity()).getColor();
                    break;
                case "TRADER_LLAMA":
                    entityType = entityType + "_" + ((TraderLlama) event.getEntity()).getColor();
                    break;
                case "VILLAGER":
                    if (((Villager) event.getEntity()).getProfession().equals(Villager.Profession.NONE))
                        entityType = entityType + "_UNEMPLOYED";
                    else entityType = entityType + "_" + ((Villager) event.getEntity()).getProfession();
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

            if (!entityType.equals("VANILLA") && !entityType.equals("PLAYER") && plugin.getChanceUtility().rollForDrop()) {
                event.getDrops().add(plugin.getHeadUtility().createSkull(entityType));
                plugin.getLogger().info(killer.getName() + " beheaded a " + entityType);
            }
        }
    }
}













