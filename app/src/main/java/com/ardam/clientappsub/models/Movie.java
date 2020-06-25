package com.ardam.clientappsub.models;

public class Movie {

    public static String COLLECTION_NAME = "movies";

    private String id;
    private String title;
    private String imageUrl;
    private String videoLink;
    private String genreId;
    private String genreName;
    private String name;
    public Movie() {
    }

    public Movie(String id, String title, String imageUrl, String videoLink, String genreId, String genreName, String name) {
        this.id = id;
        this.title = title;
        this.imageUrl = imageUrl;
        this.videoLink = videoLink;
        this.genreId = genreId;
        this.genreName = genreName;
        this.name = name;
    }
    private String createdAt;

    public static String getCollectionName() {
        return COLLECTION_NAME;
    }

    public static void setCollectionName(String collectionName) {
        COLLECTION_NAME = collectionName;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Movie(String id, String title, String imageUrl, String videoLink, String genreId, String genreName, String name, String createdAt) {
        this.id = id;
        this.title = title;
        this.imageUrl = imageUrl;
        this.videoLink = videoLink;
        this.genreId = genreId;
        this.genreName = genreName;
        this.name = name;
        this.createdAt = createdAt;
    }

    public Movie(String movieId, String title, String imageUrl, String videoLink, String id, String name) {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getVideoLink() {
        return videoLink;
    }

    public void setVideoLink(String videoLink) {
        this.videoLink = videoLink;
    }

    public String getGenreId() {
        return genreId;
    }

    public void setGenreId(String genreId) {
        this.genreId = genreId;
    }

    public String getGenreName() {
        return genreName;
    }

    public void setGenreName(String genreName) {
        this.genreName = genreName;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}