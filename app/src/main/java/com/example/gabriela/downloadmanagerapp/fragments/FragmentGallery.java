package com.example.gabriela.downloadmanagerapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.gabriela.downloadmanagerapp.MainActivity;
import com.example.gabriela.downloadmanagerapp.R;
import com.example.gabriela.downloadmanagerapp.activities.DownloadActivity;
import com.example.gabriela.downloadmanagerapp.utils.ImageAdapter;
import com.example.gabriela.downloadmanagerapp.utils.ImagesCarousel;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentGallery extends DialogFragment{

    View view;
    @BindView(R.id.myImageList) RecyclerView myImageList;
    @BindView(R.id.bigImage) ImageView bigImage;
    @BindView(R.id.fabAdd) FloatingActionButton fabAdd;
    private ArrayList<ImagesCarousel> listImage;
    private ImageAdapter adapter;

    private ImageAdapter.OnClickRecycle listener;

    public FragmentGallery() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_fragment_gallery, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        this.myImageList.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL, false));
        this.listImage = new ImagesCarousel().listImage();
        //this.bigImage.setImageResource(R.drawable.portada1);
        this.adapter = new ImageAdapter(listImage, new ImageAdapter.OnClickRecycle(){

            @Override
            public void OnClickItemRecycle(ImagesCarousel imagesCarousel) {
                Glide.with(getContext()).load(imagesCarousel.getIdImage()).into(bigImage);
            }
        });
        myImageList.setAdapter(adapter);

        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DownloadActivity.class);
                startActivity(intent);
            }
        });
    }
}
