package com.rurab.eventtrack;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;


public class Docres extends AppCompatActivity {
    TextView t;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.docres);
        t=findViewById(R.id.t);
        t.setMovementMethod(new ScrollingMovementMethod());
        Bundle b=getIntent().getExtras();
        String rev=b.getString("key");

        t.setText(rev);
    }
}