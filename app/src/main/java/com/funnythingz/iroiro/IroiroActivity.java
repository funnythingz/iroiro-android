package com.funnythingz.iroiro;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.funnythingz.iroiro.domain.Iro;
import com.funnythingz.iroiro.domain.IroFactory;

import org.json.JSONObject;

import java.util.ArrayList;

public class IroiroActivity extends Activity {


    private RequestQueue mQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iroiro);

        final IroiroActivity selfIroIroActivity = this;

        String apiUrl = "http://iroiro.space/v1/colors";
        mQueue = Volley.newRequestQueue(this);
        mQueue.add(new JsonObjectRequest(Request.Method.GET, apiUrl,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        Log.d("yyyyyy", jsonObject.toString());

                        IroFactory iroFactory = new IroFactory(jsonObject);
                        ArrayList<Iro> iroArrayList = iroFactory.createIroIro();

                        GridView iroiroView = (GridView)findViewById(R.id.iroiroView);
                        iroiroView.setAdapter(new IroAdapter(selfIroIroActivity));
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.d("VolleyError", volleyError.getMessage());
                    }
                }
        ));
    }
}