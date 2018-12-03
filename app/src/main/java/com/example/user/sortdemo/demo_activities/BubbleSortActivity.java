package com.example.user.sortdemo.demo_activities;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.sortdemo.R;
import com.example.user.sortdemo.recycler_view.ElemAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class BubbleSortActivity extends AppCompatActivity{


    ConstraintLayout constraintLayout;
    RecyclerView recyclerView;
    ElemAdapter adapter;
    Button shuffleButton;
    Button sortButton;
    TextView comment;
    final Handler handler = new Handler();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sort);
        shuffleButton = findViewById(R.id.shuffle);
        sortButton = findViewById(R.id.start);
        constraintLayout = findViewById(R.id.mainLayout);
        recyclerView = findViewById(R.id.recyclerView);
        comment = findViewById(R.id.comment);
        adapter = new ElemAdapter(comment);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(adapter);

        shuffleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.shuffle();
            }
        });
        sortButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("DEBUG", "Sorting started");
                adapter.bubble_sort();

            }
        });
    }
}
