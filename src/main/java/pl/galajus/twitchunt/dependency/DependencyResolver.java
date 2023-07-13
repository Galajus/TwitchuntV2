package pl.galajus.twitchunt.dependency;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;
import pl.galajus.twitchunt.commands.TwitchuntCommand;
import pl.galajus.twitchunt.commands.TwitchuntCommandSpigot;
import pl.galajus.twitchunt.minecraft.events.*;
import pl.galajus.twitchunt.minecraft.EventsController;
import pl.galajus.twitchunt.Translations;
import pl.galajus.twitchunt.Twitchunt;

import java.util.Objects;
import java.util.logging.Level;

public class DependencyResolver {

    private final Twitchunt twitchunt;
    private final boolean isPaper;
    private final Translations translations;
    private PaperDependency paperDependency;
    private SpigotDependency spigotDependency;

    public DependencyResolver(Twitchunt twitchunt, Translations translations) {
        this.twitchunt = twitchunt;
        this.translations = translations;
        this.isPaper = paperCheck();
        this.registerPaperDependencies();
    }

    public String getTranslatedText(String key, String... args) {
        String text = translations.getTranslation(key);
        if (args.length != 0) {
            text = this.convertArgs(text, args);
        }
        return text;
    }

    public void sendMessage(Player p, String message) {
        if (isPaper) {
            paperDependency.sendMessage(p, message);
        } else {
            spigotDependency.sendMessage(p, message);
        }
    }

    public void sendMessage(CommandSender p, String message) {
        if (isPaper) {
            paperDependency.sendMessage(p, message);
        } else {
            spigotDependency.sendMessage(p, message);
        }
    }

    public void sendTranslatedMessage(Player p, String key, @Nullable String... args) {
        String translatedText = translations.getTranslation(key);
        if (args.length != 0) {
            translatedText = this.convertArgs(translatedText, args);
        }

        if (isPaper) {
            paperDependency.sendMessage(p, translatedText);
        } else {
            spigotDependency.sendMessage(p, translatedText);
        }
    }

    public void sendTranslatedMessage(CommandSender p, String key, @Nullable String... args) {
        String translatedText = translations.getTranslation(key);
        if (args.length != 0) {
            translatedText = this.convertArgs(translatedText, args);
        }

        if (isPaper) {
            paperDependency.sendMessage(p, translatedText);
        } else {
            spigotDependency.sendMessage(p, translatedText);
        }
    }

    public void sendMessageWithoutPrefix(Player p, String message) {
        if (isPaper) {
            paperDependency.sendMessageWithoutPrefix(p, message);
        } else {
            spigotDependency.sendMessageWithoutPrefix(p, message);
        }
    }

    public void sendMessageWithoutPrefix(CommandSender p, String message) {
        if (isPaper) {
            paperDependency.sendMessageWithoutPrefix(p, message);
        } else {
            spigotDependency.sendMessageWithoutPrefix(p, message);
        }
    }

    public void sendTranslatedMessageWithoutPrefix(Player p, String key, @Nullable String... args) {
        String translatedText = translations.getTranslation(key);
        if (args.length != 0) {
            translatedText = this.convertArgs(translatedText, args);
        }

        if (isPaper) {
            paperDependency.sendMessageWithoutPrefix(p, translatedText);
        } else {
            spigotDependency.sendMessageWithoutPrefix(p, translatedText);
        }
    }

    public void sendTranslatedMessageWithoutPrefix(CommandSender p, String key, @Nullable String... args) {
        String translatedText = translations.getTranslation(key);
        if (args.length != 0) {
            translatedText = this.convertArgs(translatedText, args);
        }

        if (isPaper) {
            paperDependency.sendMessageWithoutPrefix(p, translatedText);
        } else {
            spigotDependency.sendMessageWithoutPrefix(p, translatedText);
        }
    }

    public void broadcastMessage(String message) {
        if (isPaper) {
            paperDependency.broadcastMessage(message);
        } else {
            spigotDependency.broadcastMessage(message);
        }
    }

    public void broadcastMessageWithoutPrefix(String message) {
        if (isPaper) {
            paperDependency.broadcastMessageWithoutPrefix(message);
        } else {
            spigotDependency.broadcastMessageWithoutPrefix(message);
        }
    }

    public void broadcastTranslatedMessage(String key, @Nullable String... args) {
        String translatedText = translations.getTranslation(key);
        if (args.length != 0) {
            translatedText = this.convertArgs(translatedText, args);
        }

        if (isPaper) {
            paperDependency.broadcastMessage(translatedText);
        } else {
            spigotDependency.broadcastMessage(translatedText);
        }
    }

