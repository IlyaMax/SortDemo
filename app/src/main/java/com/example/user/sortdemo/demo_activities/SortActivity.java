package com.example.user.sortdemo.demo_activities;

import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageView;

import com.example.user.sortdemo.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public abstract class SortActivity extends AppCompatActivity {
    int[] list = {0,1,2,3,4,5,6,7,8};
    Button shuffleButton;
    Button sortButton;
    List<Integer> ID;
    List<Integer> DRAWABLE;
    List<Integer> YELLOW_DRAWABLE;
    List<Integer> RED_DRAWABLE;
    void shuffleList()
    {
        // If running on Java 6 or older, use `new Random()` on RHS here
        Random rnd = ThreadLocalRandom.current();
        for (int i = list.length - 1; i > 0; i--)
        {
            int index = rnd.nextInt(i + 1);
            // Simple swap
            int a = list[index];
            list[index] = list[i];
            list[i] = a;
        }
    }
    abstract void sort();
    abstract boolean check(final int index1,final int index2);
    abstract void swap(final int index1,final int index2);
    abstract void updateList();
}
