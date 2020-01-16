package com.example.user.sortdemo.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.user.sortdemo.R;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        final Intent intent = new Intent(MenuActivity.this, SortActivity.class);

        Button quickSortButton = findViewById(R.id.quick_sort);
        quickSortButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("sort_type", "quick");
                startActivity(intent);
            }
        });

        Button bubbleSortButton = findViewById(R.id.bubble_sort);
        bubbleSortButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("sort_type", "bubble");
                startActivity(intent);
            }
        });

    }
}
