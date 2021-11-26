package pl.galajus.twitchunt.TwitchBot.TwitchEvents;

import com.github.twitch4j.pubsub.events.FollowingEvent;
import pl.galajus.twitchunt.Twitchunt;

public class Follow {

    private final Twitchunt twitchunt;

    public Follow(Twitchunt twitchunt) {
        this.twitchunt = twitchunt;
    }

    public void onFollow(FollowingEvent e) {

        //TODO Blacklist nicks/ID user of twitch and cooldown

        String follower = e.getData().getDisplayName();
        twitchunt.getDependencyResolver().broadcastMessage("§bNew follow from: §6" + follower);

        /*
        if (TimeChoices.isEnabled()) {
            int random = new Random().nextInt(DataManager.getFollow().size());
            int choiceId = DataManager.getFollow().get(random);

            EffectController.castPaidEffect(choiceId);
            PluginPollChoices pluginChoice = PluginPollChoices.getPluginPollChoicesList().get(choiceId);
            SpigotExecutor.dependBroadcastMessage("§bCasting effect: " + pluginChoice.getLore());
        }

         */
    }
}
