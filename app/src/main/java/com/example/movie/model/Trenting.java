package com.example.movie.model;

public class Trenting {
    private String poster;
    private String title;
    private long id;
    private String releasedate;
    private String overview;
    private Boolean adult;
    private String age;
    private double rate;
    private String lang;
    public Trenting() {
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public Trenting(String poster, String title, long id, String releasedate, String overview, Boolean adult, String age, double rate, String lang) {
        this.poster = poster;
        this.title = title;
        this.id = id;
        this.releasedate = releasedate;
        this.overview = overview;
        this.adult = adult;
        this.age=age;
        this.rate=rate;
        this.lang=lang;
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
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
