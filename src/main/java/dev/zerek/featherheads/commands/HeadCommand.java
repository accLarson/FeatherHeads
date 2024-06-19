package dev.zerek.featherheads.commands;

import dev.zerek.featherheads.FeatherHeads;
import dev.zerek.featherheads.utilities.HeadUtility;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;



public class HeadCommand implements CommandExecutor {

    private final FeatherHeads plugin;
    private final HeadUtility headUtility;

    public HeadCommand(FeatherHeads plugin) {
        this.plugin = plugin;
        this.headUtility = this.plugin.getHeadUtility();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (sender instanceof Player && sender.isOp()) {

            if (args.length == 1 && args[0].equals("reload")) plugin.reload(sender);

            else if (args.length == 2 && args[0].equals("player") ) {

                if (!((Player) sender).getInventory().addItem(plugin.getHeadUtility().makePlayerHead(args[1])).isEmpty()) sender.sendMessage("No space in your inventory.");
            }

            else if (args.length == 2 && args[0].equals("mob") && plugin.getHeadDataManager().getHeadDataArray().containsKey(args[1].toUpperCase())) {

                if (!((Player) sender).getInventory().addItem(plugin.getHeadUtility().makeMobHead(args[1].toUpperCase())).isEmpty()) sender.sendMessage("No space in your inventory.");

            }
        }
        return true;
    }
}
