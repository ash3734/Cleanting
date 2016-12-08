package com.example.ash.cleantind_dgu_cse_se.tab1.review;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ash.cleantind_dgu_cse_se.R;

import java.util.ArrayList;

/**
 * Created by ash on 2016-12-06.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<ViewHolder> {
    ArrayList<ReviewData> itemDatas;

    //생성자, 아이템 리스트
    public RecyclerViewAdapter(ArrayList<ReviewData> itemDatas) {
        this.itemDatas = itemDatas;
    }

    //요청 layout 연결
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_cleaner_review_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    //리스트 역기
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.textViewID.setText(itemDatas.get(position).getID());
        holder.textViewReview.setText(itemDatas.get(position).getReview());
    }

    @Override
    public int getItemCount() {
        return (itemDatas != null) ? itemDatas.size() : 0;
    }
}