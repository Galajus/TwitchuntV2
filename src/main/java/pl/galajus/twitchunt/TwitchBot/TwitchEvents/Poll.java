package pl.galajus.twitchunt.TwitchBot.TwitchEvents;

import com.github.twitch4j.pubsub.domain.PollData;
import com.github.twitch4j.pubsub.events.PollsEvent;
import org.bukkit.entity.Player;
import pl.galajus.twitchunt.TwitchBot.Bot;
import pl.galajus.twitchunt.Twitchunt;

import java.util.List;

public class Poll {

    private final Twitchunt twitchunt;
    private final Bot bot;

    public Poll(Twitchunt twitchunt, Bot bot) {
        this.twitchunt = twitchunt;
        this.bot = bot;
    }

    public void onPoll(PollsEvent e) {

        if (e.getType().equals(PollsEvent.EventType.POLL_COMPLETE)) {
            if (e.getData().getTitle().equalsIgnoreCase(twitchunt.getConfigReader().getPollTitle())) {
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
                    twitchunt.getDependencyResolver().broadcastMessage("§aWon option: §b" + bestName + " §aPoints: §b" + bestNO);
                    bot.getTwitchClient().getChat().sendMessage(twitchunt.getConfigReader().getChannelName(), "[TWITCHUNT] "  +"Won option: " + bestName + " Points: " + bestNO);
                    twitchunt.getEffectController().castEffect(bestName);

                    List<Player> huntedPlayers = twitchunt.getConfigReader().getHuntedPlayers();

                    for (Player hunted : huntedPlayers) {
                        hunted.sendTitle("§d" + bestName,  "§5Votes: " + bestNO, 10, 20, 10);
                    }

                }
            }
        }

    }
}
