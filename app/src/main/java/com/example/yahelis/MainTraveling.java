package com.example.yahelis;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.SupportMenuInflater;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;


public class MainTraveling extends FragmentActivity {

    ViewPager2 viewPager;

    private boolean showProfile;
    private String showHome;

    public String getEmail() {
        return email;
    }

    private String email="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_traveling);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        showProfile =  getIntent().getBooleanExtra("showProfile",false);

        showHome = getIntent().getStringExtra("showHome");

        initViews();


    }
    private void initViews() {
        viewPager = findViewById(R.id.viewPager);
        TabLayout tabLayout = findViewById(R.id.tab_layout);

        MyPagerAdapter adapter= new MyPagerAdapter(this);


        String[] arr = {"Home","FindTrip","AddTrip","Profil"};

        int[] arrDraw = {R.drawable.home,R.drawable.search,R.drawable.add,R.drawable.user};
        viewPager.setAdapter(adapter);
        new TabLayoutMediator(tabLayout,viewPager,(tab, position) -> tab.setIcon(arrDraw[position])
        ).attach();


        if(showProfile) {
            viewPager.setCurrentItem(3);
        }

        if(showHome!=null)
            viewPager.setCurrentItem(0);




    }

}