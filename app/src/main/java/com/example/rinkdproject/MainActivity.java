package com.example.rinkdproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    private ImageView imageView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView1 = (ImageView) findViewById(R.id.imageView);

        imageView1.setImageResource(R.drawable.logo);
        Handler timer = new Handler();
        timer.postDelayed(new Runnable(){
            public void run(){
                Intent intent = new Intent(MainActivity.this,RandomActivity.class);

                startActivity(intent);
                finish();
            }

        },3000);


    }

}
