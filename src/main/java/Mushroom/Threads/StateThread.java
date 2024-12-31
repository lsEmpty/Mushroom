package Mushroom.Threads;

import net.dv8tion.jda.api.entities.Activity;

import static Mushroom.MushroomMain.api;

public class StateThread extends Thread {
    @Override
    public void run(){
        while (true){
            try {
                api.getPresence().setActivity(Activity.watching(" a lsempty"));
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
