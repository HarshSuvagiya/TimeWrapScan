package com.example.timewrapscan.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.timewrapscan.R;

import java.io.File;

public class DisplayImageActivity extends AppCompatActivity {
    ImageView back,share,imgView;
    String path;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_image);
        getWindow().addFlags(1024);

        path=getIntent().getStringExtra("path");

        back=findViewById(R.id.back);
        share=findViewById(R.id.share);
        imgView=findViewById(R.id.img);

        Glide.with(this).load(new File(path)).into(imgView);

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                File file=new File(path);
                Uri uri= FileProvider.getUriForFile(DisplayImageActivity.this,getPackageName(),file);
                Intent intent=new Intent(Intent.ACTION_SEND);
                intent.setType("*/*");
                intent.putExtra(Intent.EXTRA_STREAM,uri);
                startActivity(Intent.createChooser(intent,"Share using"));

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }
}