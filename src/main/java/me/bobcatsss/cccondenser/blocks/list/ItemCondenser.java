package me.bobcatsss.cccondenser.blocks.list;

import com.google.common.collect.Lists;
import lib.brainsynder.item.ItemBuilder;
import me.bobcatsss.cccondenser.blocks.BlockTask;
import me.bobcatsss.cccondenser.utils.Keys;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Dropper;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

import java.util.List;

public class ItemCondenser extends BlockTask<Dropper> {
    private final List<Material> blockable;

    public ItemCondenser () {
        blockable = Lists.newArrayList(
                Material.COAL,
                Material.CHARCOAL,
                Material.IRON_INGOT,
                Material.IRON_NUGGET,
                Material.LAPIS_LAZULI,
                Material.GOLD_NUGGET,
                Material.GOLD_INGOT,
                Material.QUARTZ,
                Material.REDSTONE,
                Material.DIAMOND,
                Material.EMERALD
        );


        ShapedRecipe recipe = new ShapedRecipe(Keys.BLOCK_BREAKER_RECIPE, getItem().build());
        recipe.shape(
                "HNH",
                "DOD",
                "HDH"
        );
        recipe.setIngredient('D', Material.DIAMOND_BLOCK);
        recipe.setIngredient('N', Material.NETHERITE_INGOT);
        recipe.setIngredient('O', Material.DROPPER);
        recipe.setIngredient('H', Material.HOPPER);
        try {
            Bukkit.addRecipe(recipe);
        }catch (Exception ignored) {}
    }


    @Override
    public String getName() {
        return "Item Condenser";
    }

    @Override
    public void run(Dropper state, Location location) {

    }

    @Override
    public void onPlace(Dropper state) {
        Inventory inv = state.getInventory();
        inv.clear();
        for(int x = 0; x < inv.getSize(); x++) {
            if (inv.getItem(x) == null) {
                inv.setItem(x, new ItemStack(Material.GRAY_STAINED_GLASS_PANE));
            }
        }
        inv.setItem(4, new ItemStack(Material.AIR));
    }

    @Override
    public boolean canHandleItems(Dropper state, ItemStack stack) {
        ItemStack current = state.getInventory().getItem(4);
        if (blockable.contains(stack.getType())) {
            if (current == null) return true;
            return (current.getType() == stack.getType());
        }

        return false;
    }

    @Override
    public void handleItemInput(Dropper state, ItemStack stack) {
        ItemStack current = state.getInventory().getItem(4);




    }

    @Override
    public ItemBuilder getItem() {
        return new ItemBuilder(Material.DROPPER)
                .withName("&eItem Condenser")
                .addLore(
                        "&7When an item that can be compressed gets put in the block",
                        "&7it will output the compressed version of it."
                );
    }
}
