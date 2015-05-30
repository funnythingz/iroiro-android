package com.funnythingz.iroiro.domain;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class IroFactory {

    protected JSONObject mJsonObject;

    public IroFactory(JSONObject jsonObject) {
        mJsonObject = jsonObject;
    }

    public ArrayList<Iro> createIroIro() throws JSONException {

        JSONArray iroiroJsonArray = mJsonObject.getJSONArray("iroiro");

        ArrayList<Iro> iroArrayList = new ArrayList<Iro>();
        int len = iroiroJsonArray.length();
        for(int i = 0; i < len; i++) {
            JSONObject iro = iroiroJsonArray.getJSONObject(i);
            int iroId = iro.getInt("id");
            JSONObject colorJsonObject = iro.getJSONObject("color");
            String colorName = colorJsonObject.getString("name");
            String colorCode = colorJsonObject.getString("code");
            String colorTextCode = colorJsonObject.getString("text_code");
            String content = iro.getString("content");

            Color color = new Color(colorName, colorCode, colorTextCode);
            iroArrayList.add(new Iro(iroId, color, content));
        }

        return iroArrayList;
    }
}
