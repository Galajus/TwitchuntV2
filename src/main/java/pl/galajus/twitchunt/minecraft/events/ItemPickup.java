package pl.galajus.twitchunt.minecraft.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAttemptPickupItemEvent;
import pl.galajus.twitchunt.minecraft.EventsController;
import pl.galajus.twitchunt.Twitchunt;

public class ItemPickup implements Listener {

    private final Twitchunt twitchunt;
    private final EventsController eventsController;

    public ItemPickup(Twitchunt twitchunt, EventsController eventsController) {
        this.twitchunt = twitchunt;
        this.eventsController = eventsController;
    }

    @EventHandler
    public void onItemPickup(PlayerAttemptPickupItemEvent e) {

        if (eventsController.isBlockItemPickUp()) {
            if (twitchunt.getConfigReader().getHuntedPlayers().contains(e.getPlayer())) {
                e.setCancelled(true);
            }
        }
    }
}
