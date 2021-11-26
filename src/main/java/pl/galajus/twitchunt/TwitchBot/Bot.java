package pl.galajus.twitchunt.TwitchBot;

import com.github.philippheuer.credentialmanager.domain.OAuth2Credential;
import com.github.philippheuer.events4j.reactor.ReactorEventHandler;
import com.github.philippheuer.events4j.simple.SimpleEventHandler;
import com.github.twitch4j.TwitchClient;
import com.github.twitch4j.TwitchClientBuilder;
import com.github.twitch4j.auth.providers.TwitchIdentityProvider;
import com.github.twitch4j.eventsub.domain.PollChoice;
import com.github.twitch4j.helix.domain.User;
import com.github.twitch4j.helix.domain.UserList;
import com.github.twitch4j.pubsub.events.ChannelBitsEvent;
import com.github.twitch4j.pubsub.events.ChannelSubscribeEvent;
import com.github.twitch4j.pubsub.events.FollowingEvent;
import com.github.twitch4j.pubsub.events.PollsEvent;
import pl.galajus.twitchunt.ConfigReader;
import pl.galajus.twitchunt.TwitchBot.TwitchEvents.*;
import pl.galajus.twitchunt.Twitchunt;

import java.util.ArrayList;
import java.util.List;

public class Bot {

    private final Twitchunt twitchunt;
    private final ConfigReader configReader;

    private TwitchClient twitchClient;
    private String botID = "";

    public Bot(Twitchunt twitchunt, ConfigReader configReader) {
        this.twitchunt = twitchunt;
        this.configReader = configReader;

        this.runBot();
    }

    private void runBot() {
        OAuth2Credential credential = new OAuth2Credential("twitch", configReader.getChatToken());

        twitchClient = TwitchClientBuilder.builder()
                .withClientId(configReader.getClientID())
                .withClientSecret(configReader.getClientSecret())
                .withDefaultAuthToken(credential)
                .withEnableHelix(true)
                .withEnablePubSub(true)
                .withChatAccount(credential)
                .withEnableChat(true)
                .withDefaultEventHandler(SimpleEventHandler.class)
                .build();

        ReactorEventHandler reactorEventHandler = twitchClient.getEventManager().getEventHandler(ReactorEventHandler.class);

        UserList channelsID = twitchClient.getHelix().getUsers(null, null, List.of(configReader.getChannelName())).execute();

        User channel = null;
        if (channelsID.getUsers().size() > 0) {
            channel = channelsID.getUsers().get(0);
        }

        if (channel != null) {
            twitchClient.getPubSub().listenForCheerEvents(credential, channel.getId());
            twitchClient.getPubSub().listenForFollowingEvents(credential, channel.getId());
            twitchClient.getPubSub().listenForSubscriptionEvents(credential, channel.getId());
            twitchClient.getPubSub().listenForPollEvents(credential, channel.getId());

            Poll poll = new Poll(twitchunt);
            Cheer cheer = new Cheer(twitchunt);
            Follow follow = new Follow(twitchunt);
            Subscribe subscribe = new Subscribe(twitchunt);

            twitchClient.getEventManager().onEvent(PollsEvent.class, poll::onPoll);
            twitchClient.getEventManager().onEvent(ChannelBitsEvent.class, cheer::onCheer);
            twitchClient.getEventManager().onEvent(FollowingEvent.class, follow::onFollow);
            twitchClient.getEventManager().onEvent(ChannelSubscribeEvent.class, subscribe::onSubscribe);
        } else {
            twitchunt.getDependencyResolver().errorLogToConsole("twitchChannelIDError");
        }

        new Message(twitchunt, reactorEventHandler);

        twitchClient.getChat().joinChannel(configReader.getChannelName());
        twitchClient.getChat().sendMessage(configReader.getChannelName(), configReader.getStartMessage());

        botID = new TwitchIdentityProvider(null, null, null)
                .getAdditionalCredentialInformation(credential).map(OAuth2Credential::getUserId).orElse(null);


        List<PollChoice> choices = new ArrayList<>();
        choices.add(new PollChoice().withTitle("testowanko1").withId("jakiestam1"));
        choices.add(new PollChoice().withTitle("testowanko2").withId("jakiestam2"));

        com.github.twitch4j.helix.domain.Poll poll = new com.github.twitch4j.helix.domain.Poll()
                .withBroadcasterId(this.botID)
                .withId(configReader.getChannelName())
                .withChoices(choices)
                .withTitle("Testowy tytulik")
                .withDurationSeconds(30);

        twitchClient.getHelix().createPoll(configReader.getChatToken(), poll).queue();

    }

}