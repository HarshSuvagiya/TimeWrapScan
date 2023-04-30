package com.example.timewrapscan.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.service.autofill.ImageTransformation;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.VideoView;

import com.example.timewrapscan.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

public class PreviewVideoActivity extends AppCompatActivity {

    VideoView videoView;
    Button btnSave;
    Uri fileURI;
    File file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview_video);
        getWindow().addFlags(1024);


        videoView = findViewById(R.id.video);
        btnSave = findViewById(R.id.save);



        // file=new File(getIntent().getStringExtra("path"));
        this.videoView.setVideoPath(MainActivity.FinalVideo.getPath());
        this.videoView.start();
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                videoView.start();
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                videoView.pause();

                File f;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    f = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString() + File.separator + getResources().getString(R.string.app_name) + File.separator + "Videos" + File.separator);
                } else {
                    f = new File(Environment.getExternalStorageDirectory().toString() + File.separator + getResources().getString(R.string.app_name) + File.separator + "Videos" + File.separator);
                }
                if (!f.exists()) {
                    f.mkdirs();
                }
                String st = f.getPath();
                File file = new File(st + "/" + System.currentTimeMillis() + ".mp4");

                try {
                    moveFile(MainActivity.FinalVideo, file);
                    fileURI = Uri.fromFile(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                File file1 = new File(String.valueOf(fileURI));
                Intent intent = new Intent(PreviewVideoActivity.this, DisplayVideoActivity.class);
                intent.putExtra("path", file.getPath());
                intent.putExtra("state", false);
                startActivity(intent);
                finish();


            }
        });
    }

    public String getVideoTemp(Context context) {
        File downloadDir = context.getFilesDir();
        File APP_DIRECTORY = new File(downloadDir, "temp");
        if (!APP_DIRECTORY.exists()) {
            APP_DIRECTORY.mkdirs();
        }
        return APP_DIRECTORY.getAbsolutePath();

    }

    public static void moveFile(File file, File file2) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(file);
        FileOutputStream fileOutputStream = new FileOutputStream(file2);
        byte[] bArr = new byte[1024];
        while (true) {
            int read = fileInputStream.read(bArr);
            if (read <= 0) {
                break;
            }
            fileOutputStream.write(bArr, 0, read);
        }
        fileInputStream.close();
        fileOutputStream.close();
        if (!file.exists()) {
            return;
        }
        if (file.delete()) {
            PrintStream printStream = System.out;
            printStream.println("file Deleted :" + file.getPath());
            return;
        }
        PrintStream printStream2 = System.out;
        printStream2.println("file not Deleted :" + file.getPath());
    }
}