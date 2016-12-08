package com.example.ash.cleantind_dgu_cse_se.tab3;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ash.cleantind_dgu_cse_se.R;

import java.util.ArrayList;

/**
 * 매칭 요청한 리스트 어뎁터
 */

public class RecyclerViewAdapterRequest extends RecyclerView.Adapter<ViewHolderRequest> {
    ArrayList<RequestData> itemDatas;

    //생성자, 아이템 리스트
    public RecyclerViewAdapterRequest(ArrayList<RequestData> itemDatas) {
        this.itemDatas = itemDatas;
    }

    //요청 layout 연결
    @Override
    public ViewHolderRequest onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_request_layout, parent, false);
        ViewHolderRequest viewHolder = new ViewHolderRequest(itemView);
        return viewHolder;
    }

    //리스트 역기
    @Override
    public void onBindViewHolder(ViewHolderRequest holder, int position) {
        holder.textViewName.setText(itemDatas.get(position).getRequestUserName());
        holder.textViewTo.setText(itemDatas.get(position).getRequestTo());
        holder.textViewState.setText(itemDatas.get(position).getRequestState());
    }

    @Override
    public int getItemCount() {
        return (itemDatas != null) ? itemDatas.size() : 0;
    }
}