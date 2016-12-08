package com.example.ash.cleantind_dgu_cse_se.tab1;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ash.cleantind_dgu_cse_se.DBManager;
import com.example.ash.cleantind_dgu_cse_se.R;
import com.example.ash.cleantind_dgu_cse_se.User;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.Map;


/**
 * 클리너 등록 액티비티
 * 이미지를 서버에 업로드하는 부분이 미구현 상태
 */

public class CleanerRegisterActivity extends AppCompatActivity {

    private int PICK_IMAGE_REQUEST = 1;
    private static final int STORAGE_PERMISSION_CODE = 123;
    private String UPLOAD_URL =User.baseURL+"upload.php";
    private String KEY_IMAGE = "image";
    private String KEY_NAME = "UserID";
    private Bitmap bitmap ;

    private Button dateBtn;
    private Button dayhourBtn;
    private Button buttonChoose;;
    private Button registerCleaner;
    private ImageView imageView;

    private EditText pNameEditText;
    private EditText pGenderEditText;
    private EditText pCityEditText;
    private EditText pTownEditText;
    private EditText minAreaEditText;
    private EditText maxAreaEditText;
    private EditText pDetailEditText;


    private TextView possibleDate;
    private TextView possibleHour;


    int year, month, day, hour, minute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cleaner_register);
        GregorianCalendar calendar = new GregorianCalendar();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day= calendar.get(Calendar.DAY_OF_MONTH);
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);

        imageView = (ImageView)this.findViewById(R.id.imageView);
        dateBtn = (Button)findViewById(R.id.dateBtn);
        dayhourBtn = (Button)findViewById(R.id.dayhourBtn);
        buttonChoose =  (Button)this.findViewById(R.id.photoaddBtn);
        registerCleaner = (Button)findViewById(R.id.PostingBtn);
        //디비로 고정시키기
        pNameEditText = (EditText)findViewById(R.id.editPName);
        pGenderEditText = (EditText)findViewById(R.id.editPGender);
        pCityEditText = (EditText)findViewById(R.id.editPCity);
        pTownEditText = (EditText)findViewById(R.id.editPTown);

        setInfoWithID();
        //입력받기
        minAreaEditText = (EditText)findViewById(R.id.editMinArea);
        maxAreaEditText = (EditText)findViewById(R.id.editMaxArea);
        pDetailEditText = (EditText)findViewById(R.id.editPDetail);

        possibleDate=(TextView)findViewById(R.id.possibleDate);
        possibleHour=(TextView)findViewById(R.id.possibleHour);

        //갤러리에서 사진 불러오기
        buttonChoose.setOnClickListener(new View.OnClickListener() {
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
                //uploadImage();  //현재 미구현 사항
                finish();
            }
        });


        findViewById(R.id.dateBtn).setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {

                // TODO Auto-generated method stub

                new DatePickerDialog(CleanerRegisterActivity.this, dateSetListener, year, month, day).show();
            }
        });
        findViewById(R.id.dayhourBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // TODO Auto-generated method stub
                new TimePickerDialog(CleanerRegisterActivity.this, timeSetListener, hour, minute, false).show();
            }
        });
    }
    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }
    private DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {



        @Override

        public void onDateSet(DatePicker view, int year, int monthOfYear,

                              int dayOfMonth) {

            // TODO Auto-generated method stub

            String msg = String.format("%d / %d / %d", year,monthOfYear+1, dayOfMonth);

            Toast.makeText(CleanerRegisterActivity.this, msg, Toast.LENGTH_SHORT).show();
            possibleDate.setText(msg);

        }

    };

    private TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

            // TODO Auto-generated method stub

            String msg = String.format("%d / %d / %d", year, hourOfDay, minute);

            Toast.makeText(CleanerRegisterActivity.this, msg, Toast.LENGTH_SHORT).show();
            possibleHour.setText(msg);

        }

    };

    private void uploadImage(){
        //Showing the progress dialog
        final ProgressDialog loading = ProgressDialog.show(this,"Uploading...","Please wait...",false,false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UPLOAD_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        //Disimissing the progress dialog
                        loading.dismiss();
                        //Showing toast message of the response
                        Toast.makeText(getApplicationContext(), s , Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //Dismissing the progress dialog
                        loading.dismiss();

                        //Showing toast
                        Toast.makeText(getApplicationContext(), volleyError.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //Converting Bitmap to String
                String image = getStringImage(bitmap);

                //Getting Image Name
                String name = User.ID;

                //Creating parameters
                Map<String,String> params = new Hashtable<String, String>();

                //Adding parameters
                params.put(KEY_IMAGE, image);
                params.put(KEY_NAME, name);

                //returning parameters
                return params;
            }
        };

        //Creating a Request Queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //Adding request to the queue
        requestQueue.add(stringRequest);
    }

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {
                //Getting the Bitmap from Gallery
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                //Setting the Bitmap to ImageView
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //데이터 베이스 접근해 클리너 등록
    public void register(){
        //디비 경로를 받아서 스트링으로 저장
        DBManager dbManager = new DBManager();
        dbManager.setAParameter("UserID", User.ID);
        dbManager.setAParameter("UserName", pNameEditText.getText().toString());
        dbManager.setAParameter("gender", pGenderEditText.getText().toString());
        dbManager.setAParameter("City", pCityEditText.getText().toString());
        dbManager.setAParameter("Town", pTownEditText.getText().toString());
        dbManager.setAParameter("MaxArea", maxAreaEditText.getText().toString());
        dbManager.setAParameter("MinArea", minAreaEditText.getText().toString());
        dbManager.setAParameter("cleanerDate", possibleDate.getText().toString());
        dbManager.setAParameter("cleanerTime", possibleHour.getText().toString());
        dbManager.setAParameter("Detail",pDetailEditText.getText().toString());
        //String str= getStringImage(bitmap);
        dbManager.setAParameter("Profile_image","http://192.168.43.100/uploads/ash3734.png"); //미구현으로인해 defalult
        dbManager.db_connect(User.baseURL+"cleanRegister.php");
    }

    //자동입력 메소드
    public void setInfoWithID(){
        DBManager dbManager = new DBManager();
        dbManager.setAParameter("UserID",User.ID);
        dbManager.db_connect(User.baseURL+"registerCopy.php");
        pNameEditText.setText(dbManager.getResult("UserName"));
        pGenderEditText.setText(dbManager.getResult("gender"));
        pCityEditText.setText(dbManager.getResult("City"));
        pTownEditText.setText(dbManager.getResult("Town"));
    }
}
