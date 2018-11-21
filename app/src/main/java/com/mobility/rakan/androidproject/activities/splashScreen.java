package com.mobility.rakan.androidproject.activities;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.mobility.rakan.androidproject.R;
import com.mobility.rakan.androidproject.models.Constants;

import static com.mobility.rakan.androidproject.models.Constants.login;

public class splashScreen extends AppCompatActivity {
    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            boolean check = false;
            String word = retrieveData(login);
            if (word != "")
                check = (Boolean.valueOf(word));

            if (check == false) {
                Intent intent = new Intent(getApplicationContext(), LogingActivity.class);
                startActivity(intent);
                finish();
            } else if (check == true) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        handler.postDelayed(runnable, 2000);

    }

    public String retrieveData(String key) {
        SharedPreferences sharedPrefs;
        sharedPrefs = getSharedPreferences(Constants.Mypref, Context.MODE_PRIVATE);

        String word = sharedPrefs.getString(key, "");

        return word;

    }

}

