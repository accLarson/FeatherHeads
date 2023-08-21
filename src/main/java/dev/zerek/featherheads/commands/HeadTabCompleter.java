package dev.zerek.featherheads.commands;

import dev.zerek.featherheads.FeatherHeads;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class HeadTabCompleter implements TabCompleter {
    
    private final FeatherHeads plugin;
    private final List<String> entityTypeList;

    public HeadTabCompleter(FeatherHeads plugin) {
        this.plugin = plugin;
        this.entityTypeList = new ArrayList<>(plugin.getTextureUtility().getEntityTypes());
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        List<String> options = new ArrayList<>();

        if (sender instanceof Player && sender.isOp()) {
            options.add("reload");
            options.add("player");
            options.add("mob");

            if (args.length == 1) {
                List<String> match = new ArrayList<>();
                for (String option : options) {
                    if (option.toLowerCase().startsWith(args[0].toLowerCase())) match.add(option);
                }
                return match;
            }
            else if (args.length == 2 && args[0].equals("player")) return null;

            else if (args.length == 2 && args[0].equals("mob")) {
                List<String> match = new ArrayList<>();
                for (String entityType : entityTypeList) {
                    if (entityType.toLowerCase().startsWith(args[1].toLowerCase())) match.add(entityType);
                }
                return match;
            }
            else return new ArrayList<>();
        }
        return options;
    }
}
