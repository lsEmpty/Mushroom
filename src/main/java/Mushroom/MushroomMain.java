package Mushroom;

import Config.Manager.ManagerCustomConfig;
import Mushroom.Commands.Suggestion;
import Mushroom.Threads.StateThread;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;

public class MushroomMain extends ListenerAdapter {
    public static JDA api;
    public static ManagerCustomConfig customConfig;
    public static void main(String[] args){
        customConfig = new ManagerCustomConfig();
        String token = customConfig.getToken();
        api = JDABuilder.createLight(token, GatewayIntent.GUILD_MESSAGES,
                GatewayIntent.MESSAGE_CONTENT, GatewayIntent.GUILD_MEMBERS,
                GatewayIntent.GUILD_MESSAGE_REACTIONS).build();
        new StateThread().start();
        api.getPresence().setStatus(OnlineStatus.IDLE);
        //Listeners
        api.addEventListener(new Suggestion());
    }
}
