package com.ase.myapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class InerActivity extends AppCompatActivity {
    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iner);
        textView=findViewById(R.id.tv);
        Log.d("InerAct","ineract");
//        textView.setText(intent.getStringExtra("search"));
    }
}
