package com.example.user.sortdemo.recycler_view.adapters;

import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.user.sortdemo.R;
import com.example.user.sortdemo.recycler_view.Item;
import com.example.user.sortdemo.recycler_view.ItemColor;
import com.example.user.sortdemo.recycler_view.ItemHolder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import static com.example.user.sortdemo.recycler_view.ItemColor.BLACK;
import static com.example.user.sortdemo.recycler_view.ItemColor.RED;
import static com.example.user.sortdemo.recycler_view.ItemColor.YELLOW;

public abstract class SortAdapter extends RecyclerView.Adapter<ItemHolder> {

    private ArrayList<ItemColor> colorList;
    protected ArrayList<Item> list;
    private ArrayList<Item> handlerList;
    protected Handler handler;
    private final String TAG = "sortLog";
    protected TextView comment;
    protected Button shuffleButton;
    protected int time;

    public abstract void sort();

    protected SortAdapter(TextView comment, Button shuffleButton) {
        list = new ArrayList<>();
        handlerList = new ArrayList<>();
        colorList = new ArrayList<>();
        for (int i = 1; i <= 9; i++) {
            Item item = new Item(i);
            list.add(item);
            handlerList.add(item);
            colorList.add(BLACK);
        }
        this.comment = comment;
        this.shuffleButton = shuffleButton;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_item, viewGroup, false);
        handler = new Handler();
        return new ItemHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder itemHolder, int i) {
        itemHolder.tvElem.setText(String.format("%d", handlerList.get(i).num));
        switch (colorList.get(i)) {
            case BLACK:
                itemHolder.tvElem.setBackgroundResource(R.drawable.black_circle);
                break;
            case RED:
                itemHolder.tvElem.setBackgroundResource(R.drawable.red_circle);
                break;
            case YELLOW:
                itemHolder.tvElem.setBackgroundResource(R.drawable.yellow_circle);
                break;
            case BLUE:
                itemHolder.tvElem.setBackgroundResource(R.drawable.blue_circle);
                break;
            case ORANGE:
                itemHolder.tvElem.setBackgroundResource(R.drawable.orange_circle);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    protected void notifyItemsSwapped(final int index1, final int index2) {
        //final ItemColor color1 = colorList.get(index1);
        //final ItemColor color2 = colorList.get(index2);
        changeColor(index1, RED);
        changeColor(index2, RED);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Collections.swap(handlerList, index1, index2);
                notifyItemMoved(index1, index2);
                notifyItemMoved(index2 - 1, index1);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        changeColor(index1, BLACK);
                        changeColor(index2, BLACK);
                        Log.d("DEBUG", "swap finish " + index1 + " " + index2);
                    }
                }, 1000);
            }
        }, 1000);
    }

    protected void highlight(final int index1, final int index2) {
        changeColor(index1, YELLOW);
        changeColor(index2, YELLOW);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                changeColor(index1, BLACK);
                changeColor(index2, BLACK);
                Log.d("DEBUG", "highlight finish " + index1 + " " + index2);
            }
        }, 500);
    }

    protected void changeColor(final int index, final ItemColor color) {
        colorList.set(index, color);
        notifyItemChanged(index);
    }

    protected boolean isSorted() {
        for (int i = 1; i < list.size(); i++) {
            if (list.get(i - 1).num > list.get(i).num) {
                return false;
            }
        }
        return true;
    }

    protected void print_array() {
        StringBuilder array = new StringBuilder("array: ");
        for (Item item : list) {
            array.append(" ").append(item.num);
        }
        Log.d(TAG, array.toString());
    }

    public void shuffle() {
        Random rnd = ThreadLocalRandom.current();

        for (int i = 0; i < list.size(); i++) {
            int index = rnd.nextInt(i + 1);
            // Simple swap
            Collections.swap(list, index, i);
            Collections.swap(handlerList, index, i);
        }
        notifyItemRangeChanged(0, getItemCount());
    }
}
