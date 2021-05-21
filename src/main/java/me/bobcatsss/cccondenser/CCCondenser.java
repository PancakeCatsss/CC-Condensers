package me.bobcatsss.cccondenser;

import com.google.common.collect.Lists;
import lib.brainsynder.commands.CommandRegistry;
import lib.brainsynder.files.YamlFile;
import me.bobcatsss.cccondenser.blocks.BlockManager;
import me.bobcatsss.cccondenser.commands.BlockCommand;
import me.bobcatsss.cccondenser.handler.BlockListener;
import me.bobcatsss.cccondenser.utils.Keys;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class CCCondenser extends JavaPlugin {
	
	public static CCCondenser instance;
	private BlockManager blockManager;
	private YamlFile configuration;

	public static int COOLDOWN_TICKS = 35;
	public static int COOLDOWN_DECREASE = 5;

	
	@Override
	public void onEnable() {
		instance = this;

		configuration = new YamlFile(getDataFolder(), "config.yml") {
			@Override
			public void loadDefaults() {
				addDefault(
						"block-breaker.target-types", Lists.newArrayList("[stone]"),
						"List of blocks allowed to be broken by the Block Breaker\n" +
								"If the name has the [] brackets around it, it will just check if the material contains that word\n" +
								"Example: [stone] will target any material that has stone in the name");
				addDefault("block-breaker.cooldown-delay-ticks", 35,
						"This handles how many ticks if delay the Block Breaker has before it can break another block");
				addDefault("block-breaker.cooldown-delay-decrease-per-level", 5,
						"Each level will have the delay decreased by the set number of ticks (EG: 5 ticks)");
			}
		};
		COOLDOWN_TICKS = configuration.getInt("block-breaker.cooldown-delay-ticks", 35);
		COOLDOWN_DECREASE = configuration.getInt("block-breaker.cooldown-delay-decrease-per-level", 5);

		blockManager = new BlockManager();

		try {
			new CommandRegistry<>(this).register(new BlockCommand(blockManager));
		} catch (Exception e) {
			e.printStackTrace();
		}

		getServer().getPluginManager().registerEvents(new BlockListener(this), this);

		Bukkit.getOnlinePlayers().forEach(this::checkRecipes);
	}

	public void checkRecipes (Player player) {
		if (!player.getDiscoveredRecipes().contains(Keys.BLOCK_BREAKER_RECIPE))
			player.discoverRecipe(Keys.BLOCK_BREAKER_RECIPE);
	}

	@Override
	public void onDisable() {
		instance = null;
		blockManager.unload();
		blockManager = null;
	}

	public BlockManager getBlockManager() {
		return blockManager;
	}

	public static CCCondenser getInstance() {
		return instance;
	}

	public YamlFile getConfiguration() {
		return configuration;
	}
}
