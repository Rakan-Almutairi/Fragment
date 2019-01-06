package com.mobility.rakan.androidproject.Frafment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.mobility.rakan.androidproject.R;
import com.mobility.rakan.androidproject.models.Constants;

public class FragmentWebView extends Fragment {

    View rootView;

    WebView newsView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.activity_web_view, container, false);
        newsView = rootView.findViewById(R.id.wv_news);
        Bundle b = getArguments();
        String Link = b.getString(Constants.Link);
        WebSettings webSettings = newsView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        newsView.loadUrl(Link);
        newsView.setWebViewClient(new WebViewClient());
        return rootView;
    }
}