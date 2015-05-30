package com.funnythingz.iroiro;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class IroiroActivity extends Activity {

    private final IroiroActivity self = this;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    private RequestQueue mQueue;
    private String mApiUrl = "http://iroiro.space/v1/iroiro";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iroiro);

        // SwipeRefreshLayoutの設定
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh);
        mSwipeRefreshLayout.setOnRefreshListener(mOnRefreshListener);

        mQueue = Volley.newRequestQueue(this);
        mQueue.add(new JsonObjectRequest(Request.Method.GET, mApiUrl,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        IroFactory iroFactory = new IroFactory(jsonObject);
                        ArrayList<Iro> iroArrayList = null;
                        try {
                            iroArrayList = iroFactory.createIroIro();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        GridView iroiroView = (GridView)findViewById(R.id.iroiroView);
                        iroiroView.setAdapter(new IroAdapter(self, iroArrayList));
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

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    private SwipeRefreshLayout.OnRefreshListener mOnRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            // 3秒待機
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    self.onRestart();
                    Log.d("refresh", "yeah!");

                    mSwipeRefreshLayout.setRefreshing(false);
                    Log.d("run", "yeah!");
                }
            }, 3000);
        }
    };
}