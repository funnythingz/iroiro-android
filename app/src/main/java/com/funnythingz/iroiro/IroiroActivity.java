package com.funnythingz.iroiro;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;

import com.android.volley.DefaultRetryPolicy;
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

import java.net.URI;
import java.util.ArrayList;

public class IroiroActivity extends Activity {

    private final IroiroActivity self = this;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    private RequestQueue mQueue;
    private String mApiUrl = "http://iroiro.space/v1/iroiro";

    private Button mNewIroButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iroiro);

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh);
        mSwipeRefreshLayout.setOnRefreshListener(mOnRefreshListener);

        mNewIroButton = (Button) findViewById(R.id.new_iro_button);

        mNewIroButton.setOnClickListener(new View.OnClickListener() {
            Intent newIroIntent = new Intent(getApplicationContext(), NewIroActivity.class);
            @Override
            public void onClick(View v) {
                startActivity(newIroIntent);
            }
        });

        mQueue = Volley.newRequestQueue(this);
        resolveIroIro();
    }

    private void resolveIroIro() {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, mApiUrl,
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
        );

        // リトライポリシーの設定
        request.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));

        mQueue.add(request);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        resolveIroIro();
    }

    private SwipeRefreshLayout.OnRefreshListener mOnRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mSwipeRefreshLayout.setRefreshing(false);
                    self.onRestart();
                }
            }, 1000);
        }
    };
}