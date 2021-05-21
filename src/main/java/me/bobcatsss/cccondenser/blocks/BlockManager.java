package me.bobcatsss.cccondenser.blocks;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import me.bobcatsss.cccondenser.blocks.list.BlockBreaker;
import me.bobcatsss.cccondenser.utils.BlockDataHandler;
import me.bobcatsss.cccondenser.utils.Keys;
import me.bobcatsss.cccondenser.utils.Tuple;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Dropper;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;
import java.util.Map;

public class BlockManager {
    private Map<NamespacedKey, BlockTask<Dropper>> blockMap;


    public BlockManager() {
        this.blockMap = Maps.newHashMap();
        blockMap.put(Keys.BLOCK_BREAKER, new BlockBreaker());
        //blockMap.put(Keys.CONDENSER, new ItemCondenser());
    }

    public void unload () {
        blockMap.clear();
        blockMap = null;
    }

    public BlockTask<Dropper> getTask (ItemStack stack) {
        if (!stack.hasItemMeta()) return null;
        ItemMeta meta = stack.getItemMeta();
        for (Map.Entry<NamespacedKey, BlockTask<Dropper>> entry : blockMap.entrySet()) {
            if (meta.getPersistentDataContainer().has(entry.getKey(), PersistentDataType.STRING))
                return entry.getValue().setDataHandler(new BlockDataHandler(meta));
        }
        return null;
    }

    public Tuple<NamespacedKey, BlockTask<Dropper>> getTaskEntry (ItemStack stack) {
        if (!stack.hasItemMeta()) return null;
        ItemMeta meta = stack.getItemMeta();
        for (Map.Entry<NamespacedKey, BlockTask<Dropper>> entry : blockMap.entrySet()) {
            if (meta.getPersistentDataContainer().has(entry.getKey(), PersistentDataType.STRING))
                return new Tuple<>(entry.getKey(), entry.getValue().setDataHandler(new BlockDataHandler(meta)));
        }
        return null;
    }

    public BlockTask<Dropper> getTask (Dropper holder) {
        for (Map.Entry<NamespacedKey, BlockTask<Dropper>> entry : blockMap.entrySet()) {
            if (holder.getPersistentDataContainer().has(entry.getKey(), PersistentDataType.STRING))
                return entry.getValue().setDataHandler(new BlockDataHandler(holder));
        }
        return null;
    }

    public BlockTask<Dropper> getTask (String name) {
        for (Map.Entry<NamespacedKey, BlockTask<Dropper>> entry : blockMap.entrySet()) {
            if (entry.getKey().getKey().equalsIgnoreCase(name.replace(" ", "_")))
                return entry.getValue();
        }
        return null;
    }

    public List<String> getNames () {
        List<String> names = Lists.newArrayList();
        blockMap.forEach((namespacedKey, blockTask) -> names.add(namespacedKey.getKey()));
        return names;
    }
}
