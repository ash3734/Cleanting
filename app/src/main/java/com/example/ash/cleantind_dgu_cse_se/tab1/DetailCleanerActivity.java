package com.example.ash.cleantind_dgu_cse_se.tab1;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.ash.cleantind_dgu_cse_se.DBManager;
import com.example.ash.cleantind_dgu_cse_se.R;
import com.example.ash.cleantind_dgu_cse_se.User;
import com.example.ash.cleantind_dgu_cse_se.tab1.review.CleanerReviewListActivity;
import com.example.ash.cleantind_dgu_cse_se.tab1.review.CleanerReviewRegisterActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static com.example.ash.cleantind_dgu_cse_se.R.id.ReqBtnCleaner;

//청소부 상세화면 구현하자
public class DetailCleanerActivity extends AppCompatActivity {
    String pID;
    String imag;
    Button reviewRegButton;
    Button reviewShowButton;
    FloatingActionButton req;
    private TextView cleanerName;
    private TextView cleanerSex;
    private TextView cleanerCity;
    private TextView cleanerTown;
    private TextView cleanerMinArea;
    private TextView cleanerMaxArea;
    private TextView cleanerDate;
    private TextView cleanerHour;
    private TextView cleanerDetailexp;
    private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_cleaner);
        cleanerName = (TextView)findViewById(R.id.cleaner_name);
        cleanerSex = (TextView)findViewById(R.id.cleaner_sex);
        cleanerCity = (TextView)findViewById(R.id.cleaner_city);
        cleanerTown = (TextView)findViewById(R.id.cleaner_town);
        cleanerMinArea = (TextView)findViewById(R.id.cleaner_minarea);
        cleanerMaxArea = (TextView)findViewById(R.id.cleaner_maxarea);
        cleanerDate = (TextView)findViewById(R.id.cleaner_date);
        cleanerHour = (TextView)findViewById(R.id.cleaner_hour);
        cleanerDetailexp = (TextView)findViewById(R.id.cleaner_detailexp);
        imageView = (ImageView)findViewById(R.id.cleaner_imageView);
        reviewRegButton = (Button)findViewById(R.id.insertcleanerreview);
        reviewShowButton = (Button)findViewById(R.id.gocleanerreview);
        final Intent intent = getIntent();
        pID = intent.getExtras().getString("cleanerID");
        //아이디를 가지고 디비에 접근해 상세 정보를 출력
        setInfoWithPID();

        /*********************************************request button 눌렀을떄 *******************************************/
        req= (FloatingActionButton) findViewById(ReqBtnCleaner);
        req.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestCleaner();
                Toast.makeText(getApplicationContext(),"신청완료!! 매칭신청현황 탭에서 확인하세요",Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        reviewRegButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(getApplicationContext(),CleanerReviewRegisterActivity.class);
                intent2.putExtra("cleanerID",pID);
                startActivity(intent2);
            }
        });
        reviewShowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getApplicationContext(), CleanerReviewListActivity.class);
                intent1.putExtra("cleanerID",pID);
                startActivity(intent1);
            }
        });
    }
    //디비를 통해 상세정보 가져오기
    public void setInfoWithPID(){
        DBManager dbManager = new DBManager();
        dbManager.setAParameter("UserID",pID);
        dbManager.db_connect(User.baseURL+"cleanRegister_INFO.php"); //확인
        cleanerName.setText(dbManager.getResult("UserName"));
        cleanerSex.setText(dbManager.getResult("gender"));
        //imag = dbManager.getResult("image");
        //imageView.setImageBitmap(stringToBitmap(imag));
        cleanerDate.setText(dbManager.getResult("cleanerDate"));
        cleanerHour.setText(dbManager.getResult("cleanerHour"));
        cleanerCity.setText(dbManager.getResult("City"));
        cleanerTown.setText(dbManager.getResult("Town"));
        cleanerMaxArea.setText(dbManager.getResult("MaxArea"));
        cleanerMinArea.setText(dbManager.getResult("MinArea"));
        cleanerDetailexp.setText(dbManager.getResult("Detail"));
        Glide.with(this).load(dbManager.getResult("Profile_image")).into(imageView);
    }
    //청소부 요청시 매칭DB Table에 업데이트
    public void requestCleaner(){
        DBManager dbManager = new DBManager();
        dbManager.setAParameter("CUser_ID",pID);
        dbManager.setAParameter("HUser_ID", User.ID);
        dbManager.setAParameter("Statement","승인대기");
        dbManager.setAParameter("Register_Date",getDateString());
        dbManager.db_connect(User.baseURL+"matching.php");
    }

    //현제 날짜 받아오기
    public String getDateString()
    {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA);
        String str_date = df.format(new Date());

        return str_date;
    }
}
