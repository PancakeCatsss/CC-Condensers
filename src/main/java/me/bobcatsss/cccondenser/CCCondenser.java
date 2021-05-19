package me.bobcatsss.cccondenser;

import me.bobcatsss.cccondenser.utils.BlockUtils;
import org.bukkit.plugin.java.JavaPlugin;

public class CCCondenser extends JavaPlugin {
	
	public static CCCondenser instance;
	private BlockUtils blockUtils;
	
	@Override
	public void onEnable() {
		instance = this;
		this.blockUtils = new BlockUtils();
	}
	
	@Override
	public void onDisable() {
		instance = null;
	}

	public BlockUtils getBlockUtils() {
		return blockUtils;
	}
	
	public static CCCondenser getInstance() {
		return instance;
	}

}
