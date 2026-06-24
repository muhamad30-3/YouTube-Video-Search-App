package com.example.assmohamedfaridyoutube;

public class VideoModel {

    private String title;
    private String description;
    private String channel;
    private String date;
    private String thumbnail;

    public VideoModel(String title,
                      String description,
                      String channel,
                      String date,
                      String thumbnail) {

        this.title = title;
        this.description = description;
        this.channel = channel;
        this.date = date;
        this.thumbnail = thumbnail;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getChannel() {
        return channel;
    }

    public String getDate() {
        return date;
    }

    public String getThumbnail() {
        return thumbnail;
    }
}