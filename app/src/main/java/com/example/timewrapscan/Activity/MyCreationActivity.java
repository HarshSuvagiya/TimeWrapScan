package com.example.timewrapscan.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.timewrapscan.R;
import com.example.timewrapscan.adapter.MyPagerAdapter;


public class MyCreationActivity extends AppCompatActivity {

    ViewPager viewPager;
    Button btnImg, btnVideo;
    MyPagerAdapter adapter;
    ImageView back;
    public static int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_creation);
        getWindow().addFlags(1024);

        viewPager = findViewById(R.id.viewpager);
        btnImg = findViewById(R.id.image);
        btnVideo = findViewById(R.id.video);
        back = findViewById(R.id.back);

        adapter = new MyPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        if (i == 0) {
            viewPager.setCurrentItem(0);

        } else {
            viewPager.setCurrentItem(1);
        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        btnVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(1);
                i = 1;
            }
        });

        btnImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(0);
                i = 0;
            }
        });

    }
}