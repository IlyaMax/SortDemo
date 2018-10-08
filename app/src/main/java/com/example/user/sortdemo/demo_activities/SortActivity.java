package com.example.user.sortdemo.demo_activities;

import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageView;

import com.example.user.sortdemo.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import static com.example.user.sortdemo.demo_activities.SortActivity.ElemState.BLACK;

public abstract class SortActivity extends AppCompatActivity {
    Button shuffleButton;
    Button sortButton;
    enum ElemState {BLACK,YELLOW,RED}
    class Elem
    {
        int num;
        int id;
        int black;
        int yellow;
        int red;
        ElemState state;
        Elem (int num,int id,int black,int yellow,int red){
            this.num = num;
            this.id = id;
            this.black = black;
            this.yellow = yellow;
            this.red = red;
            state = BLACK;
        }
        void switchTo(ElemState state){this.state = state;}

    }
    List<Elem> list = Arrays.asList
                    (new Elem(1,R.id.num1,R.drawable.icons8_1_24,R.drawable.icons8_1_24_yellow,R.drawable.icons8_1_24_red),
                    new Elem(2,R.id.num2,R.drawable.icons8_2_24,R.drawable.icons8_2_24_yellow,R.drawable.icons8_2_24_red),
                    new Elem(3,R.id.num3,R.drawable.icons8_3_24,R.drawable.icons8_3_24_yellow,R.drawable.icons8_3_24_red),
                    new Elem(4,R.id.num4,R.drawable.icons8_4_24,R.drawable.icons8_4_24_yellow,R.drawable.icons8_4_24_red),
                    new Elem(5,R.id.num5,R.drawable.icons8_5_24,R.drawable.icons8_5_24_yellow,R.drawable.icons8_5_24_red),
                    new Elem(6,R.id.num6,R.drawable.icons8_6_24,R.drawable.icons8_6_24_yellow,R.drawable.icons8_6_24_red),
                    new Elem(7,R.id.num7,R.drawable.icons8_7_24,R.drawable.icons8_7_24_yellow,R.drawable.icons8_7_24_red),
                    new Elem(8,R.id.num8,R.drawable.icons8_8_24,R.drawable.icons8_8_24_yellow,R.drawable.icons8_8_24_red),
                    new Elem(9,R.id.num9,R.drawable.icons8_9_24,R.drawable.icons8_9_24_yellow,R.drawable.icons8_9_24_red));
    void shuffleList()
    {
        // If running on Java 6 or older, use `new Random()` on RHS here
        Random rnd = ThreadLocalRandom.current();
        for (int i = list.size() - 1; i > 0; i--)
        {
            int index = rnd.nextInt(i + 1);
            // Simple swap
            Collections.swap(list,index,i);
        }
    }
    abstract void sort();
    abstract boolean check(final int index1,final int index2);
    abstract void swap(final int index1,final int index2);
    abstract void updateList();
}
