package com.example.timewrapscan.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.viewpager.widget.ViewPager;


import com.example.timewrapscan.R;
import com.example.timewrapscan.adapter.ImageAdapter;
import com.example.timewrapscan.adapter.ImageDisplayAdapter;

import java.io.File;

public class MyCreDisplayActivity extends AppCompatActivity {
    TextView txtCount;
    ViewPager viewPager;
    ImageDisplayAdapter adapter;
    ImageView share,delete,back;
    int pos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cre_display);
        getWindow().addFlags(1024);
        txtCount = findViewById(R.id.tv_count);
        viewPager = findViewById(R.id.viewpager);
        share = findViewById(R.id.share);
        delete = findViewById(R.id.delete);
        back = findViewById(R.id.back);
        pos = getIntent().getIntExtra("position", 0);
        int s=pos+1;
        txtCount.setText(s + "/" + ImageAdapter.iList.size());
        adapter = new ImageDisplayAdapter(this, ImageAdapter.iList);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(pos);
        Utl.SetUILinear(this,share,54,70);
        Utl.SetUILinear(this,delete,54,70);
        Utl.SetUILinearVivo(this,back,69,69);
        Utl.SetUILinearVivo(this,findViewById(R.id.topbar),1080,150);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Log.i("TAG", "DeleteImage: position=====V==="+pos+"pos===="+position);
                pos = position;
                int ps=position+1;
                txtCount.setText(ps + "/" + ImageAdapter.iList.size());


            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = FileProvider.getUriForFile(getApplicationContext(), "com.example.timewrapscan", new File(ImageAdapter.iList.get(pos)));
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_STREAM, uri);
                intent.setType("image/*");
                startActivity(Intent.createChooser(intent, "share image"));
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogDelete();
            }
        });
    }
    public void dialogDelete() {
        final Dialog delete = new Dialog(MyCreDisplayActivity.this);
        delete.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        delete.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        delete.setContentView(R.layout.custom_delete_dailog);
        final LinearLayout linerMainDeleteDialog = delete.findViewById(R.id.linerMainDeleteDialog);
        ImageView imageViewCancel = delete.findViewById(R.id.imageViewCancel);
        ImageView imageViewDelete = delete.findViewById(R.id.imageViewDelete);

        Utl.SetUILinear(MyCreDisplayActivity.this,linerMainDeleteDialog,980,788);
        Utl.SetUILinear(MyCreDisplayActivity.this,imageViewCancel,228,100);
        Utl.SetUILinear(MyCreDisplayActivity.this,imageViewDelete,228,100);
        delete.show();
        imageViewCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete.dismiss();
            }
        });
        imageViewDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeleteImage();
                delete.dismiss();


            }
        });

    }
    private void DeleteImage() {

        Log.i("TAG", "DeleteImage: position========"+pos);
        File file1 = new File(ImageAdapter.iList.get(pos));
        if (ImageAdapter.iList.size() == 1) {
            file1.delete();
            onBackPressed();
        } else {
            if (file1.exists()) {
                if (file1.delete()) {  //run
                    int p = (viewPager.getCurrentItem());
                    ImageAdapter.iList.remove(pos);
                    viewPager.getAdapter().notifyDataSetChanged();
                    adapter = new ImageDisplayAdapter(this, ImageAdapter.iList);
                    viewPager.setAdapter(adapter);
                    viewPager.setCurrentItem(p);
                    adapter.notifyDataSetChanged();
                    txtCount.setText(pos+1 + "/" + ImageAdapter.iList.size());
                } else Toast.makeText(this, "failed to delete", Toast.LENGTH_SHORT).show();
            }
        }
    }
}