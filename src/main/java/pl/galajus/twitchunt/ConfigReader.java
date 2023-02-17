package pl.galajus.twitchunt;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import pl.galajus.twitchunt.objectsmanager.PluginPollChoice;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class ConfigReader {

    private final Twitchunt twitchunt;

    private FileConfiguration mainConfig;
    private FileConfiguration effectsConfig;
    private FileConfiguration customEffectsConfig;

    private static final Integer MAX_LORE_LENGTH = 25;

    public ConfigReader(Twitchunt twitchunt) {
        this.twitchunt = twitchunt;

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
            Bukkit.getScheduler().runTaskLater(twitchunt, () -> twitchunt.getDependencyResolver().errorLogToConsole("configLoadError"), 1);

        }
        twitchunt.saveDefaultConfig();

        ConfigurationSection section = mainConfig.getConfigurationSection("twitchRedemptions");

        if (section != null) {
            Bukkit.getScheduler().runTaskLater(twitchunt, () -> {
                try {
                    for (var entry : section.getValues(false).entrySet()) {
                        twitchunt.getManager().addTwitchReward(entry.getKey(), Integer.parseInt(entry.getValue().toString()));
                    }
                } catch (Exception ex) {
                    twitchunt.getDependencyResolver().errorLogToConsole("IDNumberCast");
                }

            }, 1);
        }

        this.validateConfig();
    }

    public String getChatToken() {
        return mainConfig.getString("chatToken", "");
    }

    public String getClientID() {
        return mainConfig.getString("clientID", "");
    }

    public String getClientSecret() {
        return mainConfig.getString("clientSecret", "");
    }

    public String getChannelName() {
        return mainConfig.getString("channelName", "");
    }

    public String getStartMessage() {
        return mainConfig.getString("startMessage", "Twitchunt by Galajus ready to play!");
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
            if (p != null) {
                huntedPlayers.add(p);
            }
        }
        return huntedPlayers;
    }

    public Long getPollDuration() {
        return mainConfig.getLong("pollDuration", 30);
    }

    public Long getPollInterval() {
        return mainConfig.getLong("pollInterval", 60);
    }

    public Integer getOptionsPerPoll() {
        return mainConfig.getInt("optionsPerPoll", 3);
    }

    public String getPollTitle() {
        return mainConfig.getString("pollTitle", "Poll title not set");
    }

    public boolean getPollInstaStart() {
        return mainConfig.getBoolean("pollInstaStart", true);
    }

    public boolean getForceSpigotUsage() {
        return mainConfig.getBoolean("forceSpigotUsage", false);
    }

    public boolean isSubsEffectsEnabled() {
        return mainConfig.getBoolean("SubsEnabled", true);
    }

    public boolean isBitsEffectsEnabled() {
        return mainConfig.getBoolean("BitsEnabled", true);
    }

    public boolean isTwitchMessageEnabled() {
        return mainConfig.getBoolean("twitchMessagesEnabled", true);
    }

    public boolean isEnabledBroadcastResultOnMinecraft() {
        return mainConfig.getBoolean("broadcastResultOnMinecraftChat", true);
    }

    public boolean isEnabledSendResultOnTwitch() {
        return mainConfig.getBoolean("sendResultOnTwitchChat", true);
    }

    public boolean isEnabledShowResultTitleToHuntedPlayers() {
        return mainConfig.getBoolean("showResultTitleToHuntedPlayers", true);
    }

    public boolean isEnabledInfoOnTwitchAboutStartedPoll() {
        return mainConfig.getBoolean("showInfoOnTwitchAboutStartedPoll", true);
    }

    public boolean isEnabledBroadcastStartStreamInMinecraft() {
        return mainConfig.getBoolean("broadcastStartStreamOnMinecraft", true);
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
            mainConfig.set("huntedPlayers", huntedPlayers);
            mainConfig.options().setHeader(Collections.singletonList("Config instructions in file: Config-help.txt"));
            twitchunt.saveConfig();
            twitchunt.getEffectController().getEffectCaster().updateHunters();

            Bukkit.getScheduler().runTaskLater(twitchunt, () -> {
                if (twitchunt.getDependencyResolver().isPaper()) {
                    twitchunt.getEffectController().getPaperEffectCaster().updateHunters();
                }
            },1);
            return true;
        }
        return false;
    }

    public void removeHunted(String hunted) {
        List<String> huntedPlayers = getHuntedPlayersAsStrings();
        huntedPlayers.remove(hunted);
        mainConfig.set("huntedPlayers", huntedPlayers);
        mainConfig.options().setHeader(Collections.singletonList("Config instructions in file: Config-help.txt"));
        twitchunt.saveConfig();
        twitchunt.getEffectController().getEffectCaster().updateHunters();

        Bukkit.getScheduler().runTaskLater(twitchunt, () -> {
            if (twitchunt.getDependencyResolver().isPaper()) {
                twitchunt.getEffectController().getPaperEffectCaster().updateHunters();
            }
        },1);
    }

    public void setPollDuration(long duration) {
        mainConfig.set("pollDuration", duration);
        mainConfig.options().setHeader(Collections.singletonList("Config instructions in file: Config-help.txt"));
        twitchunt.saveConfig();
    }

    public void setPollInterval(long interval) {
        mainConfig.set("pollInterval", interval);
        mainConfig.options().setHeader(Collections.singletonList("Config instructions in file: Config-help.txt"));
        twitchunt.saveConfig();
    }

    public void setOptionsPerPoll(int amountOfOptions) {
        mainConfig.set("optionsPerPoll", amountOfOptions);
        mainConfig.options().setHeader(Collections.singletonList("Config instructions in file: Config-help.txt"));
        twitchunt.saveConfig();
    }

    public void setTwitchMessages(boolean showMessagesFromTwitch) {
        mainConfig.set("optionsPerPoll", showMessagesFromTwitch);
        mainConfig.options().setHeader(Collections.singletonList("Config instructions in file: Config-help.txt"));
        twitchunt.saveConfig();
    }

    public void setPollInstaStart(boolean instaStart) {
        mainConfig.set("pollInstaStart", instaStart);
        mainConfig.options().setHeader(Collections.singletonList("Config instructions in file: Config-help.txt"));
        twitchunt.saveConfig();
    }

    private void validateConfig() {
        if (this.getPollDuration() < 20) {
            Bukkit.getScheduler().runTaskLater(twitchunt, () -> twitchunt.getDependencyResolver().warningLogToConsole("configPollDurationInvalid"), 1);
            this.setPollDuration(30);
        }

        if (this.getPollInterval() < (this.getPollDuration() + 4)) {
            Bukkit.getScheduler().runTaskLater(twitchunt, () -> twitchunt.getDependencyResolver().warningLogToConsole("configPollIntervalInvalid"), 1);
            this.setPollInterval(this.getPollDuration() + 10L);
        }

        this.loadPluginEffects();

        Bukkit.getScheduler().runTaskLater(twitchunt, () -> {
            twitchunt.getEffectController().getEffectCaster().updateHunters();
            if (twitchunt.getDependencyResolver().isPaper()) {
                twitchunt.getEffectController().getPaperEffectCaster().updateHunters();
            }
        }, 1);

    }

    private void loadPluginEffects() {

        ConfigurationSection configEffectList = effectsConfig.getConfigurationSection("effects");
        if (configEffectList != null) {
            for (Map.Entry<String, Object> entry : configEffectList.getValues(false).entrySet()) {
                // Set default as -1 if the configuration section is not found
                int id = effectsConfig.getInt("effects." + entry.getKey() + ".id", -1);

                // If the id equals -1
                if (id < 0) {
                    // Message: An effect in the effects.yml is missing an id, this effect will be disabled
                    continue;
                }

                // Default can be passed as argument
                String lore = effectsConfig.getString("effects." + entry.getKey() + ".lore", "[empty lore]");

                // "Bad Practice" to disable the plugin whenever something is not correct.
                // "Good Practice" send error to the console, change the outcome
                // This could be to shorten the lore or to not include the effect
                if (lore.length() > MAX_LORE_LENGTH) {
                    twitchunt.getDependencyResolver().errorLogToConsole("LoreTooLong", lore);
                    lore = lore.substring(0, MAX_LORE_LENGTH);
                }

                // No need to check the boolean if you can pass it as argument
                new PluginPollChoice(id, lore, effectsConfig.getBoolean("effects." + entry.getKey() + ".enabled", true));
            }
        }
        this.loadCustomEffects();
    }

    private void loadCustomEffects() {
        ConfigurationSection configCustomEffectList = customEffectsConfig.getConfigurationSection("effects");
        if (configCustomEffectList != null) {
            for (Map.Entry<String, Object> entry : configCustomEffectList.getValues(false).entrySet()) {


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
                    Bukkit.getScheduler().runTaskLater(twitchunt, () -> {
                        twitchunt.getDependencyResolver().errorLogToConsole("tooLongLore1");
                        twitchunt.getDependencyResolver().errorLogToConsole("tooLongLore2");
                        Bukkit.getPluginManager().disablePlugin(twitchunt);
                    }, 1);
                    return;
                }
                if (customEffectsConfig.getBoolean("effects." + entry.getKey() + ".enabled")) {
                    new PluginPollChoice(id, lore, command1, command2, delay, commandDelay1, commandDelay2, true);
                } else {
                    new PluginPollChoice(id, lore, command1, command2, delay, commandDelay1, commandDelay2, false);
                }

            }
        }
    }

    //PAID EFFECTS LOAD?
}
