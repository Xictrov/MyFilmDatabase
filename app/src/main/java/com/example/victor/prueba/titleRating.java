package com.example.victor.prueba;

/**
 * Created by Victor on 05/01/2017.
 */

public class titleRating {
    private Integer itemId;
    private String filmTitle;
    private Float rate;
    private long filmId;

    //Constructor

    public titleRating(Integer itemId, String filmTitle, Float rate, long filmId) {
        this.itemId = itemId;
        this.filmTitle = filmTitle;
        this.rate = rate;
        this.filmId = filmId;
    }


    //Setters

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public String getFilmTitle() {
        return filmTitle;
    }

    public void setFilmTitle(String filmTitle) {
        this.filmTitle = filmTitle;
    }

    public Float getRate() {
        return rate;
    }

    public void setRate(Float rate) {
        this.rate = rate;
    }

    public long getFilmId() {
        return filmId;
    }

    public void setFilmId(long filmId) {
        this.filmId = filmId;
    }
}
