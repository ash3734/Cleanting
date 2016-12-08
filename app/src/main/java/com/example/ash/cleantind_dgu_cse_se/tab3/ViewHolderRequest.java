package com.example.ash.cleantind_dgu_cse_se.tab3;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.ash.cleantind_dgu_cse_se.R;

/**
 * 발신한 layout역는 클래스
 */

public class ViewHolderRequest extends RecyclerView.ViewHolder {
    TextView textViewName;
    TextView textViewTo;
    TextView textViewState;

    public ViewHolderRequest(View itemView) {
        super(itemView);
        textViewName = (TextView) itemView.findViewById(R.id.requestNameText);
        textViewTo = (TextView)itemView.findViewById(R.id.requestToText);
        textViewState = (TextView)itemView.findViewById(R.id.requestState);
    }
}