package Mushroom.Threads;

import Entities.BotState;
import net.dv8tion.jda.api.entities.Activity;

import java.util.List;
import java.util.Objects;

import static Mushroom.MushroomMain.api;
import static Mushroom.MushroomMain.customConfig;

public class StateThread extends Thread {
    @Override
    public void run(){
        int aux_accumulator = 0;
        int users = 0;
        int states_size = customConfig.getBt_states().size();
        int elapsed_time = customConfig.getBt_elapsed_time() * 1000;
        List<BotState> states = customConfig.getBt_states();
        String server_id = customConfig.getServer_id();
        while (true){
            try {
                if (api.getGuildById(server_id) != null){
                    users = Objects.requireNonNull(api.getGuildById(server_id)).getMemberCount();
                }
                BotState state = states.get(aux_accumulator);
                String state_to_set = state.getState()
                        .replace("%users%", String.valueOf(users));
                switch (state.getType()){
                    case "%watching%":
                        api.getPresence().setActivity(Activity.watching(state_to_set));
                        break;
                    case "%competing%":
                        api.getPresence().setActivity(Activity.competing(state_to_set));
                        break;
                    case "%listening%":
                        api.getPresence().setActivity(Activity.listening(state_to_set));
                        break;
                    case "%playing%":
                        api.getPresence().setActivity(Activity.playing(state_to_set));
                        break;
                    default:
                        api.getPresence().setActivity(Activity.customStatus(state_to_set));
                        break;
                }
                if (aux_accumulator == (states_size - 1)){
                    aux_accumulator = 0;
                }else{
                    aux_accumulator++;
                }
                Thread.sleep(elapsed_time);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
