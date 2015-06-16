package com.funnythingz.iroiro;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.funnythingz.iroiro.domain.Color;
import com.funnythingz.iroiro.domain.ColorsFactory;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class NewIroActivity extends AppCompatActivity {

    private final NewIroActivity self = this;

    private RequestQueue mQueue;
    private String mApiUrl = "http://iroiro.space/v1/colors";
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_iro);

        setToolbar();

        mQueue = Volley.newRequestQueue(this);
        resolveSelectColors();
    }

    private void setToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.NewIroToolbar);
        mToolbar.setTitle("New Iro");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void resolveSelectColors() {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, mApiUrl,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        ColorsFactory colorsFactory = new ColorsFactory(jsonObject);
                        ArrayList<Color> colorsArrayList = null;
                        try {
                            colorsArrayList = colorsFactory.createColors();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        RelativeLayout newIroLayout = (RelativeLayout)findViewById(R.id.newIroLayout);
                        newIroLayout.setBackgroundColor(android.graphics.Color.parseColor(colorsArrayList.get(0).code));

                        //TODO: カラーリストを表示
                        GridView colorsView = (GridView)findViewById(R.id.colorsView);
                        ViewGroup.LayoutParams layoutParams = colorsView.getLayoutParams();
                        final float scale = getResources().getDisplayMetrics().density; // dpで入力したいので変換用scaleをセット
                        layoutParams.width = (int) ((colorsArrayList.size() * 48) * scale); // px * scale = dp
                        Log.d("width", "" + layoutParams.width);
                        colorsView.setLayoutParams(layoutParams);
                        colorsView.setAdapter(new ColorAdapter(self, colorsArrayList, newIroLayout));
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
}
