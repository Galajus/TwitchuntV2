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

    private int blockItemPickUpTimeLeft = 0;
    private int blockInventoryOpenTimeLeft = 0;
    private int blockInventoryClickTimeLeft = 0;
    private int doubleEntityDeathDropTimeLeft = 0;
    private int damageOnBreakTimeLeft = 0;
    private int entityDamageEntityBLockTimeLeft = 0;


    public EventsController(Twitchunt twitchunt) {
        this.twitchunt = twitchunt;
    }

    public Twitchunt getTwitchunt() {
        return twitchunt;
    }

    public void tick() {
        if (blockItemPickUpTimeLeft > 0) {
            blockItemPickUpTimeLeft--;
            if (blockItemPickUpTimeLeft == 0) {
                blockItemPickUp = false;
            }
        }
        if (blockInventoryOpenTimeLeft > 0) {
            blockInventoryOpenTimeLeft--;
            if (blockInventoryOpenTimeLeft == 0) {
                blockInventoryOpen = false;
            }
        }
        if (blockInventoryClickTimeLeft > 0) {
            blockInventoryClickTimeLeft--;
            if (blockInventoryClickTimeLeft == 0) {
                blockInventoryClick = false;
            }
        }
        if (doubleEntityDeathDropTimeLeft > 0) {
            doubleEntityDeathDropTimeLeft--;
            if (doubleEntityDeathDropTimeLeft == 0) {
                doubleEntityDeathDrop = false;
            }
        }
        if (damageOnBreakTimeLeft > 0) {
            damageOnBreakTimeLeft--;
            if (damageOnBreakTimeLeft == 0) {
                damageOnBreak = false;
            }
        }
        if (entityDamageEntityBLockTimeLeft > 0) {
            entityDamageEntityBLockTimeLeft--;
            if (entityDamageEntityBLockTimeLeft == 0) {
                entityDamageEntityBLock = false;
            }
        }
    }

    public boolean isEntityDamageEntityBLock() {
        return entityDamageEntityBLock;
    }

    public void setEntityDamageEntityBLock(boolean entityDamageEntityBLock) {
        this.entityDamageEntityBLock = entityDamageEntityBLock;
        if (entityDamageEntityBLock) {
            entityDamageEntityBLockTimeLeft = 30;
        } else {
            entityDamageEntityBLockTimeLeft = 0;
        }
    }

    public boolean isDamageOnBreak() {
        return damageOnBreak;
    }

    public void setDamageOnBreak(boolean damageOnBreak) {
        this.damageOnBreak = damageOnBreak;
        if (damageOnBreak) {
            damageOnBreakTimeLeft = 30;
        } else {
            damageOnBreakTimeLeft = 0;
        }
    }

    public boolean isDoubleEntityDeathDrop() {
        return doubleEntityDeathDrop;
    }

    public void setDoubleEntityDeathDrop(boolean doubleEntityDeathDrop) {
        this.doubleEntityDeathDrop = doubleEntityDeathDrop;
        if (doubleEntityDeathDrop) {
            doubleEntityDeathDropTimeLeft = 45;
        } else {
            doubleEntityDeathDropTimeLeft = 0;
        }
    }

    public boolean isBlockInventoryClick() {
        return blockInventoryClick;
    }

    public void setBlockInventoryClick(boolean blockInventoryClick) {
        this.blockInventoryClick = blockInventoryClick;
        if (blockInventoryClick) {
            blockInventoryClickTimeLeft = 60;
        } else {
            blockInventoryClickTimeLeft = 0;
        }
    }

    public boolean isBlockInventoryOpen() {
        return blockInventoryOpen;
    }

    public void setBlockInventoryOpen(boolean blockInventoryOpen) {
        this.blockInventoryOpen = blockInventoryOpen;
        if (blockInventoryOpen) {
            blockInventoryOpenTimeLeft = 60;
        } else {
            blockInventoryOpenTimeLeft = 0;
        }
    }

    public boolean isBlockItemPickUp() {
        return blockItemPickUp;
    }

    public void setBlockItemPickUp(boolean blockItemPickUp) {
        this.blockItemPickUp = blockItemPickUp;
        if (blockItemPickUp) {
            blockItemPickUpTimeLeft = 45;
        } else {
            blockItemPickUpTimeLeft = 0;
        }
    }
}
