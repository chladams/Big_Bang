package com.longshihan.kuang.bigbang1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private Toolbar mtoolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mtoolbar= (Toolbar) findViewById(R.id.main_tool);

        final TextView textView = (TextView) findViewById(R.id.textView);







        initData();


    }

    private void initData() {
        Intent intent = new Intent(MainActivity.this, ClipServer.class);
        startService(intent);
    }


}
