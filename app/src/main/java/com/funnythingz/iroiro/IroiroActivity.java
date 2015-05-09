package com.funnythingz.iroiro;

import android.app.Activity;
import android.os.Bundle;
import android.widget.GridView;

public class IroiroActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iroiro);

        GridView iroiroView = (GridView)findViewById(R.id.iroiroView);
        iroiroView.setAdapter(new IroHueAdapter(this));
    }
}
