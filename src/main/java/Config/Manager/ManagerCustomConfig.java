package Config.Manager;

import Config.CustomConfig;
import Entities.State;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ManagerCustomConfig {
    private final CustomConfig customConfig;
    private String token;

    // Suggestions Config
    private String sg_suggestion_channel_id;
    private String sg_role_id_with_permissions_to_write_in_suggestion_channel;
    private String sg_emoji_check;
    private String sg_emoji_no_check;
    private String sg_prefix;
    private String sg_embed_color;
    private String sg_title;
    private String sg_description;
    private String sg_head_field;
    private String sg_footer;
    private String sg_role_id_with_permissions_to_change_suggestion_state;
    private String sg_channel_id_to_set_change_state;
    private List<String> sg_channels_to_change_state;
    private State sg_accepted;
    private State sg_denied;
    private State sg_implemented;


    public ManagerCustomConfig() {
        customConfig = new CustomConfig("config.yml", "resources");
        getAllConfiguration();
    }

    private void getAllConfiguration(){
        token = (String) customConfig.get("token");
        getSuggestionConfig();
    }

    private void getSuggestionConfig(){
        sg_channels_to_change_state = new ArrayList<>();
        sg_suggestion_channel_id = (String) customConfig.get("config.suggestion.add.suggestion_channel_id");
        sg_role_id_with_permissions_to_write_in_suggestion_channel = (String) customConfig.get("config.suggestion.add.role_id_with_permissions_to_write_in_suggestion_channel");
        sg_emoji_check = (String) customConfig.get("config.suggestion.add.emoji_check");
        sg_emoji_no_check = (String) customConfig.get("config.suggestion.add.emoji_no_check");
        sg_prefix = (String) customConfig.get("config.suggestion.add.prefix");
        sg_embed_color = (String) customConfig.get("config.suggestion.add.embed_color");
        sg_title = (String) customConfig.get("config.suggestion.add.title");
        sg_description = (String) customConfig.get("config.suggestion.add.description");
        sg_head_field = (String) customConfig.get("config.suggestion.add.head_field");
        sg_footer = (String) customConfig.get("config.suggestion.add.footer");
        sg_role_id_with_permissions_to_change_suggestion_state = (String) customConfig.get("config.suggestion.change_state.role_id_with_permissions_to_change_suggestion_state");
        sg_channel_id_to_set_change_state = (String) customConfig.get("config.suggestion.change_state.channel_id_to_set_change_state");
        Object channels_object = customConfig.get("config.suggestion.change_state.channels");
        if (channels_object instanceof List<?> channels){
            for (Object channel : channels){
                if (channel instanceof String){
                    sg_channels_to_change_state.add((String) channel);
                }
            }
        }
        String emoji = (String) customConfig.get("config.suggestion.change_state.states.accepted.emoji");
        String title = (String) customConfig.get("config.suggestion.change_state.states.accepted.title");;
        String description = (String) customConfig.get("config.suggestion.change_state.states.accepted.description");;
        String footer = (String) customConfig.get("config.suggestion.change_state.states.accepted.footer");;
        String color = (String) customConfig.get("config.suggestion.change_state.states.accepted.color");;
        sg_accepted = new State(emoji, title, description, footer, color);
        emoji = (String) customConfig.get("config.suggestion.change_state.states.denied.emoji");
        title = (String) customConfig.get("config.suggestion.change_state.states.denied.title");;
        description = (String) customConfig.get("config.suggestion.change_state.states.denied.description");;
        footer = (String) customConfig.get("config.suggestion.change_state.states.denied.footer");;
        color = (String) customConfig.get("config.suggestion.change_state.states.denied.color");;
        sg_denied = new State(emoji, title, description, footer, color);
        emoji = (String) customConfig.get("config.suggestion.change_state.states.implemented.emoji");
        title = (String) customConfig.get("config.suggestion.change_state.states.implemented.title");;
        description = (String) customConfig.get("config.suggestion.change_state.states.implemented.description");;
        footer = (String) customConfig.get("config.suggestion.change_state.states.implemented.footer");;
        color = (String) customConfig.get("config.suggestion.change_state.states.implemented.color");;
        sg_implemented = new State(emoji, title, description, footer, color);
    }

    public String getToken() {
        return token;
    }

    public String getSg_suggestion_channel_id() {
        return sg_suggestion_channel_id;
    }

    public String getSg_role_id_with_permissions_to_write_in_suggestion_channel() {
        return sg_role_id_with_permissions_to_write_in_suggestion_channel;
    }

    public String getSg_emoji_check() {
        return sg_emoji_check;
    }

    public String getSg_emoji_no_check() {
        return sg_emoji_no_check;
    }

    public String getSg_prefix() {
        return sg_prefix;
    }

    public String getSg_embed_color() {
        return sg_embed_color;
    }

    public String getSg_title() {
        return sg_title;
    }

    public String getSg_description() {
        return sg_description;
    }

    public String getSg_head_field() {
        return sg_head_field;
    }

    public String getSg_footer() {
        return sg_footer;
    }

    public String getSg_role_id_with_permissions_to_change_suggestion_state() {
        return sg_role_id_with_permissions_to_change_suggestion_state;
    }

    public String getSg_channel_id_to_set_change_state() {
        return sg_channel_id_to_set_change_state;
    }

    public List<String> getSg_channels_to_change_state() {
        return sg_channels_to_change_state;
    }

    public State getSg_accepted() {
        return sg_accepted;
    }

    public State getSg_denied() {
        return sg_denied;
    }

    public State getSg_implemented() {
        return sg_implemented;
    }
}
