package dev.longeh.eco.loader;

import dev.longeh.eco.EcoSheetGenerator;
import dev.longeh.eco.utils.ItemUtils;
import net.brcdev.shopgui.ShopGuiPlugin;
import net.brcdev.shopgui.shop.Shop;
import net.brcdev.shopgui.shop.ShopItem;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShopLoader {

    private final int taskID;
    private EcoSheetGenerator ecoSheetGenerator;

    public ShopLoader(EcoSheetGenerator ecoSheetGenerator) {
        this.ecoSheetGenerator = ecoSheetGenerator;

        this.taskID = Bukkit.getScheduler().runTaskTimer(ecoSheetGenerator, this::loadShopPrices, 20L, 20L).getTaskId();
    }

    private void loadShopPrices() {
        if(!ShopGuiPlugin.getInstance().getShopManager().shops.isEmpty()) {
            for(Map.Entry<String, Shop> shopEntry : ShopGuiPlugin.getInstance().getShopManager().shops.entrySet()) {
                List<String[]> shopData = new ArrayList<>();
                for(ShopItem shopItem : shopEntry.getValue().getShopItems()) {
                    String buyPrice = "$" + shopItem.getBuyPrice();
                    String sellPrice = "$" + shopItem.getSellPrice();
                    shopData.add(new String[]{ItemUtils.getItemName(shopItem.getItem()), buyPrice, sellPrice});
                }
                try {
                    EcoSheetGenerator.convert(shopData, new File(ecoSheetGenerator.getDataFolder() + "/" + shopEntry.getKey() + ".csv"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            Bukkit.getScheduler().cancelTask(taskID);
        }
    }
}
