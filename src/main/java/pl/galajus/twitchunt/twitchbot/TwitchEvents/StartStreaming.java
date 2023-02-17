package pl.galajus.twitchunt.twitchbot.TwitchEvents;

import com.github.twitch4j.events.ChannelGoLiveEvent;
import pl.galajus.twitchunt.Twitchunt;

public class StartStreaming {

    private final Twitchunt twitchunt;

    public StartStreaming(Twitchunt twitchunt) {
        this.twitchunt = twitchunt;
    }

    public void onStartStreaming(ChannelGoLiveEvent e) {

        if (twitchunt.getConfigReader().isEnabledBroadcastStartStreamInMinecraft()) {
            twitchunt.getDependencyResolver().broadcastTranslatedMessage
                    ("channelGoesLive", e.getChannel().getName(), e.getStream().getGameName(), e.getStream().getTitle());
        }
    }
}
