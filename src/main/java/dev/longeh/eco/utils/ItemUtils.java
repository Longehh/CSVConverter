package dev.longeh.eco.utils;

import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;

public class ItemUtils {

    public static String getItemName(org.bukkit.inventory.ItemStack item) {
        net.minecraft.server.v1_8_R3.ItemStack nmsStack = CraftItemStack.asNMSCopy(item);
        return nmsStack.getItem().a(nmsStack);
    }
}
