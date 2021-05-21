package me.bobcatsss.cccondenser.upgrades;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import lib.brainsynder.item.ItemBuilder;
import me.bobcatsss.cccondenser.CCCondenser;
import me.bobcatsss.cccondenser.blocks.BlockTask;

public class UpgradeGUI implements InventoryHolder {
	
	private final Inventory inventory;
	private final BlockTask blockTask;
	private boolean upgradeAvailable = false;
	
	public UpgradeGUI(BlockTask task, Player player) {
		this.blockTask = task;
		this.inventory = Bukkit.createInventory(this, 9, "Block Breaker Upgrades");
		int blocks = blockTask.getDataHandler().getBlocksBroken();
		int level = blockTask.getDataHandler().getLevel();
		this.inventory.setItem(4, getUpgradeItem(blocks, level));
		player.openInventory(inventory);
		update();
	}
	
	private ItemStack getUpgradeItem(int blocksBroken, int level) {
		ItemBuilder builder = new ItemBuilder(Material.BEACON);
		builder.withName("&6Upgrade").addLore("&fCurrent level: &a" + level).addLore("&fBlocks Broken: &a" + blocksBroken);
		if(canUpgrade(blocksBroken, level)) {
			builder.addLore("&m &m &m &m &m &m &m &m &m &m &m &m &m &m &m &m &m &m &m &m &m &m &m &m &m &m &m &m &m &m &m &m");
			builder.addLore("&aUpgrade Available");
			builder.addLore("&fCost: &a$ &a" + 1000 * level);
			builder.addLore("&2Click to purchase");
			builder.addLore("&m &m &m &m &m &m &m &m &m &m &m &m &m &m &m &m &m &m &m &m &m &m &m &m &m &m &m &m &m &m &m &m");
		}
		return builder.build();
	}
	
	private boolean canUpgrade(int blocksBroken, int level) {
		if(level == 5) return false;
		switch(level) {
		case 1:
			if(blocksBroken >= 1000) return true;
		case 2:
			if(blocksBroken >= 2000) return true;
		case 3:
			if(blocksBroken >= 3000) return true;
		case 4:
			if(blocksBroken >= 4000) return true;
		}
		return false;
	}
	
	private void update() {
		new BukkitRunnable() {

			@Override
			public void run() {
				if(inventory == null || inventory.getViewers().isEmpty()) {
					cancel();
					return;
				}
				inventory.setItem(4, getUpgradeItem(blockTask.getDataHandler().getBlocksBroken(), blockTask.getDataHandler().getLevel()));
				for(HumanEntity e : inventory.getViewers()) {
					((Player)e).updateInventory();
				}
			}
		}.runTaskTimer(CCCondenser.getInstance(), 1L, 1L);
	}
	
	public void handle(Player whoClicked, ItemStack clicked) {
		if(clicked.getType() != Material.BEACON) return;
		if(upgradeAvailable) {
			blockTask.getDataHandler().setLevel(2);
			blockTask.getDataHandler().setBlocksBroken(0);
			whoClicked.closeInventory();
			whoClicked.sendMessage("Block Breaker upgraded to level " + blockTask.getDataHandler().getLevel());
		}
	}

	@Override
	public Inventory getInventory() {
		return inventory;
	}

}
