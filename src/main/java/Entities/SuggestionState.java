package Entities;

public class SuggestionState {
    private String emoji;
    private String title;
    private String description;
    private String footer;
    private String color;

    public SuggestionState() {
    }

    public SuggestionState(String emoji, String title, String description, String footer, String color) {
        this.emoji = emoji;
        this.title = title;
        this.description = description;
        this.footer = footer;
        this.color = color;
    }

    public String getEmoji() {
        return emoji;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getFooter() {
        return footer;
    }

    public String getColor() {
        return color;
    }
}
