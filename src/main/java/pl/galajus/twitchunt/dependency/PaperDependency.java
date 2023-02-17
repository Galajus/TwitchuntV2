package pl.galajus.twitchunt.dependency;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.galajus.twitchunt.Twitchunt;

public class PaperDependency {

    private final Twitchunt twitchunt;

    public PaperDependency(Twitchunt twitchunt) {
        this.twitchunt = twitchunt;
    }

    public void sendMessage(Player p, String message) {
        p.sendMessage(Component.text(twitchunt.getPrefix() + message));
    }

    public void sendMessage(CommandSender p, String message) {
        p.sendMessage(Component.text(twitchunt.getPrefix() + message));
    }

    public void sendMessageWithoutPrefix(Player p, String message) {
        p.sendMessage(Component.text(message));
    }

    public void sendMessageWithoutPrefix(CommandSender p, String message) {
        p.sendMessage(Component.text(message));
    }

    public void broadcastMessage(String message) {
        Bukkit.broadcast(Component.text(twitchunt.getPrefix() + message));
    }

    public void broadcastMessageWithoutPrefix(String message) {
        Bukkit.broadcast(Component.text(message));
    }
}
