package pl.galajus.twitchunt.minecraft.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import pl.galajus.twitchunt.minecraft.EventsController;
import pl.galajus.twitchunt.Twitchunt;

public class EntityDeath implements Listener {

    private final Twitchunt twitchunt;
    private final EventsController eventsController;

    public EntityDeath(Twitchunt twitchunt, EventsController eventsController) {
        this.twitchunt = twitchunt;
        this.eventsController = eventsController;
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent e) {

        if (eventsController.isDoubleEntityDeathDrop()) {
            if (!(e.getEntity() instanceof Player) && twitchunt.getConfigReader().getHuntedPlayers().contains(e.getEntity().getKiller())) {
                for (ItemStack itemStack : e.getDrops()) {
                    e.getEntity().getLocation().getWorld().dropItemNaturally(e.getEntity().getLocation(), itemStack);
                }
            }
        }
    }
}
