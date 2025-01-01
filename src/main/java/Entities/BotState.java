package Entities;

public class BotState {
    private String state;
    private String type;

    public BotState() {
    }

    public BotState(String state, String type) {
        this.state = state;
        this.type = type;
    }

    public String getState() {
        return state;
    }

    public String getType() {
        return type;
    }
}
