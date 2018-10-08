package com.example.user.sortdemo.demo_activities;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
    void sort() {
        boolean isSorted = false;
        while(!isSorted) {
            isSorted = true;
            for (int i = 0;i < list.size()-1;i++){
                final int curr = i;
                final Thread t1 = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        check(curr,curr+1);
                    }
                });
                Handler h = new Handler();
                final Thread t2 = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            t1.join();
                            if (list.get(curr).num > list.get(curr+1).num)
                            {
                                swap(curr,curr+1);
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });
                if (check(i,i+1)){
                    isSorted = false;
                    swap(i,i+1);
                }
            }
        }
    }

    @Override
    boolean check(final int index1, final int index2) {
        final Elem el1 = list.get(index1);
        final Elem el2 = list.get(index2);
        final ImageView view1 = findViewById(el1.id);
        final ImageView view2 = findViewById(el2.id);
        view1.setImageResource(el1.yellow);
        view2.setImageResource(el2.yellow);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                view1.setImageResource(el1.black);
                view2.setImageResource(el2.black);
            }
        },1000);
        return (el1.num > el2.num);
    }

    @Override
    void swap(final int index1, final int index2) {
        final Elem el1 = list.get(index1);
        final Elem el2 = list.get(index2);
        final ImageView view1 = findViewById(el1.id);
        final ImageView view2 = findViewById(el2.id);
        view1.setImageResource(el1.red);
        view2.setImageResource(el2.red);
        Collections.swap(list,index1,index2);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                updateList();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        view1.setImageResource(el1.black);
                        view2.setImageResource(el2.black);
                    }
                },1000);
            }
        },1000);
    }

    //    @Override
//    void sort()
//    {
//        boolean isSorted = false;
//        while(!isSorted) {
//            isSorted = true;
//            for (int i = 0;i < list.length-1;i++){
//                if (check(i,i+1)){
//                    isSorted = false;
//                    swap(i,i+1);
//                }
//            }
//        }
//    }
//
//
//    @Override
//    boolean check(final int index1,final int index2)
//    {
//        final ImageView view1 = findViewById(ID.get(list[index1]));
//        final ImageView view2 = findViewById(ID.get(list[index2]));
//        view1.setImageResource(YELLOW_DRAWABLE.get(list[index1]));
//        view2.setImageResource(YELLOW_DRAWABLE.get(list[index2]));
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                view1.setImageResource(DRAWABLE.get(list[index1]));
//                view2.setImageResource(DRAWABLE.get(list[index2]));
//            }
//        },1000);
//        return (list[index1]>list[index2]);
//    }
    @Override
    void updateList() {
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(constraintLayout);
        for (int i=0;i<list.size();i++){
            constraintSet.setHorizontalBias(list.get(i).id,(i+1)*0.1f);
            final ImageView view = findViewById(list.get(i).id);
            //view.setImageResource();
        }
        constraintSet.applyTo(constraintLayout);
    }
//    @Override
//    void swap(final int index1,final int index2)
//    {
//        final ImageView view1 = findViewById(ID.get(list[index1]));
//        final ImageView view2 = findViewById(ID.get(list[index2]));
//        view1.setImageResource(RED_DRAWABLE.get(list[index1]));
//        view2.setImageResource(RED_DRAWABLE.get(list[index2]));
//        int buff = list[index1];
//        list[index1] = list[index2];
//        list[index2] = buff;
//
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                view1.setImageResource(RED_DRAWABLE.get(list[index1]));
//                view2.setImageResource(RED_DRAWABLE.get(list[index2]));
//                handler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        view1.setImageResource(DRAWABLE.get(list[index1]));
//                        view2.setImageResource(DRAWABLE.get(list[index2]));
//                    }
//                },1000);
//            }
//        },1000);
//    }
}
