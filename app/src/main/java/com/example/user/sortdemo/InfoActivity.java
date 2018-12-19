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

public class InfoActivity extends AppCompatActivity {
    ImageView info;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        info = findViewById(R.id.theory);
        if (getIntent().getStringExtra("sort_type").equals("bubble")) info.setImageResource(R.drawable.bubble_sort_theory);
        else info.setImageResource(R.drawable.quick_sort_theory);
    }
}
