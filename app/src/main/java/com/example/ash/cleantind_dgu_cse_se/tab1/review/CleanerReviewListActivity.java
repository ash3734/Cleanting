package com.example.ash.cleantind_dgu_cse_se.tab1.review;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.example.ash.cleantind_dgu_cse_se.DBManager;
import com.example.ash.cleantind_dgu_cse_se.R;
import com.example.ash.cleantind_dgu_cse_se.User;

import java.util.ArrayList;

public class CleanerReviewListActivity extends AppCompatActivity {
    ArrayList<ReviewData> mDatas;
    RecyclerView recyclerView;
    LinearLayoutManager mLayoutManager;
    RecyclerViewAdapter adapter;
    TextView textView;
    String mID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cleaner_review_list);
        recyclerView = (RecyclerView)findViewById(R.id.cleanerreviewlist);
        textView = (TextView)findViewById(R.id.review_ID);
        Intent intent = getIntent();
        mID = intent.getExtras().getString("cleanerID");
        textView.setText(mID);
        mDatas = new ArrayList<ReviewData>();

        mDatas = dbadd();

        adapter = new RecyclerViewAdapter(mDatas); //어뎁터 생성
        recyclerView.setAdapter(adapter); //어뎁터 연결
        //각 item의 크기가 일정할 경우 고정
        recyclerView.setHasFixedSize(true);

        // layoutManager 설정
        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mLayoutManager);
    }
    public  ArrayList<ReviewData> dbadd(){
        ArrayList<ReviewData> tempArray = new ArrayList<ReviewData>();
        DBManager dbManager = new DBManager();
        dbManager.setAParameter("CUser_ID",mID);
        dbManager.db_connect(User.baseURL+"cleanerReviewList.php");
        do{
            ReviewData tempReviewData = new ReviewData();
            String str =dbManager.getResult("RE_ID");
            tempReviewData.setID(str);

            tempReviewData.setReview(dbManager.getResult("review"));
            tempArray.add(tempReviewData);
        }while(dbManager.setNextData());
        if(tempArray.get(0).getID().equals(""))
            tempArray.clear();
        return tempArray;
    }
}
