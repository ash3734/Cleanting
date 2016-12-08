package com.example.ash.cleantind_dgu_cse_se.tab1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.example.ash.cleantind_dgu_cse_se.DBManager;
import com.example.ash.cleantind_dgu_cse_se.R;
import com.example.ash.cleantind_dgu_cse_se.User;

import java.util.ArrayList;
import java.util.Locale;

/**
 * 첫번째 탭 fragment recycler view를 담음
 * 청소부 게시판
 */
public class TabFragment1 extends Fragment {
    ArrayList<CleanerData> mDatas;
    RecyclerView recyclerView;
    LinearLayoutManager mLayoutManager;
    RecyclerViewAdapter adapter;
    EditText filterInput;
    public RequestManager mGlideRequestManager;
    Button button;
    String mCity;
    String mTown;
    ImageView recomendCleanerImageView;
    TextView recomendCleanerIDTextView;
    TextView recomendCleanerCityTextView;
    TextView recomendCleanerTownTextView;
    @Override
    public void onStart() {
        super.onStart();
        mDatas = new ArrayList<CleanerData>();
        mDatas = dbadd();
        setwithdb();

        mGlideRequestManager = Glide.with(this);  //glide요청을 위한 파라메터 생성 미구현 사항

        //각 item의 크기가 일정할 경우 고정
        recyclerView.setHasFixedSize(true);

        // layoutManager 설정
        mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mLayoutManager);
        adapter = new RecyclerViewAdapter(mDatas, clickEvent, mGlideRequestManager); //어뎁터 생성
        recyclerView.setAdapter(adapter); //어뎁터 연결
        //mdatas에서 나의 씨티가 같은 것을 찾는다.
        //같은게 없을 경우 추천할 청소부가 없습니다.라고 띄운다.
        //같은게 있을 경우
        // 타운이 같은 것을 찾는다.
        //같은게 없을 경우 시티가 같은 것을 띄운다.
        //같은게 있을 경우 타운까지 같은것을 띄운다.
        Log.d("myTag",mCity);
        Log.d("myTag",mTown);
        for(int i=0;i<mDatas.size();i++){
            if(mDatas.get(i).getCITY().equals(mCity)){
                for(int j=0;j<mDatas.size();j++){
                    if(mDatas.get(j).getTown().equals(mTown)){
                        if(!mDatas.get(j).getID().equals(User.ID)) {
                            recomendCleanerIDTextView.setText(mDatas.get(j).getID());
                            recomendCleanerTownTextView.setText(mDatas.get(j).getTown());
                            recomendCleanerCityTextView.setText(mDatas.get(j).getCITY());
                            Glide.with(this).load(mDatas.get(j).getImageURL()).into(recomendCleanerImageView);
                        }
                    }
                }
            }
        }
        //등록 버튼 클릭시
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),CleanerRegisterActivity.class);
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

    //리스트 아이템 클릭시 상세 화면으로 전환
    public View.OnClickListener clickEvent = new View.OnClickListener() {
        public void onClick(View v) {
            int itemPosition = recyclerView.getChildPosition(v);
            String temp = mDatas.get(itemPosition).ID;
            Intent intent = new Intent(v.getContext(),DetailCleanerActivity.class);
            intent.putExtra("cleanerID",temp);
            startActivity(intent);
        }
    };
    public void setwithdb(){
        DBManager dbManager = new DBManager();
        dbManager.setAParameter("UserID",User.ID);
        dbManager.db_connect(User.baseURL+"getMyAddress.php");
        mCity = dbManager.getResult("City");
        mTown = dbManager.getResult("Town");
    }
    //리싸이클러 뷰에 뿌릴 아이템 호출
    //현제 청소부 등록 테이블에 있는 모든 정보를 뽑아 온다.
    public  ArrayList<CleanerData> dbadd(){
        ArrayList<CleanerData> tempArray = new ArrayList<CleanerData>();
        DBManager dbManager = new DBManager();
        dbManager.db_connect(User.baseURL+"po_list.php");
        do{
            CleanerData tempCleanerData = new CleanerData();
            tempCleanerData.setImageURL(dbManager.getResult("Profile_image"));
            tempCleanerData.setID(dbManager.getResult("UserID"));
            tempCleanerData.setCITY(dbManager.getResult("City"));
            tempCleanerData.setTown(dbManager.getResult("Town"));
            tempArray.add(tempCleanerData);
        }while(dbManager.setNextData());
        if(tempArray.get(0).getID().equals(""))
            tempArray.clear();
        return tempArray;
    }

    //화면에 뿌리기
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View frag = inflater.inflate(R.layout.tab_fragment_1, container, false);
        filterInput = (EditText)(frag.findViewById(R.id.cleanerfilter));
        button = (Button)(frag.findViewById(R.id.registerCleanerBtn));
        recyclerView = (RecyclerView)(frag.findViewById(R.id.recylerView));
        recomendCleanerImageView = (ImageView)(frag.findViewById(R.id.recommend_cleaner_image));
        recomendCleanerIDTextView = (TextView)(frag.findViewById(R.id.recommend_cleaner_id));
        recomendCleanerCityTextView = (TextView)(frag.findViewById(R.id.recommend_cleaner_city));
        recomendCleanerTownTextView = (TextView)(frag.findViewById(R.id.recommend_cleaner_town));
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
