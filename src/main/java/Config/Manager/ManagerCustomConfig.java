package Config.Manager;

import Config.CustomConfig;
import Entities.BotState;
import Entities.SuggestionState;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ManagerCustomConfig {
    private final CustomConfig customConfig;
    private String token;
    private String server_id;

    // Verification
    private String vf_title;
    private String vf_content;
    private String vf_thumbnail;
    private String vf_image;
    private String vf_color;
    private String vf_text_button;
    private String vf_role_to_add;
    private String vf_verify_message;
    private String vf_prefix;
    private String vf_role_id_to_use_verification_command;

    // Welcome Embed Content
    private String wec_channel_id;
    private String wec_title;
    private String wec_content;
    private String wec_thumbnail;
    private String wec_image;
    private String wec_color;

    // Bot State
    private List<BotState> bt_states;
    private int bt_elapsed_time;

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
    private SuggestionState sg_accepted;
    private SuggestionState sg_denied;
    private SuggestionState sg_implemented;


    public ManagerCustomConfig() {
        customConfig = new CustomConfig("config.yml", "resources");
        getAllConfiguration();
    }

    private void getAllConfiguration(){
        token = (String) customConfig.get("token");
        server_id = (String) customConfig.get("server_id");
        getSuggestionConfig();
        getBotStates();
        getWelcomeEmbedContent();
        getVerificationContent();
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
        sg_accepted = new SuggestionState(emoji, title, description, footer, color);
        emoji = (String) customConfig.get("config.suggestion.change_state.states.denied.emoji");
        title = (String) customConfig.get("config.suggestion.change_state.states.denied.title");;
        description = (String) customConfig.get("config.suggestion.change_state.states.denied.description");;
        footer = (String) customConfig.get("config.suggestion.change_state.states.denied.footer");;
        color = (String) customConfig.get("config.suggestion.change_state.states.denied.color");;
        sg_denied = new SuggestionState(emoji, title, description, footer, color);
        emoji = (String) customConfig.get("config.suggestion.change_state.states.implemented.emoji");
        title = (String) customConfig.get("config.suggestion.change_state.states.implemented.title");;
        description = (String) customConfig.get("config.suggestion.change_state.states.implemented.description");;
        footer = (String) customConfig.get("config.suggestion.change_state.states.implemented.footer");;
        color = (String) customConfig.get("config.suggestion.change_state.states.implemented.color");;
        sg_implemented = new SuggestionState(emoji, title, description, footer, color);
    }

    private void getBotStates(){
        bt_states = new ArrayList<>();
        if (customConfig.get("config.status.states") instanceof List<?> list_states){
            for (Object object_state : list_states){
                if (object_state instanceof Map<?,?> map_state){
                    String bot_state = (String) map_state.get("state");
                    String type = (String) map_state.get("type");
                    bt_states.add(new BotState(bot_state, type));
                }
            }
        }
        if (customConfig.get("config.status.elapsed_time") instanceof Integer elapsed_time) bt_elapsed_time = elapsed_time;
    }

    private void getWelcomeEmbedContent(){
        wec_content = "";
        wec_channel_id = (String) customConfig.get("config.welcome.channel_id");
        wec_title = (String) customConfig.get("config.welcome.title");
        wec_thumbnail = (String) customConfig.get("config.welcome.thumbnail");
        wec_image = (String) customConfig.get("config.welcome.image");
        wec_color = (String) customConfig.get("config.welcome.color");
        Object content_object = customConfig.get("config.welcome.content");
        wec_content = joinStrings(content_object);
    }

    private void getVerificationContent(){
        vf_title = (String) customConfig.get("config.verification.title");
        vf_color = (String) customConfig.get("config.verification.color");
        vf_thumbnail = (String) customConfig.get("config.verification.thumbnail");
        vf_image = (String) customConfig.get("config.verification.image");
        vf_text_button = (String) customConfig.get("config.verification.text_button");
        vf_role_to_add = (String) customConfig.get("config.verification.role_id_to_add");
        vf_verify_message = (String) customConfig.get("config.verification.verify_message");
        vf_prefix = (String) customConfig.get("config.verification.prefix");
        vf_role_id_to_use_verification_command = (String) customConfig.get("config.verification.role_id_to_use_verification_command");
        Object content_object = customConfig.get("config.verification.content");
        vf_content = joinStrings(content_object);

    }

    private String joinStrings(Object content_object){
        String object_to_return = "";
        if (content_object instanceof List<?> content_list){
            boolean aux = false;
            for (Object content : content_list){
                if (content instanceof String content_to_set){
                    if (!aux){
                        object_to_return = object_to_return.concat(content_to_set);
                    }else{
                        object_to_return = object_to_return.concat("\n"+content_to_set);
                    }
                    aux = true;
                }
            }
        }
        return object_to_return;
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

    public SuggestionState getSg_accepted() {
        return sg_accepted;
    }

    public SuggestionState getSg_denied() {
        return sg_denied;
    }

    public SuggestionState getSg_implemented() {
        return sg_implemented;
    }

    public List<BotState> getBt_states() {
        return bt_states;
    }

    public int getBt_elapsed_time() {
        return bt_elapsed_time;
    }

    public String getServer_id() {
        return server_id;
    }

    public String getWec_channel_id() {
        return wec_channel_id;
    }

    public String getWec_title() {
        return wec_title;
    }

    public String getWec_content() {
        return wec_content;
    }

    public String getWec_thumbnail() {
        return wec_thumbnail;
    }

    public String getWec_image() {
        return wec_image;
    }

    public String getWec_color() {
        return wec_color;
    }

    public String getVf_title() {
        return vf_title;
    }

    public String getVf_content() {
        return vf_content;
    }

    public String getVf_thumbnail() {
        return vf_thumbnail;
    }

    public String getVf_image() {
        return vf_image;
    }

    public String getVf_color() {
        return vf_color;
    }

    public String getVf_text_button() {
        return vf_text_button;
    }

    public String getVf_role_to_add() {
        return vf_role_to_add;
    }

    public String getVf_verify_message() {
        return vf_verify_message;
    }

    public String getVf_prefix() {
        return vf_prefix;
    }

    public String getVf_role_id_to_use_verification_command() {
        return vf_role_id_to_use_verification_command;
    }
}
