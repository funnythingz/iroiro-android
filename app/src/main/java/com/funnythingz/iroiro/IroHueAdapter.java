package com.funnythingz.iroiro;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;

public class IroHueAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private String[] iroHueList = {
            "ef5350", "ec407a", "ab47bc", "7e57c2", "5c6bc0", "42a5f5",
            "29b6f6", "26c6da", "26a69a", "66bb6a", "9ccc65", "d4e157",
            "ffee58", "ffca28", "ffa726", "ff5722", "795548", "9e9e9e",
            "607d8b"
    };

    private static class ViewHolder {
        public RoundedImageView iroRoundedImageView;
        public TextView iroTextView;
    }

    public IroHueAdapter(Context context) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return iroHueList.length;
    }

    @Override
    public Object getItem(int position) {
        return iroHueList[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = new ViewHolder();

        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.iro_hue, null);
            holder.iroRoundedImageView = (RoundedImageView)convertView.findViewById(R.id.iroRoundedImageView);
            holder.iroTextView = (TextView)convertView.findViewById(R.id.iroTextView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }

        holder.iroRoundedImageView.setBackgroundColor(Color.parseColor('#' + iroHueList[position]));

        holder.iroTextView.setText(iroHueList[position]);

        return convertView;
    }
}