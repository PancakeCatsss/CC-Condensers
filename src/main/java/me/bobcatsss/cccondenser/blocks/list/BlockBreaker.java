package me.bobcatsss.cccondenser.blocks.list;

import com.google.common.collect.Lists;
import lib.brainsynder.item.ItemBuilder;
import lib.brainsynder.particle.Particle;
import lib.brainsynder.particle.ParticleMaker;
import lib.brainsynder.utils.AdvString;
import me.bobcatsss.cccondenser.CCCondenser;
import me.bobcatsss.cccondenser.blocks.BlockTask;
import me.bobcatsss.cccondenser.utils.Keys;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Dropper;
import org.bukkit.block.data.type.Dispenser;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;

public class BlockBreaker extends BlockTask<Dropper> {
    private final List<Material> materials;
    private final ParticleMaker particle;

    public BlockBreaker () {
        materials = Lists.newArrayList();
        CCCondenser.getInstance().getConfiguration().getStringList("block-breaker.target-types").forEach(key -> {
            for (Material material : Material.values()) {
                if (key.startsWith("[") && key.endsWith("]")) {
                    if (material.name().toLowerCase().contains(AdvString.between("[", "]",key).toLowerCase()))
                        materials.add(material);
                    continue;
                }
                if (material.name().equalsIgnoreCase(key)) materials.add(material);
            }
        });

        particle = new ParticleMaker(Particle.SMOKE_LARGE, 5, 0.3);

        ShapedRecipe recipe = new ShapedRecipe(Keys.BLOCK_BREAKER_RECIPE, getItem().build());
        recipe.shape(
                "DND",
                "NON",
                "DND"
        );
        recipe.setIngredient('D', Material.DIAMOND_BLOCK);
        recipe.setIngredient('N', Material.NETHERITE_INGOT);
        recipe.setIngredient('O', Material.DROPPER);
        try {
            Bukkit.addRecipe(recipe);
        }catch (Exception ignored) {}
    }

    @Override
    public List<String> additionalData() {
        List<String> data = super.additionalData();
        materials.forEach(material -> data.add(material.name()));
        return data;
    }

    @Override
    public String getName() {
        return "Block Breaker";
    }

    @Override
    public void run(Dropper state, Location location) {
        Block target = state.getBlock().getRelative(((Dispenser)state.getBlockData()).getFacing());

        if (getDataHandler().isOffCooldown()) {
            target.breakNaturally();
            getDataHandler().setBlocksBroken(getDataHandler().getBlocksBroken() + 1);
            getDataHandler().resetCooldown();
            getDataHandler().update(state);
        }else{
            target.getWorld().getNearbyEntities(target.getLocation(), 10,10,10,entity -> entity instanceof Player).forEach(entity -> {
                particle.sendToPlayer((Player) entity, target.getLocation());
            });
        }
    }

    @Override
    public boolean canExecute(Dropper state) {
        Block target = state.getBlock().getRelative(((Dispenser)state.getBlockData()).getFacing());
        Material material = target.getType();
        return materials.contains(material);
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
