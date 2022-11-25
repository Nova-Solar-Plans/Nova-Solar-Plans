package com.user.main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    private ImageView companyLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.companyLogo = findViewById(R.id.main_logo);
        this.companyLogo.animate().translationY(1000).setDuration(4000).setStartDelay(5);

        Thread thread = new Thread(){
            public void run(){
                try{
                    Thread.sleep(4100);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
                finally {
                    Intent intent = new Intent(MainActivity.this,UserNavigation.class);
                    startActivity(intent);
                    finish();
                }
            }
        };
        thread.start();


    }
}