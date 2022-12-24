package com.cps3230assignment.models;

public class UploadAlertResponse {
    private String id;
    private int alertType;
    private String heading;
    private String description;
    private String url;
    private String imageURL;
    private String postedBy;
    private float priceInCents;
    private String postDate;


    // Getter Methods

    public String getId() {
        return id;
    }

    public int getAlertType() {
        return alertType;
    }

    public String getHeading() {
        return heading;
    }

    public String getDescription() {
        return description;
    }

    public String getUrl() {
        return url;
    }

    public String getImageURL() {
        return imageURL;
    }

    public String getPostedBy() {
        return postedBy;
    }

    public float getPriceInCents() {
        return priceInCents;
    }

    public String getPostDate() {
        return postDate;
    }

    // Setter Methods

    public void setId(String id) {
        this.id = id;
    }

    public void setAlertType(int alertType) {
        this.alertType = alertType;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public void setPostedBy(String postedBy) {
        this.postedBy = postedBy;
    }

    public void setPriceInCents(float priceInCents) {
        this.priceInCents = priceInCents;
    }

    public void setPostDate(String postDate) {
        this.postDate = postDate;
    }
}
