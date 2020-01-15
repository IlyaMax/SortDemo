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

import io.reactivex.functions.Action;

import static com.example.user.sortdemo.recycler_view.ItemColor.BLACK;
import static com.example.user.sortdemo.recycler_view.ItemColor.RED;
import static com.example.user.sortdemo.recycler_view.ItemColor.YELLOW;

public abstract class SortAdapter extends RecyclerView.Adapter<ItemHolder> {
    protected Action callback;
    protected final String TAG = "sortLog";
    protected final int SHORT_INTERVAL = 500;
    protected final int LONG_INTERVAL = 1000;
    protected ArrayList<Integer> sortList;
    protected ArrayList<Item> adapterList;
    protected Handler handler;
    protected TextView comment;
    protected Button shuffleButton;
    protected int time;

    protected SortAdapter(Action callback) {
        sortList = new ArrayList<>();
        adapterList = new ArrayList<>();
        for (int i = 1; i <= 9; i++) {
            Item item = new Item(i, BLACK);
            sortList.add(i);
            adapterList.add(item);
        }
        this.callback = callback;
    }

    public abstract void sort();

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_item, viewGroup, false);
        handler = new Handler();
        return new ItemHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder itemHolder, int i) {
        itemHolder.tvElem.setText(String.format("%d", adapterList.get(i).num));
        switch (adapterList.get(i).color) {
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
        return adapterList.size();
    }

    protected void notifyItemsSwapped(final int index1, final int index2) {
        //final ItemColor color1 = colorList.get(index1);
        //final ItemColor color2 = colorList.get(index2);
        changeColor(index1, RED);
        changeColor(index2, RED);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
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
        Item item = adapterList.get(index);
        adapterList.set(index, new Item(item.num, color));
        notifyItemChanged(index);
    }

    protected boolean isSorted() {
        for (int i = 1; i < sortList.size(); i++) {
            if (sortList.get(i - 1) > sortList.get(i)) {
                return false;
            }
        }
        return true;
    }

    protected void print_array() {
        StringBuilder array = new StringBuilder("array: ");
        for (Integer el : sortList) {
            array.append(" ").append(el);
        }
        Log.d(TAG, array.toString());
    }

    public void shuffle() {
        Random rnd = ThreadLocalRandom.current();

        for (int i = 0; i < sortList.size(); i++) {
            int index = rnd.nextInt(i + 1);
            // Simple swap
            Collections.swap(sortList, index, i);
            Collections.swap(adapterList, index, i);
        }
        notifyItemRangeChanged(0, getItemCount());
    }

    protected interface SortTask {
        void execute();
    }

    protected class SwapTask implements SortTask {
        private int index1;
        private int index2;

        SwapTask(int index1, int index2) {
            this.index1 = index1;
            this.index2 = index2;
        }

        @Override
        public void execute() {
            Collections.swap(adapterList, index1, index2);
            notifyItemMoved(index1, index2);
            notifyItemMoved(index2 - 1, index1);
        }
    }

    protected class HighlightTask implements SortTask {
        private ItemColor color;
        private int[] indexes;

        HighlightTask(ItemColor color, int... indexes) {
            this.color = color;
            this.indexes = indexes;
        }

        @Override
        public void execute() {
            for (int index : indexes) {
                changeColor(index, color);
            }
        }
    }


}
