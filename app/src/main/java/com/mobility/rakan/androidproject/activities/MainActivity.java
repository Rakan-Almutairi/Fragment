package com.mobility.rakan.androidproject.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.mobility.rakan.androidproject.Frafment.FragmentWebView;
import com.mobility.rakan.androidproject.R;
import com.mobility.rakan.androidproject.adapters.NewsAdapter;
import com.mobility.rakan.androidproject.models.Constants;
import com.mobility.rakan.androidproject.models.News;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import static com.mobility.rakan.androidproject.models.Constants.login;

public class MainActivity extends AppCompatActivity {

    //region views
    ListView listNews;
    TextView etUserName;
    Button logOut;
    //endregion

    //region other
    ArrayList<News> news;
    String userName;
    FragmentManager mFragmentManager;
    //endregion


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listNews = findViewById(R.id.list_news);
        logOut = findViewById(R.id.btn_logout);


        //region set user name
        userName = getUserName();
        etUserName = findViewById(R.id.tv_UserName);
        etUserName.setText(userName);
        //endregion

        //region reading and display list of news
        news = new ArrayList<>();
        news = readJsonFile(Constants.newsJson);
//        NewsAdapter mNewsAdapter = new NewsAdapter(MainActivity.this, news);
//        listNews.setAdapter(mNewsAdapter);
        mFragmentManager = getSupportFragmentManager();
        FragmentWebView mFragmentTow = new FragmentWebView();
        FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.add(R.id.frame_container2, mFragmentTow, "FragmentTow");
        Bundle b = new Bundle();
        mFragmentTransaction.commit();
        //endregion

        //log out
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                sendToLogIn();
            }
        });


        listNews.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // onclick in list with send the user to the link of the news
                mFragmentManager = getSupportFragmentManager();
                FragmentWebView mFragmentOne = new FragmentWebView();
                FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();
                mFragmentTransaction.add(R.id.frame_container, mFragmentOne, "FragmentOne");
                Bundle b = new Bundle();
                b.putString(Constants.Link, news.get(i).getLink());
                mFragmentTransaction.commit();

            }
        });
    }

    public ArrayList readJsonFile(String json_file) {

        //----------------------------------------------
        BufferedReader reader = null;
        String mLine, title, description, link;
        JSONObject obj;
        JSONArray ary;
        news = new ArrayList<>();
        //----------------------------------------------

        try {
            InputStream is = getAssets().open(json_file);
            int size = is.available();
            byte[] buffer = new byte[size];

            is.read(buffer);
            is.close();

            mLine = new String(buffer, "UTF-8");

            obj = new JSONObject(mLine);
            ary = obj.getJSONArray("news");

            for (int i = 0; i < ary.length(); i++) {
                News News = new News();
                obj = ary.getJSONObject(i);
                //----------------------------------------------
                title = obj.getString("title");
                description = obj.getString("description");
                link = obj.getString("link");
                //----------------------------------------------
                News.setTitle((i + 1) + "-" + title);
                News.setDescription(description);
                News.setLink(link);
                news.add(News);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    //log the exception
                }
            }
        }
        return news;
    }

    private String getUserName() {
        Bundle b = getIntent().getExtras();
        String userName = retrieveData(Constants.UserName);
        if (userName == "")
            return userName = b.getString(Constants.UserName);
        else return userName;
    }

    public void sendToLogIn() {
        saveWord(login, false);
        Intent myIntent = new Intent(MainActivity.this, LogingActivity.class);
        startActivity(myIntent);
        finish();
    }

    public void saveWord(String key, Boolean saveThisWord) {
        SharedPreferences sharedPrefs;
        sharedPrefs = getSharedPreferences(Constants.Mypref, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPrefs.edit();

        editor.putString(key, String.valueOf(saveThisWord));

        editor.commit();
    }

    public String retrieveData(String key) {
        SharedPreferences sharedPrefs;
        sharedPrefs = getSharedPreferences(Constants.Mypref, Context.MODE_PRIVATE);

        String word = sharedPrefs.getString(key, "");

        return word;

    }
}
