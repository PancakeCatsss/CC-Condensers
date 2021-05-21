package me.bobcatsss.cccondenser.utils;

import me.bobcatsss.cccondenser.CCCondenser;
import org.bukkit.block.Dropper;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataHolder;
import org.bukkit.persistence.PersistentDataType;

public class BlockDataHandler {


    private int level = 0;
    private long cooldownTarget = 0;
    private int blocksBroken = 0;

    public BlockDataHandler () {}

    public BlockDataHandler (Dropper holder) {
        PersistentDataContainer container = holder.getPersistentDataContainer();
        level = container.getOrDefault(Keys.BLOCK_LEVEL, PersistentDataType.INTEGER, 1);
        blocksBroken = container.getOrDefault(Keys.BLOCKS_BROKEN, PersistentDataType.INTEGER, 0);
        cooldownTarget = container.getOrDefault(Keys.BLOCK_COOLDOWN, PersistentDataType.LONG, 0L);
    }

    public BlockDataHandler (PersistentDataHolder holder) {
        PersistentDataContainer container = holder.getPersistentDataContainer();
        level = container.getOrDefault(Keys.BLOCK_LEVEL, PersistentDataType.INTEGER, 1);
        cooldownTarget = container.getOrDefault(Keys.BLOCK_COOLDOWN, PersistentDataType.LONG, 0L);
        blocksBroken = container.getOrDefault(Keys.BLOCKS_BROKEN, PersistentDataType.INTEGER, 0);
    }

    public void update (Dropper holder) {
        PersistentDataContainer container = holder.getPersistentDataContainer();
        container.set(Keys.BLOCK_LEVEL, PersistentDataType.INTEGER, level);
        container.set(Keys.BLOCK_COOLDOWN, PersistentDataType.LONG, cooldownTarget);
        container.set(Keys.BLOCKS_BROKEN, PersistentDataType.INTEGER, blocksBroken);
        holder.update(true);
    }

    public long cooldownTarget () {
        return cooldownTarget;
    }

    public void resetCooldown () {
        int delay = 50* CCCondenser.COOLDOWN_TICKS;
        for (int i = 0; i != level; i++) {
            delay = (delay - (50*CCCondenser.COOLDOWN_DECREASE));
        }

        cooldownTarget = (System.currentTimeMillis()+delay);
    }

    public boolean isOffCooldown () {
        return System.currentTimeMillis() > cooldownTarget;
    }
    
    public int getBlocksBroken() {
    	return blocksBroken;
    }
    
    public void setBlocksBroken(int broken) {
    	this.blocksBroken = broken;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
