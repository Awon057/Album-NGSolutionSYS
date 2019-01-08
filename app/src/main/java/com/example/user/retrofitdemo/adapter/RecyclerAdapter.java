package com.example.user.retrofitdemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.user.retrofitdemo.R;
import com.example.user.retrofitdemo.model.Photos;
import com.example.user.retrofitdemo.model.photo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 8/2/2017.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {

    private List<photo> photos;
    public OnclickListener mListener;

    public RecyclerAdapter(OnclickListener mListener) {
        this.mListener = mListener;
    }

    private List<Photos> mData = new ArrayList<>();

    private Context context;



    public void setPhotos(List<photo> photos) {
        this.photos = photos;
        notifyDataSetChanged();
    }

//    public void setMyAdapterListener(Onclick myAdapterListener){
//        mListener = myAdapterListener;
//    }

    public void updateData(List<Photos> dataset){
        mData.clear();
        mData.addAll(dataset);
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item, parent, false);
        return new MyViewHolder(view,mListener);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.albumName.setText("Album name : "+photos.get(position).getAlbumName());
        holder.user.setText("User : "+photos.get(position).getUserName());
    }


    @Override
    public int getItemCount() {
        if (photos != null)
            return photos.size();
        else
            return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView albumName, user;


        public MyViewHolder(View itemView,OnclickListener listener) {
            super(itemView);
            albumName = (TextView) itemView.findViewById(R.id.albumname);
            user = (TextView) itemView.findViewById(R.id.user);
            //mListener= listener;
            itemView.setOnClickListener(this);
        }
        public void onClick(View v){
            mListener.onItemClick(v,getAdapterPosition());

        }
    }
    public interface OnclickListener {
        void onItemClick(View view,int position);
    }
}
