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
import com.example.user.sortdemo.base.SortType;
import com.example.user.sortdemo.recycler_view.NumberItem;
import com.example.user.sortdemo.recycler_view.adapters.BubbleSortAdapter;
import com.example.user.sortdemo.recycler_view.adapters.CSortAdapter;
import com.example.user.sortdemo.recycler_view.adapters.QuickSortAdapter;
import com.example.user.sortdemo.recycler_view.adapters.SortAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class SortActivity extends AppCompatActivity {

    public static final String EXTRA_SORT_TYPE = "sort_type";

    ConstraintLayout constraintLayout;
    RecyclerView recyclerView;
    TextView comment;
    SortAdapter adapter;
    // FIXME удалить временный адаптер
    CSortAdapter adapterNew;
    Button shuffleButton;
    Button sortButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sort);
        findActivitiesViews();

        String sortType = getIntent().getExtras().getString(EXTRA_SORT_TYPE);
//        adapter = (sortType.equals("quick")) ? new QuickSortAdapter(comment, shuffleButton)
//                : new BubbleSortAdapter(comment, shuffleButton);
        switch (SortType.valueOf(sortType)) {
            case Bubble:
                adapter = new BubbleSortAdapter(comment, shuffleButton);
            case Quick:
                adapter = new QuickSortAdapter(comment, shuffleButton);
            case Reactive:
                // FIXME удалить временную инициализацию элементов
                ArrayList<NumberItem> items = new ArrayList<>();
                for (int i = 0; i < 10; i++) {
                    items.add(new NumberItem(i));
                }
                adapterNew = new CSortAdapter(items);
            default:

        }

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        if (adapter == null) {
            recyclerView.setAdapter(adapterNew);
            shuffleButton.setVisibility(View.GONE);
            sortButton.setVisibility(View.GONE);
        } else {
            recyclerView.setAdapter(adapter);
        }
        recyclerView.setOverScrollMode(View.OVER_SCROLL_NEVER);

        shuffleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.shuffle();
            }
        });

        sortButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comment.setText("");
                adapter.sort();
                shuffleButton.setEnabled(false);
            }
        });
    }

    private void findActivitiesViews() {
        shuffleButton = findViewById(R.id.shuffle);
        sortButton = findViewById(R.id.start);
        constraintLayout = findViewById(R.id.mainLayout);
        recyclerView = findViewById(R.id.recyclerView);
        comment = findViewById(R.id.comment);
    }
}
