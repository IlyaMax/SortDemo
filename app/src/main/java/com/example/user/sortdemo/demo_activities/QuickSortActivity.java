package com.example.user.sortdemo.demo_activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.user.sortdemo.InfoActivity;
import com.example.user.sortdemo.R;
import com.example.user.sortdemo.recycler_view.ElemAdapter;

public class QuickSortActivity extends AppCompatActivity {
    ConstraintLayout constraintLayout;
    RecyclerView recyclerView;
    TextView comment;
    ElemAdapter adapter;
    Button shuffleButton;
    Button sortButton;
    Button infoButton;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sort);
        shuffleButton = findViewById(R.id.shuffle);
        sortButton = findViewById(R.id.start);
        infoButton = findViewById(R.id.infoButton);
        constraintLayout = findViewById(R.id.mainLayout);
        recyclerView = findViewById(R.id.recyclerView);
        comment = findViewById(R.id.comment);
        adapter = new ElemAdapter(comment,shuffleButton);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false));
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
                Log.d("DEBUG","Sorting started");
                comment.setText("");
                adapter.quick_sort();
                shuffleButton.setEnabled(false);
            }
        });
        infoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QuickSortActivity.this, InfoActivity.class);
                intent.putExtra("sort_type","quick");
                startActivity(intent);
            }
        });
    }
}
