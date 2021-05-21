package me.bobcatsss.cccondenser.handler;

import me.bobcatsss.cccondenser.CCCondenser;
import me.bobcatsss.cccondenser.blocks.BlockManager;
import me.bobcatsss.cccondenser.blocks.BlockTask;
import me.bobcatsss.cccondenser.utils.Tuple;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.Dropper;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;


public class BlockListener implements Listener {

    public CCCondenser plugin;
    public BlockManager blockManager;

    public BlockListener(CCCondenser pl) {
        this.plugin = pl;
        //BlockTask<?> blockTask = blockManager.getTask(name);
        this.blockManager = plugin.getBlockManager();
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        plugin.checkRecipes(event.getPlayer());
    }

    /**
     * Handles when the block is powered
     */
    @EventHandler
    public void onPower(BlockDispenseEvent event) {
        Block block = event.getBlock();
        if (!(block.getState() instanceof Dropper)) return;
        BlockTask blockTask = blockManager.getTask((Dropper) block.getState());
        if (blockTask == null) return;
        event.setCancelled(true);

        if (!blockTask.canExecute(block.getState())) return;
        blockTask.run(block.getState(), block.getLocation());
    }

    /**
     * Handles when the custom block is placed
     */
    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        ItemStack stack = event.getItemInHand();

        Tuple<NamespacedKey, BlockTask<Dropper>> entry = blockManager.getTaskEntry(stack);
        if (entry == null) return;

        BlockTask<Dropper> blockTask = entry.getValue();
        if (blockTask.getItem().isSimilar(stack)) {
            Dropper dropper = (Dropper) event.getBlock().getState();
            dropper.getPersistentDataContainer().set(entry.getKey(), PersistentDataType.STRING, "block");
            dropper.update();

            // There has to be an item in the GUI for the 'BlockDispenseEvent' to get fired
            dropper.getInventory().addItem(new ItemStack(Material.STONE));
            blockTask.onPlace(dropper);
        }
    }

    /**
     * This will drop the actual custom block item
     */
    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        if (!(event.getBlock().getState() instanceof Dropper)) return;
        BlockTask blockTask = blockManager.getTask((Dropper) event.getBlock().getState());
        if (blockTask == null) return;
        Dropper dropper = (Dropper) event.getBlock().getState();
        blockTask.onBreak(dropper);
        dropper.getInventory().clear(); // Need to clear the inventory to prevent the dummy item from being dropped

        event.setCancelled(true);
        event.getBlock().setType(Material.AIR, true);
        if (event.getPlayer().getGameMode() != GameMode.CREATIVE)
            event.getPlayer().getWorld().dropItemNaturally(event.getBlock().getLocation(), blockTask.getItem().build());
    }

    /**
     * This will prevent players from opening the GUI
     */
    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (!event.hasBlock()) return;
        if (!event.getAction().name().contains("RIGHT")) return;
        if (event.getPlayer().isSneaking()) {
            EntityEquipment equipment = event.getPlayer().getEquipment();
            if (equipment != null) {
                if ((equipment.getItemInMainHand().getType() != Material.AIR)) return;
                if ((equipment.getItemInOffHand().getType() != Material.AIR)) return;
            }
        }
        if (!(event.getClickedBlock().getState() instanceof Dropper)) return;
        BlockTask blockTask = blockManager.getTask((Dropper) event.getClickedBlock().getState());
        if (blockTask == null) return;
        event.setCancelled(true);
    }

    /**
     * This will prevent hoppers from adding/taking items from the GUI
     */
    @EventHandler
    public void preventTransfer(InventoryMoveItemEvent event) {
        Inventory dest = event.getDestination();
        if (dest.getHolder() != null) {
            InventoryHolder holder = dest.getHolder();
            if (holder instanceof Dropper) {
                Dropper dropper = (Dropper) holder;
				BlockTask blockTask = blockManager.getTask(dropper);
				if (blockTask == null) return;
				if (blockTask.canHandleItems(dropper, event.getItem())) {
					blockTask.handleItemInput(dropper, event.getItem());
					return;
				}
            }
        }

        Inventory src = event.getSource();
        if (src.getHolder() != null) {
            InventoryHolder holder = src.getHolder();
            if (holder instanceof Dropper) {
                Dropper dropper = (Dropper) holder;
				BlockTask blockTask = blockManager.getTask(dropper);
				if (blockTask == null) return;
				event.setCancelled(true);
            }
        }
    }

}
