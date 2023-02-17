package pl.galajus.twitchunt.objectsmanager;

import pl.galajus.twitchunt.Twitchunt;

public class PluginPollChoice {

    private final int id;
    private final String lore;
    private final String customCommand1;
    private final String customCommand2;
    private final String customDelayCommand1;
    private final String customDelayCommand2;
    private final int delay;

    public PluginPollChoice(int id, String lore, boolean enabled) {
        this.id = id;
        this.lore = lore;
        customCommand1 = null;
        customCommand2 = null;
        customDelayCommand1 = null;
        customDelayCommand2 = null;
        delay = 0;
        if (enabled) {
            Twitchunt.getInstance().getManager().addPluginPollChoice(id, this);
        } else {
            Twitchunt.getInstance().getManager().addDisabledPluginPollChoice(id, this);
        }
    }

    public PluginPollChoice(Integer id, String lore, String customCommand1, String customCommand2, int delay, String customDelayCommand1, String customDelayCommand2, boolean enabled) {
        String stringID = "999" + id;
        int tempID;
        tempID = 0;
        try {
            tempID = Integer.parseInt(stringID);
        } catch (NumberFormatException e) {
            Twitchunt.getInstance().getDependencyResolver().errorLogToConsole("IDNotNumber");
        }

        this.id = tempID;
        this.lore = lore;
        this.customCommand1 = customCommand1;
        this.customCommand2 = customCommand2;
        this.customDelayCommand1 = customDelayCommand1;
        this.customDelayCommand2 = customDelayCommand2;
        this.delay = delay;
        if (enabled) {
            Twitchunt.getInstance().getManager().addPluginPollChoice(this.id, this);
        } else {
            Twitchunt.getInstance().getManager().addDisabledPluginPollChoice(this.id, this);
        }
    }

    public int getId() {
        return id;
    }

    public String getLore() {
        return lore;
    }

    public String getCustomCommand1() {
        return customCommand1;
    }

    public String getCustomCommand2() {
        return customCommand2;
    }

    public String getCustomDelayCommand1() {
        return customDelayCommand1;
    }

    public String getCustomDelayCommand2() {
        return customDelayCommand2;
    }

    public int getDelay() {
        return delay;
    }

    public String toString() {

        String cc1 = "";
        String cc2 = "";
        String cdc1 = "";
        String cdc2 = "";

        if (customCommand1 != null) {
            cc1 = customCommand1;
        }
        if (customCommand2 != null) {
            cc2 = customCommand2;
        }
        if (customDelayCommand1 != null) {
            cdc1 = customDelayCommand1;
        }
        if (customDelayCommand2 != null) {
            cdc2 = customDelayCommand2;
        }

        return "id: [" + id + "] lore: [" + lore + "] delay: [" + delay + "] customCommand1: [" + cc1 + "] customCommand2: [" + cc2 + "] customDelayCommand1: [" + cdc1 + "] customDelayCommand2: [" + cdc2 + "]";

    }
}
