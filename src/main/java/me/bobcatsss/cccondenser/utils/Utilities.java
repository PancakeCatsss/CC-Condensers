package me.bobcatsss.cccondenser.utils;

import java.util.concurrent.TimeUnit;

public class Utilities {
    public static long convertTime(long ticks, TimeUnit unit) {
        long milliseconds = 50 * ticks;
        return TimeUnit.MILLISECONDS.convert(milliseconds, unit);
    }
}
