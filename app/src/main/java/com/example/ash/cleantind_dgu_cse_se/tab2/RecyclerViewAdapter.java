package com.example.ash.cleantind_dgu_cse_se.tab2;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.RequestManager;
import com.example.ash.cleantind_dgu_cse_se.R;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by ash on 2016-11-06.
 * recycler adapter 리싸이클러 뷰에 클래스를 적용하기 위함
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<ViewHolder>{
    ArrayList<HostData> totalDatas;
    ArrayList<HostData> arSrc;
    View.OnClickListener clickEvent;
    RequestManager mRequestManager;
    public RecyclerViewAdapter(ArrayList<HostData> itemDatas, View.OnClickListener clickEvent, RequestManager requestManager) {
        arSrc = itemDatas;
        this.totalDatas = new ArrayList<HostData>();
        totalDatas.addAll(arSrc);
        this.clickEvent = clickEvent;
        this.mRequestManager = requestManager;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_host_layout, parent,false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        itemView.setOnClickListener(clickEvent);
        return viewHolder;
    }
    public void setAdaper(ArrayList<HostData> itemDatas){
        arSrc = itemDatas;

        this.totalDatas = new ArrayList<HostData>();
        this.totalDatas.addAll(arSrc);

        notifyDataSetChanged();
    }

    //이미지를 서버에서 받아와 출력 id,interest 출력
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
       // mRequestManager.load(itemDatas.get(position).getImag_URl()).into(holder.imageView); //glide적용
        holder.textViewID.setText(arSrc.get(position).getID());
        holder.textViewCity.setText(arSrc.get(position).getCity());
        holder.textViewTown.setText(arSrc.get(position).getTown());
    }

    @Override
    public int getItemCount() {
        return (arSrc != null) ? arSrc.size() : 0;
    }

    // Filter Class
    public void filter(String charText) {
        /**
         * 검색창에 입력한 데이터를 가져오는 것이죠
         * 밑에 toLowerCase()의 경우에는 소문자로 바꿔주는 함수죠
         * 대소문자 구분없이 검색하도록 넣어준 것입니다
         * 만약 대소문자 구분하여 검색하려면 toLowerCase()부분을 제거해주시면 됩니다!
         */
        charText = charText.toLowerCase(Locale.getDefault());

        //먼저 arSrc객체를 비워줍니다.
        arSrc.clear();


        //입력한 데이터가 없을 경우에는 모든 데이터항목을 출력해줍니다.
        if (charText.length() == 0) {
            arSrc.addAll(totalDatas);
        }
        //입력한 데이터가 있을 경우에는 일치하는 항목들만 찾아 출력해줍니다.
        else
        {

            for (int i = 0; i < totalDatas.size() ; i++)
            {
                String wp = totalDatas.get(i).getID();

                if (wp.toLowerCase(Locale.getDefault()).contains(charText))
                {
                    arSrc.add(totalDatas.get(i));

                }
            }
        }

        notifyDataSetChanged();
    }


}