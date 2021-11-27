package pl.galajus.twitchunt.Minecraft;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import pl.galajus.twitchunt.Twitchunt;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class PaperEffectCaster {

    private final Twitchunt twitchunt;
    private final EventsController eventsController;

    private List<Player> huntedPlayers;

    public PaperEffectCaster(Twitchunt twitchunt, EventsController eventsController) {
        this.twitchunt = twitchunt;
        this.eventsController = eventsController;
        this.updateHunters();
    }

    public void updateHunters() {
        huntedPlayers = twitchunt.getConfigReader().getHuntedPlayers();
    }



    /**
     * Teleport hunted player to random location in radius +/- 4000 from x: 0 z: 0 coords
     */
    public void effectTeleportRandom() {

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

                Bukkit.getScheduler().runTask(twitchunt, () -> hunted.teleportAsync(tpLoc));
            }
        });
    }

    /**
     * Teleport hunted player 3 blocks up
     */
    public void effectTeleportUp() {
        for (Player hunted : huntedPlayers) {
            Bukkit.getScheduler().runTask(twitchunt, () -> hunted.teleportAsync(hunted.getLocation().add(0, 3, 0)));
        }
    }

    /**
     * Teleport hunted player 6 blocks up
     */
    public void effectTeleportUpv2() {
        for (Player hunted : huntedPlayers) {
            Bukkit.getScheduler().runTask(twitchunt, () -> hunted.teleportAsync(hunted.getLocation().add(0, 6, 0)));
        }
    }
}
