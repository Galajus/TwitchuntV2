package pl.galajus.twitchunt.Minecraft;

import org.bukkit.*;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import pl.galajus.twitchunt.Twitchunt;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class EffectCaster {

    private final Twitchunt twitchunt;
    private final EventsController eventsController;

    private List<Player> huntedPlayers;

    public EffectCaster(Twitchunt twitchunt, EventsController eventsController) {
        this.twitchunt = twitchunt;
        this.eventsController = eventsController;
        this.updateHunters();
    }

    public void updateHunters() {
        huntedPlayers = twitchunt.getConfigReader().getHuntedPlayers();
    }

    /**
     * Blind hunted for 10 second
     */
    public void effectBlindness() {
        this.updateHunters();
        for (Player hunted : huntedPlayers) {
            PotionEffect effect = new PotionEffect(PotionEffectType.BLINDNESS, 10 * 20, 0);
            Bukkit.getScheduler().runTask(twitchunt, () -> hunted.addPotionEffect(effect));
        }
    }

    /**
     * Blind hunted for 20 second
     */
    public void effectBlindnessv2() {
        this.updateHunters();
        for (Player hunted : huntedPlayers) {
            PotionEffect effect = new PotionEffect(PotionEffectType.BLINDNESS, 20 * 20, 0);
            Bukkit.getScheduler().runTask(twitchunt, () -> hunted.addPotionEffect(effect));
        }
    }

    /**
     * Block damages to other entities for 30 seconds
     */
    public void effectBlockDamageOnAttack() {
        this.updateHunters();
        eventsController.setEntityDamageEntityBLock(true);
    }

    /**
     * Block inventory click for 30 seconds
     */
    public void effectBlockInventoryClick() {
        this.updateHunters();
        eventsController.setBlockInventoryClick(true);

    }

    /**
     * Block pickup for 45 seconds
     */
    public void effectBlockPickup() {
        this.updateHunters();
        eventsController.setBlockItemPickUp(true);

    }

    /**
     * Double for 45 seconds
     */
    public void effectBoostEntityDrop() {
        this.updateHunters();
        eventsController.setDoubleEntityDeathDrop(true);
    }

    /**
     * Give 16 bread to hunted
     */
    public void effectGiveSomeBread() {
        this.updateHunters();
        ItemStack bread = new ItemStack(Material.BREAD, 16);

        for (Player hunted : huntedPlayers) {
            hunted.getInventory().addItem(bread);
        }
    }

    /**
     * Breaking blocks take damage to hunted for 30 seconds
     */
    public void damageOnBreak() {
        this.updateHunters();
        eventsController.setDamageOnBreak(true);
    }

    /**
     * Make player burning 8 seconds
     */
    public void effectBurn() {
        this.updateHunters();
        for (Player hunted : huntedPlayers) {
            Bukkit.getScheduler().runTask(twitchunt, () -> hunted.setFireTicks(8*20));
        }
    }

    /**
     * Clear hunted inventory
     */
    public void effectClearInventory() {
        this.updateHunters();
        for (Player hunted : huntedPlayers) {
            hunted.getInventory().clear();
        }
    }

    /**
     * Spawn some creepers around hunted player
     */
    public void effectCreepers() {
        this.updateHunters();
        for (Player hunted : huntedPlayers) {
            Bukkit.getScheduler().runTask(twitchunt, () -> {
                Location hLoc = hunted.getLocation();
                hunted.getWorld().spawnEntity(hLoc.clone().add(0, 3, 0), EntityType.CREEPER);
                hunted.getWorld().spawnEntity(hLoc.clone().add(3, 2, 0), EntityType.CREEPER);
                hunted.getWorld().spawnEntity(hLoc.clone().add(-3, 2, 0), EntityType.CREEPER);
                hunted.getWorld().spawnEntity(hLoc.clone().add(0, 2, 3), EntityType.CREEPER);
                hunted.getWorld().spawnEntity(hLoc.clone().add(0, 2, -3), EntityType.CREEPER);

            });
        }
    }

    /**
     * Set block at hunted to lava
     */
    public void effectFloorIsLava() {
        this.updateHunters();
        for (Player hunted : huntedPlayers) {
            Bukkit.getScheduler().runTask(twitchunt, () -> {
                Location loc = hunted.getLocation();
                loc.getBlock().setType(Material.LAVA);
            });
        }
    }

    /**
     * Set Hp and feed level to 20
     */
    public  void effectHealAndFeed() {
        this.updateHunters();
        for (Player hunted : huntedPlayers) {
            hunted.setHealth(20);
            hunted.setFoodLevel(20);
        }
    }

    /**
     * Levitation for hunted for 10 seconds
     */
    public void effectLevitation() {
        this.updateHunters();
        PotionEffect effect = new PotionEffect(PotionEffectType.LEVITATION, 10 * 20, 0);
        for (Player hunted : huntedPlayers) {
            Bukkit.getScheduler().runTask(twitchunt, () -> hunted.addPotionEffect(effect));
        }
    }

    /**
     * Levitation for hunted for 20 seconds
     */
    public void effectLevitationv2() {
        this.updateHunters();
        PotionEffect effect = new PotionEffect(PotionEffectType.LEVITATION, 20 * 20, 0);
        for (Player hunted : huntedPlayers) {
            Bukkit.getScheduler().runTask(twitchunt, () -> hunted.addPotionEffect(effect));
        }
    }

    /**
     * Block open inventory for 60 seconds
     */
    public void effectBlockInventory() {
        this.updateHunters();
        eventsController.setBlockInventoryOpen(true);

    }

    /**
     * Make morning and sunny weather
     */
    public void effectMorningAndSun() {
        this.updateHunters();
        World world = Bukkit.getServer().getWorld("world");
        if (world != null) {
            Bukkit.getScheduler().runTask(twitchunt, () -> {
                world.setTime(0);
                world.setClearWeatherDuration(60000);
            });
        }
    }

    /**
     * Nausea for 30 seconds
     */
    public void effectNausea() {
        this.updateHunters();
        PotionEffect effect = new PotionEffect(PotionEffectType.CONFUSION, 30 * 20, 0);

        for (Player hunted : huntedPlayers) {
            Bukkit.getScheduler().runTask(twitchunt, () -> hunted.addPotionEffect(effect));
        }
    }

    /**
     * Find the nearest village
     */
    public void effectNearestVillage() {
        this.updateHunters();
        for (Player hunted : huntedPlayers) {
            if (hunted.getLocation().getWorld().getName().equals("world")) {
                Bukkit.getScheduler().runTask(twitchunt, () -> {
                    Location village = hunted.getLocation().getWorld().locateNearestStructure(hunted.getLocation(), StructureType.VILLAGE, 1000, false);
                    if (village != null) {
                        twitchunt.getDependencyResolver().sendMessage(hunted, "Location of nearest village: X: " + village.getBlockX() + " Z: " + village.getBlockZ());
                    } else {
                        twitchunt.getDependencyResolver().sendMessage(hunted, "Not found any village in radius 1000");
                    }
                });
            } else {
                twitchunt.getDependencyResolver().sendMessage(hunted, "In this world i can't find village");
            }
        }
    }

    /**
     * Make night in World, world
     */
    public void effectNight() {
        this.updateHunters();
        World world = Bukkit.getServer().getWorld("world");
        if (world != null) {
            Bukkit.getScheduler().runTask(twitchunt, () -> world.setTime(13000));
        }
    }

    /**
     * Nightvison for 60 seconds
     */
    public void effectNightvision() {
        this.updateHunters();
        PotionEffect effect = new PotionEffect(PotionEffectType.NIGHT_VISION, 60 * 20, 0);

        for (Player hunted : huntedPlayers) {
            Bukkit.getScheduler().runTask(twitchunt, () -> hunted.addPotionEffect(effect));
        }
    }

    /**
     * Set Hp of hunted to 1 and food level to 0
     */
    public void effectOneHealth() {
        this.updateHunters();
        for (Player hunted : huntedPlayers) {
            hunted.setHealth(1);
            hunted.setFoodLevel(0);
        }
    }

    /**
     * Poisoning for 60 seconds
     */
    public void effectPoison() {
        this.updateHunters();
        PotionEffect effect = new PotionEffect(PotionEffectType.POISON, 60 * 20, 0);

        for (Player hunted : huntedPlayers) {
            Bukkit.getScheduler().runTask(twitchunt, () -> hunted.addPotionEffect(effect));
        }
    }

    /**
     * Regeneration lvl 3 for 15 seconds
     */
    public void effectRegeneration() {
        this.updateHunters();
        PotionEffect effect = new PotionEffect(PotionEffectType.REGENERATION, 15 * 20, 2);

        for (Player hunted : huntedPlayers) {
            Bukkit.getScheduler().runTask(twitchunt, () -> hunted.addPotionEffect(effect));
        }
    }

    /**
     * Remove random item from hunted inventory
     */
    public void effectRemoveRandomItem() {
        this.updateHunters();
        for (Player hunted : huntedPlayers) {
            List<ItemStack> huntedItems = new ArrayList<>();
            for (ItemStack itemStack : hunted.getInventory()) {
                if (itemStack != null) huntedItems.add(itemStack);
            }
            if (huntedItems.isEmpty()) {
                return;
            }
            int rand = new Random().nextInt(huntedItems.size());
            hunted.getInventory().removeItem(huntedItems.get(rand));
        }
    }

    /**
     * Clear hunted inventory and fill sand
     */
    public void effectSandAttack() {
        this.updateHunters();
        ItemStack sand = new ItemStack(Material.SAND, 4000);

        for (Player hunted : huntedPlayers) {
            hunted.getInventory().addItem(sand);
        }
    }

    /**
     * Slow for 60 seconds
     */
    public void effectSlow() {
        this.updateHunters();
        PotionEffect effect = new PotionEffect(PotionEffectType.SLOW, 60 * 20, 0);

        for (Player hunted : huntedPlayers) {
            Bukkit.getScheduler().runTask(twitchunt, () -> hunted.addPotionEffect(effect));
        }
    }

    /**
     * Set gamemode of hunted to spectator for 5 seconds
     */
    public void effectSpectator() {
        this.updateHunters();
        for (Player hunted : huntedPlayers) {
            Bukkit.getScheduler().runTask(twitchunt, () -> {

                hunted.setGameMode(GameMode.SPECTATOR);
                Bukkit.getScheduler().runTaskLater(twitchunt,
                        () -> hunted.setGameMode(GameMode.SURVIVAL),
                        5 * 20);
            });
        }
    }

    /**
     * Set gamemode of hunted to spectator for 10 seconds
     */
    public void effectSpectatorv2() {
        this.updateHunters();
        for (Player hunted : huntedPlayers) {
            Bukkit.getScheduler().runTask(twitchunt, () -> {

                hunted.setGameMode(GameMode.SPECTATOR);
                Bukkit.getScheduler().runTaskLater(twitchunt,
                        () -> hunted.setGameMode(GameMode.SURVIVAL),
                        10 * 20);
            });
        }
    }

    /**
     * Teleport hunted player to random location in radius +/- 4000 from x: 0 z: 0 coords
     * SPIGOT COMPATIBLE
     */
    public void effectTeleportRandomSpigot() {
        this.updateHunters();
        Bukkit.getScheduler().runTaskAsynchronously(twitchunt, () -> {

            for (Player hunted : huntedPlayers) {
                int x = ThreadLocalRandom.current().nextInt(0, 4000 + 1);
                int z = ThreadLocalRandom.current().nextInt(0, 4000 + 1);
                int y = 90;

                while (hunted.getWorld().getBlockAt(x, y - 1, z).getType().equals(Material.AIR)) { // && hunted.getWorld().getBlockAt(x, y-1, z).getType().equals(Material.AIR) && hunted.getWorld().getBlockAt(x, y-1, z).getType().equals(Material.WATER)) {
                    y--;
                    if (y < 35) {
                        x = ThreadLocalRandom.current().nextInt(0, 4000 + 1);
                        z = ThreadLocalRandom.current().nextInt(0, 4000 + 1);
                        y = 90;
                    }
                }
                Location tpLoc = new Location(hunted.getWorld(), x, y, z);

                Bukkit.getScheduler().runTask(twitchunt, () -> hunted.teleport(tpLoc));

            }
        });
    }

    /**
     * Teleport hunted player 3 or 6 (v2) blocks up
     * SPIGOT COMPATIBLE
     */
    public void effectTeleportUpSpigot() {
        this.updateHunters();
        for (Player hunted : huntedPlayers) {
            Bukkit.getScheduler().runTask(twitchunt, () -> hunted.teleport(hunted.getLocation().add(0, 3, 0)));
        }
    }

    public void effectTeleportUpSpigotv2() {
        this.updateHunters();
        for (Player hunted : huntedPlayers) {
            Bukkit.getScheduler().runTask(twitchunt, () -> hunted.teleport(hunted.getLocation().add(0, 6, 0)));
        }
    }

    /**
     * Spawn primed tnt at hunted
     */
    public void effectSpawnTNT() {
        this.updateHunters();
        for (Player hunted : huntedPlayers) {
            Bukkit.getScheduler().runTask(twitchunt, () -> {
                Location loc = hunted.getLocation();
                loc.getWorld().spawnEntity(loc, EntityType.PRIMED_TNT);
            });
        }
    }

    /**
     * Wither lvl 2 effect for 20 seconds
     */
    public void effectWitherPoison() {
        this.updateHunters();
        PotionEffect effect = new PotionEffect(PotionEffectType.WITHER, 20 * 20, 1);

        for (Player hunted : huntedPlayers) {
            Bukkit.getScheduler().runTask(twitchunt, () -> hunted.addPotionEffect(effect));
        }
    }

    public  void effectZombieAttack() {
        this.updateHunters();
        for (Player hunted : huntedPlayers) {
            zombieAttack(hunted);
        }
    }

    private void zombieAttack(Player hunted) {
        this.updateHunters();
        new BukkitRunnable() {
            private int wait = 0;

            @Override
            public void run() {
                wait++;
                Location loc = hunted.getLocation();
                Bukkit.getScheduler().runTask(twitchunt, () -> loc.getWorld().spawnEntity(loc, EntityType.ZOMBIE));
                if (wait > 20) {
                    cancel();
                }

            }
        }.runTaskTimer(twitchunt, 20L, 20L);
    }

}
