package com.example.ash.cleantind_dgu_cse_se.tab3;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.ash.cleantind_dgu_cse_se.R;

/**
 * 수신 요청한 View역는 클래스
 */

public class ViewHolderRecieve extends RecyclerView.ViewHolder{
    TextView textViewName;
    TextView textViewTo;
    Button buttonAdmit;
    Button buttonReject;
    public ViewHolderRecieve(View itemView) {
        super(itemView);
        textViewName = (TextView) itemView.findViewById(R.id.recieveNameText);
        textViewTo = (TextView)itemView.findViewById(R.id.recieveToText);
        buttonAdmit = (Button)itemView.findViewById(R.id.recieveAdmitBtn);
        buttonReject = (Button)itemView.findViewById(R.id.recieveRejectBtn);
    }
}
