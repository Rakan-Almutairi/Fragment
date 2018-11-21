package com.mobility.rakan.androidproject.activities;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;

import com.mobility.rakan.androidproject.models.Constants;

public class sharedPreferences extends AppCompatActivity {
    SharedPreferences sharedPrefs;
    /*
MODE_PRIVATE: File creation mode: the default mode, where the created file can only be accessed by the calling application (or all applications sharing the same user ID).

MODE_WORLD_READABLE: File creation mode: allow all other applications to have read access to the created file.

MODE_WORLD_WRITEABLE : File creation mode: allow all other applications to have write access to the created file.*/

    public void saveWord(String key, Boolean saveThisWord) {

        sharedPrefs = getSharedPreferences(Constants.Mypref, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPrefs.edit();

        editor.putString(key, String.valueOf(saveThisWord));

        editor.commit();
    }

    public void saveWord(String key, String saveThisWord) {

        sharedPrefs = getSharedPreferences(Constants.Mypref, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPrefs.edit();

        editor.putString(key, String.valueOf(saveThisWord));

        editor.commit();
    }

    public void saveWord(String key, int saveThisWord) {

        sharedPrefs = getSharedPreferences(Constants.Mypref, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPrefs.edit();

        editor.putString(key, String.valueOf(saveThisWord));
        editor.commit();
    }


    public String retrieveData(String key) {

        sharedPrefs = getSharedPreferences(Constants.Mypref, Context.MODE_PRIVATE);

        String word = sharedPrefs.getString(key, "");

        return word;

    }
}
