package pl.galajus.twitchunt.minecraft;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;
import pl.galajus.twitchunt.objectsmanager.PaidCastType;
import pl.galajus.twitchunt.objectsmanager.PluginPollChoice;
import pl.galajus.twitchunt.Twitchunt;

import java.util.List;
import java.util.Map;

public class EffectController {

    private final Twitchunt twitchunt;
    private final boolean isPaper;
    private final EffectCaster effectCaster;
    private final PaperEffectCaster paperEffectCaster;

    public EffectController(Twitchunt twitchunt) {
        this.twitchunt = twitchunt;
        this.isPaper = twitchunt.getDependencyResolver().isPaper();
        this.effectCaster = new EffectCaster(twitchunt, twitchunt.getEventsController());

        if (isPaper) {
            this.paperEffectCaster = new PaperEffectCaster(twitchunt, twitchunt.getEventsController());
        } else {
            paperEffectCaster = null;
        }
    }

    public void castEffect(String loreOfEffect) {

        Bukkit.getScheduler().runTaskAsynchronously(twitchunt, () -> {

            int option = 0;
            String customCommand1 = null;
            String customCommand2 = null;
            String customDelayCommand1 = null;
            String customDelayCommand2 = null;
            int delay = 0;
            Map<Integer, PluginPollChoice> choicesMap = twitchunt.getManager().getPluginPollChoicesList();
            for (var entry : choicesMap.entrySet()) {
                PluginPollChoice c = entry.getValue();
                if (loreOfEffect.equalsIgnoreCase(c.getLore())) {
                    option = c.getId();
                    customCommand1 = c.getCustomCommand1();
                    customCommand2 = c.getCustomCommand2();
                    customDelayCommand1 = c.getCustomDelayCommand1();
                    customDelayCommand2 = c.getCustomDelayCommand2();
                    delay = c.getDelay();
                    break;
                }
            }
            if (customCommand1 != null) {
                customEffect(customCommand1, customCommand2, delay, customDelayCommand1, customDelayCommand2);
            } else {
                executePluginEffect(option);
            }

        });
    }

    private void executePluginEffect(Integer option) {

        Bukkit.getScheduler().runTaskAsynchronously(twitchunt, () -> {
            switch (option) {
                case 1:
                    effectCaster.effectCreepers();
                    break;
                case 2:
                    if (isPaper) {
                        paperEffectCaster.effectTeleportRandom();
                    } else {
                        effectCaster.effectTeleportRandomSpigot();
                    }
                    break;
                case 3:
                    if (isPaper) {
                        paperEffectCaster.effectTeleportUp();
                    } else {
                        effectCaster.effectTeleportUpSpigot();
                    }
                    break;
                case 4:
                    if (isPaper) {
                        paperEffectCaster.effectTeleportUpv2();
                    } else {
                        effectCaster.effectTeleportUpSpigotv2();
                    }
                    break;
                case 5:
                    effectCaster.effectBlindness();
                    break;
                case 6:
                    effectCaster.effectBlindnessv2();
                    break;
                case 7:
                    effectCaster.effectNight();
                    break;
                case 8:
                    effectCaster.effectLevitation();
                    break;
                case 9:
                    effectCaster.effectLevitationv2();
                    break;
                case 10:
                    effectCaster.effectClearInventory();
                    break;
                case 11:
                    effectCaster.effectOneHealth();
                    break;
                case 12:
                    effectCaster.effectPoison();
                    break;
                case 13:
                    effectCaster.effectFloorIsLava();
                    break;
                case 14:
                    effectCaster.effectGiveSomeBread();
                    break;
                case 15:
                    effectCaster.effectSandAttack();
                    break;
                case 16:
                    effectCaster.effectSpawnTNT();
                    break;
                case 17:
                    effectCaster.effectBlockInventory();
                    break;
                case 18:
                    effectCaster.effectSpectator();
                    break;
                case 19:
                    effectCaster.effectSpectatorv2();
                    break;
                case 20:
                    effectCaster.effectZombieAttack();
                    break;
                case 21:
                    effectCaster.effectBurn();
                    break;
                case 22:
                    effectCaster.effectSlow();
                    break;
                case 23:
                    effectCaster.effectBlockInventoryClick();
                    break;
                case 24:
                    effectCaster.effectBlockPickup();
                    break;
                case 25:
                    effectCaster.damageOnBreak();
                    break;
                case 26:
                    effectCaster.effectNausea();
                    break;
                case 27:
                    effectCaster.effectRegeneration();
                    break;
                case 28:
                    effectCaster.effectNightvision();
                    break;
                case 29:
                    effectCaster.effectNearestVillage();
                    break;
                case 30:
                    effectCaster.effectHealAndFeed();
                    break;
                case 31:
                    effectCaster.effectBoostEntityDrop();
                    break;
                case 32:
                    effectCaster.effectMorningAndSun();
                    break;
                case 33:
                    effectCaster.effectBlockDamageOnAttack();
                    break;
                case 34:
                    effectCaster.effectRemoveRandomItem();
                    break;
                case 35:
                    effectCaster.effectWitherPoison();
                    break;
                default:
                    twitchunt.getDependencyResolver().errorLogToConsole("notFoundID");
                    break;
            }

        });

    }

