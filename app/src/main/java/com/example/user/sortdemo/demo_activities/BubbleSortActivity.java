package com.example.user.sortdemo.demo_activities;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.user.sortdemo.ConstArrays;
import com.example.user.sortdemo.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class BubbleSortActivity extends SortActivity{


    ConstraintLayout constraintLayout;
    final Handler handler = new Handler();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sort);

        shuffleButton = findViewById(R.id.shuffle);
        sortButton = findViewById(R.id.start);
        constraintLayout = findViewById(R.id.mainLayout);

        ID = Arrays.asList(R.id.num1,R.id.num2,R.id.num3,R.id.num4,R.id.num5,R.id.num6,R.id.num7,R.id.num8,R.id.num9);
        DRAWABLE = Arrays.asList(R.drawable.icons8_1_24,R.drawable.icons8_2_24,R.drawable.icons8_3_24,R.drawable.icons8_4_24,R.drawable.icons8_5_24,
                R.drawable.icons8_6_24,R.drawable.icons8_7_24,R.drawable.icons8_8_24,R.drawable.icons8_9_24);
        RED_DRAWABLE = Arrays.asList(R.drawable.icons8_1_24_red,R.drawable.icons8_2_24_red,R.drawable.icons8_3_24_red,R.drawable.icons8_4_24_red,R.drawable.icons8_5_24_red,
                R.drawable.icons8_6_24_red,R.drawable.icons8_7_24_red,R.drawable.icons8_8_24_red,R.drawable.icons8_9_24_red);
        YELLOW_DRAWABLE = Arrays.asList(R.drawable.icons8_1_24_yellow,R.drawable.icons8_2_24_yellow,R.drawable.icons8_3_24_yellow,R.drawable.icons8_4_24_yellow,R.drawable.icons8_5_24_yellow,
                R.drawable.icons8_6_24_yellow,R.drawable.icons8_7_24_yellow,R.drawable.icons8_8_24_yellow,R.drawable.icons8_9_24_yellow);

        shuffleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shuffleList();
                updateList();
            }
        });

        sortButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                swap(1,2);
            }
        });

    }
    @Override
    void sort()
    {
        boolean isSorted = false;
        while(!isSorted) {
            isSorted = true;
            for (int i = 0;i < list.length-1;i++){
                if (check(i,i+1)){
                    isSorted = false;
                    swap(i,i+1);
                }
            }
        }
    }


    @Override
    boolean check(final int index1,final int index2)
    {
        final ImageView view1 = findViewById(ID.get(list[index1]));
        final ImageView view2 = findViewById(ID.get(list[index2]));
        view1.setImageResource(YELLOW_DRAWABLE.get(list[index1]));
        view2.setImageResource(YELLOW_DRAWABLE.get(list[index2]));
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                view1.setImageResource(DRAWABLE.get(list[index1]));
                view2.setImageResource(DRAWABLE.get(list[index2]));
            }
        },500);
        return (list[index1]>list[index2]);
    }
    @Override
    void updateList() {
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(constraintLayout);
        for (int i=0;i<list.length;i++){
            constraintSet.setHorizontalBias(ID.get(list[i]),(i+1)*0.1f);
        }
        constraintSet.applyTo(constraintLayout);
    }
    @Override
    void swap(final int index1,final int index2)
    {
        final ImageView view1 = findViewById(ID.get(list[index1]));
        final ImageView view2 = findViewById(ID.get(list[index2]));
        view1.setImageResource(RED_DRAWABLE.get(list[index1]));
        view2.setImageResource(RED_DRAWABLE.get(list[index2]));
        int buff = list[index1];
        list[index1] = list[index2];
        list[index2] = buff;

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                updateList();
//                handler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        view1.setImageResource(DRAWABLE.get(list[index1]));
//                        view2.setImageResource(DRAWABLE.get(list[index2]));
//                    }
//                },1000);
            }
        },1000);
    }
}
