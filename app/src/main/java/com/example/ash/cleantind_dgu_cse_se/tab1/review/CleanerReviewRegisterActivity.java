package com.example.ash.cleantind_dgu_cse_se.tab1.review;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ash.cleantind_dgu_cse_se.DBManager;
import com.example.ash.cleantind_dgu_cse_se.R;
import com.example.ash.cleantind_dgu_cse_se.User;

public class CleanerReviewRegisterActivity extends AppCompatActivity {

    Button button;
    EditText editText;
    String pID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cleaner_review_register);
        button = (Button)findViewById(R.id.cleanerreviewregister);
        editText = (EditText)findViewById(R.id.cleanerreviewedittext);
        final Intent intent = getIntent();
        pID = intent.getExtras().getString("cleanerID");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
                Toast.makeText(getApplicationContext(),"리뷰 등록이 완료되었습니다.",Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
    public void register(){
        DBManager dbManager = new DBManager();
        dbManager.setAParameter("CUser_ID",pID);
        dbManager.setAParameter("RE_ID", User.ID);
        dbManager.setAParameter("review",editText.getText().toString());
        dbManager.db_connect(User.baseURL+"cleanerReviewReg.php");
    }
}
