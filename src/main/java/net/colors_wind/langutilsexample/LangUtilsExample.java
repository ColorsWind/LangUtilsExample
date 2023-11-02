package net.colors_wind.langutilsexample;

import com.google.common.collect.Lists;
import com.meowj.langutils.LangUtils;
import com.meowj.langutils.lang.LanguageHelper;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public final class LangUtilsExample extends JavaPlugin {

    @Override
    public void onEnable() {
        try {
            saveLocate(new File(getDataFolder(), "item.yml"), Arrays.asList(Material.values()), Material::name,
                    (Material m) -> LanguageHelper.getItemName(new ItemStack(m), "zh_CN"));
            saveLocate(new File(getDataFolder(), "biome.yml"), Arrays.asList(Biome.values()), Biome::name,
                    (Biome b) -> LanguageHelper.getBiomeName(b, "zh_CN"));
            saveLocate(new File(getDataFolder(), "entry.yml"), Arrays.asList(EntityType.values()), EntityType::name,
                    (EntityType e) -> LanguageHelper.getEntityName(e, "zh_CN"));
            saveLocate(new File(getDataFolder(), "enchantment.yml"), Arrays.asList(Enchantment.values()), Enchantment::getName,
                    (Enchantment e) -> LanguageHelper.getEnchantmentName(e, "zh_CN"));
            saveLocate(new File(getDataFolder(), "enchantment_level.yml"), IntStream.rangeClosed(1, 10).boxed().collect(Collectors.toList()),
                    (Integer i) -> "Level-" + i, (Integer i) -> LanguageHelper.getEnchantmentLevelName(i, "zh_CN"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }



    private <T> void saveLocate(File file, List<T> entries, Function<T, String> entryKey, Function<T, String> entryValue) throws IOException {
        if (file.exists()) {
            return;
        }
        YamlConfiguration config = new YamlConfiguration();
        for (T entry : entries) {
            config.set(entryKey.apply(entry), entryValue.apply(entry));
        }
        config.save(file);
    }


    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
