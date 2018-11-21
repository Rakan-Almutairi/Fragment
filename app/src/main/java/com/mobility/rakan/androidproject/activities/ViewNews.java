package com.mobility.rakan.androidproject.activities;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

import com.mobility.rakan.androidproject.R;
import com.mobility.rakan.androidproject.models.Constants;

public class ViewNews extends AppCompatActivity {
    WebView newsView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        newsView = findViewById(R.id.wv_news);
        Bundle b = getIntent().getExtras();
        String Link;
        Link = b.getString(Constants.Link);
        newsView.loadUrl(Link);
    }
}
