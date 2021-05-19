package me.bobcatsss.cccondenser.commands;

import com.google.common.collect.Lists;
import lib.brainsynder.commands.ParentCommand;
import lib.brainsynder.commands.annotations.ICommand;
import me.bobcatsss.cccondenser.utils.BlockUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@ICommand (
        name = "blocks",
        usage = "<block>",
        description = "Gives you one of the custom blocks"
)
public class BlockCommand extends ParentCommand {

    private final BlockUtils blockUtils;

    public BlockCommand(BlockUtils blockUtils) {
        this.blockUtils = blockUtils;
        registerCompletion(1, Lists.newArrayList("breaker"));
    }

    @Override
    public void run(CommandSender sender, String[] args) {
        if (args.length == 0) {
            sendUsage(sender);
            return;
        }

        if (!(sender instanceof Player)) {
            sender.sendMessage("You need to be a player to run this command");
            return;
        }
        Player player = (Player) sender;

        String name = args[0];
        if (name.equalsIgnoreCase("breaker")) {
            player.getWorld().dropItem(player.getEyeLocation(), blockUtils.blockBreaker.getItem().build());
            return;
        }
    }

    @Override
    public boolean canExecute(CommandSender sender) {
        return sender.hasPermission("blocks.command");
    }
}
