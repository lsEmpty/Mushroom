package Entities;

public class EmbedEntity {
    private String user_id;
    private String title;
    private String description;
    private String thumbnail;
    private String image;
    private String footer;
    private String footer_image;
    private String color;
    public EmbedEntity() {
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public String getImage() {
        return image;
    }

    public String getFooter() {
        return footer;
    }

    public String getFooter_image() {
        return footer_image;
    }

    public String getColor() {
        return color;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setFooter(String footer) {
        this.footer = footer;
    }

    public void setFooter_image(String footer_image) {
        this.footer_image = footer_image;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
