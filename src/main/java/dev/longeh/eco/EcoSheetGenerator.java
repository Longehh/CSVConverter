package dev.longeh.eco;

import dev.longeh.eco.loader.ShopLoader;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EcoSheetGenerator extends JavaPlugin {

    @Override
    public void onEnable() {
        saveDefaultConfig();
        new ShopLoader(this);
    }

    @Override
    public void onDisable() {

    }

    public static void convert(List<String[]> list, File file) throws IOException {
        try (PrintWriter pw = new PrintWriter(file)) {
            list.stream().map(EcoSheetGenerator::convertToCSV).forEach(pw::println);
        }
    }

    public static String convertToCSV(String[] data) {
        return Stream.of(data).map(EcoSheetGenerator::escapeSpecialCharacters).collect(Collectors.joining(","));
    }

    public static String escapeSpecialCharacters(String data) {
        String escapedData = data.replaceAll("\\R", " ");
        if (data.contains(",") || data.contains("\"") || data.contains("'")) {
            data = data.replace("\"", "\"\"");
            escapedData = "\"" + data + "\"";
        }
        return escapedData;
    }
}
