package pl.galajus.twitchunt.TwitchBot.TwitchEvents;

import com.github.twitch4j.pubsub.events.ChannelSubscribeEvent;
import pl.galajus.twitchunt.Twitchunt;

public class Subscribe {

    private final Twitchunt twitchunt;

    public Subscribe(Twitchunt twitchunt) {
        this.twitchunt = twitchunt;
    }

    public void onSubscribe(ChannelSubscribeEvent e) {

        twitchunt.getDependencyResolver().broadcastMessage("§bNew Sub from: §6" + e.getData().getRecipientUserName() + "§bLevel: §6" + e.getData().getSubPlanName());

        twitchunt.getDependencyResolver().broadcastMessage(e.getData().toString());

        e.getData().getSubPlan();

    }
}
