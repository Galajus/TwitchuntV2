package pl.galajus.twitchunt.TwitchBot.TwitchEvents;

import com.github.twitch4j.pubsub.events.ChannelBitsEvent;
import pl.galajus.twitchunt.ConfigReader;
import pl.galajus.twitchunt.ObjectsManager.PaidCastType;
import pl.galajus.twitchunt.Twitchunt;

import java.util.Random;

public class Cheer {

    private final Twitchunt twitchunt;

    public Cheer(Twitchunt twitchunt) {
        this.twitchunt = twitchunt;
    }

    public void onCheer(ChannelBitsEvent e) {

        if(!twitchunt.getPollCreator().isPollsEnabled()) return;

        Integer bits = e.getData().getBitsUsed();
        String user = e.getData().getUserName();

        ConfigReader configReader = twitchunt.getConfigReader();

        if (bits >= configReader.getBitsThresholdThree()) {
            int random = new Random().nextInt(configReader.getBitsThree().size());
            Integer choiceID = configReader.getBitsThree().get(random);

            if (choiceID != null) {
                twitchunt.getEffectController().castPaidEffect(choiceID, PaidCastType.CHEER, user, bits.doubleValue());
            }
            return;
        }

        if (bits >= configReader.getBitsThresholdTwo()) {
            int random = new Random().nextInt(configReader.getBitsTwo().size());
            Integer choiceID = configReader.getBitsTwo().get(random);

            if (choiceID != null) {
                twitchunt.getEffectController().castPaidEffect(choiceID, PaidCastType.CHEER, user, bits.doubleValue());
            }
            return;
        }

        int random = new Random().nextInt(configReader.getBitsOne().size());
        Integer choiceID = configReader.getBitsOne().get(random);

        if (choiceID != null) {
            twitchunt.getEffectController().castPaidEffect(choiceID, PaidCastType.CHEER, user, bits.doubleValue());
        }
    }
}
