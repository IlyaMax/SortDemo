package com.example.user.sortdemo.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.user.sortdemo.R;
import com.example.user.sortdemo.base.SortType;

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
                intent.putExtra(SortActivity.EXTRA_SORT_TYPE, SortType.Quick.toString());
                startActivity(intent);
            }
        });

        Button bubbleSortButton = findViewById(R.id.bubble_sort);
        bubbleSortButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra(SortActivity.EXTRA_SORT_TYPE, SortType.Bubble.toString());
                startActivity(intent);
            }
        });

        Button reactiveButton = findViewById(R.id.button_reactive);
        reactiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra(SortActivity.EXTRA_SORT_TYPE, SortType.Reactive.toString());
                startActivity(intent);
            }
        });
    }
}
