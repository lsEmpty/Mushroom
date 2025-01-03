package Mushroom.Service.JoinServer;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

import java.awt.*;

public class EmbedToManipulate {
    public static EmbedBuilder set(String title, String content, String url_thumbnail, String url_image, String url_user_thumbnail, String color){
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
        return embed;
    }

    public static EmbedBuilder setCustomEmbed(String title, String content, String thumbnail, String image, String footer, String footer_image, String color){
       EmbedBuilder embed = new EmbedBuilder();
       if (title != null) embed.setTitle(title);
       embed.setDescription(content);
       if (thumbnail != null) embed.setThumbnail(thumbnail);
       if (image != null) embed.setImage(image);
       if (footer != null && footer_image != null){
           embed.setFooter(footer, footer_image);
       }else if (footer != null){
           embed.setFooter(footer);
       }
       if (color != null){
           if (isHexColor(color)){
               embed.setColor(Color.decode(color));
           }else{
               embed.setColor(Color.decode("#2b2d30"));
           }
       }else{
           embed.setColor(Color.decode("#2b2d30"));
       }
       return embed;
    }

    private static boolean isHexColor(String input) {
        String hexPattern = "^#?([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$";
        return input != null && input.matches(hexPattern);
    }
}
