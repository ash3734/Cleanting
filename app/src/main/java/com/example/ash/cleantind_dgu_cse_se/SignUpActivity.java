package com.example.ash.cleantind_dgu_cse_se;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * 회원가입 액티비티
 */

public class SignUpActivity extends AppCompatActivity {
    Button buttonDuplicate;
    Button buttonSignUp;
    Button buttonReset;

    private EditText editTextID;
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
    /**********************************************************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        editTextID =(EditText)findViewById(R.id.editId);
        editTextPwd = (EditText)findViewById(R.id.editPwd);
        editTextPwdCheck = (EditText)findViewById(R.id.editPwdCheck);
        editTextName =(EditText)findViewById(R.id.editName);
        editTextPhone = (EditText)findViewById(R.id.editphoneNumber);
        editTextEmail = (EditText)findViewById(R.id.editEmail);
        editTextDetailAddress = (EditText)findViewById(R.id.editDetailAddress);
        groupGender = (RadioGroup)findViewById(R.id.radioGender);
        //Spinner01초기화
        spinner01 = (Spinner)findViewById(R.id.spinner_01);
        //Spinner02초기화
        spinner02 = (Spinner)findViewById(R.id.spinner_02);
        populateSpinners();

        buttonDuplicate = (Button)findViewById(R.id.dupCheckBtn);
        buttonSignUp = (Button)findViewById(R.id.signUpBtn);
        buttonReset = (Button)findViewById(R.id.resetBtn);
        /**
         * reset 버튼에 대한 클릭이벤트 부여
         * 초기 상태로 돌린다
         */

        //리셋 버튼 클릭시
        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editTextID.setText("");
                editTextName.setText("");
                editTextPwd.setText("");
                editTextPwdCheck.setText("");
                editTextPhone.setText("");
                editTextEmail.setText("");
                editTextDetailAddress.setText("");
            }
        });
        /**
         * submit 버튼에 대한 클릭이벤트 부여
         * 클릭시 입력한 정보를 Toast로 출력해준다
         */

        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /**
                 * id/pwd/이름/주소를 입력했는지 체크한다.+비밀번호 확인 입력과 입력받은 비밀번호를 확인한다.
                 */

                tempGender = (RadioButton) findViewById(groupGender.getCheckedRadioButtonId());

                if(!editTextPwd.getText().toString().equals(editTextPwdCheck.getText().toString())){
                    Toast.makeText(getApplicationContext(),"비밀번호가 다릅니다",Toast.LENGTH_SHORT).show();
                    editTextPwdCheck.setText("");
                    editTextPwd.setText("");
                    return;
                }
                if(!(6<=editTextPwd.length()&&editTextPwd.length()<=12)) {
                    Toast.makeText(getApplicationContext(), "비밀번호는 6~12자리여야 합니다.", Toast.LENGTH_SHORT).show();
                    editTextPwdCheck.setText("");
                    editTextPwd.setText("");
                    return;
                }

                if(editTextID.length() == 0 )
                {
                    Toast.makeText(getApplicationContext(),"Id을 입력해주세요.",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(editTextPwd.length() == 0) {
                    Toast.makeText(getApplicationContext(), "Pwd을 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(editTextName.length() == 0 )
                {
                    Toast.makeText(getApplicationContext(),"이름을 입력해주세요.",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(editTextPhone.length() == 0) {
                    Toast.makeText(getApplicationContext(), "연락처를 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }
                signUp();
                User.ID = editTextID.getText().toString();
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //중복확인 버튼 클릭시
        buttonDuplicate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(dbDuplicate()){
                    Toast.makeText(getApplicationContext(),"아이디를 사용할 수 없습니다.",Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(getApplicationContext(),"아이디를 사용할 수 있습니다.",Toast.LENGTH_LONG).show();
                }
            }
        });
        //주소에 스피너를 넣어 클릭이벤트 부여
        spinner01.setOnItemSelectedListener(spinSelectedlistener);

    }

    //중복되면 true 아니면 false
    public boolean dbDuplicate(){
        DBManager dbManager = new DBManager();
        dbManager.setAParameter("UserID",editTextID.getText().toString());
        dbManager.db_connect(User.baseURL+"join_id_check.php");
        if(dbManager.getResult().equals("no")){
            return true;
        }
        else{
            return false;
        }
    }

    //회원가입 DB Insert
    public void signUp(){
        DBManager dbManager = new DBManager();
        dbManager.setAParameter("UserID",editTextID.getText().toString());
        dbManager.setAParameter("Password",editTextPwd.getText().toString());
        dbManager.setAParameter("UserName",editTextName.getText().toString());
        dbManager.setAParameter("Phone",editTextPhone.getText().toString());
        dbManager.setAParameter("Email",editTextEmail.getText().toString());
        dbManager.setAParameter("City",spinner01.getSelectedItem().toString());
        dbManager.setAParameter("Town",spinner02.getSelectedItem().toString());
        dbManager.setAParameter("DetailAddress",editTextDetailAddress.getText().toString());
        dbManager.setAParameter("gender",tempGender.getText().toString());
        dbManager.db_connect(User.baseURL+"join.php");
    }
    //주소 스피너 2개 사용 :
    private void populateSpinners() {
        ArrayAdapter<CharSequence> fAdapter;
        fAdapter = ArrayAdapter.createFromResource(this, R.array.city, android.R.layout.simple_spinner_item);
        fAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner01.setAdapter(fAdapter);
    }

    private void populateSubSpinners(int itemNum) {
        ArrayAdapter<CharSequence> fAdapter;
        fAdapter = ArrayAdapter.createFromResource(this, itemNum, android.R.layout.simple_spinner_item);
        fAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner02.setAdapter(fAdapter);
    }

    //2번째 (Town) 스피너 연결
    private AdapterView.OnItemSelectedListener spinSelectedlistener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            switch(position){
                case (0):
                    populateSubSpinners(R.array.SEOUL);
                    break;
                case (1):
                    populateSubSpinners(R.array.BUSAN);
                    break;
                case (2):
                    populateSubSpinners(R.array.DAEGU);
                    break;
                case (3):
                    populateSubSpinners(R.array.INCHEON);
                    break;
                case (4):
                    populateSubSpinners(R.array.KWANGJU);
                    break;
                case (5):
                    populateSubSpinners(R.array.DAEJEON);
                    break;
                case (6):
                    populateSubSpinners(R.array.ULSAN);
                    break;
                case (7):
                    populateSubSpinners(R.array.SEJONG);
                    break;
                case (8):
                    populateSubSpinners(R.array.KK);
                    break;
                case (9):
                    populateSubSpinners(R.array.KW);
                    break;
                case (10):
                    populateSubSpinners(R.array.CN);
                    break;
                case (11):
                    populateSubSpinners(R.array.CB);
                    break;
                case (12):
                    populateSubSpinners(R.array.JB);
                    break;
                case (13):
                    populateSubSpinners(R.array.JN);
                    break;
                case (14):
                    populateSubSpinners(R.array.KB);
                    break;
                case (15):
                    populateSubSpinners(R.array.KN);
                    break;
                case (16):
                    populateSubSpinners(R.array.JEJU);
                    break;
            }
        }
        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    };
}
