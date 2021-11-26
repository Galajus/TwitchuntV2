package pl.galajus.twitchunt;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import pl.galajus.twitchunt.Dependency.DependencyResolver;
import pl.galajus.twitchunt.ObjectsManager.PluginPollChoice;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ConfigReader {

    private final Twitchunt twitchunt;
    private final DependencyResolver dependencyResolver;

    private FileConfiguration mainConfig;
    private FileConfiguration effectsConfig;
    private FileConfiguration customEffectsConfig;

    public ConfigReader(Twitchunt twitchunt, DependencyResolver dependencyResolver) {
        this.twitchunt = twitchunt;
        this.dependencyResolver = dependencyResolver;

        this.readConfigs();
    }

    public void readConfigs() {
        File f = new File(twitchunt.getDataFolder(), "effects.yml");
        File f2 = new File(twitchunt.getDataFolder(), "customEffects.yml");
        File fDefault = new File(twitchunt.getDataFolder(), "config.yml");
        if (!f.exists()) {
            twitchunt.saveResource("effects.yml", false);
        }
        if (!f2.exists()) {
            twitchunt.saveResource("customEffects.yml", false);
        }
        twitchunt.saveResource("Config-help.txt", true);

        mainConfig = twitchunt.getConfig();
        effectsConfig = YamlConfiguration.loadConfiguration(f);
        customEffectsConfig = YamlConfiguration.loadConfiguration(f2);
        try {
            mainConfig.load(fDefault);
        } catch (Exception ex) {
            dependencyResolver.errorLogToConsole("configLoadError");
        }
        twitchunt.saveDefaultConfig();

        this.validateConfig();
    }

    public String getChatToken() {
        return mainConfig.getString("chatToken");
    }

    public String getClientID() {
        return mainConfig.getString("clientID");
    }

    public String getClientSecret() {
        return mainConfig.getString("clientSecret");
    }

    public String getChannelName() {
        return mainConfig.getString("channelName");
    }

    public String getStartMessage() {
        return mainConfig.getString("startMessage");
    }

    public List<String> getHuntedPlayersAsStrings() {
        return mainConfig.getStringList("huntedPlayers");
    }

    public List<String> getExcludedSenders() {
        return mainConfig.getStringList("excludeSenders");
    }

    public List<Player> getHuntedPlayers() {
        List<String> stringPlayers = mainConfig.getStringList("huntedPlayers");
        List<Player> huntedPlayers = new ArrayList<>();
        for (String stringPlayer : stringPlayers) {
            Player p = Bukkit.getPlayerExact(stringPlayer);
            if (p != null) huntedPlayers.add(p);
        }
        return huntedPlayers;
    }

    public Long getPollDuration() {
        return mainConfig.getLong("pollDuration");
    }

    public Long getPollInterval() {
        return mainConfig.getLong("pollInterval");
    }

    public Integer getOptionsPerPoll() {
        return mainConfig.getInt("optionsPerPoll");
    }

    public String getPollTitle() {
        return mainConfig.getString("pollTitle");
    }

    public boolean getPollInstaStart() {
        return mainConfig.getBoolean("pollInstaStart");
    }

    public boolean getForceSpigotUsage() {
        return mainConfig.getBoolean("forceSpigotUsage");
    }

    public boolean isSubsEffectsEnabled() {
        return mainConfig.getBoolean("SubsEnabled");
    }

    public boolean isBitsEffectsEnabled() {
        return mainConfig.getBoolean("BitsEnabled");
    }

    public boolean isTwitchMessageEnabled() {
        return mainConfig.getBoolean("twitchMessagesEnabled");
    }

    public List<Integer> getBitsOne() {
        return mainConfig.getIntegerList("BitsEffects.LevelOne");
    }

    public List<Integer> getBitsTwo() {
        return mainConfig.getIntegerList("BitsEffects.LevelTwo");
    }

    public List<Integer> getBitsThree() {
        return mainConfig.getIntegerList("BitsEffects.LevelThree");
    }

    public List<Integer> getSubOne() {
        return mainConfig.getIntegerList("SubsEffects.TierOne");
    }

    public List<Integer> getSubTwo() {
        return mainConfig.getIntegerList("SubsEffects.TierTwo");
    }

    public List<Integer> getSubThree() {
        return mainConfig.getIntegerList("SubsEffects.TierThree");
    }

    public Double getBitsThresholdOne() {
        return mainConfig.getDouble("BitsThresholds.FirstLevel");
    }

    public Double getBitsThresholdTwo() {
        return mainConfig.getDouble("BitsThresholds.SecondLevel");
    }

    public Double getBitsThresholdThree() {
        return mainConfig.getDouble("BitsThresholds.ThirdLevel");
    }

    public boolean addHunted(String hunted) {
        List<String> huntedPlayers = getHuntedPlayersAsStrings();
        if (!this.getHuntedPlayersAsStrings().contains(hunted)) {
            huntedPlayers.add(hunted);
            mainConfig.set("huntedPlayers", getHuntedPlayersAsStrings().add(hunted));
            mainConfig.options().header("Config instructions in file: Config-help.txt");
            twitchunt.saveConfig();
            return true;
        }
        return false;
    }

    public void removeHunted(String hunted) {
        List<String> huntedPlayers = getHuntedPlayersAsStrings();
        huntedPlayers.remove(hunted);
        mainConfig.set("huntedPlayers", huntedPlayers);
        mainConfig.options().header("Config instructions in file: Config-help.txt");
        twitchunt.saveConfig();
    }

    public void setPollDuration(long duration) {
        mainConfig.set("pollDuration", duration);
        mainConfig.options().header("Config instructions in file: Config-help.txt");
        twitchunt.saveConfig();
    }

    public void setPollInterval(long interval) {
        mainConfig.set("pollInterval", interval);
        mainConfig.options().header("Config instructions in file: Config-help.txt");
        twitchunt.saveConfig();
    }

    public void setOptionsPerPoll(int amountOfOptions) {
        mainConfig.set("optionsPerPoll", amountOfOptions);
        mainConfig.options().header("Config instructions in file: Config-help.txt");
        twitchunt.saveConfig();
    }

    public void setTwitchMessages(boolean showMessagesFromTwitch) {
        mainConfig.set("optionsPerPoll", showMessagesFromTwitch);
        mainConfig.options().header("Config instructions in file: Config-help.txt");
        twitchunt.saveConfig();
    }

    public void setPollInstaStart(boolean instaStart) {
        mainConfig.set("pollInstaStart", instaStart);
        mainConfig.options().header("Config instructions in file: Config-help.txt");
        twitchunt.saveConfig();
    }

    private void validateConfig() {
        if (this.getPollDuration() < 20) {
            dependencyResolver.warningLogToConsole("configPollDurationInvalid");
            this.setPollDuration(30);
        }

        if (this.getPollInterval() < (this.getPollDuration() + 4)) {
            dependencyResolver.warningLogToConsole("configPollIntervalInvalid");
            this.setPollInterval(this.getPollDuration() + 10L);
        }

        this.loadPluginEffects();
    }

    private void loadPluginEffects() {

        ConfigurationSection configEffectList = effectsConfig.getConfigurationSection("effects");
        if (configEffectList != null) {
            for (Map.Entry<String, Object> entry : configEffectList.getValues(false).entrySet()) {

                if (effectsConfig.getBoolean("effects." + entry.getKey() + ".enabled")) {
                    int id = effectsConfig.getInt("effects." + entry.getKey() + ".id");
                    String lore = effectsConfig.getString("effects." + entry.getKey() + ".lore");
                    if (lore == null) {
                        lore = "[empty lore]";
                    }
                    if (lore.length() > 25) {
                        dependencyResolver.errorLogToConsole("tooLongLore1");
                        dependencyResolver.errorLogToConsole("tooLongLore2");
                        Bukkit.getPluginManager().disablePlugin(twitchunt);
                        return;
                    }
                    new PluginPollChoice(id, lore);
                }
            }
        }
        this.loadCustomEffects();
    }

    private void loadCustomEffects() {
        ConfigurationSection configCustomEffectList = customEffectsConfig.getConfigurationSection("effects");
        if (configCustomEffectList != null) {
            for (Map.Entry<String, Object> entry : configCustomEffectList.getValues(false).entrySet()) {

                if (customEffectsConfig.getBoolean("effects." + entry.getKey() + ".enabled")) {
                    int id = customEffectsConfig.getInt("effects." + entry.getKey() + ".id");
                    String lore = customEffectsConfig.getString("effects." + entry.getKey() + ".lore");
                    String command1 = customEffectsConfig.getString("effects." + entry.getKey() + ".command");
                    String command2 = customEffectsConfig.getString("effects." + entry.getKey() + ".command2");
                    String commandDelay1 = customEffectsConfig.getString("effects." + entry.getKey() + ".commandDelay");
                    String commandDelay2 = customEffectsConfig.getString("effects." + entry.getKey() + ".commandDelay2");
                    int delay = customEffectsConfig.getInt("effects." + entry.getKey() + ".delay");
                    if (lore == null) {
                        lore = "[empty lore]";
                    }
                    if (lore.length() > 25) {
                        dependencyResolver.errorLogToConsole("tooLongLore1");
                        dependencyResolver.errorLogToConsole("tooLongLore2");
                        Bukkit.getPluginManager().disablePlugin(twitchunt);
                        return;
                    }
                    new PluginPollChoice(id, lore, command1, command2, delay, commandDelay1, commandDelay2);
                }
            }
        }
    }

    //PAID EFFECTS LOAD?
}
