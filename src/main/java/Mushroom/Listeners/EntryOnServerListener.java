package Mushroom.Listeners;

import Mushroom.Service.JoinServer.WelcomeEmbed;
import net.dv8tion.jda.api.entities.Invite;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ExecutionException;

import static Mushroom.MushroomMain.customConfig;
import static Mushroom.MushroomMain.inviteTracker;

public class EntryOnServerListener extends ListenerAdapter {

    @Override
    public void onGuildMemberJoin(GuildMemberJoinEvent event) {
        String user_id = event.getMember().getId();
        event.getGuild().retrieveInvites().queue(invites -> {
            Invite invite;
            try {
                invite = inviteTracker.findUsedInvite(event.getGuild()).get();
                String content = getContent(event, user_id, invite);
                WelcomeEmbed.set(event.getGuild().getTextChannelById(customConfig.getWec_channel_id()),
                        customConfig.getWec_title(),
                        content,
                        customConfig.getWec_thumbnail(),
                        customConfig.getWec_image(),
                        event.getMember().getEffectiveAvatarUrl(),
                        customConfig.getWec_color());
                inviteTracker.updateInvites(event.getGuild());
                invite.getUses();
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private static @NotNull String getContent(GuildMemberJoinEvent event, String user_id, Invite invite) {
        String content = customConfig.getWec_content()
                .replace("%user%", "<@"+ user_id +">")
                .replace("%member_number%", String.valueOf(event.getGuild().getMemberCount()));
        if (invite != null && invite.getInviter() != null) {
            content = content
                    .replace("%invited_by_id%", "<@"+invite.getInviter().getId()+">")
                    .replace("%invited_by_name%", invite.getInviter().getName());
        }else{
            content = content.replace("%invited_by%", "unknown");
        }
        return content;
    }
}
