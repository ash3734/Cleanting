package com.example.ash.cleantind_dgu_cse_se.tab2;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ash.cleantind_dgu_cse_se.DBManager;
import com.example.ash.cleantind_dgu_cse_se.R;
import com.example.ash.cleantind_dgu_cse_se.User;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static com.example.ash.cleantind_dgu_cse_se.R.id.ReqBtnHost;

//집주인 상세 정보 액티비티
public class DetailHostActivity extends AppCompatActivity {
    String hID;
    FloatingActionButton req;

    private TextView hostName;
    private TextView hostSex;
    private TextView hostCity;
    private TextView hostTown;
    private TextView hostArea;
    private TextView hostDwellPattern;
    private TextView hostDetailAddress;
    private TextView hostDate;
    private TextView hostHour;
    private TextView hostDetailexp;
    private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_host);
        imageView = (ImageView)findViewById(R.id.hostDetailImage);
        hostName = (TextView)findViewById(R.id.host_name);
        hostSex= (TextView)findViewById(R.id.host_sex);
        hostCity= (TextView)findViewById(R.id.host_city);
        hostTown= (TextView)findViewById(R.id.host_town);
        hostArea= (TextView)findViewById(R.id.host_area);
        hostDwellPattern= (TextView)findViewById(R.id.host_dwellpattern);
        hostDate= (TextView)findViewById(R.id.host_date);
        hostHour= (TextView)findViewById(R.id.host_hour);
        hostDetailAddress= (TextView)findViewById(R.id.host_detailaddress);
        Intent intent = getIntent();
        hID = intent.getExtras().getString("hostID");
        setInfoWithHID();
        //아이디를 가지고 디비에 접근해 상세 정보를 출력


        /*********************************************request button 눌렀을떄 *******************************************/
        req= (FloatingActionButton) findViewById(ReqBtnHost);
        req.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestCleaner();
                Toast.makeText(getApplicationContext(),"신청완료!! 매칭신청현황 탭에서 확인하세요",Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
    //디비를 통해 상세정보 가져오기
    public void setInfoWithHID(){
        DBManager dbManager = new DBManager();
        dbManager.setAParameter("UserID",hID);
        dbManager.db_connect(User.baseURL+"homeRegister_INFO.php"); //확인
        hostName.setText(dbManager.getResult("UserName"));
        hostSex.setText(dbManager.getResult("gender"));
        hostCity.setText(dbManager.getResult("City"));
        hostTown.setText(dbManager.getResult("Town"));
        hostArea.setText(dbManager.getResult("Area"));
        hostDwellPattern.setText(dbManager.getResult("Dwell"));
        hostDate.setText(dbManager.getResult("hostDate"));
        hostHour.setText(dbManager.getResult("hostHour"));
        hostDetailAddress.setText(dbManager.getResult("Detail"));
    }
    //청소부 요청시 매칭DB Table에 업데이트
    public void requestCleaner(){
        DBManager dbManager = new DBManager();
        dbManager.setAParameter("HUser_ID",hID);
        dbManager.setAParameter("CUser_ID", User.ID);
        dbManager.setAParameter("Statement","승인대기");
        dbManager.setAParameter("Register_Date",getDateString());
        dbManager.db_connect(User.baseURL+"matching1.php");
    }
    //현재 날짜 받아오기
    public String getDateString()
    {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA);
        String str_date = df.format(new Date());

        return str_date;
    }
}
