package pl.galajus.twitchunt.TwitchBot.TwitchEvents;

import com.github.twitch4j.pubsub.events.RewardRedeemedEvent;
import pl.galajus.twitchunt.ObjectsManager.PaidCastType;
import pl.galajus.twitchunt.Twitchunt;

public class ChannelPointsRedemption {

    private final Twitchunt twitchunt;

    public ChannelPointsRedemption(Twitchunt twitchunt) {
        this.twitchunt = twitchunt;
    }

    public void onPointsRedemption(RewardRedeemedEvent e) {

        if (!twitchunt.getPollCreator().isPollsEnabled()) return;

        Integer rewardID = twitchunt.getManager().getTwitchChannelPointsRewards().get(e.getRedemption().getReward().getTitle());

        if (rewardID != null) {
            twitchunt.getEffectController().castPaidEffect(rewardID, PaidCastType.POINTS_REDEMPTION, e.getRedemption().getUser().getDisplayName(), null);
        }
    }
}
