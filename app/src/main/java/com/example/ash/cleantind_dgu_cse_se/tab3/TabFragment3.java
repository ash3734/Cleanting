package com.example.ash.cleantind_dgu_cse_se.tab3;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ash.cleantind_dgu_cse_se.DBManager;
import com.example.ash.cleantind_dgu_cse_se.R;
import com.example.ash.cleantind_dgu_cse_se.User;

import java.util.ArrayList;

/**
 * 나의 매칭 현황을 담는 faragment
 * 내가 요청한 매칭 리싸이클러뷰
 * 내가 수신한 매칭 리싸이클러뷰
 */
public class TabFragment3 extends Fragment {
    ArrayList<RequestData> RequestDatas;
    RecyclerView recyclerViewRequest;
    RecyclerViewAdapterRequest adapterRequest;

    ArrayList<RecieveData> recieveDatas;
    RecyclerView recyclerViewRecieve;
    RecyclerViewAdapterRecieve adapterRecieve;

    LinearLayoutManager mLayoutManager;

    @Override
    public void onStart() {
        super.onStart();
        RequestDatas = new ArrayList<RequestData>();

        RequestDatas = dbaddRequest();
        RequestDatas.addAll(dbaddRequest2());
        RequestDatas.remove(1);
        //요청한 매칭이 0개가 아닐결우
        if(!RequestDatas.get(0).getRequestUserName().equals("")) {
            adapterRequest = new RecyclerViewAdapterRequest(RequestDatas); //어뎁터 생성
            recyclerViewRequest.setAdapter(adapterRequest); //어뎁터 연결
            //각 item의 크기가 일정할 경우 고정
            recyclerViewRequest.setHasFixedSize(true);

            // layoutManager 설정
            mLayoutManager = new LinearLayoutManager(getActivity());
            mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerViewRequest.setLayoutManager(mLayoutManager);
        }

        recieveDatas = new ArrayList<RecieveData>();

        recieveDatas = dbaddRecieve();
        recieveDatas.addAll(dbaddRecieve());
        recieveDatas.remove(1);
        //수신한 매칭이 0개가 아닐경우
        if(!recieveDatas.get(0).getRecieveUserName().equals("")) {
            adapterRecieve = new RecyclerViewAdapterRecieve(recieveDatas);
            recyclerViewRecieve.setAdapter(adapterRecieve);
            recyclerViewRecieve.setHasFixedSize(true);

            mLayoutManager = new LinearLayoutManager(getActivity());
            mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerViewRecieve.setLayoutManager(mLayoutManager);
        }
    }
    //수신한 결과 table에서 불러 리싸이클러 뷰에 뿌릴 ArrayList에 담는다.
    public  ArrayList<RecieveData> dbaddRecieve(){
        ArrayList<RecieveData> tempArray = new ArrayList<RecieveData>();
        DBManager dbManager = new DBManager();
        dbManager.setAParameter("UserID",User.ID);
        dbManager.db_connect(User.baseURL+"recieveCleaner.php");
        do{
            RecieveData tempRecieveData = new RecieveData();
            tempRecieveData.setRecieveUserName(dbManager.getResult("HUser_ID"));
            tempRecieveData.setRecieveFrom("청소부");
            tempArray.add(tempRecieveData);
        }while(dbManager.setNextData());

        return tempArray;
    }
    //수신한 결과 table에서 불러 리싸이클러 뷰에 뿌릴 ArrayList에 담는다.
    public  ArrayList<RecieveData> dbaddRecieve2(){
        ArrayList<RecieveData> tempArray = new ArrayList<RecieveData>();
        DBManager dbManager = new DBManager();
        dbManager.setAParameter("UserID",User.ID);
        dbManager.db_connect(User.baseURL+"recieveHost.php");
        do{
            RecieveData tempRecieveData = new RecieveData();
            tempRecieveData.setRecieveUserName(dbManager.getResult("CUser_ID"));
            tempRecieveData.setRecieveFrom("집주인");
            tempArray.add(tempRecieveData);
        }while(dbManager.setNextData());

        return tempArray;
    }
    //발신한 결과 table에서 불러 리싸이클러 뷰에 뿌릴 ArrayList에 담는다.
    public  ArrayList<RequestData> dbaddRequest(){
        ArrayList<RequestData> tempArray = new ArrayList<RequestData>();
        DBManager dbManager = new DBManager();
        dbManager.setAParameter("UserID",User.ID);
        dbManager.db_connect(User.baseURL+"requsetCleaner.php");
        do{
            RequestData tempRequestData = new RequestData();
            tempRequestData.setRequestUserName(dbManager.getResult("CUser_ID"));
            tempRequestData.setRequestTo("청소부");
            tempRequestData.setRequestState(dbManager.getResult("Statement"));
            tempArray.add(tempRequestData);
        }while(dbManager.setNextData());
        return tempArray;
    }
    //발신한 결과 table에서 불러 리싸이클러 뷰에 뿌릴 ArrayList에 담는다.
    public  ArrayList<RequestData> dbaddRequest2(){
        ArrayList<RequestData> tempArray = new ArrayList<RequestData>();
        DBManager dbManager = new DBManager();
        dbManager.setAParameter("UserID",User.ID);
        dbManager.db_connect(User.baseURL+"requsetHost.php");
        do{
            RequestData tempRequestData = new RequestData();
            tempRequestData.setRequestUserName(dbManager.getResult("HUser_ID"));
            tempRequestData.setRequestTo("집주인");
            tempRequestData.setRequestState(dbManager.getResult("Statement"));
            tempArray.add(tempRequestData);
        }while(dbManager.setNextData());
        return tempArray;
    }
    //fragment에 화면 뿌리기
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View frag = inflater.inflate(R.layout.tab_fragment_3, container, false);
        recyclerViewRequest = (RecyclerView)(frag.findViewById(R.id.recylerViewRequest));
        recyclerViewRecieve = (RecyclerView)(frag.findViewById(R.id.recylerViewRecieve));
        return frag;
    }
}