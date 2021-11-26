package pl.galajus.twitchunt.TwitchBot.TwitchEvents;

import com.github.philippheuer.events4j.reactor.ReactorEventHandler;
import com.github.twitch4j.chat.events.channel.ChannelMessageEvent;
import org.bukkit.Bukkit;
import pl.galajus.twitchunt.Twitchunt;

public class Message {

    private final Twitchunt twitchunt;

    public Message(Twitchunt twitchunt, ReactorEventHandler reactorEventHandler) {
        this.twitchunt = twitchunt;
        reactorEventHandler.onEvent(ChannelMessageEvent.class, this::onMessageEvent);
    }

    private void onMessageEvent(ChannelMessageEvent e) {
        if (twitchunt.getConfigReader().isTwitchMessageEnabled()) {
            Bukkit.getScheduler().runTaskAsynchronously(twitchunt, () -> {
                if (twitchunt.getConfigReader().getExcludedSenders().stream().noneMatch(e.getUser().getName()::equalsIgnoreCase)) {
                    twitchunt.getDependencyResolver().broadcastMessageWithoutPrefix("§5" + e.getUser().getName() + ": " + "§r" + e.getMessage());
                }
            });
        }
    }
}
