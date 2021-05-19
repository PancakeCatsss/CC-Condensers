package me.bobcatsss.cccondenser.blocks.list;

import lib.brainsynder.item.ItemBuilder;
import me.bobcatsss.cccondenser.blocks.BlockTask;
import me.bobcatsss.cccondenser.utils.Keys;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Dropper;
import org.bukkit.block.data.type.Dispenser;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class BlockBreaker extends BlockTask<Dropper> {
    @Override
    public String getName() {
        return "Block Breaker";
    }

    @Override
    public void run(Dropper state, Location location) {
        Block target = state.getBlock().getRelative(((Dispenser)state.getBlockData()).getFacing());
        Bukkit.broadcastMessage("Target: "+target.getType());
        target.breakNaturally();
    }

    @Override
    public boolean canExecute(Dropper state) {
        Block target = state.getBlock().getRelative(((Dispenser)state.getBlockData()).getFacing());
        Bukkit.broadcastMessage("Target: "+target.getType());
        Material material = target.getType();
        return (material.name().contains("STONE"));
    }

    @Override
    public ItemBuilder getItem() {
        return new ItemBuilder(Material.DROPPER)
                .withName("&eBlock Breaker").handleMeta(ItemMeta.class, itemMeta -> {
                    itemMeta.getPersistentDataContainer().set(Keys.BLOCK_BREAKER, PersistentDataType.STRING, "block");
                    return itemMeta;
                });
    }
}
