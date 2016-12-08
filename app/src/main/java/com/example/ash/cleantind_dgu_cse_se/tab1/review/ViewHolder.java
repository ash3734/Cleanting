package com.example.ash.cleantind_dgu_cse_se.tab1.review;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.ash.cleantind_dgu_cse_se.R;

/**
 * Created by ash on 2016-12-06.
 */

public class ViewHolder extends RecyclerView.ViewHolder {
    TextView textViewID;
    TextView textViewReview;

    public ViewHolder(View itemView) {
        super(itemView);
        textViewID = (TextView) itemView.findViewById(R.id.cleaner_review_ID);
        textViewReview = (TextView) itemView.findViewById(R.id.clener_review);
    }
}