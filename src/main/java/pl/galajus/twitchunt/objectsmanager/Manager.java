package pl.galajus.twitchunt.objectsmanager;

import pl.galajus.twitchunt.Twitchunt;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Manager {

    private final Twitchunt twitchunt;
    private final Map<Integer, PluginPollChoice> pluginPollChoicesList = Collections.synchronizedMap(new HashMap<>());
    private final Map<Integer, PluginPollChoice> disabledPluginPollChoicesList = Collections.synchronizedMap(new HashMap<>());
    private final Map<String, Integer> twitchChannelPointsRewards = Collections.synchronizedMap(new HashMap<>());

    public Manager(Twitchunt twitchunt) {
        this.twitchunt = twitchunt;
    }

    public void addPluginPollChoice(Integer id, PluginPollChoice pluginPollChoice) {
        pluginPollChoicesList.put(id, pluginPollChoice);
    }

    public void addDisabledPluginPollChoice(Integer id, PluginPollChoice pluginPollChoice) {
        disabledPluginPollChoicesList.put(id, pluginPollChoice);
    }

    public Map<Integer, PluginPollChoice> getPluginPollChoicesList() {
        Map<Integer, PluginPollChoice> cloned = Collections.synchronizedMap(new HashMap<>());
        cloned.putAll(pluginPollChoicesList);
        return cloned;
    }

    public Map<Integer, PluginPollChoice> getDisabledPluginPollChoicesList() {
        Map<Integer, PluginPollChoice> cloned = Collections.synchronizedMap(new HashMap<>());
        cloned.putAll(disabledPluginPollChoicesList);
        return cloned;
    }

    public PluginPollChoice getPluginPollChoice(Integer ID) {
        return pluginPollChoicesList.get(ID);
    }

    public PluginPollChoice getDisabledPluginPollChoice(Integer ID) {
        return disabledPluginPollChoicesList.get(ID);
    }

    public void reloadConfigOptions() {
        pluginPollChoicesList.clear();
        disabledPluginPollChoicesList.clear();
        twitchunt.getConfigReader().readConfigs();
    }

    public void addTwitchReward(String name, Integer id) {
        twitchChannelPointsRewards.put(name, id);
    }

    public Map<String, Integer> getTwitchChannelPointsRewards() {
        return twitchChannelPointsRewards;
    }

}
