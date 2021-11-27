package pl.galajus.twitchunt;

import org.bukkit.Bukkit;

import java.util.Calendar;

public class Clock {

    private final Twitchunt twitchunt;

    //Maybe usable in future
    //private static int dayOfMonth;
    //private static int hourOfDay;
   // private static int minuteOfHour;
    private static int secondOfMinute;

    public Clock(Twitchunt twitchunt) {
        this.twitchunt = twitchunt;

        Calendar rightNow = Calendar.getInstance();
        secondOfMinute = rightNow.get(Calendar.SECOND);

        //Maybe usable in future
        //minuteOfHour = rightNow.get(Calendar.MINUTE);
        //hourOfDay = rightNow.get(Calendar.HOUR_OF_DAY);
        //dayOfMonth = rightNow.get(Calendar.DAY_OF_MONTH);

        Bukkit.getScheduler().runTaskLater(twitchunt, this::timer, 50);
    }

    private void timer() {
        Bukkit.getScheduler().runTaskAsynchronously(twitchunt, () -> {
            Calendar rightNow = Calendar.getInstance();

            if (secondOfMinute != rightNow.get(Calendar.SECOND)) {
                secondOfMinute = rightNow.get(Calendar.SECOND);

                twitchunt.getPollCreator().tick();
            }


            Bukkit.getScheduler().runTaskLater(twitchunt, this::timer, 9);
        });
    }

}
