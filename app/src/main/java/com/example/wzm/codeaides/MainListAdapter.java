package com.example.wzm.codeaides;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by wzm on 2016/4/14.
 */
public class MainListAdapter extends BaseAdapter {
    private ArrayList<String> ls = new ArrayList<>();

    public MainListAdapter(ArrayList ls) {
        this.ls = ls;
    }

    @Override
    public int getCount() {
        return ls.size();
    }

    @Override
    public Object getItem(int position) {
        return ls.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main_list, null);
            holder = new Holder();
            holder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
        } else {
            holder = (Holder) convertView.getTag();
        }
        holder.tv_title.setText(ls.get(position));
        return convertView;
    }

    class Holder {
        TextView tv_title;
    }
}
