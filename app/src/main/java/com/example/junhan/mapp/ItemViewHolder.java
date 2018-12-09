package com.example.junhan.mapp;

import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ItemViewHolder extends RecyclerView.ViewHolder{
    public TextView mTextView1;
    public TextView mTextView2;
    public TextView mTextViewAttend;
    public ImageView mImageView;

    public ItemViewHolder(final View itemView, final HeaderRecyclerViewSection.onItemClickListener listener) {
        super(itemView);
        mTextView1 = itemView.findViewById(R.id.textView);
        mTextView2 = itemView.findViewById(R.id.textView2);
        mTextViewAttend = itemView.findViewById(R.id.textAttend);
        mImageView = itemView.findViewById(R.id.imageView);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null){
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION){
                        listener.onItemClick(position);
                    }
                }
            }
        });

        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null){
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION){
                        listener.onCheckClick(itemView, position);
                    }
                }
            }
        });
    }
}
