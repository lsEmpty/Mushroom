package Mushroom.Service.JoinServer;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

import java.awt.*;
import java.util.List;

public class WelcomeEmbed {
    public static void set(TextChannel channel, String title, String content, String url_thumbnail, String url_image, String url_user_thumbnail, String color){
        EmbedBuilder embed = new EmbedBuilder();
        if (title != null) embed.setTitle(title);
        embed.setDescription(content);
        if (url_thumbnail != null) {
            if (url_thumbnail.equals("%user_thumbnail%")) {
                embed.setThumbnail(url_user_thumbnail);
            }else{
                embed.setThumbnail(url_thumbnail);
            }
        }
        if (url_image != null) embed.setImage(url_image);
        if (color == null) {
            embed.setColor(Color.decode("#2b2d30"));
        }else{
            embed.setColor(Color.decode(color));
        }
        channel.sendMessageEmbeds(embed.build()).queue();
    }
}
