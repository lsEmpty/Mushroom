package Mushroom.Commands;

import Entities.SuggestionState;
import Mushroom.Service.Suggestion.SuggestionEmbeds;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.entities.emoji.EmojiUnion;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.util.Objects;

import static Mushroom.MushroomMain.customConfig;

public class Suggestion extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent event){
        //CONFIG
        if (!event.getChannel().getId().equals(customConfig.getSg_suggestion_channel_id())) return;
        if (event.getAuthor().isBot()) {
            Emoji emoji_check = Emoji.fromFormatted(customConfig.getSg_emoji_check());
            Emoji emoji_no_check = Emoji.fromFormatted(customConfig.getSg_emoji_no_check());
            event.getMessage().addReaction(emoji_check).queue();
            event.getMessage().addReaction(emoji_no_check).queue();
            return;
        }

        String[] message = event.getMessage().getContentRaw().split(" ");
        String message_complete = event.getMessage().getContentRaw();
        if (message[0].equalsIgnoreCase(customConfig.getSg_prefix()) && message.length > 1){
            EmbedBuilder embed = new EmbedBuilder();
            embed.setColor(Color.decode(customConfig.getSg_embed_color()));
            embed.setTitle(customConfig.getSg_title());
            embed.setDescription("-# <@"+event.getMember().getId()+"> "+customConfig.getSg_description());

            embed.setThumbnail(event.getMember().getEffectiveAvatarUrl());
            embed.addField(customConfig.getSg_head_field(), message_complete.substring(message[0].length() + 1) , false);

            embed.setFooter(customConfig.getSg_footer(), event.getGuild().getIconUrl());
            event.getChannel().sendMessageEmbeds(embed.build()).queue();
        }
        //CONFIG
        if (Objects.requireNonNull(event.getMember()).getRoles().contains(event.getGuild().getRoleById(customConfig.getSg_role_id_with_permissions_to_write_in_suggestion_channel()))
        &&  !message[0].equalsIgnoreCase(customConfig.getSg_prefix())){
            return;
        }
        String message_id = event.getMessageId();
        event.getChannel().deleteMessageById(message_id).queue();
    }

    @Override
    public void onMessageReactionAdd(MessageReactionAddEvent event) {
        boolean isChannel = false;
        for (String channel : customConfig.getSg_channels_to_change_state()){
            if (event.getChannel().getId().equals(channel)){
                isChannel = true;
                break;
            }
        }
        if (!isChannel) return;
        if (event.getUser().isBot()) return;
        if (!Objects.requireNonNull(event.getMember()).getRoles().contains(event.getGuild().getRoleById(customConfig.getSg_role_id_with_permissions_to_change_suggestion_state()))) return;
        EmojiUnion emoji_union = event.getEmoji();
        if (emoji_union.getType().equals(Emoji.Type.UNICODE)){
            Emoji emoji = emoji_union.asUnicode();
            event.retrieveMessage().queue(message -> {
                MessageEmbed message_embed = null;
                for (MessageEmbed embed : message.getEmbeds()){
                    message_embed = embed;
                    break;
                }
                if (message_embed == null) return;
                Guild guild = event.getGuild();
                Member member_who_changed_state = event.getMember();
                String channel_id = customConfig.getSg_channel_id_to_set_change_state();
                String title = "";
                String description = "";
                Color embed_color = Color.BLACK;
                String footer = "";
                SuggestionState suggestionState = null;
                boolean isEmoji = false;
                if (emoji.equals(Emoji.fromUnicode(customConfig.getSg_accepted().getEmoji()))){
                    suggestionState = customConfig.getSg_accepted();
                    isEmoji = true;
                } else if (emoji.equals(Emoji.fromUnicode(customConfig.getSg_denied().getEmoji()))) {
                    suggestionState = customConfig.getSg_denied();
                    isEmoji = true;
                } else if (emoji.equals(Emoji.fromUnicode(customConfig.getSg_implemented().getEmoji()))) {
                    suggestionState = customConfig.getSg_implemented();
                    isEmoji = true;
                }
                if (isEmoji){
                    title = suggestionState.getTitle();
                    description = "-# %user% " + suggestionState.getDescription();
                    embed_color = Color.decode(suggestionState.getColor());
                    footer = suggestionState.getFooter();
                    SuggestionEmbeds.suggestionChangeState(
                            guild,
                            message_embed,
                            member_who_changed_state,
                            channel_id,
                            title,
                            description,
                            footer,
                            embed_color);
                    event.getChannel().deleteMessageById(event.getMessageId()).queue();
                    event.getUser().openPrivateChannel().queue(privateChannel -> {
                        privateChannel.sendMessage("Has changed a suggestion successfully ✅").queue();
                    });
                }
            }, throwable -> {
                event.getUser().openPrivateChannel().queue(privateChannel -> {
                    privateChannel.sendMessage("Has occurred a error unexpected, could not change the status of the suggestion ⛔").queue();
                });
            });
        }
    }
}
