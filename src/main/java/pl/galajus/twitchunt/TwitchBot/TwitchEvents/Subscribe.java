package pl.galajus.twitchunt.TwitchBot.TwitchEvents;

import com.github.twitch4j.common.enums.SubscriptionPlan;
import com.github.twitch4j.pubsub.events.ChannelSubscribeEvent;
import pl.galajus.twitchunt.ConfigReader;
import pl.galajus.twitchunt.ObjectsManager.PaidCastType;
import pl.galajus.twitchunt.Twitchunt;

import java.util.Random;

public class Subscribe {

    private final Twitchunt twitchunt;

    public Subscribe(Twitchunt twitchunt) {
        this.twitchunt = twitchunt;
    }

    public void onSubscribe(ChannelSubscribeEvent e) {

        if(!twitchunt.getPollCreator().isPollsEnabled()) return;

        SubscriptionPlan tier = e.getData().getSubPlan();

        ConfigReader configReader = twitchunt.getConfigReader();

        if (tier.equals(SubscriptionPlan.NONE) || tier.equals(SubscriptionPlan.TIER1) || tier.equals(SubscriptionPlan.TWITCH_PRIME)) {
            int random = new Random().nextInt(configReader.getSubOne().size());
            Integer choiceID = configReader.getSubOne().get(random);

            if (choiceID != null) {
                twitchunt.getEffectController().castPaidEffect(choiceID, PaidCastType.SUBSCRIPTION, e.getData().getRecipientDisplayName(), 1D);
            }
        }

        if (tier.equals(SubscriptionPlan.TIER2)) {
            int random = new Random().nextInt(configReader.getSubTwo().size());
            Integer choiceID = configReader.getSubTwo().get(random);

            if (choiceID != null) {
                twitchunt.getEffectController().castPaidEffect(choiceID, PaidCastType.SUBSCRIPTION, e.getData().getRecipientDisplayName(), 2D);
            }
        }

        if (tier.equals(SubscriptionPlan.TIER3)) {
            int random = new Random().nextInt(configReader.getSubThree().size());
            Integer choiceID = configReader.getSubThree().get(random);

            if (choiceID != null) {
                twitchunt.getEffectController().castPaidEffect(choiceID, PaidCastType.SUBSCRIPTION, e.getData().getRecipientDisplayName(), 3D);
            }
        }

    }
}
