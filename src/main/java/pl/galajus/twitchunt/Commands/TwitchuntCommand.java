package pl.galajus.twitchunt.Commands;

import com.destroystokyo.paper.event.server.AsyncTabCompleteEvent;
import org.apache.commons.lang.ArrayUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import pl.galajus.twitchunt.ConfigReader;
import pl.galajus.twitchunt.Dependency.DependencyResolver;
import pl.galajus.twitchunt.Twitchunt;

import java.util.ArrayList;
import java.util.List;

public class TwitchuntCommand implements CommandExecutor, Listener {

    private final Twitchunt twitchunt;
    private final CommandsHelper commandsHelper;
    private final DependencyResolver dependencyResolver;
    private final ConfigReader configReader;

    private boolean cooldown = false;

    public TwitchuntCommand(Twitchunt twitchunt, CommandsHelper commandsHelper, DependencyResolver dependencyResolver, ConfigReader configReader) {
        this.twitchunt = twitchunt;
        this.commandsHelper = commandsHelper;
        this.dependencyResolver = dependencyResolver;
        this.configReader = configReader;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        
        if (sender instanceof Player || sender instanceof ConsoleCommandSender) {

            if (args.length > 0) {
                if (args[0].equalsIgnoreCase("addhunted")) {
                    if (args.length > 1) {
                        if (configReader.addHunted(args[1])) {
                            dependencyResolver.sendTranslatedMessage(sender,"huntedAdded");
                        } else {
                            dependencyResolver.sendTranslatedMessage(sender,"playersIsOnHuntedList");
                        }

                    } else {
                        dependencyResolver.sendTranslatedMessage(sender,"missingHuntedName");
                    }
                    return true;
                }

                if (args[0].equalsIgnoreCase("removehunted")) {
                    if (args.length > 1) {
                        configReader.removeHunted(args[1]);
                        dependencyResolver.sendTranslatedMessage(sender,"huntedRemoved");
                    } else {
                        dependencyResolver.sendTranslatedMessage(sender,"missingHuntedName");
                    }
                    return true;
                }

                if (args[0].equalsIgnoreCase("reload")) {
                    twitchunt.getManager().reloadConfigOptions();
                    dependencyResolver.sendTranslatedMessage(sender,"configReloaded");
                    return true;
                }
                if (args[0].equalsIgnoreCase("start")) {
                    if (cooldown) {
                        dependencyResolver.sendTranslatedMessage(sender,"stopCooldown");
                        return true;
                    }
                    //TODO:
//                    if (startEffectPolls()) {
//                        dependencyResolver.sendTranslatedMessage(sender,"pollsStarted");
//                    } else {
//                        dependencyResolver.sendTranslatedMessage(sender,"pollsAlreadyStarted");
//                    }
                    return true;
                }
                if (args[0].equalsIgnoreCase("stop")) {

                    //TODO:
//                    if (stopEffectPolls()) {
//                        dependencyResolver.sendTranslatedMessage(sender,"pollsDisabled");
//                        Bukkit.getScheduler().cancelTask(TimeChoices.getTaskIDPoll());
//                        Bukkit.getScheduler().cancelTask(TimeChoices.getTaskIDInterval());
//                        cooldown = true;
//                        Bukkit.getScheduler().runTaskLater(twitchunt,
//                                () -> cooldown = false,
//                                (configReader.getPollInterval() + 5L) * 20);
//                    } else {
//                        dependencyResolver.sendTranslatedMessage(sender,"pollsNotEnabled");
//                    }
                    return true;
                }

                if (args[0].equalsIgnoreCase("setpollduration")) {
                    if (args.length > 1) {
                        try {
                            long pollDuration = Long.parseLong(args[1]);
                            if (pollDuration > 19 && pollDuration < configReader.getPollInterval() - 5) {
                                configReader.setPollDuration(pollDuration);
                                dependencyResolver.sendTranslatedMessage(sender,"pollDurationChanged");
                            } else {
                                dependencyResolver.sendTranslatedMessage(sender,"pollDurationTooShort");
                            }
                        } catch (NumberFormatException ex) {
                            dependencyResolver.sendTranslatedMessage(sender,"notInteger");
                        }
                    } else {
                        dependencyResolver.sendTranslatedMessage(sender,"missingTimeArg");
                    }
                    return true;
                }

                if (args[0].equalsIgnoreCase("setpollinterval")) {
                    if (args.length > 1) {
                        try {
                            long pollInterval = Long.parseLong(args[1]);
                            long pollDuration = configReader.getPollDuration();
                            if (pollInterval > pollDuration + 5) {
                                configReader.setPollInterval(pollInterval);
                                dependencyResolver.sendTranslatedMessage(sender,"pollIntervalChanged");
                            } else {
                                dependencyResolver.sendTranslatedMessage(sender,"pollIntervalTooShort");
                            }
                        } catch (NumberFormatException ex) {
                            dependencyResolver.sendTranslatedMessage(sender,"notInteger");
                        }
                    } else {
                        dependencyResolver.sendTranslatedMessage(sender,"missingTimeArg");
                    }
                    return true;
                }

                if (args[0].equalsIgnoreCase("setpollchoicesamount")) {
                    if (args.length > 1) {
                        try {
                            int amountChoices = Integer.parseInt(args[1]);
                            if (amountChoices > 1 && amountChoices < 6) {
                                configReader.setOptionsPerPoll(amountChoices);
                                dependencyResolver.sendTranslatedMessage(sender,"amountChoicesChanged");
                            } else {
                                dependencyResolver.sendTranslatedMessage(sender,"badAmountChoices");
                            }
                        } catch (NumberFormatException ex) {
                            dependencyResolver.sendTranslatedMessage(sender,"notInteger");
                        }
                    } else {
                        dependencyResolver.sendTranslatedMessage(sender,"missingAmountArg");
                    }
                    return true;
                }

                if (args[0].equalsIgnoreCase("twitchmessages")) {
                    if (args.length > 1) {
                        try {
                            boolean choice = Boolean.parseBoolean(args[1]);
                            if (choice) {
                                dependencyResolver.sendTranslatedMessage(sender,"streamMessagesEnabled");
                            } else {
                                dependencyResolver.sendTranslatedMessage(sender,"streamMessagesDisabled");
                            }
                            configReader.setTwitchMessages(choice);
                        } catch (Exception ex) {
                            dependencyResolver.sendTranslatedMessage(sender,"notBoolean");
                        }
                    } else {
                        dependencyResolver.sendTranslatedMessage(sender,"makeChoose");
                    }
                    return true;
                }

                if (args[0].equalsIgnoreCase("pollsinstastart")) {
                    if (args.length > 1) {
                        try {
                            boolean choice = Boolean.parseBoolean(args[1]);
                            if (choice) {
                                dependencyResolver.sendTranslatedMessage(sender,"instaStartEnabled");
                            } else {
                                dependencyResolver.sendTranslatedMessage(sender,"instaStartDisabled");
                            }
                            configReader.setPollInstaStart(choice);
                        } catch (Exception ex) {
                            dependencyResolver.sendTranslatedMessage(sender,"notBoolean");
                        }
                    } else {
                        dependencyResolver.sendTranslatedMessage(sender,"makeChoose");
                    }
                    return true;
                }
            }

            String version = twitchunt.getDescription().getVersion();
            dependencyResolver.sendMessage(sender, "§aVersion: " + version + " §bCreated by §6Galajus");
            dependencyResolver.sendTranslatedMessage(sender,"help1");
            dependencyResolver.sendTranslatedMessage(sender,"help2");
            dependencyResolver.sendTranslatedMessage(sender,"help3");
            dependencyResolver.sendTranslatedMessage(sender,"help4");
            dependencyResolver.sendTranslatedMessage(sender,"help5");
            dependencyResolver.sendTranslatedMessage(sender,"help6");
            dependencyResolver.sendTranslatedMessage(sender,"help7");
            dependencyResolver.sendTranslatedMessage(sender,"help8");
            dependencyResolver.sendTranslatedMessage(sender,"help9");
            dependencyResolver.sendTranslatedMessage(sender,"help10");
            return true;
        }

        return false;
    }

