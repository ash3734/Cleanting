package com.example.ash.cleantind_dgu_cse_se.tab2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.example.ash.cleantind_dgu_cse_se.DBManager;
import com.example.ash.cleantind_dgu_cse_se.R;
import com.example.ash.cleantind_dgu_cse_se.User;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by ash on 2016-11-06.
 */
public class TabFragment2 extends Fragment {

    ArrayList<HostData> mDatas;
    RecyclerView recyclerView;
    LinearLayoutManager mLayoutManager;
    RecyclerViewAdapter adapter;
    EditText filterInput;
    public RequestManager mGlideRequestManager;
    Button button;
    @Override
    public void onStart() {
        super.onStart();
        mDatas = new ArrayList<HostData>();

        mDatas = dbadd();
        mGlideRequestManager = Glide.with(this);  //glide요청을 위한 파라메터 생성
        adapter = new RecyclerViewAdapter(mDatas, clickEvent, mGlideRequestManager); //어뎁터 생성
        recyclerView.setAdapter(adapter); //어뎁터 연결
        //각 item의 크기가 일정할 경우 고정
        recyclerView.setHasFixedSize(true);

        // layoutManager 설정
        mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mLayoutManager);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),HostRegisterActivity.class);
                startActivity(intent);
            }
        });
        filterInput.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
                String text = filterInput.getText().toString().toLowerCase(Locale.getDefault());
                adapter.filter(text);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2,
                                      int arg3) {
                // TODO Auto-generated method stub
            }
        });
    }


    public View.OnClickListener clickEvent = new View.OnClickListener() {
        public void onClick(View v) {
            int itemPosition = recyclerView.getChildPosition(v);
            String temp = mDatas.get(itemPosition).getID();
            Intent intent = new Intent(v.getContext(),DetailHostActivity.class);
            intent.putExtra("hostID",temp);
            startActivity(intent);
        }
    };

    public  ArrayList<HostData> dbadd(){
        ArrayList<HostData> tempArray = new ArrayList<HostData>();
        DBManager dbManager = new DBManager();
        dbManager.db_connect(User.baseURL+"ho_list.php");
        do{
            HostData tempHostData = new HostData();
            tempHostData.setImag_URl(dbManager.getResult("Profile_image"));
            tempHostData.setID(dbManager.getResult("UserID"));
            tempHostData.setCity(dbManager.getResult("City"));
            tempHostData.setTown(dbManager.getResult("Town"));
            tempArray.add(tempHostData);
        }while(dbManager.setNextData());
        if(tempArray.get(0).getID().equals(""))
            tempArray.clear();
        return tempArray;
    }

    //화면에 뿌리기
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View frag = inflater.inflate(R.layout.tab_fragment_2, container, false);
        filterInput = (EditText)(frag.findViewById(R.id.hostfilter));
        button = (Button)(frag.findViewById(R.id.registerHostBtn));
        recyclerView = (RecyclerView)(frag.findViewById(R.id.recylerViewHost));
        return frag;
    }

    @Override
    public void onResume() {
        super.onResume();
        mDatas.clear();
        mDatas=dbadd();
        recyclerView.setAdapter(adapter);
    }
}