    /**
     * Execute custom effect
     */
    public void customEffect(String command1, @Nullable String command2, int delay, @Nullable  String delayCommand1, @Nullable  String delayCommand2 ) {
        List<Player> huntedPlayers = twitchunt.getConfigReader().getHuntedPlayers();

        for (Player hunted : huntedPlayers) {
            String preparedCommand1 = command1.replaceAll("%player%", hunted.getName());
            preparedCommand1 = preparedCommand1.replaceAll("%world%", hunted.getLocation().getWorld().getName());
            preparedCommand1 = preparedCommand1.replaceAll("%x-coord%", String.valueOf(hunted.getLocation().getX()));
            preparedCommand1 = preparedCommand1.replaceAll("%y-coord%", String.valueOf(hunted.getLocation().getY()));
            preparedCommand1 = preparedCommand1.replaceAll("%z-coord%", String.valueOf(hunted.getLocation().getZ()));

            String preparedCommand2 = null;
            if (command2 != null) {
                preparedCommand2 = command2.replaceAll("%player%", hunted.getName());
                preparedCommand2 = preparedCommand2.replaceAll("%world%", hunted.getLocation().getWorld().getName());
                preparedCommand2 = preparedCommand2.replaceAll("%x-coord%", String.valueOf(hunted.getLocation().getX()));
                preparedCommand2 = preparedCommand2.replaceAll("%y-coord%", String.valueOf(hunted.getLocation().getY()));
                preparedCommand2 = preparedCommand2.replaceAll("%z-coord%", String.valueOf(hunted.getLocation().getZ()));
            }

            boolean delayed = delay != 0;

            String preparedDelayedCommand1 = null;
            if (delayCommand1 != null) {
                preparedDelayedCommand1 = delayCommand1.replaceAll("%player%", hunted.getName());
                preparedDelayedCommand1 = preparedDelayedCommand1.replaceAll("%world%", hunted.getLocation().getWorld().getName());
                preparedDelayedCommand1 = preparedDelayedCommand1.replaceAll("%x-coord%", String.valueOf(hunted.getLocation().getX()));
                preparedDelayedCommand1 = preparedDelayedCommand1.replaceAll("%y-coord%", String.valueOf(hunted.getLocation().getY()));
                preparedDelayedCommand1 = preparedDelayedCommand1.replaceAll("%z-coord%", String.valueOf(hunted.getLocation().getZ()));
            }

            String preparedDelayedCommand2 = null;
            if (delayCommand2 != null) {
                preparedDelayedCommand2 = delayCommand2.replaceAll("%player%", hunted.getName());
                preparedDelayedCommand2 = preparedDelayedCommand2.replaceAll("%world%", hunted.getLocation().getWorld().getName());
                preparedDelayedCommand2 = preparedDelayedCommand2.replaceAll("%x-coord%", String.valueOf(hunted.getLocation().getX()));
                preparedDelayedCommand2 = preparedDelayedCommand2.replaceAll("%y-coord%", String.valueOf(hunted.getLocation().getY()));
                preparedDelayedCommand2 = preparedDelayedCommand2.replaceAll("%z-coord%", String.valueOf(hunted.getLocation().getZ()));
            }

            String finalPreparedCommand1 = preparedCommand1;
            String finalPreparedCommand2 = preparedCommand2;
            String finalPreparedDelayedCommand1 = preparedDelayedCommand1;
            String finalPreparedDelayedCommand2 = preparedDelayedCommand2;
            Bukkit.getScheduler().runTask(twitchunt, () -> {

                Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), finalPreparedCommand1);
                if (finalPreparedCommand2 != null) {
                    Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), finalPreparedCommand2);
                }

