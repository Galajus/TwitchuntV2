package pl.galajus.twitchunt.Dependency;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.galajus.twitchunt.Translations;
import pl.galajus.twitchunt.Twitchunt;

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
        this.registerEvents();
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

    public void sendTranslatedMessage(Player p, String key) {
        if (isPaper) {
            paperDependency.sendMessage(p, translations.getTranslation(key));
        } else {
            spigotDependency.sendMessage(p, translations.getTranslation(key));
        }
    }

    public void sendTranslatedMessage(CommandSender p, String key) {
        if (isPaper) {
            paperDependency.sendMessage(p, translations.getTranslation(key));
        } else {
            spigotDependency.sendMessage(p, translations.getTranslation(key));
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

    public void sendTranslatedMessageWithoutPrefix(Player p, String key) {
        if (isPaper) {
            paperDependency.sendMessageWithoutPrefix(p, translations.getTranslation(key));
        } else {
            spigotDependency.sendMessageWithoutPrefix(p, translations.getTranslation(key));
        }
    }

    public void sendTranslatedMessageWithoutPrefix(CommandSender p, String key) {
        if (isPaper) {
            paperDependency.sendMessageWithoutPrefix(p, translations.getTranslation(key));
        } else {
            spigotDependency.sendMessageWithoutPrefix(p, translations.getTranslation(key));
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

    public void broadcastTranslatedMessage(String key) {
        if (isPaper) {
            paperDependency.broadcastMessage(translations.getTranslation(key));
        } else {
            spigotDependency.broadcastMessage(translations.getTranslation(key));
        }
    }

    public void broadcastTranslatedMessageWithoutPrefix(String key) {
        if (isPaper) {
            paperDependency.broadcastMessageWithoutPrefix(translations.getTranslation(key));
        } else {
            spigotDependency.broadcastMessageWithoutPrefix(translations.getTranslation(key));
        }
    }

    public void infoLogToConsole(String key) {
        Bukkit.getLogger().log(Level.INFO, twitchunt.getPrefix() + translations.getTranslation(key));
    }

    public void warningLogToConsole(String key) {
        Bukkit.getLogger().log(Level.WARNING, twitchunt.getPrefix() + translations.getTranslation(key));
    }

    public void errorLogToConsole(String key) {
        Bukkit.getLogger().log(Level.SEVERE, twitchunt.getPrefix() + translations.getTranslation(key));
    }

    private void registerEvents() {

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
