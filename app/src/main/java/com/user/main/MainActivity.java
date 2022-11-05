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
        this.companyLogo.animate().translationX(1000).setDuration(2000).setStartDelay(2500);

        Thread thread = new Thread(){
            public void run(){
                try{
                    Thread.sleep(4000);
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