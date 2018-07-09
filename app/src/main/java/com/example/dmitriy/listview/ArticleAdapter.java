package com.example.dmitriy.listview;

import android.app.LauncherActivity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewManager;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.LinkedList;

public class ArticleAdapter extends ArrayAdapter {
    ArrayList<Article> articles;
    int count=0;
    LayoutInflater mInflater;
    public ArticleAdapter(@NonNull Context context, int resource, ArrayList<Article> atricles,LayoutInflater inflater) {
        super(context, resource);
        this.articles= atricles;
        mInflater=inflater;
    }
    @Override
    public View getView(int position,View convertView,ViewGroup parent) {

        if(convertView==null) {
            convertView = mInflater.inflate(R.layout.article_layout,null,false);

            ((TextView) convertView.findViewById(R.id.textViewHeader)).setText(articles.get(position).headder);
            ((TextView) convertView.findViewById(R.id.textViewDescription)).setText(articles.get(position).descriprion);

        }
        else{
            ((TextView) convertView.findViewById(R.id.textViewHeader)).setText(articles.get(position).headder);
            ((TextView) convertView.findViewById(R.id.textViewDescription)).setText(articles.get(position).descriprion);
        }
        return (convertView);
    }

    @Override
    public int getCount() {

        return articles.size();
    }
}
