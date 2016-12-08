package com.example.ash.cleantind_dgu_cse_se.tab4;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.ash.cleantind_dgu_cse_se.DBManager;
import com.example.ash.cleantind_dgu_cse_se.R;
import com.example.ash.cleantind_dgu_cse_se.User;

public class UpdateProfileActivity extends AppCompatActivity {
    private TextView myId;
    Button updateBtn;
    Button buttonReset;

    private EditText editTextPwd;
    private EditText editTextPwdCheck;
    private EditText editTextName;
    private EditText editTextPhone;
    private EditText editTextEmail;
    private EditText editTextDetailAddress;
    private RadioGroup groupGender;
    private RadioButton tempGender;
    public Spinner spinner01;        // city_Spinner
    public Spinner spinner02;        // town_Spinner


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        myId=(TextView)findViewById(R.id.myId);
        myId.setText(User.ID);
        editTextPwd = (EditText)findViewById(R.id.editPwd);
        editTextPwdCheck = (EditText)findViewById(R.id.editPwdCheck);
        editTextName =(EditText)findViewById(R.id.editName);
        editTextPhone = (EditText)findViewById(R.id.editphoneNumber);
        editTextEmail = (EditText)findViewById(R.id.editEmail);
        editTextDetailAddress = (EditText)findViewById(R.id.editDetailAddress);
        groupGender = (RadioGroup)findViewById(R.id.radioGender);

        updateBtn = (Button)findViewById(R.id.updateBtn);
        buttonReset = (Button)findViewById(R.id.resetBtn);
        setdb();
        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                editTextName.setText("");
                editTextPwd.setText("");
                editTextPwdCheck.setText("");
                editTextPhone.setText("");
                editTextEmail.setText("");
                editTextDetailAddress.setText("");
            }
        });
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateProfile();
                finish();
            }
        });
    }
    public void updateProfile(){
        DBManager dbManager = new DBManager();
        dbManager.setAParameter("UserID",myId.getText().toString());
        dbManager.setAParameter("Password",editTextPwdCheck.getText().toString());
        dbManager.setAParameter("Phone",editTextPhone.getText().toString());
        dbManager.setAParameter("Email",editTextEmail.getText().toString());
        dbManager.db_connect(User.baseURL+"UserUpdate.php");
    }
    public void setdb(){
        DBManager dbManager = new DBManager();
        dbManager.setAParameter("UserID",User.ID);
        dbManager.db_connect(User.baseURL+"userCopy.php");
        editTextPhone.setText(dbManager.getResult("Phone"));
        editTextEmail.setText(dbManager.getResult("Email"));
    }
}
