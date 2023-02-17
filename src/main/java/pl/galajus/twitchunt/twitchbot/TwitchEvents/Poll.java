package pl.galajus.twitchunt.twitchbot.TwitchEvents;

import com.github.twitch4j.pubsub.domain.PollData;
import com.github.twitch4j.pubsub.events.PollsEvent;
import org.bukkit.entity.Player;
import pl.galajus.twitchunt.ConfigReader;
import pl.galajus.twitchunt.twitchbot.Bot;
import pl.galajus.twitchunt.Twitchunt;

import java.util.List;

public class Poll {

    private final Twitchunt twitchunt;
    private final Bot bot;
    private final ConfigReader configReader;

    public Poll(Twitchunt twitchunt, Bot bot) {
        this.twitchunt = twitchunt;
        this.bot = bot;
        this.configReader = twitchunt.getConfigReader();
    }

    public void onPoll(PollsEvent e) {

        if (!twitchunt.getPollCreator().isPollsEnabled()) return;

        if (e.getType().equals(PollsEvent.EventType.POLL_COMPLETE)) {
            if (e.getData().getTitle().equalsIgnoreCase(configReader.getPollTitle())) {
                int bestNO = -1;
                String bestName = "";

                for (PollData.PollChoice choice : e.getData().getChoices()) {

                    if (choice.getVotes() != null) {
                        if (choice.getVotes().getTotal() > bestNO || bestNO == -1) {
                            bestNO = Math.toIntExact(choice.getVotes().getTotal());
                            bestName = choice.getTitle();
                        }
                    }
                }

                if (!bestName.isBlank()) {
                    if (configReader.isEnabledBroadcastResultOnMinecraft()) {
                        twitchunt.getDependencyResolver().broadcastTranslatedMessage("pollResultMinecraftBroadcast", bestName, String.valueOf(bestNO));
                    }
                    
                    if (configReader.isEnabledSendResultOnTwitch()) {
                        bot.getTwitchClient().getChat().sendMessage(configReader.getChannelName(), twitchunt.getDependencyResolver().getTranslatedText("pollResultTwitchChat", bestName, String.valueOf(bestNO)));
                    }
                    twitchunt.getEffectController().castEffect(bestName);

                    List<Player> huntedPlayers = configReader.getHuntedPlayers();

                    if (configReader.isEnabledShowResultTitleToHuntedPlayers()) {
                        String translated = twitchunt.getDependencyResolver().getTranslatedText("pollResultTitle", bestName);
                        String translated2 = twitchunt.getDependencyResolver().getTranslatedText("pollResultSubTitle", String.valueOf(bestNO));

                        for (Player hunted : huntedPlayers) {
                            hunted.sendTitle(translated, translated2, 10, 20, 10);
                        }
                    }

                }
            }
        }

    }
}
