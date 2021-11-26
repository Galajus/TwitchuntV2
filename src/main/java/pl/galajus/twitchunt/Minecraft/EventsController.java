package pl.galajus.twitchunt.Minecraft;

import pl.galajus.twitchunt.Twitchunt;

public class EventsController {

    private final Twitchunt twitchunt;

    private boolean entityDamageEntityBLock = false;
    private boolean damageOnBreak = false;
    private boolean doubleEntityDeathDrop = false;
    private boolean blockInventoryClick = false;
    private boolean blockInventoryOpen = false;
    private boolean blockItemPickUp = false;


    public EventsController(Twitchunt twitchunt) {
        this.twitchunt = twitchunt;
    }

    public Twitchunt getTwitchunt() {
        return twitchunt;
    }

    public boolean isEntityDamageEntityBLock() {
        return entityDamageEntityBLock;
    }

    public void setEntityDamageEntityBLock(boolean entityDamageEntityBLock) {
        this.entityDamageEntityBLock = entityDamageEntityBLock;
    }

    public boolean isDamageOnBreak() {
        return damageOnBreak;
    }

    public void setDamageOnBreak(boolean damageOnBreak) {
        this.damageOnBreak = damageOnBreak;
    }

    public boolean isDoubleEntityDeathDrop() {
        return doubleEntityDeathDrop;
    }

    public void setDoubleEntityDeathDrop(boolean doubleEntityDeathDrop) {
        this.doubleEntityDeathDrop = doubleEntityDeathDrop;
    }

    public boolean isBlockInventoryClick() {
        return blockInventoryClick;
    }

    public void setBlockInventoryClick(boolean blockInventoryClick) {
        this.blockInventoryClick = blockInventoryClick;
    }

    public boolean isBlockInventoryOpen() {
        return blockInventoryOpen;
    }

    public void setBlockInventoryOpen(boolean blockInventoryOpen) {
        this.blockInventoryOpen = blockInventoryOpen;
    }

    public boolean isBlockItemPickUp() {
        return blockItemPickUp;
    }

    public void setBlockItemPickUp(boolean blockItemPickUp) {
        this.blockItemPickUp = blockItemPickUp;
    }
}
