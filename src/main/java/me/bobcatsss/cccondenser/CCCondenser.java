package me.bobcatsss.cccondenser;

import lib.brainsynder.commands.CommandRegistry;
import me.bobcatsss.cccondenser.commands.BlockCommand;
import me.bobcatsss.cccondenser.handler.BlockListener;
import me.bobcatsss.cccondenser.utils.BlockUtils;
import org.bukkit.plugin.java.JavaPlugin;

public class CCCondenser extends JavaPlugin {
	
	public static CCCondenser instance;
	private BlockUtils blockUtils;
	
	@Override
	public void onEnable() {
		instance = this;
		this.blockUtils = new BlockUtils();

		try {
			new CommandRegistry<>(this).register(new BlockCommand(blockUtils));
		} catch (Exception e) {
			e.printStackTrace();
		}

		getServer().getPluginManager().registerEvents(new BlockListener(this), this);
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