    @EventHandler
    public void onAsyncTabCompleteEvent(AsyncTabCompleteEvent e) {
        String buffer = "";

        if (e.getBuffer().startsWith("/")) {
            buffer = e.getBuffer().substring(1);
        }

        boolean spaceArg = e.getBuffer().endsWith(" ");

        String[] args = null;
        if (!buffer.isBlank()) {
            if (spaceArg) {
                String[] spaceString = {""};
                args = (String[]) ArrayUtils.addAll(buffer.split(" "), spaceString);
            } else {
                args = buffer.split(" ");
            }

        }

        if (args != null) {
            if (args.length == 0 || !e.isCommand() || e.isCancelled()) return;
        } else  {
            return;
        }

        List<String> suggestions = new ArrayList<>();

        if (args[0].equalsIgnoreCase("twitchunt")) {

            if (args.length <= 3) {

                if (args.length == 2) {
                    StringUtil.copyPartialMatches(args[1], commandsHelper.getCommandTwitchuntArgsList(), suggestions);
                }
            }
            if (args.length >= 3) {
                if (args[1].equalsIgnoreCase("addhunted")) {
                    if (args.length == 3) {
                        for (Player p : Bukkit.getOnlinePlayers()) {
                            suggestions.add(p.getName());
                        }
                    }
                }

                if (args[1].equalsIgnoreCase("removehunted")) {
                    if (args.length == 3) {
                        StringUtil.copyPartialMatches(args[2], configReader.getHuntedPlayersAsStrings(), suggestions);
                    }
                }

                if (args[1].equalsIgnoreCase("twitchmessages")) {

                    if (args.length == 3) {
                        StringUtil.copyPartialMatches(args[2], commandsHelper.getCommandTwitchuntChoicesArgsList(), suggestions);
                    }
                }

                if (args[1].equalsIgnoreCase("pollsinstastart")) {

                    if (args.length == 3) {
                        StringUtil.copyPartialMatches(args[2], commandsHelper.getCommandTwitchuntChoicesArgsList(), suggestions);
                    }
                }

                if (args[1].equalsIgnoreCase("setpollinterval") || args[1].equalsIgnoreCase("setpollduration")) {

                    if (args.length == 3) {
                        suggestions.add(":timeinseconds");
                    }
                }

                if (args[1].equalsIgnoreCase("setpollchoicesamount")) {
                    if (args.length == 3) {
                        suggestions.add("2");
                        suggestions.add("3");
                        suggestions.add("4");
                        suggestions.add("5");
                    }
                }
            }
        }

        e.getCompletions().addAll(suggestions);
    }
}
