package me.bobcatsss.cccondenser.commands;

import lib.brainsynder.commands.ParentCommand;
import lib.brainsynder.commands.annotations.ICommand;
import lib.brainsynder.nms.Tellraw;
import me.bobcatsss.cccondenser.blocks.BlockManager;
import me.bobcatsss.cccondenser.blocks.BlockTask;
import me.bobcatsss.cccondenser.utils.BlockDataHandler;
import org.bukkit.ChatColor;
import org.bukkit.FluidCollisionMode;
import org.bukkit.block.Block;
import org.bukkit.block.Dropper;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

@ICommand(
        name = "blocks",
        usage = "<block>",
        description = "Gives you one of the custom blocks"
)
public class BlockCommand extends ParentCommand {

    private final BlockManager blockManager;

    public BlockCommand(BlockManager blockManager) {
        this.blockManager = blockManager;
        List<String> names = blockManager.getNames();
        names.add("data");
        registerCompletion(1, names);
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
        if (name.equalsIgnoreCase("data")) {
            Block target = player.getTargetBlockExact(10, FluidCollisionMode.NEVER);
            if ((target == null) || (!(target.getState() instanceof Dropper))) {
                player.sendMessage(ChatColor.RED + "Invalid block target");
                return;
            }

            BlockTask blockTask = blockManager.getTask((Dropper) target.getState());
            if (blockTask == null)  {
                player.sendMessage(ChatColor.RED + "Invalid block target");
                return;
            }

            BlockDataHandler handler = blockTask.getDataHandler();
            Tellraw raw = Tellraw.fromLegacy("&7Block Data for ")
                    .then("[" + blockTask.getName() + "]").color(ChatColor.YELLOW);
            raw.tooltip(
                    "&7Current Millis: &e" + System.currentTimeMillis(),
                    "&7On Cooldown Till: &e" + handler.cooldownTarget(),
                    "&7Is Off Cooldown: &e" + handler.isOffCooldown(),
                    "&7Level: &e" + handler.getLevel()
            ).send(player);
            return;
        }

        BlockTask<?> blockTask = blockManager.getTask(name);
        if (blockTask == null) {
            player.sendMessage(ChatColor.RED + "Unknown Block...");
            return;
        }
        player.getWorld().dropItem(player.getEyeLocation(), blockTask.getItem().build());
    }

    @Override
    public boolean canExecute(CommandSender sender) {
        return sender.hasPermission("blocks.command");
    }
}
