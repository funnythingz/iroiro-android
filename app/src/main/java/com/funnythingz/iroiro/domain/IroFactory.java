package com.funnythingz.iroiro.domain;

import org.json.JSONObject;

import java.util.ArrayList;

public class IroFactory {

    protected JSONObject jsonObject;

    public IroFactory(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    public ArrayList<Iro> createIroIro() {
        //TODO
        ArrayList<Iro> iroArrayList = new ArrayList<Iro>();
        iroArrayList.add(new Iro(1, new Color("red", "#ff0000"), "hello world"));
        return iroArrayList;
    }
}
