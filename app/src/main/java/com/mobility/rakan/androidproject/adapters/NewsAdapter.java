package com.mobility.rakan.androidproject.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mobility.rakan.androidproject.R;
import com.mobility.rakan.androidproject.models.News;

import java.util.ArrayList;

public class NewsAdapter extends BaseAdapter {
    private static ArrayList<News> news;
    public Context mContext;

    public NewsAdapter(Context context, ArrayList<News> comingNews) {
        news = comingNews;
        mContext = context;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater mInflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        convertView = mInflater.inflate(R.layout.list_item_news, null);
        TextView tvTitle = convertView.findViewById(R.id.tv_title);
        TextView tvDescription = convertView.findViewById(R.id.tv_description);
        tvTitle.setText(news.get(position).getTitle());
        tvDescription.setText(news.get(position).getDescription());

        return convertView;
    }

    //region other methods
    public int getCount() {
        return news.size();
    }

    public Object getItem(int position) {
        return news.get(position);
    }

    public long getItemId(int position) {
        return position;
    }
    //endregion
}
