package com.funnythingz.iroiro.domain;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ColorsFactory {

    protected JSONObject mJsonObject;

    public ColorsFactory(JSONObject jsonObject) {
        mJsonObject = jsonObject;
    }

    public ArrayList<Color> createColors() throws JSONException {

        JSONArray colorsJsonArray = mJsonObject.getJSONArray("color_list");

        ArrayList<Color> colorsArrayList = new ArrayList<Color>();
        int len = colorsJsonArray.length();
        for(int i = 0; i < len; i++) {
            JSONObject color = colorsJsonArray.getJSONObject(i);
            int colorId = color.getInt("id");
            String colorName = color.getString("name");
            String colorCode = color.getString("code");
            String colorTextCode = color.getString("text_code");

            //colorsArrayList.add(new Color(colorName, colorCode, colorTextCode));
        }

        return colorsArrayList;
    }
}
