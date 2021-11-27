package pl.galajus.twitchunt.Commands;

import java.util.List;

public class CommandsHelper {

    private List<String> commandTwitchuntArgsList;
    private List<String> commandTwitchuntChoiceArgsList;

    public CommandsHelper() {
        this.initialize();
    }

    private void initialize() {
        commandTwitchuntArgsList = List.of(
                "twitchmessages",
                "addhunted",
                "removehunted",
                "setpollduration",
                "setpollinterval",
                "setpollchoicesamount",
                "start",
                "stop",
                "reload",
                "pollsinstastart");

        commandTwitchuntChoiceArgsList = List.of("true", "false");
    }

    public List<String> getCommandTwitchuntArgsList() {
        return commandTwitchuntArgsList;
    }

    public List<String> getCommandTwitchuntChoicesArgsList() {
        return commandTwitchuntChoiceArgsList;
    }
}
