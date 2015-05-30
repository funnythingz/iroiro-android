package com.funnythingz.iroiro;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.funnythingz.iroiro.domain.Iro;
import com.funnythingz.iroiro.domain.IroFactory;
import com.makeramen.roundedimageview.RoundedImageView;

import org.json.JSONObject;

import java.util.ArrayList;

public class IroAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private ArrayList<Iro> mIroArrayList;

    private static class ViewHolder {
        public RoundedImageView iroRoundedImageView;
        public TextView iroTextView;
    }

    public IroAdapter(Context context, ArrayList<Iro> iroArrayList) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        mIroArrayList = iroArrayList;
    }

    @Override
    public int getCount() {
        return mIroArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return mIroArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = new ViewHolder();

        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.iro_item, null);
            holder.iroRoundedImageView = (RoundedImageView)convertView.findViewById(R.id.iroRoundedImageView);
            holder.iroTextView = (TextView)convertView.findViewById(R.id.iroTextView);
            YoYo.with(Techniques.Tada).playOn(holder.iroRoundedImageView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }

        holder.iroRoundedImageView.setBackgroundColor(Color.parseColor(mIroArrayList.get(position).color.code));
        holder.iroRoundedImageView.setOnClickListener(openIroView(mIroArrayList.get(position).content, mIroArrayList.get(position).color.code));

        holder.iroTextView.setText(mIroArrayList.get(position).content);

        return convertView;
    }

    private View.OnClickListener openIroView(final String text,
                                             final String colorCode) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                YoYo.with(Techniques.Pulse).duration(100).playOn(v);
                Intent intent = new Intent(mContext, IroActivity.class);
                intent.putExtra("text", text);
                intent.putExtra("colorCode", colorCode);
                mContext.startActivity(intent);
            }
        };
    }
}