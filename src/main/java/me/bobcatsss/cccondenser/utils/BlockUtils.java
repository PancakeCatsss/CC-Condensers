package me.bobcatsss.cccondenser.utils;

import me.bobcatsss.cccondenser.blocks.list.BlockBreaker;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Dropper;
import org.bukkit.persistence.PersistentDataType;

public class BlockUtils {
	public BlockBreaker blockBreaker;

	public BlockUtils () {
		blockBreaker = new BlockBreaker();
	}

	public boolean isCondenser(Block block) {
		if(block == null) return false;
		if(block.getType() != Material.DROPPER) return false;
		Dropper dropper = (Dropper) block.getState();
		return dropper.getPersistentDataContainer().has(Keys.CONDENSER, PersistentDataType.STRING);
	}

	public boolean isBlockBreaker(Block block) {
		if(block == null) return false;
		if(block.getType() != Material.DROPPER) return false;
		Dropper dropper = (Dropper) block.getState();
		return dropper.getPersistentDataContainer().has(Keys.BLOCK_BREAKER, PersistentDataType.STRING);
	}

}
