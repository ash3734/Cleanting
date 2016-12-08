package com.example.ash.cleantind_dgu_cse_se.tab2;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ash.cleantind_dgu_cse_se.R;

/**
 * Created by ash on 2016-11-28.
 */

public class ViewHolder extends RecyclerView.ViewHolder{

    TextView textViewID;
    TextView textViewCity;
    TextView textViewTown;
    ImageView imageView;
    public ViewHolder(View itemView) {
        super(itemView);
        textViewID = (TextView) itemView.findViewById(R.id.hostListIDText);
        textViewCity = (TextView) itemView.findViewById(R.id.hostListCityText);
        textViewTown = (TextView) itemView.findViewById(R.id.hostListTownText);
        imageView = (ImageView) itemView.findViewById(R.id.hostListImage);
    }
}
