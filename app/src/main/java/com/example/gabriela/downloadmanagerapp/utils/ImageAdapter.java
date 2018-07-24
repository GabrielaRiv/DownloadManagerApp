package com.example.gabriela.downloadmanagerapp.utils;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.gabriela.downloadmanagerapp.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.MyViewHolder>{
    private ArrayList<ImagesCarousel> listImage;
    private OnClickRecycle listener;

    // Constructor
    public ImageAdapter(ArrayList<ImagesCarousel> listImage, OnClickRecycle listener) {
        this.listImage = listImage;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflater Recycler
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ImagesCarousel imagesCarousel = listImage.get(position);
        holder.bind(imagesCarousel, listener);
    }

    @Override
    public int getItemCount() {
        return listImage.size();
    }

    // Interface to click Recycle
    public interface OnClickRecycle{
        void OnClickItemRecycle(ImagesCarousel imagesCarousel);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.imvGallery) ImageView imvGallery;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(final ImagesCarousel imagesCarousel, final OnClickRecycle listener){
            //Glide helps to make fast the images list
            Glide.with(imvGallery.getContext()).load(imagesCarousel.getIdImage()).into(imvGallery);

            imvGallery.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.OnClickItemRecycle(imagesCarousel);
                }
            });
        }
    }
}
