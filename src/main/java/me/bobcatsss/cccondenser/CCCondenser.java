package me.bobcatsss.cccondenser;

import me.bobcatsss.cccondenser.utils.BlockUtils;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class CCCondenser extends JavaPlugin {
	
	public static CCCondenser instance;
	private BlockUtils blockUtils;
	
	@Override
	public void onEnable() {
		instance = this;
		this.blockUtils = new BlockUtils();
		registerHandlers();
	}
	
	@Override
	public void onDisable() {
		instance = null;
	}
	
	private void registerHandlers() {
		PluginManager m = Bukkit.getPluginManager();
	}
	
	public BlockUtils getBlockUtils() {
		return blockUtils;
	}
	
	public static CCCondenser getInstance() {
		return instance;
	}

}
