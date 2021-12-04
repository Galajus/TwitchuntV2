package pl.galajus.twitchunt.TwitchBot;

import com.github.philippheuer.credentialmanager.domain.OAuth2Credential;
import com.github.philippheuer.events4j.reactor.ReactorEventHandler;
import com.github.philippheuer.events4j.simple.SimpleEventHandler;
import com.github.twitch4j.TwitchClient;
import com.github.twitch4j.TwitchClientBuilder;
import com.github.twitch4j.auth.providers.TwitchIdentityProvider;
import com.github.twitch4j.events.ChannelGoLiveEvent;
import com.github.twitch4j.helix.domain.User;
import com.github.twitch4j.helix.domain.UserList;
import com.github.twitch4j.pubsub.events.*;
import pl.galajus.twitchunt.ConfigReader;
import pl.galajus.twitchunt.TwitchBot.TwitchEvents.*;
import pl.galajus.twitchunt.Twitchunt;

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
            twitchClient.getClientHelper().enableStreamEventListener(configReader.getChannelName());

            twitchClient.getPubSub().listenForCheerEvents(credential, channel.getId());
            twitchClient.getPubSub().listenForFollowingEvents(credential, channel.getId());
            twitchClient.getPubSub().listenForSubscriptionEvents(credential, channel.getId());
            twitchClient.getPubSub().listenForPollEvents(credential, channel.getId());
            twitchClient.getPubSub().listenForChannelPointsRedemptionEvents(credential, channel.getId());

            Poll poll = new Poll(twitchunt, this);
            Cheer cheer = new Cheer(twitchunt);
            Follow follow = new Follow(twitchunt);
            Subscribe subscribe = new Subscribe(twitchunt);
            ChannelPointsRedemption channelPointsRedemption = new ChannelPointsRedemption(twitchunt);
            StartStreaming startStreaming = new StartStreaming(twitchunt);

            twitchClient.getEventManager().onEvent(PollsEvent.class, poll::onPoll);
            twitchClient.getEventManager().onEvent(ChannelBitsEvent.class, cheer::onCheer);
            twitchClient.getEventManager().onEvent(FollowingEvent.class, follow::onFollow);
            twitchClient.getEventManager().onEvent(ChannelSubscribeEvent.class, subscribe::onSubscribe);
            twitchClient.getEventManager().onEvent(RewardRedeemedEvent.class, channelPointsRedemption::onPointsRedemption);
            twitchClient.getEventManager().onEvent(ChannelGoLiveEvent.class, startStreaming::onStartStreaming);


        } else {
            twitchunt.getDependencyResolver().errorLogToConsole("twitchChannelIDError");
        }

        new Message(twitchunt, reactorEventHandler);

        twitchClient.getChat().joinChannel(configReader.getChannelName());
        twitchClient.getChat().sendMessage(configReader.getChannelName(), configReader.getStartMessage());

        botID = new TwitchIdentityProvider(null, null, null)
                .getAdditionalCredentialInformation(credential).map(OAuth2Credential::getUserId).orElse(null);

    }

    public String getBotID() {
        return this.botID;
    }

    public TwitchClient getTwitchClient() {
        return twitchClient;
    }
}
