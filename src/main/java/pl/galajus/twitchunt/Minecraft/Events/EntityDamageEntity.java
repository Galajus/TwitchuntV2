package pl.galajus.twitchunt.Minecraft.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import pl.galajus.twitchunt.Minecraft.EventsController;
import pl.galajus.twitchunt.Twitchunt;

public class EntityDamageEntity implements Listener {

    private final Twitchunt twitchunt;
    private final EventsController eventsController;

    public EntityDamageEntity(Twitchunt twitchunt, EventsController eventsController) {
        this.twitchunt = twitchunt;
        this.eventsController = eventsController;
    }

    @EventHandler
    public void onEntityDamageEntity(EntityDamageByEntityEvent e) {

        if (eventsController.isEntityDamageEntityBLock()) {
            if (e.getDamager() instanceof Player && twitchunt.getConfigReader().getHuntedPlayers().contains((Player) e.getDamager())) {
                e.setCancelled(true);
            }
        }
    }
}
