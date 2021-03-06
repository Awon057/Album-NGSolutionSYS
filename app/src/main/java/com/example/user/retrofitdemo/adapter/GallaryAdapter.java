package com.example.user.retrofitdemo.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.user.retrofitdemo.R;
import com.example.user.retrofitdemo.activity.DetailImage;

import java.util.ArrayList;

/**
 * Created by User on 8/5/2017.
 */

public class GallaryAdapter extends RecyclerView.Adapter<GallaryAdapter.MyViewHolder> {
    Context context;
    ArrayList<String> links;

    public GallaryAdapter(Context context) {
        this.context = context;
    }

    public void setLinks(ArrayList<String> links) {
        this.links = links;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Glide.with(context)
                .load(links.get(position))
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.mImageView);
    }

    @Override
    public int getItemCount() {
        if(links != null)
            return links.size();
        else
            return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public ImageView mImageView;

        public MyViewHolder(View itemView) {
            super(itemView);
            mImageView =(ImageView) itemView.findViewById(R.id.imageView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION){
                        Intent ii = new Intent(context,DetailImage.class);
                        ActivityOptionsCompat options = ActivityOptionsCompat.
                                makeSceneTransitionAnimation((Activity) context, (View)mImageView, "image");
                        ii.putExtra("URL",links.get(position).toString());
                        context.startActivity(ii, options.toBundle());
                    }
                }
            });
        }

        @Override
        public void onClick(View v) {

        }
    }
}