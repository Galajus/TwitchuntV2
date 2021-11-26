package pl.galajus.twitchunt.TwitchBot.TwitchEvents;

import com.github.twitch4j.pubsub.events.PollsEvent;
import pl.galajus.twitchunt.Twitchunt;

public class Poll {

    private final Twitchunt twitchunt;

    public Poll(Twitchunt twitchunt) {
        this.twitchunt = twitchunt;

    }

    public void onPoll(PollsEvent e) {



    }
}
