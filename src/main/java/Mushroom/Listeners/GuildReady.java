package Mushroom.Listeners;

import Mushroom.MushroomMain;
import Mushroom.Threads.StateThread;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.Objects;

import static Mushroom.MushroomMain.api;
import static Mushroom.MushroomMain.customConfig;

public class GuildReady extends ListenerAdapter {

    @Override
    public void onReady(ReadyEvent event) {
        // load invites
        MushroomMain.inviteTracker.updateInvites(Objects.requireNonNull(api.getGuildById(customConfig.getServer_id())));
        // start state thread
        new StateThread().start();
    }
}
