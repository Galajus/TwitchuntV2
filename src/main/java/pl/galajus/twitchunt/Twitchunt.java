package pl.galajus.twitchunt;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import pl.galajus.twitchunt.Commands.CommandsHelper;
import pl.galajus.twitchunt.Commands.TwitchuntCommand;
import pl.galajus.twitchunt.Dependency.DependencyResolver;
import pl.galajus.twitchunt.Minecraft.EffectController;
import pl.galajus.twitchunt.Minecraft.EventsController;
import pl.galajus.twitchunt.ObjectsManager.Manager;
import pl.galajus.twitchunt.TwitchBot.Bot;
import pl.galajus.twitchunt.TwitchBot.PollCreator;

import java.util.Objects;

public final class Twitchunt extends JavaPlugin {

    private static Twitchunt instance;

    private Bot bot;
    private String prefix;

    private Manager manager;

    private Translations translations;
    private CommandsHelper commandsHelper;
    private DependencyResolver dependencyResolver;
    private ConfigReader configReader;
    private PollCreator pollCreator;
    private EventsController eventsController;
    private EffectController effectController;

    @Override
    public void onEnable() {

        instance = this;
        prefix = "§7[§4TWITCHUNT§7]§r ";

        translations = new Translations(this);

        manager = new Manager(this);

        //Reading configs
        configReader = new ConfigReader(this);

        //Paper check + dependencies
        dependencyResolver = new DependencyResolver(this, translations);

        //Registering Commands/Events
        commandsHelper = new CommandsHelper();

        TwitchuntCommand twitchuntCommand = new TwitchuntCommand(this, commandsHelper, dependencyResolver, configReader);
        Objects.requireNonNull(getCommand("twitchunt")).setExecutor(twitchuntCommand);
        Bukkit.getPluginManager().registerEvents(twitchuntCommand, this);

        new Clock(this);

        eventsController = new EventsController(this);

        effectController = new EffectController(this);

        //Registering Twitch bot
        this.bot = new Bot(this, configReader);

        pollCreator = new PollCreator(this, bot, configReader);

        dependencyResolver.registerEvents();

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public Bot getBot() {
        return bot;
    }

    public String getPrefix() {
        return prefix;
    }

    public Translations getTranslations() {
        return translations;
    }

    public CommandsHelper getCommandsHelper() {
        return commandsHelper;
    }

    public DependencyResolver getDependencyResolver() {
        return dependencyResolver;
    }

    public ConfigReader getConfigReader() {
        return configReader;
    }

    public Manager getManager() {
        return manager;
    }

    public PollCreator getPollCreator() {
        return pollCreator;
    }

    public EffectController getEffectController() {
        return effectController;
    }

    public EventsController getEventsController() {
        return eventsController;
    }

    public static Twitchunt getInstance() {
        return instance;
    }
}
