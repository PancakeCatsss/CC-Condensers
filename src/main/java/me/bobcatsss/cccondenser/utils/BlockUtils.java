package me.bobcatsss.cccondenser.utils;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Dropper;
import org.bukkit.persistence.PersistentDataType;

public class BlockUtils {
	
	public boolean isCondenser(Block block) {
		if(block == null) return false;
		if(block.getType() != Material.DROPPER) return false;
		Dropper dropper = (Dropper) block.getState();
		return dropper.getPersistentDataContainer().has(Keys.CONDENSER, PersistentDataType.STRING);
	}

}
