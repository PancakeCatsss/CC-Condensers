package me.bobcatsss.cccondenser.utils;

import org.bukkit.NamespacedKey;

import me.bobcatsss.cccondenser.CCCondenser;

public class Keys {

    public final static NamespacedKey CONDENSER;

    static {
        CONDENSER = new NamespacedKey(CCCondenser.getInstance(), "wand");
    }
}
