package pl.galajus.twitchunt.ObjectsManager;

import pl.galajus.twitchunt.Twitchunt;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Manager {

    private final Twitchunt twitchunt;
    private final Map<Integer, PluginPollChoice> pluginPollChoicesList = Collections.synchronizedMap(new HashMap<>());

    public Manager(Twitchunt twitchunt) {
        this.twitchunt = twitchunt;
    }

    public void addPluginPollChoice(int id, PluginPollChoice pluginPollChoice) {
        pluginPollChoicesList.put(id, pluginPollChoice);
    }

    public Map<Integer, PluginPollChoice> getPluginPollChoicesList() {
        Map<Integer, PluginPollChoice> cloned = Collections.synchronizedMap(new HashMap<>());
        cloned.putAll(pluginPollChoicesList);
        return cloned;
    }
    public void reloadConfigOptions() {
        pluginPollChoicesList.clear();
        twitchunt.getConfigReader().readConfigs();
    }


}
