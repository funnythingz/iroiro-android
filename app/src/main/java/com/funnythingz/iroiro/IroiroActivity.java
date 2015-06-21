package com.funnythingz.iroiro;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;

import com.android.volley.toolbox.Volley;
import com.funnythingz.iroiro.domain.Iro;
import com.funnythingz.iroiro.infrastructure.IroIroAPI;
import com.google.gson.Gson;

import java.util.ArrayList;

import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;
import rx.Observer;

import static rx.android.schedulers.AndroidSchedulers.*;
import static rx.schedulers.Schedulers.*;

public class IroiroActivity extends Activity {

    private final IroiroActivity self = this;
    private SwipeRefreshLayout mSwipeRefreshLayout;

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

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setConverter(new GsonConverter(new Gson()))
                .setEndpoint("http://iroiro.space/v1")
                .build();

        IroIroAPI iroIroAPI = restAdapter.create(IroIroAPI.class);
        iroIroAPI.getIroIro()
                .subscribeOn(newThread())
                .observeOn(mainThread())
                .subscribe(new Observer<ArrayList<Iro>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("error", e.toString());
                    }

                    @Override
                    public void onNext(ArrayList<Iro> iroArrayList) {
                        GridView iroiroView = (GridView)findViewById(R.id.iroiroView);
                        iroiroView.setAdapter(new IroAdapter(self, iroArrayList));
                    }
                });

        // /iroiro/:id
        // 単体で取得
        //iroIroAPI.getIro(1)
        //        .subscribeOn(newThread())
        //        .observeOn(mainThread())
        //        .subscribe(new Observer<Iro>() {
        //            @Override
        //            public void onCompleted() {
        //            }

        //            @Override
        //            public void onError(Throwable e) {
        //                Log.e("error", e.toString());
        //            }

        //            @Override
        //            public void onNext(Iro iro) {
        //                Log.d("iro_id: ", "" + iro.id);
        //                Log.d("iro_content: ", iro.content);
        //            }
        //        });
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