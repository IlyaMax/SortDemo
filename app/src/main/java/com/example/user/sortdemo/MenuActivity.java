package com.example.user.sortdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.user.sortdemo.demo_activities.BubbleSortActivity;
import com.example.user.sortdemo.demo_activities.QuickSortActivity;

public class MenuActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Button quickSortButton = (Button) findViewById(R.id.quick_sort);
        Button bubbleSortButton = (Button) findViewById(R.id.bubble_sort);
        ImageView info = findViewById(R.id.info);
        quickSortButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuActivity.this, QuickSortActivity.class));
            }
        });
        bubbleSortButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuActivity.this, BubbleSortActivity.class));
            }
        });
        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuActivity.this, InfoActivity.class));
            }
        });
    }
}
