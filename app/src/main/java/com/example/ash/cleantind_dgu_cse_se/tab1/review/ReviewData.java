package com.example.ash.cleantind_dgu_cse_se.tab1.review;

/**
 * Created by ash on 2016-12-06.
 */

public class ReviewData {
    String ID;
    String review;

    public ReviewData(){

    }

    public ReviewData(String ID, String review) {
        this.ID = ID;
        this.review = review;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }
}
