package Mushroom.Service.Suggestion;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

import java.awt.*;

public class SuggestionEmbeds {
    public static void suggestionChangeState(Guild guild, MessageEmbed message, Member member_who_changed_state, String channel_id, String title, String description, String footer, Color embed_color) {
        EmbedBuilder embed = new EmbedBuilder();
        String[] description_message_split = message.getDescription().split(" ");
        embed.setTitle(title);
        embed.setDescription(description.replace("%user%", description_message_split[1]));
        embed.setColor(embed_color);

        String thumbnail = message.getThumbnail().getUrl() != null ? message.getThumbnail().getUrl() : "https://cdn3.iconfinder.com/data/icons/meteocons/512/n-a-512.png";
        embed.setThumbnail(thumbnail);
        for (MessageEmbed.Field field : message.getFields()) {
            embed.addField(field);
        }
        embed.setFooter(footer + " " + member_who_changed_state.getUser().getName(),
                member_who_changed_state.getEffectiveAvatarUrl());
        TextChannel channel = guild.getTextChannelById(channel_id);
        if (channel != null) channel.sendMessageEmbeds(embed.build()).queue();
    }
}
