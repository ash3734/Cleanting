package com.example.ash.cleantind_dgu_cse_se.tab1;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ash.cleantind_dgu_cse_se.R;

/**
 * 리싸이클러 뷰에 담을 View를 엮는 클래스
 */
public class ViewHolder extends RecyclerView.ViewHolder {
    TextView textViewID;
    TextView textViewCity;
    TextView textViewTown;
    ImageView imageView;
    public ViewHolder(View itemView) {
        super(itemView);
        textViewID = (TextView) itemView.findViewById(R.id.idTextView);
        textViewCity = (TextView) itemView.findViewById(R.id.cityTextView);
        textViewTown = (TextView) itemView.findViewById(R.id.townTextView);
        imageView = (ImageView) itemView.findViewById(R.id.imageView1);
    }
}