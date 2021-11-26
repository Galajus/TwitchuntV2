package pl.galajus.twitchunt;

import org.bukkit.plugin.java.JavaPlugin;
import pl.galajus.twitchunt.Commands.CommandsHelper;
import pl.galajus.twitchunt.Commands.TwitchuntCommand;
import pl.galajus.twitchunt.Dependency.DependencyResolver;
import pl.galajus.twitchunt.ObjectsManager.Manager;
import pl.galajus.twitchunt.TwitchBot.Bot;

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

    @Override
    public void onEnable() {

        instance = this;
        prefix = "§7[§4TWITCHUNT§7]§r ";

        translations = new Translations(this);

        manager = new Manager(this);

        //TODO: Paper check + dependencies + registering events
        dependencyResolver = new DependencyResolver(this, translations);

        //TODO: Reading configs
        configReader = new ConfigReader(this, dependencyResolver);

        //Registering Commands
        commandsHelper = new CommandsHelper();
        Objects.requireNonNull(getCommand("twitchunt")).setExecutor(new TwitchuntCommand(this, commandsHelper, dependencyResolver, configReader));

        //Registering Twitch bot
        this.bot = new Bot(this, configReader);

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

    public static Twitchunt getInstance() {
        return instance;
    }
}
