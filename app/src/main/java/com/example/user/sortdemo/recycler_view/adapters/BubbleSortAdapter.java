package com.example.user.sortdemo.recycler_view.adapters;

import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import java.util.Collections;

import static com.example.user.sortdemo.recycler_view.ItemColor.BLACK;
import static com.example.user.sortdemo.recycler_view.ItemColor.ORANGE;

public class BubbleSortAdapter extends SortAdapter {

    public BubbleSortAdapter(TextView comment, Button shuffleButton) {
        super(comment, shuffleButton);
    }

    public void sort() {
        time = 0;
        for (int j = list.size() - 1; j >= 0; j--) {
            if (isSorted()) break;
            for (int i = 0; i < j; i++) {
                final int curr = i;
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("DEBUG", "highlight start " + curr + " " + (curr + 1));
                        highlight(curr, curr + 1);
                    }
                }, time);
                time += 1000;
                if (list.get(curr).num > list.get(curr + 1).num) {
                    Collections.swap(list, curr, curr + 1);
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Log.d("DEBUG", "swap start " + curr + " " + (curr + 1));
                            notifyItemsSwapped(curr, curr + 1);
                        }
                    }, time);
                    time += 2500;
                }
            }
            final int finJ = j;
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    changeColor(finJ, ORANGE);
                }
            }, time);
            time += 2500;
        }

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < list.size(); i++) changeColor(i, ORANGE);

            }
        }, time);
        time += 1000;

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                comment.setText("Mассив отсортирован");
                shuffleButton.setEnabled(true);
                for (int i = 0; i < list.size(); i++) changeColor(i, BLACK);
            }
        }, time);
        time += 2000;

    }
}
