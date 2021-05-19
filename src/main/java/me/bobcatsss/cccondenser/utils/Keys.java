package me.bobcatsss.cccondenser.utils;

import me.bobcatsss.cccondenser.CCCondenser;
import org.bukkit.NamespacedKey;

public class Keys {

    public final static NamespacedKey CONDENSER;

    static {
        CONDENSER = new NamespacedKey(CCCondenser.getInstance(), "condenser");
    }
}
