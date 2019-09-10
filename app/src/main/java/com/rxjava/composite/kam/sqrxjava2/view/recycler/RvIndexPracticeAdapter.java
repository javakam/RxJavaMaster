package com.rxjava.composite.kam.sqrxjava2.view.recycler;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import kam.sqrxjava2.R;


/**
 * Created by javakam on 2018/5/27.
 */
public class RvIndexPracticeAdapter extends RecyclerView.Adapter<RvIndexPracticeAdapter.MyHolder> {
    public Context mContext;
    public List<?> mData;

    public RvIndexPracticeAdapter(Context context, List<?> data) {
        this.mContext = context;
        this.mData = data;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyHolder(LayoutInflater.from(mContext).inflate(R.layout.item_rv_index_practice, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        holder.tv.setText(String.valueOf(mData.get(position)));
        holder.tv.setOnClickListener(v -> {
            try {
                //eg: AndroidManifest.xml-> kam.sqrxjava2.practice.Practice1Activity
                Class cls = Class.forName("kam.sqrxjava2.practice." + "Practice"
                        + String.valueOf(position + 1) + "Activity");
//                Class cls = Class.forName("kam.sqrxjava2.practice.Practice1Activity" );
                mContext.startActivity(new Intent(mContext, cls));
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        private TextView tv;

        public MyHolder(View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.tv_item_rv_index_practice);
        }
    }
}
