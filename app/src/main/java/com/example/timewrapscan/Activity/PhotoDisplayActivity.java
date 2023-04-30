package com.example.timewrapscan.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.timewrapscan.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class PhotoDisplayActivity extends AppCompatActivity {
    ImageView img,back;
    Button btnSave;
    String str;
    Bitmap bmp;
    String filepath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_display);
        img = findViewById(R.id.img);
        btnSave = findViewById(R.id.save);
        back = findViewById(R.id.back);

       /* byte[] byteArray = getIntent().getByteArrayExtra("image");
        bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length)*/;

        Utl.SetUILinear(this,img,1000,1500);
        img.setImageBitmap(MainActivity.resultBitmap1);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveBitmapInGalary(MainActivity.resultBitmap1);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }
    private void saveBitmapInGalary(Bitmap bitmap) {
        File f;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            f = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString() + File.separator + getResources().getString(R.string.app_name) + File.separator+"Images"+File.separator);
        } else {
            f = new File(Environment.getExternalStorageDirectory().toString() + File.separator +getResources().getString(R.string.app_name)+ File.separator+"Images"+File.separator);
        }


        if(!f.exists()){
            f.mkdirs();
        }
        String s=f.getPath();

        String str = s +"/"+ System.currentTimeMillis() + ".jpeg";
        File file = new File(str);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(str);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Toast.makeText(getApplicationContext(), "Save Successful...", Toast.LENGTH_SHORT).show();
        Intent i=new Intent(PhotoDisplayActivity.this,DisplayImageActivity.class);
        i.putExtra("path",file.getPath());
        startActivity(i);
        finish();

    }
}