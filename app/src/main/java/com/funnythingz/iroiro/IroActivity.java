package com.funnythingz.iroiro;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

public class IroActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iro);

        TextView iroView = (TextView)findViewById(R.id.iroView);
        String text = getIntent().getStringExtra("text");
        String color = getIntent().getParcelableExtra("color");

        iroView.setText(text);
        iroView.setBackgroundColor(Color.parseColor(color));
    }

}
