package com.example.user.sortdemo.recycler_view;

import android.graphics.Color;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import static com.example.user.sortdemo.recycler_view.ItemColor.*;

public class ElemAdapter extends RecyclerView.Adapter<ElemViewHolder>{

    private ArrayList<ItemColor> colorList;
    private ArrayList<Item> list;
    private ArrayList<Item> handlerList;
    private Handler handler;
    private final String TAG = "log";
    private TextView comment;
    private Button shuffleButton;
    private int time;
    public ElemAdapter(TextView comment,Button shuffleButton) {
        list = new ArrayList<>();
        handlerList = new ArrayList<>();
        colorList = new ArrayList<>();
        for (int i=1;i<=9;i++){
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
    public ElemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_item, viewGroup, false);
        handler = new Handler();
        return new ElemViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(@NonNull ElemViewHolder elemViewHolder, int i) {

        Integer num = handlerList.get(i).num;
        elemViewHolder.tvElem.setText(num.toString());
        switch(colorList.get(i)){
            case BLACK:
                elemViewHolder.tvElem.setBackgroundResource(R.drawable.black_circle);
                break;
            case RED:
                elemViewHolder.tvElem.setBackgroundResource(R.drawable.red_circle);
                break;
            case YELLOW:
                elemViewHolder.tvElem.setBackgroundResource(R.drawable.yellow_circle);
                break;
            case BLUE:
                elemViewHolder.tvElem.setBackgroundResource(R.drawable.blue_circle);
                break;
            case ORANGE:
                elemViewHolder.tvElem.setBackgroundResource(R.drawable.orange_circle);
                break;
        }

    }
    @Override
    public int getItemCount() {
        return list.size();
    }
    private void notifyItemsSwapped(final int index1,final int index2){
        //final ItemColor color1 = colorList.get(index1);
        //final ItemColor color2 = colorList.get(index2);
        changeColor(index1,RED);
        changeColor(index2,RED);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Collections.swap(handlerList, index1, index2);
                notifyItemMoved(index1, index2);
                notifyItemMoved(index2 - 1, index1);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        changeColor(index1,BLACK);
                        changeColor(index2,BLACK);
                        Log.d("DEBUG","swap finish " + index1 + " " + index2);
                    }
                },1000);
            }
        },1000);
    }
    private void highlight(final int index1,final int index2){
        changeColor(index1,YELLOW);
        changeColor(index2,YELLOW);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                changeColor(index1,BLACK);
                changeColor(index2,BLACK);
                Log.d("DEBUG","highlight finish " + index1 + " " + index2);
            }
        },500);
    }
    private void changeColor(final int index,final ItemColor color){
        colorList.set(index,color);
        notifyItemChanged(index);
    }
    private boolean isSorted() {
        for (int i = 1; i < list.size(); i++) {
            if (list.get(i - 1).num > list.get(i).num) {
                return false;
            }
        }
        return true;
    }
    public void bubble_sort() {
        time = 0;
        for(int j = list.size()-1;j>=0;j--) {
            if (isSorted()) break;
            for (int i = 0; i < j; i++) {
                final int curr = i;
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("DEBUG","highlight start " + curr + " " + (curr+1));
                        highlight(curr,curr+1);
                    }
                }, time);
                time +=1000;
                if (list.get(curr).num > list.get(curr + 1).num) {
                    Collections.swap(list, curr, curr + 1);
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Log.d("DEBUG","swap start " + curr + " " + (curr+1));
                            notifyItemsSwapped(curr, curr+1);
                        }
                    }, time);
                    time +=2500;
                }
            }
        }
    }
    private void print_array(){
        String array = "array: ";
        for(Item item:list){
            array += " " + item.num;
        }
        Log.d(TAG,array);
    }
    public void quick_sort() {
        print_array();
        time = 0;
        quickSort(0,list.size()-1);
        //Log.d(TAG,"Длительность сортировки: " + seconds);
    }
    private void quickSort(int low, int high) {
        if (low >= high)
            return;//завершить выполнение если уже нечего делить

        // выбрать опорный элемент
        int middle = low + (high - low) / 2;
        final int pivot = list.get(middle).num;
        final int finMid_1 = middle;

        // разделить на подмассивы, который больше и меньше опорного элемента
        int i = low;
        int j = high;
        final int finLow = low;
        final int finHigh = high;
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                String msg = (finLow == 0 && finHigh == list.size()-1) ? "Начало сортировки массива"
                        : "Начало сортировки подмассива от " + finLow + " до " + finHigh;
                comment.setText(msg);
            }
        },time);
        time+=3000;
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                String msg = "Опорным элементом выбираем " + pivot;
                comment.setText(msg);
            }
        },time);
        time+=1000;
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                changeColor(finMid_1,BLUE);
            }
        },time);
        time+=1000;

        while (i < j) {
            final int finMid_2 = middle;
            if (isPartitioned(middle)) break;
            //final int finJ_1 = j;
            do {
                final int finI_1 = i;
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        changeColor(finI_1, YELLOW);
                    }
                }, time);
                time += 500;
                if (list.get(i).num < pivot) {
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            changeColor(finI_1, BLACK);
                        }
                    }, time);
                    time += 500;
                }
                else {
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            changeColor(finI_1, RED);
                        }
                    }, time);
                    time += 500;
                }
            }while (list.get(i++).num < pivot);
            i--;
            do {
                final int finJ_1 = j;
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        changeColor(finJ_1, YELLOW);
                    }
                }, time);
                time += 500;
                if (list.get(j).num > pivot) {
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            changeColor(finJ_1, BLACK);
                        }
                    }, time);
                    time += 500;
                }
                else {
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            changeColor(finJ_1, RED);
                        }
                    }, time);
                    time += 500;
                }
            }while (list.get(j--).num > pivot);
            j++;
            if (i <= j) {//меняем местами
                Collections.swap(list,i,j);
                final int finI_2 = i,finJ_2 = j;
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (finI_2 != finJ_2) notifyItemsSwapped(finI_2,finJ_2);
                    }
                },time);
                time += 2500;
                if (finJ_2 == finMid_2){
                    middle = finI_2;
                }
                if (finI_2 == finMid_2){
                    middle = finJ_2;
                }
                final int finMid_3 = middle;
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        changeColor(finMid_3,BLUE);
                    }
                },time);
                time+=1000;
            }
        }
        final int finMid_4 = middle;
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                changeColor(finMid_4,ORANGE);
            }
        },time);
        time+=1000;
        // вызов рекурсии для сортировки левой и правой части
        quickSort(low, middle-1);
        quickSort(middle+1, high);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                String msg;
                if (finLow == 0&&finHigh == list.size()-1){
                    msg = "Mассив отсортирован";
                    shuffleButton.setEnabled(true);
                    for(int item=finLow;item<=finHigh;item++)changeColor(item,BLACK);
                }
                else{
                    msg = "Сортировка подмассива от " + finLow + " до " + finHigh + " завершена";
                    for(int item=finLow;item<=finHigh;item++)changeColor(item,ORANGE);
                }
                comment.setText(msg);
            }
        },time);
        time += 3000;
    }
    private boolean isPartitioned(int mid){
        int opora = list.get(mid).num;
        for (int i=0;i<mid;i++){
            if (list.get(i).num>opora) return false;
        }
        for (int i=list.size()-1;i>mid;i--){
            if (list.get(i).num<opora) return false;
        }
        return true;
    }
    public void shuffle() {
        Random rnd = ThreadLocalRandom.current();

        for (int i = 0; i < list.size(); i++)
        {
            int index = rnd.nextInt(i + 1);
            // Simple swap
            Collections.swap(list,index,i);
            Collections.swap(handlerList,index,i);
        }
        notifyItemRangeChanged(0,getItemCount());
    }
}
