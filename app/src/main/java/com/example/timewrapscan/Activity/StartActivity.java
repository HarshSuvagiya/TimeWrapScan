package com.example.timewrapscan.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.timewrapscan.R;

public class StartActivity extends AppCompatActivity {
    Button btnStart,btnCreation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        getWindow().addFlags(1024);

        btnStart=findViewById(R.id.start);
        btnCreation=findViewById(R.id.myCreation);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(StartActivity.this,MainActivity.class));
            }
        });
        btnCreation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(StartActivity.this,MyCreationActivity.class));
            }
        });

    }
}