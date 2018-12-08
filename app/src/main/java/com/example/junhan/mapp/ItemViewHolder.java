package com.example.junhan.mapp;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class ItemViewHolder extends RecyclerView.ViewHolder{
    public TextView mTextView1;
    public TextView mTextView2;
    public TextView mTextViewAttend;

    public ItemViewHolder(View itemView) {
        super(itemView);
        mTextView1= itemView.findViewById(R.id.textView);
        mTextView2 = itemView.findViewById(R.id.textView2);
        mTextViewAttend = itemView.findViewById(R.id.TextAttend);
    }
}
