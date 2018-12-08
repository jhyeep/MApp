package com.example.junhan.mapp;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class HeaderViewHolder extends RecyclerView.ViewHolder{
    public TextView headerTitle;
    public HeaderViewHolder(View itemView) {
        super(itemView);
        headerTitle = itemView.findViewById(R.id.eventsHeader);
    }
}