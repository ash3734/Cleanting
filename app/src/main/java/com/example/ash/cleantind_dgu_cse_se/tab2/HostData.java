package com.example.ash.cleantind_dgu_cse_se.tab2;

/**
 * Created by ash on 2016-11-28.
 * 집주인 게시판 리스트에 뿌려줄 데이터
 */

public class HostData {
    private String imag_URl;
    private String ID;
    private String City;
    private String Town;

    public String getImag_URl() {
        return imag_URl;
    }

    public void setImag_URl(String imag_URl) {
        this.imag_URl = imag_URl;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getTown() {
        return Town;
    }

    public void setTown(String town) {
        Town = town;
    }
}
