package Mushroom;

import Config.Manager.ManagerCustomConfig;
import Entities.EmbedEntity;
import Entities.Enums.EmbedState;
import Mushroom.Commands.Embed;
import Mushroom.Commands.Suggestion;
import Mushroom.Commands.Verification;
import Mushroom.Listeners.EntryOnServerListener;
import Mushroom.Listeners.GuildReady;
import Mushroom.Listeners.VerificationButtonListener;
import Mushroom.Service.JoinServer.InviteTracker;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;

import java.util.concurrent.ScheduledExecutorService;

public class MushroomMain extends ListenerAdapter {
    // JPA API
    public static JDA api;
    // Configuration
    public static ManagerCustomConfig customConfig;
    // Static variables
    public static final InviteTracker inviteTracker = new InviteTracker();
    public static EmbedState embedState = EmbedState.INACTIVE;
    public static ScheduledExecutorService scheduledExecutor = null;
    public static EmbedEntity embedEntity = null;

    public static void main(String[] args) throws InterruptedException {
        customConfig = new ManagerCustomConfig();
        String token = customConfig.getToken();
        api = JDABuilder.createLight(token, GatewayIntent.GUILD_MESSAGES,
                GatewayIntent.MESSAGE_CONTENT, GatewayIntent.GUILD_MEMBERS,
                GatewayIntent.GUILD_MESSAGE_REACTIONS, GatewayIntent.GUILD_INVITES,
                GatewayIntent.DIRECT_MESSAGES
        ).build();
        api.getPresence().setStatus(OnlineStatus.IDLE);
        //Listeners
        api.addEventListener(new Suggestion());
        api.addEventListener(new EntryOnServerListener());
        api.addEventListener(new Verification());
        api.addEventListener(new Embed());
        api.addEventListener(new VerificationButtonListener());
        api.addEventListener(new GuildReady());
    }
}
