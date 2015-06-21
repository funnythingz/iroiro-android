package com.funnythingz.iroiro;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.funnythingz.iroiro.domain.Color;
import com.funnythingz.iroiro.infrastructure.ColorAPI;
import com.funnythingz.iroiro.infrastructure.IroIroAPI;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit.RestAdapter;
import retrofit.client.Response;
import retrofit.converter.GsonConverter;
import retrofit.mime.TypedByteArray;
import rx.Observer;
import rx.Scheduler;
import rx.Subscription;

import static rx.android.schedulers.AndroidSchedulers.*;
import static rx.schedulers.Schedulers.*;


public class NewIroActivity extends AppCompatActivity {

    private final NewIroActivity self = this;

    private Toolbar mToolbar;

    private RestAdapter restAdapter = new RestAdapter.Builder()
            .setConverter(new GsonConverter(new Gson()))
            .setEndpoint("http://iroiro.space/v1")
            .build();

    private IroIroAPI iroiroAPI = restAdapter.create(IroIroAPI.class);
    private ColorAPI colorAPI = restAdapter.create(ColorAPI.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_iro);
        setToolbar();
        resolveSelectColors();
    }

    private void setToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.NewIroToolbar);
        mToolbar.setTitle("New Iro");

        mToolbar.inflateMenu(R.menu.new_iro_actions);

        // 投稿ボタン
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                TextView textView = (TextView) findViewById(R.id.editText);

                // TODO: 色の選択
                iroiroAPI.postNewIro(textView.getText().toString(), 1)
                        .subscribeOn(newThread())
                        .observeOn(mainThread())
                        .subscribe(new Observer<Response>() {

                            private Boolean flg = false;

                            private Boolean isFlg() {
                                return flg;
                            }

                            @Override
                            public void onCompleted() {
                                if (isFlg()) {
                                    onBackPressed();
                                }
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.e("error", e.toString());
                            }

                            @Override
                            public void onNext(Response response) {
                                // 返ってきたJSONをパース
                                JSONObject responseJson = null;
                                try {
                                    responseJson = new JSONObject(new String(((TypedByteArray) response.getBody()).getBytes()));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                // Validates
                                try {
                                    // TODO:
                                    // エラーメッセージがあればアラート出す
                                    AlertDialog.Builder ab = new AlertDialog.Builder(NewIroActivity.this);
                                    AlertDialog alertDialog = ab.create();
                                    alertDialog.setTitle("Error");
                                    alertDialog.setMessage((CharSequence) responseJson.getJSONArray("Message").get(0));
                                    alertDialog.show();
                                } catch (JSONException e) {
                                    // エラーがなければ終了
                                    e.printStackTrace();
                                    flg = true;
                                }
                            }
                        });
                return true;
            }
        });

        // 戻るボタン
        mToolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void resolveSelectColors() {
        colorAPI.getColors()
                .subscribeOn(newThread())
                .observeOn(mainThread())
                .subscribe(new Observer<ArrayList<Color>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("error", e.toString());
                    }

                    @Override
                    public void onNext(ArrayList<Color> colorArrayList) {
                        RelativeLayout newIroLayout = (RelativeLayout) findViewById(R.id.newIroLayout);
                        newIroLayout.setBackgroundColor(android.graphics.Color.parseColor(colorArrayList.get(0).code));

                        //TODO: カラーリストを表示
                        GridView colorsView = (GridView) findViewById(R.id.colorsView);
                        ViewGroup.LayoutParams layoutParams = colorsView.getLayoutParams();
                        final float scale = getResources().getDisplayMetrics().density; // dpで入力したいので変換用scaleをセット
                        layoutParams.width = (int) ((colorArrayList.size() * 48) * scale); // px * scale = dp
                        Log.d("width", "" + layoutParams.width);
                        colorsView.setLayoutParams(layoutParams);
                        colorsView.setAdapter(new ColorAdapter(self, colorArrayList, newIroLayout));
                    }
                });
    }
}
