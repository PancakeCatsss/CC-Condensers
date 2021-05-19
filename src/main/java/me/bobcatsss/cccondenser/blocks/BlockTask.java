package me.bobcatsss.cccondenser.blocks;

import lib.brainsynder.item.ItemBuilder;
import org.bukkit.Location;
import org.bukkit.block.BlockState;

public abstract class BlockTask<BS extends BlockState> {
    public abstract String getName ();

    public abstract void run (BS state, Location location);

    public boolean canExecute (BS state) {
        return true;
    }


    public abstract ItemBuilder getItem ();
}
