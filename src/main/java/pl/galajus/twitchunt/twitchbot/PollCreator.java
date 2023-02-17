package pl.galajus.twitchunt.twitchbot;

import com.github.twitch4j.eventsub.domain.PollChoice;
import com.github.twitch4j.helix.domain.Poll;
import pl.galajus.twitchunt.ConfigReader;
import pl.galajus.twitchunt.objectsmanager.PluginPollChoice;
import pl.galajus.twitchunt.Twitchunt;

import java.util.*;

public class PollCreator {

    private final Twitchunt twitchunt;
    private final Bot bot;
    private final ConfigReader configReader;

    private boolean pollsEnabled = false;
    private Long toNextPoll = -1L;

    public PollCreator(Twitchunt twitchunt, Bot bot, ConfigReader configReader) {
        this.twitchunt = twitchunt;
        this.bot = bot;
        this.configReader = configReader;
    }

    private void createPoll() {

        int amountChoices = configReader.getOptionsPerPoll();

        if (amountChoices > 5 || amountChoices < 2) {
            configReader.setOptionsPerPoll(3);
            twitchunt.getDependencyResolver().warningLogToConsole("badChoicesAmount");
        }

        List<PollChoice> choices = new ArrayList<>();

        List<PluginPollChoice> randomChoice = getRandomChoices(amountChoices);
        for (PluginPollChoice p : randomChoice) {
            String title = p.getLore();
            String id = String.valueOf(p.getId());
            choices.add(new PollChoice().withTitle(title).withId(id));
        }

        if (choices.size() < 2) {
            twitchunt.getDependencyResolver().errorLogToConsole("notEnoughEffectsWithDisable");
            pollsEnabled = false;
            return;
        }



        Poll poll = new Poll()
                .withBroadcasterId(bot.getBotID())
                .withId(configReader.getChannelName())
                .withChoices(choices)
                .withTitle(configReader.getPollTitle())
                .withDurationSeconds(configReader.getPollDuration().intValue());

        bot.getTwitchClient().getHelix().createPoll(configReader.getChatToken(), poll).queue();
        if (configReader.isEnabledInfoOnTwitchAboutStartedPoll()) {
            bot.getTwitchClient().getChat().sendMessage(configReader.getChannelName(), twitchunt.getTranslations().getTranslation("pollStarted"));
        }

    }

    private List<PluginPollChoice> getRandomChoices(int amount) {

        List<PluginPollChoice> choiceArray = new ArrayList<>();
        Map<Integer, PluginPollChoice> choicesMap = twitchunt.getManager().getPluginPollChoicesList();

        for (int i = 0; i < amount; i++) {

            Set<Integer> keySet = choicesMap.keySet();
            List<Integer> keyList = new ArrayList<>(keySet);

            int size = keyList.size();
            if (size < 1) {
                twitchunt.getDependencyResolver().warningLogToConsole("notEnoughEffects");
                return choiceArray;
            }
            int randomIndex = new Random().nextInt(size);

            int randomKey = keyList.get(randomIndex);
            choiceArray.add(choicesMap.get(randomKey));
            choicesMap.remove(randomKey);

        }
        return choiceArray;
    }


    public void tick() {
        if (pollsEnabled) {
            toNextPoll = toNextPoll - 1L;

            if (toNextPoll == 0) {
                toNextPoll = twitchunt.getConfigReader().getPollInterval();
                this.createPoll();
            }
        }
    }

    public boolean enablePolls() {
        if (!pollsEnabled) {
            pollsEnabled = true;
            if (configReader.getPollInstaStart()) {
                toNextPoll = 1L;
            } else {
                toNextPoll = twitchunt.getConfigReader().getPollInterval();
            }
            return true;
        }
        return  false;
    }

    public boolean disablePolls() {
        if (pollsEnabled) {
            pollsEnabled = false;
            return true;
        }
        return  false;
    }

    public boolean isPollsEnabled() {
        return pollsEnabled;
    }

}
