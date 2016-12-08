package com.example.ash.cleantind_dgu_cse_se.tab3;

import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.ash.cleantind_dgu_cse_se.DBManager;
import com.example.ash.cleantind_dgu_cse_se.R;
import com.example.ash.cleantind_dgu_cse_se.User;

import java.util.ArrayList;

/**
 * 매칭 받은 정보 리스트 어뎁터
 */

public class RecyclerViewAdapterRecieve extends RecyclerView.Adapter<ViewHolderRecieve> {
    ArrayList<RecieveData> itemDatas;

    //생성자 리스트에 담을 리스트
    public RecyclerViewAdapterRecieve(ArrayList<RecieveData> itemDatas) {
        this.itemDatas = itemDatas;
    }

    //Layout을 연결
    @Override
    public ViewHolderRecieve onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_recieve_layout, parent, false);
        ViewHolderRecieve viewHolder = new ViewHolderRecieve(itemView);
        return viewHolder;
    }

    //현재 아이템 묶기
    @Override
    public void onBindViewHolder(ViewHolderRecieve holder, final int position) {
        holder.textViewName.setText(itemDatas.get(position).getRecieveUserName());
        holder.textViewTo.setText(itemDatas.get(position).getRecieveFrom());
        //거절 버튼 클릭시
        //매칭 결과 UPDATE
        holder.buttonReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reject();
                Toast.makeText(v.getContext(),"거절되었습니다.",Toast.LENGTH_SHORT).show();
            }
        });
        //승인 버튼 클릭시 상대방 번호 띄우기
        //매칭 결과 UPDATE
        holder.buttonAdmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder ab = new AlertDialog.Builder(v.getContext());
                ab.setMessage(Html.fromHtml("<strong><font color=\"#ff0000\"> " + "승인 완료 "
                        + "</font></strong><br> 핸드폰 번호"+getPhoneNumber(itemDatas.get(position).getRecieveUserName())));
                ab.setPositiveButton("ok", null);
                ab.show();
            }
        });
        //holder.textViewName.setText(itemDatas.get(position).getRequestUserName());
    }

    @Override
    public int getItemCount() {
        return (itemDatas != null) ? itemDatas.size() : 0;
    }

    //신청한 사람 번호 디비에 접근해 출력
    public String getPhoneNumber(String ID){
        DBManager dbManager = new DBManager();
        dbManager.setAParameter("UserID",ID);
        dbManager.db_connect(User.baseURL+"approveCleaner.php");
        return dbManager.getResult("Phone");
    }
    //거절할경우 매칭 table 업데이트
    public void reject(){
        DBManager dbManager = new DBManager();
        dbManager.setAParameter("UserID",User.ID);
        dbManager.db_connect(User.baseURL+"rejectionCleaner.php");
    }
}