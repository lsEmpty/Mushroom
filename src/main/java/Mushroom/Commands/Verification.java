package Mushroom.Commands;

import Mushroom.Service.JoinServer.EmbedToManipulate;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

import java.util.Objects;

import static Mushroom.MushroomMain.customConfig;

public class Verification extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) return;
        if (!Objects.requireNonNull(event.getMember()).getRoles().contains(event.getGuild().getRoleById(customConfig.getVf_role_id_to_use_verification_command()))) return;
        if (event.getMessage().getContentRaw().equalsIgnoreCase(customConfig.getVf_prefix())){
            Button button = Button.success("verification_button", customConfig.getVf_text_button());
            event.getMessage().delete().queue();
            EmbedBuilder embed = EmbedToManipulate.set(
                    customConfig.getVf_title(),
                    customConfig.getVf_content(),
                    customConfig.getVf_thumbnail(),
                    customConfig.getVf_image(),
                    event.getGuild().getIconUrl(),
                    customConfig.getVf_color()
            );
            event.getChannel().sendMessageEmbeds(embed.build()).setActionRow(button).queue();
        }
    }
}