                if (delayed) {
                    Bukkit.getScheduler().runTaskLater(twitchunt, () -> {
                        if (finalPreparedDelayedCommand1 != null) {
                            Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), finalPreparedDelayedCommand1);
                        }
                        if (finalPreparedDelayedCommand2 != null) {
                            Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), finalPreparedDelayedCommand2);
                        }
                    }, delay);
                }

            });
        }
    }

    public void castPaidEffect(Integer effectId, PaidCastType type, String who, @Nullable Double amountOrLevel) {
        String idString = String.valueOf(effectId);

        PluginPollChoice choice = twitchunt.getManager().getPluginPollChoice(effectId);

        if (choice == null) {
            choice = twitchunt.getManager().getDisabledPluginPollChoice(effectId);
        }

        if (idString.startsWith("999")) {
            if (choice != null) {
                customEffect(choice.getCustomCommand1(), choice.getCustomCommand2(), choice.getDelay(), choice.getCustomDelayCommand1(), choice.getCustomDelayCommand2());
                this.infoAboutPaidCaster(type, who, amountOrLevel, choice.getLore());
            } else {
                twitchunt.getDependencyResolver().errorLogToConsole("paidEffectNull");
            }

        } else {
            executePluginEffect(effectId);
            this.infoAboutPaidCaster(type, who, amountOrLevel, choice.getLore());
        }
    }

    private void infoAboutPaidCaster(PaidCastType type, String who, @Nullable Double amountOrLevel, String effectName) {

        String title = "";
        if (amountOrLevel == null) {
            amountOrLevel = 0D;
        }
        String subTitle = twitchunt.getDependencyResolver().getTranslatedText("paidEffectSubtitle", effectName);

        if (type.equals(PaidCastType.POINTS_REDEMPTION)) {
            title = twitchunt.getDependencyResolver().getTranslatedText("pointsRedemptionAlertTitle", who);
        }
        if (type.equals(PaidCastType.CHEER)) {
            title = twitchunt.getDependencyResolver().getTranslatedText("cheerAlertTitle", who, Double.toString(amountOrLevel).replaceAll("\\.0", ""));
        }
        if (type.equals(PaidCastType.SUBSCRIPTION)) {
            title = twitchunt.getDependencyResolver().getTranslatedText("subscriptionAlertTitle", who, Double.toString(amountOrLevel).replaceAll("\\.0", ""));
        }
        if (type.equals(PaidCastType.DONATION)) {
            title = twitchunt.getDependencyResolver().getTranslatedText("donationAlertTitle", who, Double.toString(amountOrLevel).replaceAll("\\.0", ""));
        }

        for (Player p : twitchunt.getConfigReader().getHuntedPlayers()) {
            p.sendTitle(title, subTitle, 10, 50, 10);
        }
    }

    public EffectCaster getEffectCaster() {
        return effectCaster;
    }

    public PaperEffectCaster getPaperEffectCaster() {
        return paperEffectCaster;
    }
}
