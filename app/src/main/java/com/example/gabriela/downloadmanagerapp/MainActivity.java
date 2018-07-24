package com.example.gabriela.downloadmanagerapp;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import com.example.gabriela.downloadmanagerapp.fragments.FragmentGallery;
import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;

public class MainActivity extends FragmentActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_main);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentGallery gallery = new FragmentGallery();
        gallery.setStyle(DialogFragment.STYLE_NO_FRAME, R.style.transparent);
        gallery.show(fragmentManager, "");
    }
}
