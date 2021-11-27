package pl.galajus.twitchunt.Minecraft.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import pl.galajus.twitchunt.Minecraft.EventsController;
import pl.galajus.twitchunt.Twitchunt;

public class InventoryClick implements Listener {

    private final Twitchunt twitchunt;
    private final EventsController eventsController;

    public InventoryClick(Twitchunt twitchunt, EventsController eventsController) {
        this.twitchunt = twitchunt;
        this.eventsController = eventsController;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {

        if (eventsController.isBlockInventoryClick()) {
            if (twitchunt.getConfigReader().getHuntedPlayers().contains((Player) e.getWhoClicked())) {
                e.setCancelled(true);
            }
        }
    }
}
