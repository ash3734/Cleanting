package com.example.ash.cleantind_dgu_cse_se.tab2;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.ash.cleantind_dgu_cse_se.DBManager;
import com.example.ash.cleantind_dgu_cse_se.R;
import com.example.ash.cleantind_dgu_cse_se.User;

import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;

//집주인 등록 액티비티
public class HostRegisterActivity extends AppCompatActivity {
    private Button addPictureBtn;
    private Button registerCleaner;
    private ImageView homeImage;
    private Bitmap bitmap ;

    private int PICK_IMAGE_REQUEST = 1;
    private static final int STORAGE_PERMISSION_CODE = 123;

    private EditText hNameEditText;
    private EditText hGenderEditText;
    private EditText hCityEditText;
    private EditText hTownEditText;
    private EditText dwellEditText;
    private EditText areaEditText;
    private EditText hDetailEditText;
    private Button dateBtn;
    private Button dayhourBtn;
    private TextView possibleDate;
    private TextView possibleHour;

    int year, month, day, hour, minute;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host_register);
        GregorianCalendar calendar = new GregorianCalendar();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day= calendar.get(Calendar.DAY_OF_MONTH);
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);
        hNameEditText = (EditText) findViewById(R.id.editHName);
        hGenderEditText = (EditText) findViewById(R.id.editHGender);
        hCityEditText = (EditText)findViewById(R.id.editHCity);
        hTownEditText = (EditText)findViewById(R.id.editHTown);
        dateBtn = (Button)findViewById(R.id.dateBtn2);
        dayhourBtn = (Button)findViewById(R.id.dayhourBtn2);
        setInfoWithID();
        dwellEditText = (EditText) findViewById(R.id.editDwell);
        areaEditText = (EditText) findViewById(R.id.editArea);
        hDetailEditText = (EditText) findViewById(R.id.editHDetail);

        addPictureBtn = (Button) findViewById(R.id.hostImageAddBtn);
        homeImage = (ImageView) findViewById(R.id.hostImage);
        registerCleaner = (Button)findViewById(R.id.HostingBtn);


        possibleDate=(TextView)findViewById(R.id.possibleDate2);
        possibleHour=(TextView)findViewById(R.id.possibleHour2);

        addPictureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser();
            }
        });
        //등록하기 버튼
        registerCleaner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
                finish();
            }
        });

        findViewById(R.id.dateBtn2).setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {

                // TODO Auto-generated method stub

                new DatePickerDialog(HostRegisterActivity.this, dateSetListener, year, month, day).show();
            }
        });
        findViewById(R.id.dayhourBtn2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // TODO Auto-generated method stub
                new TimePickerDialog(HostRegisterActivity.this, timeSetListener, hour, minute, false).show();
            }
        });
    }
    private DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {



        @Override

        public void onDateSet(DatePicker view, int year, int monthOfYear,

                              int dayOfMonth) {

            // TODO Auto-generated method stub

            String msg = String.format("%d / %d / %d", year,monthOfYear+1, dayOfMonth);

            Toast.makeText(HostRegisterActivity.this, msg, Toast.LENGTH_SHORT).show();
            possibleDate.setText(msg);

        }

    };

    private TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

            // TODO Auto-generated method stub

            String msg = String.format("%d / %d / %d", year, hourOfDay, minute);

            Toast.makeText(HostRegisterActivity.this, msg, Toast.LENGTH_SHORT).show();
            possibleHour.setText(msg);

        }

    };
    //갤러리에서 사진 불러오기
    public void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    //사진 불러온 후 세팅
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {
                //비트맵으로 저장
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                //이미지뷰에 적용
                homeImage.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //데이터 베이스 접근해 호스트 등록
    public void register(){
        //디비 경로를 받아서 스트링으로 저장
        DBManager dbManager = new DBManager();
        dbManager.setAParameter("UserID",User.ID);
        dbManager.setAParameter("UserName", hNameEditText.getText().toString());
        dbManager.setAParameter("gender", hGenderEditText.getText().toString());
        dbManager.setAParameter("City", hCityEditText.getText().toString());
        dbManager.setAParameter("Town", hTownEditText.getText().toString());
        dbManager.setAParameter("dwell", dwellEditText.getText().toString());
        dbManager.setAParameter("Area", areaEditText.getText().toString());
        dbManager.setAParameter("hostDate",possibleDate.getText().toString());
        dbManager.setAParameter("hostTime",possibleHour.getText().toString());
        dbManager.setAParameter("Detail",hDetailEditText.getText().toString());
        dbManager.setAParameter("Home_image","default"); //미구현으로인해 defalult
        dbManager.db_connect(User.baseURL+"homeRegister.php");
    }
    //자동입력 메소드
    public void setInfoWithID(){
        DBManager dbManager = new DBManager();
        dbManager.setAParameter("UserID", User.ID);
        dbManager.db_connect(User.baseURL+"registerCopy.php");
        hNameEditText.setText(dbManager.getResult("UserName"));
        hGenderEditText.setText(dbManager.getResult("gender"));
        hCityEditText.setText(dbManager.getResult("City"));
        hTownEditText.setText(dbManager.getResult("Town"));
    }

}