    public void broadcastTranslatedMessageWithoutPrefix(String key, @Nullable String... args) {
        String translatedText = translations.getTranslation(key);
        if (args.length != 0) {
            translatedText = this.convertArgs(translatedText, args);
        }

        if (isPaper) {
            paperDependency.broadcastMessageWithoutPrefix(translatedText);
        } else {
            spigotDependency.broadcastMessageWithoutPrefix(translatedText);
        }
    }

    public void infoLogToConsole(String key, @Nullable String... args) {
        String translatedText = translations.getTranslation(key);
        if (args.length != 0) {
            translatedText = this.convertArgs(translatedText, args);
        }

        Bukkit.getLogger().log(Level.INFO, twitchunt.getPrefix() + translatedText);
    }

    public void warningLogToConsole(String key, @Nullable String... args) {
        String translatedText = translations.getTranslation(key);
        if (args.length != 0) {
            translatedText = this.convertArgs(translatedText, args);
        }

        Bukkit.getLogger().log(Level.WARNING, twitchunt.getPrefix() + translatedText);
    }

    public void errorLogToConsole(String key, @Nullable String... args) {
        String translatedText = translations.getTranslation(key);
        if (args.length != 0) {
            translatedText = this.convertArgs(translatedText, args);
        }

        Bukkit.getLogger().log(Level.SEVERE, twitchunt.getPrefix() + translatedText);
    }

    public void infoLogToConsoleWithoutTranslation(String message) {
        Bukkit.getLogger().log(Level.INFO, twitchunt.getPrefix() + message);
    }

    public void warningLogToConsoleWithoutTranslation(String message) {
        Bukkit.getLogger().log(Level.WARNING, twitchunt.getPrefix() + message);
    }

    public void errorLogToConsoleWithoutTranslation(String message) {
        Bukkit.getLogger().log(Level.SEVERE, twitchunt.getPrefix() + message);
    }

    public void registerEvents() {

        EventsController eventsController = twitchunt.getEventsController();
        if (isPaper) {
            Bukkit.getPluginManager().registerEvents(new ItemPickup(twitchunt, eventsController), twitchunt);

            TwitchuntCommand twitchuntCommand = new TwitchuntCommand(twitchunt, twitchunt.getCommandsHelper(), twitchunt.getDependencyResolver(), twitchunt.getConfigReader());
            Objects.requireNonNull(twitchunt.getCommand("twitchunt")).setExecutor(twitchuntCommand);
            Bukkit.getPluginManager().registerEvents(twitchuntCommand, twitchunt);
        } else {
            Bukkit.getPluginManager().registerEvents(new ItemPickupSpigot(twitchunt, eventsController), twitchunt);

            TwitchuntCommandSpigot twitchuntCommandSpigot = new TwitchuntCommandSpigot(twitchunt, twitchunt.getCommandsHelper(), twitchunt.getDependencyResolver(), twitchunt.getConfigReader());
            Objects.requireNonNull(twitchunt.getCommand("twitchunt")).setExecutor(twitchuntCommandSpigot);
        }
        Bukkit.getPluginManager().registerEvents(new BlockBreak(twitchunt, eventsController), twitchunt);
        Bukkit.getPluginManager().registerEvents(new EntityDamageEntity(twitchunt, eventsController), twitchunt);
        Bukkit.getPluginManager().registerEvents(new EntityDeath(twitchunt, eventsController), twitchunt);
        Bukkit.getPluginManager().registerEvents(new InventoryClick(twitchunt, eventsController), twitchunt);
        Bukkit.getPluginManager().registerEvents(new InventoryOpen(twitchunt, eventsController), twitchunt);
    }

    public boolean isPaper() {
        return isPaper;
    }

    private String convertArgs(String translatedText, String... args) {
        for (int loopNO = 1; loopNO <= args.length; loopNO++) {
            translatedText = translatedText.replaceAll("%arg" + loopNO + "%", args[loopNO - 1]);
        }
        return translatedText;
    }

    private void registerPaperDependencies() {
        if (!isPaper) {
            paperDependency = null;
            spigotDependency = new SpigotDependency(twitchunt);
        } else {
            paperDependency = new PaperDependency(twitchunt);
            spigotDependency = null;
        }
    }

    private boolean paperCheck() {

        if (twitchunt.getConfigReader().getForceSpigotUsage()) {
            return false;
        }

        try {
            String paperTest = Class.forName("com.destroystokyo.paper.VersionHistoryManager$VersionData").toString();
            if (!paperTest.isBlank()) {
                return true;
            }
        } catch (ClassNotFoundException e) {
            this.infoLogToConsole("paperNotFound");
            return false;
        }
        return false;
    }

}
