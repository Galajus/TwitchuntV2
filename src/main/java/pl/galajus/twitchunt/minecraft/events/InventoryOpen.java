package pl.galajus.twitchunt.minecraft.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import pl.galajus.twitchunt.minecraft.EventsController;
import pl.galajus.twitchunt.Twitchunt;

public class InventoryOpen implements Listener {

    private final Twitchunt twitchunt;
    private final EventsController eventsController;

    public InventoryOpen(Twitchunt twitchunt, EventsController eventsController) {
        this.twitchunt = twitchunt;
        this.eventsController = eventsController;
    }

    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent e) {

        if (eventsController.isBlockInventoryOpen()) {
            if (twitchunt.getConfigReader().getHuntedPlayers().contains((Player) e.getPlayer())) {
                e.setCancelled(true);
            }
        }
    }
}
