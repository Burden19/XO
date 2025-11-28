package com.example.xo;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class PrincipleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principle);

        TextView tvPrinciple = findViewById(R.id.tv_principle);
        tvPrinciple.setText(R.string.game_principle_text);
    }
}

