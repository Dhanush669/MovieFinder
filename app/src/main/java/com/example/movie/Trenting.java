package com.example.movie;

public class Trenting {
    private String poster;
    private String title;
    private String id;
    private String releasedate;
    private String overview;
    private Boolean adult;
    public Trenting() {
    }

    public Trenting(String poster, String title, String id, String releasedate, String overview, Boolean adult) {
        this.poster = poster;
        this.title = title;
        this.id = id;
        this.releasedate = releasedate;
        this.overview = overview;
        this.adult = adult;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getReleasedate() {
        return releasedate;
    }

    public void setReleasedate(String releasedate) {
        this.releasedate = releasedate;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public Boolean getAdult() {
        return adult;
    }

    public void setAdult(Boolean adult) {
        this.adult = adult;
    }
}
