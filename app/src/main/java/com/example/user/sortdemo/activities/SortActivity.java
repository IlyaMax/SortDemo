package com.example.user.sortdemo.activities;

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

import com.example.user.sortdemo.R;
import com.example.user.sortdemo.recycler_view.adapters.BubbleSortAdapter;
import com.example.user.sortdemo.recycler_view.adapters.QuickSortAdapter;
import com.example.user.sortdemo.recycler_view.adapters.SortAdapter;

import io.reactivex.functions.Action;

public class SortActivity extends AppCompatActivity {
    ConstraintLayout constraintLayout;
    RecyclerView recyclerView;
    TextView comment;
    SortAdapter adapter;
    Button shuffleButton;
    Button sortButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sort);
        shuffleButton = findViewById(R.id.shuffle);
        sortButton = findViewById(R.id.start);
        constraintLayout = findViewById(R.id.mainLayout);
        recyclerView = findViewById(R.id.recyclerView);
        comment = findViewById(R.id.comment);
        String sortType = getIntent().getExtras().getString("sort_type");
        Action sortFinishedAction = ()->{

        };
        adapter = (sortType.equals("quick")) ? new QuickSortAdapter(comment,shuffleButton) : new BubbleSortAdapter(comment,shuffleButton);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(adapter);
        recyclerView.setOverScrollMode(View.OVER_SCROLL_NEVER);

        shuffleButton.setOnClickListener(v -> adapter.shuffle());
        sortButton.setOnClickListener(v -> {
            Log.d("DEBUG", "Sorting started");
            comment.setText("");
            shuffleButton.setEnabled(false);
            adapter.sort();
        });
    }
}
