package Mushroom.Listeners;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import static Mushroom.MushroomMain.customConfig;

public class VerificationButtonListener extends ListenerAdapter {
    @Override
    public void onButtonInteraction(@NotNull ButtonInteractionEvent event) {
        if (Objects.equals(event.getComponent().getId(), "verification_button")){
            Member member_to_add_role = event.getMember();
            Role role_to_add = Objects.requireNonNull(event.getGuild()).getRoleById(customConfig.getVf_role_to_add());
            if (member_to_add_role != null && role_to_add != null){
                event.getGuild().addRoleToMember(member_to_add_role, role_to_add).queue();
            }
        }
    }
}
