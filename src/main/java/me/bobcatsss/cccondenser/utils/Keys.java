package me.bobcatsss.cccondenser.utils;

import me.bobcatsss.cccondenser.CCCondenser;
import org.bukkit.NamespacedKey;

public class Keys {

    public final static NamespacedKey CONDENSER;
    public final static NamespacedKey BLOCK_BREAKER;

    static {
        CONDENSER = new NamespacedKey(CCCondenser.getInstance(), "condenser");
        BLOCK_BREAKER = new NamespacedKey(CCCondenser.getInstance(), "blockbreaker");
    }
}
