package com.example.junhan.mapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

// this class is useless
public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.ExampleViewHolder> {

    Context mContext;
    ArrayList<EventsItem> mExampleList;
    private onItemCLickListener mListener;

    public static class ExampleViewHolder extends RecyclerView.ViewHolder{
        public TextView mTextView1;
        public TextView mTextView2;

        public ExampleViewHolder(@NonNull View itemView, final onItemCLickListener listener) {
            super(itemView);
            mTextView1= itemView.findViewById(R.id.textView);
            mTextView2 = itemView.findViewById(R.id.textView2);
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
        }
    }

    public EventsAdapter(Context context, ArrayList<EventsItem> exampleList){
        this.mExampleList = exampleList;
        this.mContext = context;
    }

    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.event_item, parent, false);
        ExampleViewHolder evh = new ExampleViewHolder(v, mListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull ExampleViewHolder holder, int position) {
        EventsItem currentItem = mExampleList.get(position);

        DateFormat convDate = new SimpleDateFormat("dd/MM/yyyy");
        DateFormat convTime = new SimpleDateFormat("HH:mm");

        holder.mTextView1.setText(currentItem.getName() + " on " + convDate.format(currentItem.getDateStart()));
        holder.mTextView2.setText(convTime.format(currentItem.getDateStart()) + " - " + convTime.format(currentItem.getDateEnd()));
    }

    @Override
    public int getItemCount() {
        return mExampleList.size();
    }

    public interface onItemCLickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(onItemCLickListener listener) {
        mListener = listener;
    }

}

