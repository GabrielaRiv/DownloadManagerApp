package com.example.gabriela.downloadmanagerapp.utils;

import com.example.gabriela.downloadmanagerapp.R;

import java.util.ArrayList;

public class ImagesCarousel {

    private int idImage;

    // Constructor
    public ImagesCarousel(int idImage) {
        this.idImage = idImage;
    }

    public ImagesCarousel(){
        idImage = 0;
    }

    // Get idImage variable
    public int getIdImage() {
        return idImage;
    }

    // Images list
    public ArrayList<ImagesCarousel>listImage(){
        ImagesCarousel imagesCarousel;
        ArrayList<ImagesCarousel> list = new ArrayList<ImagesCarousel>();

        Integer[] IdImages = new Integer[]{R.drawable.portada1,R.drawable.portada2, R.drawable.portada4, R.drawable.portada5};

        for(int i=0; i<IdImages.length; ++i ){
            imagesCarousel = new ImagesCarousel(IdImages[i]);

            list.add(imagesCarousel);
        }
        return list;
    }

}
