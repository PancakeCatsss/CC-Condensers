package me.bobcatsss.cccondenser.blocks;

import com.google.common.collect.Lists;
import lib.brainsynder.item.ItemBuilder;
import me.bobcatsss.cccondenser.utils.BlockDataHandler;
import org.bukkit.Location;
import org.bukkit.block.BlockState;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public abstract class BlockTask<BS extends BlockState> {
    private BlockDataHandler dataHandler;

    public BlockTask () {
        dataHandler = new BlockDataHandler();
    }

    public BlockTask setDataHandler(BlockDataHandler dataHandler) {
        this.dataHandler = dataHandler;
        return this;
    }

    public BlockDataHandler getDataHandler() {
        return dataHandler;
    }

    public abstract String getName ();

    public abstract void run (BS state, Location location);

    public boolean canExecute (BS state) {
        return true;
    }

    /**
     * This method checks if the block can handle item inputs
     */
    public boolean canHandleItems (BS state, ItemStack stack) {
        return false;
    }

    public void handleItemInput (BS state, ItemStack stack) {}

    public void onPlace (BS state) {}
    public void onBreak (BS state) {}

    public List<String> additionalData () {
        return Lists.newArrayList();
    }

    public abstract ItemBuilder getItem ();
}
