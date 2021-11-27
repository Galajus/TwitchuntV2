package pl.galajus.twitchunt.Minecraft.Events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;
import pl.galajus.twitchunt.Minecraft.EventsController;
import pl.galajus.twitchunt.Twitchunt;

public class ItemPickupSpigot implements Listener {

    private final Twitchunt twitchunt;
    private final EventsController eventsController;

    public ItemPickupSpigot(Twitchunt twitchunt, EventsController eventsController) {
        this.twitchunt = twitchunt;
        this.eventsController = eventsController;
    }

    @EventHandler
    public void onItemPickup(PlayerPickupItemEvent e) {

        if (eventsController.isBlockItemPickUp()) {
            if (twitchunt.getConfigReader().getHuntedPlayers().contains(e.getPlayer())) {
                e.setCancelled(true);
            }
        }
    }
}
