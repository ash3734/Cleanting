package com.example.ash.cleantind_dgu_cse_se;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * 로그인 액티비티
 */

public class LoginActivity extends AppCompatActivity {
    EditText inputIdEdit;
    EditText inputPwdEdit;
    Button loginBtn;
    Button signUpBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        backPressCloseHandler = new BackPressCloseHandler(this);

        inputIdEdit = (EditText)findViewById(R.id.inputIdEdit);
        inputPwdEdit = (EditText)findViewById(R.id.inputPwdEdit);
        loginBtn = (Button)findViewById(R.id.loginBtn);
        signUpBtn = (Button)findViewById(R.id.signUpBtn);

        //로그인 버튼 클릭시
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //입력여부 예외처리
                if(inputIdEdit.length() == 0){
                    Toast.makeText(getApplicationContext(),"ID를 입력해주세요",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(inputPwdEdit.length() == 0){
                    Toast.makeText(getApplicationContext(),"Pwd를 입력해주세요",Toast.LENGTH_SHORT).show();
                    return;
                }

                //로그인시 메인으로 이동
                if(login()){
                    Toast.makeText(getApplicationContext(),"로그인 성공!!!",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    User.ID=inputIdEdit.getText().toString();
                    startActivity(intent);
                    finish();

                }
                else
                    Toast.makeText(getApplicationContext(),"로그인 실패!!!",Toast.LENGTH_SHORT).show();
                    inputIdEdit.setText("");
                    inputPwdEdit.setText("");
            }
        });

        //회원가입 버튼 클릭시 회원가입 액티비티 이동
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(),SignUpActivity.class);
                startActivity(intent);

            }
        });
    }
    //로그인 디비연동
    public boolean login(){
        DBManager dbManager = new DBManager();
        dbManager.setAParameter("UserID",inputIdEdit.getText().toString());
        dbManager.setAParameter("Password",inputPwdEdit.getText().toString());
        dbManager.db_connect(User.baseURL+"login.php");
        if(dbManager.getResult().equals("failure")){
            return false;
        }
        else{
            return true;
        }
    }
    private BackPressCloseHandler backPressCloseHandler;

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        backPressCloseHandler.onBackPressed();
    }
}
