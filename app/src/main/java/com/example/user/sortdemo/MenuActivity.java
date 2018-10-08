package com.example.user.sortdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.user.sortdemo.demo_activities.BubbleSortActivity;
import com.example.user.sortdemo.demo_activities.MergeSortActivity;
import com.example.user.sortdemo.demo_activities.QuickSortActivity;

public class MenuActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Button quickSortButton = (Button) findViewById(R.id.quick_sort);
        Button bubbleSortButton = (Button) findViewById(R.id.bubble_sort);
        Button mergeSortButton = (Button) findViewById(R.id.merge_sort);
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
        mergeSortButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuActivity.this, MergeSortActivity.class));
            }
        });
    }
}