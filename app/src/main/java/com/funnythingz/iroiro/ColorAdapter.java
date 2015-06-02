package com.funnythingz.iroiro;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;

public class ColorAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private ArrayList<com.funnythingz.iroiro.domain.Color> mColorsArrayList;

    private RoundedImageView mColorSelectView;
    private RelativeLayout mNewIroLayout;

    public ColorAdapter(Context context, ArrayList<com.funnythingz.iroiro.domain.Color> colorsArrayList, RelativeLayout newIroLayout) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        mColorsArrayList = colorsArrayList;
        mNewIroLayout = newIroLayout;
    }

    @Override
    public int getCount() {
        return mColorsArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return mColorsArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = mLayoutInflater.inflate(R.layout.color_item, null);
        mColorSelectView = (RoundedImageView)convertView.findViewById(R.id.colorSelectView);
        YoYo.with(Techniques.BounceInRight)
                .duration(1000)
                .playOn(mColorSelectView);

        mColorSelectView.setBackgroundColor(Color.parseColor(mColorsArrayList.get(position).code));
        mColorSelectView.setOnClickListener(selectColor());

        return convertView;
    }

    private View.OnClickListener selectColor() {

        return new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //アニメーション
                YoYo.with(Techniques.RubberBand)
                        .duration(300)
                        .playOn(v);

                //TODO: 色を選択
                ColorDrawable selectDrawable = (ColorDrawable)v.getBackground();
                mNewIroLayout.setBackground(selectDrawable);
            }
        };
    }
}