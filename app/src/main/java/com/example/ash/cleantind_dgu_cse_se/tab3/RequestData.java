package com.example.ash.cleantind_dgu_cse_se.tab3;

/**
 * 요청한 리스트에 각 정보를 담는 클래스
 */

public class RequestData {
    private String requestUserName;
    private String requestTo;
    private String requestState;

    public RequestData(){

    }

    public String getRequestUserName() {
        return requestUserName;
    }

    public void setRequestUserName(String requestUserName) {
        this.requestUserName = requestUserName;
    }

    public String getRequestTo() {
        return requestTo;
    }

    public void setRequestTo(String requestTo) {
        this.requestTo = requestTo;
    }

    public String getRequestState() {
        return requestState;
    }

    public void setRequestState(String requestState) {
        this.requestState = requestState;
    }
}
