package Mushroom.Commands;

import Entities.EmbedEntity;
import Entities.Enums.EmbedState;
import Mushroom.Service.JoinServer.EmbedToManipulate;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static Mushroom.MushroomMain.*;

public class Embed extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (Objects.requireNonNull(event.getMember()).getUser().isBot()) return;
        if (!event.getMessage().getContentRaw().equals("!embed")) return;
        if (!Objects.requireNonNull(event.getMember()).getRoles().contains(event.getGuild().getRoleById(customConfig.getEcb_role_id_permissions()))){
            event.getMessage().delete().queue();
            return;
        }
        String message = event.getMessage().getContentRaw();
        if (setCancel(event, message)) return;
        if (embedState == EmbedState.INACTIVE){
            embedState = EmbedState.TITLE;
            embedEntity = new EmbedEntity();
            embedEntity.setUser_id(event.getMember().getId());
            embedEntity.setChannel_id(event.getChannel().getId());
            setScheduledExecutor(event.getChannel().asTextChannel());
            event.getChannel().sendMessage("!embed was activated, follow the steps to complete creation ✅").queue();
            event.getChannel().sendMessage("Write !no if you don't need a title.\nWrite the title if you need it.").queue();
            return;
        }else if (message.equals("!embed")){
            event.getChannel().sendMessage("!embed has been active, you can't use this command on this moment ⛔").queue();
            return;
        }
        if (embedState == EmbedState.INACTIVE) return;
        if (!embedEntity.getUser_id().equals(event.getMember().getId())) return;
        if (!embedEntity.getChannel_id().equals(event.getChannel().getId())) return;

        getEmbedInformation(event, message);
    }

    private void setScheduledExecutor(TextChannel channel){
        scheduledExecutor = Executors.newScheduledThreadPool(1);
        Runnable task = () -> {
            embedEntity = null;
            embedState = EmbedState.INACTIVE;
            channel.sendMessage("Timed out to create an embed ⛔").queue();
        };
        scheduledExecutor.schedule(task, 15, TimeUnit.MINUTES);
    }

    private static boolean setCancel(MessageReceivedEvent event, String message) {
        if (message.equals("!cancel") && embedState != EmbedState.INACTIVE){
            scheduledExecutor.shutdownNow();
            scheduledExecutor = null;
            embedState = EmbedState.INACTIVE;
            event.getChannel().sendMessage("Embed event canceled ⛔").queue();
            return true;
        }else if (message.equals("!cancel")){
            event.getChannel().sendMessage("There isn't an embed event active ⛔").queue();
            return true;
        }
        return false;
    }

    private static void getEmbedInformation(MessageReceivedEvent event, String message) {
        // Verify state
        if (embedState == EmbedState.TITLE){
            if (message.equals("!no")){
                embedEntity.setTitle(null);
            }else{
                embedEntity.setTitle(message);
                event.getChannel().sendMessage("Title set correctly ✅").queue();
            }
            event.getChannel().sendMessage("Write the embed description.").queue();
            embedState = EmbedState.DESCRIPTION;
        } else if (embedState == EmbedState.DESCRIPTION){
            embedEntity.setDescription(message);
            event.getChannel().sendMessage("Description set correctly ✅").queue();
            event.getChannel().sendMessage("Write !no if you don't need the thumbnail.\nWrite the url if you need it.").queue();
            embedState = EmbedState.THUMBNAIL;
        } else if (embedState == EmbedState.THUMBNAIL) {
            if (message.equals("!no")){
                embedEntity.setThumbnail(null);
            }else{
                embedEntity.setThumbnail(message);
                event.getChannel().sendMessage("Thumbnail set correctly ✅").queue();
            }
            event.getChannel().sendMessage("Write !no if you don't need an image.\nWrite the url if you need it.").queue();
            embedState = EmbedState.IMAGE;
        } else if (embedState == EmbedState.IMAGE){
            if (message.equals("!no")){
                embedEntity.setImage(null);
            }else{
                embedEntity.setImage(message);
                event.getChannel().sendMessage("Image set correctly ✅").queue();
            }
            event.getChannel().sendMessage("Write !no if you don't need the footer.\nWrite the footer if you need it.").queue();
            embedState = EmbedState.FOOTER;
        } else if (embedState == EmbedState.FOOTER){
            if (message.equals("!no")){
                embedEntity.setFooter(null);
                embedEntity.setFooter_image(null);
                event.getChannel().sendMessage("Write !no if you don't need the color embed.\nWrite the HexColor if you need it.").queue();
                embedState = EmbedState.COLOR;
            }else{
                embedEntity.setFooter(message);
                event.getChannel().sendMessage("Footer set correctly ✅").queue();
                event.getChannel().sendMessage("Write !no if you don't need the footer image.\n" +
                        "Write !server if you need the server image.\n Write the url if you need it.").queue();
                embedState = EmbedState.FOOTER_IMAGE;
            }
        } else if (embedState == EmbedState.FOOTER_IMAGE){
            if (message.equals("!no")) {
                embedEntity.setFooter_image(null);
            }else if(message.equals("!server")){
                embedEntity.setFooter_image(event.getGuild().getIconUrl());
            }else{
                embedEntity.setFooter_image(message);
                event.getChannel().sendMessage("Footer image set correctly ✅").queue();
            }
            event.getChannel().sendMessage("Write !no if you don't need the color embed. Write the HexColor if you need it.").queue();
            embedState = EmbedState.COLOR;
        } else if (embedState == EmbedState.COLOR){
            if (message.equals("!no")){
                embedEntity.setColor(null);
            }else{
                embedEntity.setColor(message);
                event.getChannel().sendMessage("Color set correctly ✅").queue();
            }
            event.getChannel().sendMessage("Write the channel id to send embed.").queue();
            embedState = EmbedState.CHANNEL_TO_SEND;
        } else if (embedState == EmbedState.CHANNEL_TO_SEND){
            TextChannel channel = event.getGuild().getTextChannelById(message);
            if (channel != null){
                scheduledExecutor.shutdownNow();
                scheduledExecutor = null;
                embedState = EmbedState.INACTIVE;
                event.getChannel().sendMessage("Embed sent ✅").queue();
                channel.sendMessageEmbeds(
                        EmbedToManipulate.setCustomEmbed(
                                embedEntity.getTitle(),
                                embedEntity.getDescription(),
                                embedEntity.getThumbnail(),
                                embedEntity.getImage(),
                                embedEntity.getFooter(),
                                embedEntity.getFooter_image(),
                                embedEntity.getColor()).build()).queue();
            }else{
                setCancel(event, "!cancel");
            }
        }
    }
}
