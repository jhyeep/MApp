//package com.example.junhan.mapp;
//
//import android.content.Context;
//import android.support.annotation.NonNull;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.jaychang.srv.SimpleCell;
//import com.jaychang.srv.SimpleViewHolder;
//
//import java.util.ArrayList;
//
//public class EventsCell extends SimpleCell<EventsItem,EventsCell.ViewHolder>  {
//
//    Context mContext;
//    ArrayList<EventsItem> mExampleList;
//
//    public EventsCell(@NonNull EventsItem item) {
//        super(item);
//    }
//
//    @Override
//    protected int getLayoutRes() {
//        return R.layout.event_item;
//    }
//
//    /*
//    - Return a ViewHolder instance
//     */
//    @NonNull
//    @Override
//    protected ViewHolder onCreateViewHolder(ViewGroup parent, View cellView) {
//        return new ViewHolder(cellView);
//    }
//
//    /*
//    - Bind data to widgets in our viewholder.
//     */
//    @Override
//    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Context context, Object o) {
//        EventsItem currentItem = mExampleList.get(position);
//
////        holder.mImageView.setImageResource(currentItem.getImageResource());
//        holder.mTextView1.setText(currentItem.getText1());
//        holder.mTextView2.setText(currentItem.getText2());
//    }
//    /**
//     - Our ViewHolder class.
//     - Inner static class.
//     * Define your view holder, which must extend SimpleViewHolder.
//     * */
//    static class ViewHolder extends SimpleViewHolder {
//        public TextView mTextView1;
//        public TextView mTextView2;
//
//        ViewHolder(View itemView) {
//            super(itemView);
//            mTextView1 = itemView.findViewById(R.id.textView);
//            mTextView2 = itemView.findViewById(R.id.textView2);
//        }
//    }
//}
