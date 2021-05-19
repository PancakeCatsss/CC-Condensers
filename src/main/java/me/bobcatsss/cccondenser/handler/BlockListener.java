package me.bobcatsss.cccondenser.handler;

import me.bobcatsss.cccondenser.CCCondenser;
import me.bobcatsss.cccondenser.utils.BlockUtils;
import me.bobcatsss.cccondenser.utils.Keys;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Dropper;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;


public class BlockListener implements Listener {
	
	public CCCondenser plugin;
	public BlockUtils blockUtils;

	public BlockListener(CCCondenser pl) {
		this.plugin = pl;
		this.blockUtils = plugin.getBlockUtils();
	}

	/**
	 * Handles when the block is powered
	 */
	@EventHandler
	public void onPower(BlockDispenseEvent event) {
		Block block = event.getBlock();


		if(blockUtils.isBlockBreaker(block)) {
			event.setCancelled(true);
			if (!blockUtils.blockBreaker.canExecute((Dropper) block.getState())) return;
			blockUtils.blockBreaker.run((Dropper) block.getState(), block.getLocation());
		}
	}

	/**
	 * Handles when the custom block is placed
	 */
	@EventHandler
	public void onPlace (BlockPlaceEvent event) {
		ItemStack stack = event.getItemInHand();

		if (blockUtils.blockBreaker.getItem().isSimilar(stack)) {
			Dropper dropper = (Dropper) event.getBlock().getState();
			dropper.getPersistentDataContainer().set(Keys.BLOCK_BREAKER, PersistentDataType.STRING, "block");
			dropper.update();

			// There has to be an item in the GUI for the 'BlockDispenseEvent' to get fired
			dropper.getInventory().addItem(new ItemStack(Material.STONE));
		}
	}

	/**
	 * This will drop the actual custom block item
	 */
	@EventHandler
	public void onBreak (BlockBreakEvent event) {
		if (blockUtils.isBlockBreaker(event.getBlock())) {
			Dropper dropper = (Dropper) event.getBlock().getState();
			dropper.getInventory().clear(); // Need to clear the inventory to prevent the dummy item from being dropped

			event.setCancelled(true);
			event.getBlock().setType(Material.AIR, true);
			event.getPlayer().getWorld().dropItemNaturally(event.getBlock().getLocation(), blockUtils.blockBreaker.getItem().build());
		}
	}

	/**
	 * This will prevent players from opening the GUI
	 */
	@EventHandler
	public void onInteract (PlayerInteractEvent event) {
		if (!event.hasBlock()) return;
		if (!event.getAction().name().contains("RIGHT")) return;
		if (event.getPlayer().isSneaking()) return;
		if (blockUtils.isBlockBreaker(event.getClickedBlock()) || blockUtils.isCondenser(event.getClickedBlock())) {
			event.setUseItemInHand(Event.Result.DENY);
			event.setUseInteractedBlock(Event.Result.DENY);
			event.setCancelled(true);
		}
	}

	/**
	 * This will prevent hoppers from adding/taking items from the GUI
	 */
	@EventHandler
	public void preventTransfer (InventoryMoveItemEvent event) {
		Inventory dest = event.getDestination();
		if (dest.getHolder() != null) {
			InventoryHolder holder = dest.getHolder();
			if (holder instanceof Dropper) {
				Dropper dropper = (Dropper) holder;
				if (blockUtils.isBlockBreaker(dropper.getBlock())) event.setCancelled(true);
				if (blockUtils.isCondenser(dropper.getBlock())) event.setCancelled(true);
			}
		}

		Inventory src = event.getSource();
		if (src.getHolder() != null) {
			InventoryHolder holder = src.getHolder();
			if (holder instanceof Dropper) {
				Dropper dropper = (Dropper) holder;
				if (blockUtils.isBlockBreaker(dropper.getBlock())) event.setCancelled(true);
				if (blockUtils.isCondenser(dropper.getBlock())) event.setCancelled(true);
			}
		}
	}

}
