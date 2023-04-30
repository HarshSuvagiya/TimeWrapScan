package com.example.timewrapscan.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.timewrapscan.R;

import java.io.File;
import java.util.concurrent.TimeUnit;

public class DisplayVideoActivity extends AppCompatActivity {
    String path;

    ImageView play,back,share,delete;
    VideoView videoView;
    SeekBar seekBar;
    File vFile;
    Boolean state;
    TextView endTime,startTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_video);
        getWindow().addFlags(1024);

        path=getIntent().getStringExtra("path");
        state=getIntent().getBooleanExtra("state",true);
        Log.i("TAG", "onCreate: ===="+new File(path).toString());
       // vFile=new File(path);

        share=findViewById(R.id.share);
        delete=findViewById(R.id.delete);

        if(state){
            delete.setVisibility(View.VISIBLE);
        }else {
            delete.setVisibility(View.GONE);
        }


        play=findViewById(R.id.play);
        seekBar=findViewById(R.id.seek);
        back=findViewById(R.id.back);
        videoView=findViewById(R.id.video);
        endTime=findViewById(R.id.end);
        startTime=findViewById(R.id.start);


        Utl.SetUILinear(this,findViewById(R.id.bottom_box),1041,177);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                File file=new File(path);
                Uri uri= FileProvider.getUriForFile(DisplayVideoActivity.this,getPackageName(), new File(path));
                Intent intent=new Intent(Intent.ACTION_SEND);
                intent.setType("*/*");
                intent.putExtra(Intent.EXTRA_STREAM,uri);
                startActivity(Intent.createChooser(intent,"Share using"));
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteV();
            }
        });
        videoView.setVideoPath(path);
        videoView.requestFocus();
        videoView.start();

        endTime.setText(getVideoDuration(path));
        final Handler handler = new Handler();
        final int delay = 1000;

        handler.postDelayed(new Runnable() {
            public void run() {

                seekBar.setMax(videoView.getDuration());
                seekBar.setProgress(videoView.getCurrentPosition());
                handler.postDelayed(this, delay);

            }
        }, delay);
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                play.setImageResource(R.drawable.play_unpress);

            }
        });

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (videoView.isPlaying()) {
                    videoView.pause();
                    play.setImageResource(R.drawable.play_unpress);
                } else {
                    videoView.start();
                    play.setImageResource(R.drawable.pause_unpress);
                }
            }
        });
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                startTime.setText(getDuration(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                videoView.seekTo(seekBar.getProgress());
            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (videoView.isPlaying()) {
            videoView.pause();
        }
    }
    public void deleteV() {
        final Dialog delete = new Dialog(DisplayVideoActivity.this);
        delete.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        delete.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        delete.setContentView(R.layout.custom_delete_dailog);
        final LinearLayout linerMainDeleteDialog = delete.findViewById(R.id.linerMainDeleteDialog);
        ImageView imageViewCancel = delete.findViewById(R.id.imageViewCancel);
        ImageView imageViewDelete = delete.findViewById(R.id.imageViewDelete);

        Utl.SetUILinear(DisplayVideoActivity.this, linerMainDeleteDialog, 980, 788);
        Utl.SetUILinear(DisplayVideoActivity.this, imageViewCancel, 228, 100);
        Utl.SetUILinear(DisplayVideoActivity.this, imageViewDelete, 228, 100);
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
                DeleteVideo();
                delete.dismiss();
                onBackPressed();
            }
        });
    }

    private void DeleteVideo() {
        File file1 = new File(path);
        if (file1.exists()) {
            file1.delete();
        }
    }

    public static String getVideoDuration(String path) {
        String duration = "";
        MediaPlayer player = new MediaPlayer();
        try {
            player.setDataSource(path);
            player.prepare();
            duration = getDuration(player.getDuration());
            player.reset();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return duration;
    }
    @SuppressLint("DefaultLocale")
    public static String getDuration(long val) {
        return String.format(
                "%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(val),
                TimeUnit.MILLISECONDS.toSeconds(val)
                        - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS
                        .toMinutes(val)));
    }



}