package com.example.ash.cleantind_dgu_cse_se.tab1;

/**
 * Created by ash on 2016-11-06.
 * 첫번째 탭 recyclerView에 담을 아이템
 */
public class CleanerData {
    String imageURL;
    String ID;
    String CITY;
    String Town;


    public CleanerData(){

    }
    public CleanerData(String imageURL, String ID, String CITY, String town) {
        this.imageURL = imageURL;
        this.ID = ID;
        this.CITY = CITY;
        Town = town;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getCITY() {
        return CITY;
    }

    public void setCITY(String CITY) {
        this.CITY = CITY;
    }

    public String getTown() {
        return Town;
    }

    public void setTown(String town) {
        Town = town;
    }
}