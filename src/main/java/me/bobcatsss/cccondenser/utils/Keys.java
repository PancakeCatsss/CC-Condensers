package me.bobcatsss.cccondenser.utils;

import me.bobcatsss.cccondenser.CCCondenser;
import org.bukkit.NamespacedKey;

public class Keys {

    public final static NamespacedKey CONDENSER;
    public final static NamespacedKey BLOCK_BREAKER;
    public final static NamespacedKey BLOCK_BREAKER_RECIPE;

    public final static NamespacedKey BLOCK_LEVEL;
    public final static NamespacedKey BLOCK_COOLDOWN;

    static {
        BLOCK_LEVEL = new NamespacedKey(CCCondenser.getInstance(), "level");
        BLOCK_COOLDOWN = new NamespacedKey(CCCondenser.getInstance(), "cooldown");

        CONDENSER = new NamespacedKey(CCCondenser.getInstance(), "item_condenser");
        BLOCK_BREAKER = new NamespacedKey(CCCondenser.getInstance(), "block_breaker");
        BLOCK_BREAKER_RECIPE = new NamespacedKey(CCCondenser.getInstance(), "blockbreaker_recipe");
    }
}
