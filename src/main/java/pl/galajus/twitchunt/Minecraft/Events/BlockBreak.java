package pl.galajus.twitchunt.Minecraft.Events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import pl.galajus.twitchunt.Minecraft.EventsController;
import pl.galajus.twitchunt.Twitchunt;

public class BlockBreak implements Listener {

    private final Twitchunt twitchunt;
    private final EventsController eventsController;

    public BlockBreak(Twitchunt twitchunt, EventsController eventsController) {
        this.twitchunt = twitchunt;
        this.eventsController = eventsController;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {

        if (eventsController.isDamageOnBreak()) {
            if (twitchunt.getConfigReader().getHuntedPlayers().contains(e.getPlayer())) {
                e.getPlayer().damage(1.5);
            }
        }
    }
}
