package pl.galajus.twitchunt;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.Objects;

public class Translations {

    private final Twitchunt twitchunt;

    private FileConfiguration translations;

    public Translations(Twitchunt twitchunt) {
        this.twitchunt = twitchunt;
        this.initialize();
    }

    public String getTranslation(String key) {
        return Objects.requireNonNullElseGet(translations.getString(key, null), () -> "[Translation Key: " + key + "]");
    }

    public void initialize() {
        File translationFile = new File(twitchunt.getDataFolder(), "translation.yml");
        if (!translationFile.exists()) {
            twitchunt.saveResource("translation.yml", false);
        }
        translations = YamlConfiguration.loadConfiguration(translationFile);
    }

}
