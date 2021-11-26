package pl.galajus.twitchunt.Dependency;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.galajus.twitchunt.Twitchunt;

public class SpigotDependency {

    private final Twitchunt twitchunt;

    public SpigotDependency(Twitchunt twitchunt) {
        this.twitchunt = twitchunt;
    }

    public void sendMessage(Player p, String message) {
        p.sendMessage(twitchunt.getPrefix() + message);
    }

    public void sendMessage(CommandSender p, String message) {
        p.sendMessage(twitchunt.getPrefix() + message);
    }

    public void sendMessageWithoutPrefix(Player p, String message) {
        p.sendMessage(message);
    }

    public void sendMessageWithoutPrefix(CommandSender p, String message) {
        p.sendMessage(message);
    }

    public void broadcastMessage(String message) {
        Bukkit.broadcastMessage(twitchunt.getPrefix() + message);
    }

    public void broadcastMessageWithoutPrefix(String message) {
        Bukkit.broadcastMessage(message);
    }

}
