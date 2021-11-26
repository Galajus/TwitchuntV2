package pl.galajus.twitchunt.TwitchBot.TwitchEvents;

import com.github.twitch4j.pubsub.events.ChannelBitsEvent;
import pl.galajus.twitchunt.ConfigReader;
import pl.galajus.twitchunt.Twitchunt;

public class Cheer {

    private final Twitchunt twitchunt;
    private final ConfigReader configReader;

    public Cheer(Twitchunt twitchunt) {
        this.twitchunt = twitchunt;
        this.configReader = twitchunt.getConfigReader();
    }

    public void onCheer(ChannelBitsEvent e) {

        Integer bits = e.getData().getBitsUsed();
        String user = e.getData().getUserName();

        twitchunt.getDependencyResolver().broadcastMessage("§6" + user + " §bsended §6" + bits + "§bits");

        /*
        if (TimeChoices.isEnabled()) {
            if (bits <= configReader.getBitsThresholdOne()) {
                int random = new Random().nextInt(configReader.getBitsOne().size());
                int choiceId = configReader.getBitsOne().get(random);

                EffectController.castPaidEffect(choiceId);
                PluginPollChoices pluginChoice = PluginPollChoices.getPluginPollChoicesList().get(choiceId);
                SpigotExecutor.dependBroadcastMessage("§bCasting effect: " + pluginChoice.getLore());
                return;
            }
            if (bits <= DataManager.getBitsThresholdTwo()) {
                int random = new Random().nextInt(configReader.getBitsTwo().size());
                int choiceId = DataManager.getBitsTwo().get(random);

                EffectController.castPaidEffect(choiceId);
                PluginPollChoices pluginChoice = PluginPollChoices.getPluginPollChoicesList().get(choiceId);
                SpigotExecutor.dependBroadcastMessage("§bCasting effect: " + pluginChoice.getLore());
                return;
            }
            int random = new Random().nextInt(configReader.getBitsThree().size());
            int choiceId = configReader.getBitsThree().get(random);

            EffectController.castPaidEffect(choiceId);
            PluginPollChoices pluginChoice = PluginPollChoices.getPluginPollChoicesList().get(choiceId);
            SpigotExecutor.dependBroadcastMessage("§bCasting effect: " + pluginChoice.getLore());
        }
         */
    }
}
