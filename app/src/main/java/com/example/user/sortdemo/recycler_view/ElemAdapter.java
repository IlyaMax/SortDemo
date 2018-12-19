package com.example.user.sortdemo.recycler_view;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.util.Pair;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.user.sortdemo.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.StringJoiner;
import java.util.concurrent.ThreadLocalRandom;

public class ElemAdapter extends RecyclerView.Adapter<ElemViewHolder>{

    private List<Integer> STATE = Arrays.asList(0,0,0,0,0,0,0,0,0);
    private List<Integer> DRAWABLE = Arrays.asList(R.drawable.icons8_1_24,R.drawable.icons8_2_24,R.drawable.icons8_3_24,R.drawable.icons8_4_24,R.drawable.icons8_5_24,
    R.drawable.icons8_6_24,R.drawable.icons8_7_24,R.drawable.icons8_8_24,R.drawable.icons8_9_24);
    private List<Integer> RED_DRAWABLE = Arrays.asList(R.drawable.icons8_1_24_red,R.drawable.icons8_2_24_red,R.drawable.icons8_3_24_red,R.drawable.icons8_4_24_red,R.drawable.icons8_5_24_red,
    R.drawable.icons8_6_24_red,R.drawable.icons8_7_24_red,R.drawable.icons8_8_24_red,R.drawable.icons8_9_24_red);
    private List<Integer> YELLOW_DRAWABLE = Arrays.asList(R.drawable.icons8_1_24_yellow,R.drawable.icons8_2_24_yellow,R.drawable.icons8_3_24_yellow,R.drawable.icons8_4_24_yellow,R.drawable.icons8_5_24_yellow,
    R.drawable.icons8_6_24_yellow,R.drawable.icons8_7_24_yellow,R.drawable.icons8_8_24_yellow,R.drawable.icons8_9_24_yellow);
    private List<Integer> GREEN_DRAWABLE = Arrays.asList(R.drawable.icons8_1_24_green,R.drawable.icons8_2_24_green,R.drawable.icons8_3_24_green,R.drawable.icons8_4_24_green,R.drawable.icons8_5_24_green,
            R.drawable.icons8_6_24_green,R.drawable.icons8_7_24_green,R.drawable.icons8_8_24_green,R.drawable.icons8_9_24_green);
    private ArrayList<Item> list;
    private Handler handler;
    private final String TAG = "log";
    private Context context;
    private TextView comment;
    private Button shuffleButton;
    //private Pair<Integer,Integer> groupIndexes1 = new Pair<Integer, Integer>(-1,-1);
    //private Pair<Integer,Integer> groupIndexes2 = new Pair<Integer, Integer>(-1,-1);
    public ElemAdapter(TextView comment,Button shuffleButton) {
        list = new ArrayList<>();
        for (int i=1;i<=9;i++){
            Item item = new Item(i);
            list.add(item);
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
//        int startIndex1 = groupIndexes1.first;
//        int endIndex1 = groupIndexes1.second;
//        int startIndex2 = groupIndexes2.first;
//        int endIndex2 = groupIndexes2.second;
//        if ((i == startIndex1 && i == endIndex1) || (i == startIndex2 && i == endIndex2)){
//            elemViewHolder.ivElem.setImageResource(R.drawable.simple_back);
//        }
//        if (i == startIndex1 || i == startIndex2) {
//            elemViewHolder.ivElem.setImageResource(R.drawable.start_back);
//        }
//        else if (i == endIndex1 || i == endIndex2) {
//            elemViewHolder.ivElem.setImageResource(R.drawable.end_back);
//        }
//        else if ((i>=startIndex1 && i<=endIndex1) || (i>=startIndex2 && i<=endIndex2)){
//            elemViewHolder.ivElem.setImageResource(R.drawable.inner_back);
//        }
//        else{
//            elemViewHolder.ivElem.setImageResource(R.drawable.blank_back);
//        }
        switch(STATE.get(i)){
            case 0:
                elemViewHolder.ivElem.setBackgroundResource(DRAWABLE.get(i));
                break;
            case 1:
                elemViewHolder.ivElem.setBackgroundResource(RED_DRAWABLE.get(i));
                break;
            case 2:
                elemViewHolder.ivElem.setBackgroundResource(YELLOW_DRAWABLE.get(i));
                break;
            case 3:
                elemViewHolder.ivElem.setBackgroundResource(GREEN_DRAWABLE.get(i));
                break;
        }

    }
    @Override
    public int getItemCount() {
        return list.size();
    }
    private void notifyItemsSwapped(final int fromPosition,final int toPosition){
        final int fromState = STATE.get(fromPosition);
        final int toState = STATE.get(toPosition);
        STATE.set(fromPosition,1);
        STATE.set(toPosition,1);
        notifyItemChanged(fromPosition);
        notifyItemChanged(toPosition);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Collections.swap(STATE, fromPosition, toPosition);
                Collections.swap(DRAWABLE, fromPosition, toPosition);
                Collections.swap(RED_DRAWABLE, fromPosition, toPosition);
                Collections.swap(YELLOW_DRAWABLE, fromPosition, toPosition);
                Collections.swap(GREEN_DRAWABLE,fromPosition,toPosition);
                notifyItemMoved(fromPosition, toPosition);
                notifyItemMoved(toPosition - 1, fromPosition);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        STATE.set(fromPosition,fromState);
                        STATE.set(toPosition,toState);
                        notifyItemChanged(fromPosition);
                        notifyItemChanged(toPosition);
                        Log.d("DEBUG","swap end " + fromPosition + " " + toPosition);
                    }
                },1000);
            }
        },1000);
    }
    private void highlight(final int index1,final int index2){


        STATE.set(index1,2);
        STATE.set(index2,2);
        notifyItemChanged(index1);
        notifyItemChanged(index2);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                STATE.set(index1,0);
                STATE.set(index2,0);
                notifyItemChanged(index1);
                notifyItemChanged(index2);
                Log.d("DEBUG","highlight end " + index1 + " " + index2);
            }
        },500);
    }
    private void toGreen(final int index){
        STATE.set(index,3);
        notifyItemChanged(index);
    }
    private void toBlack(final int index){
        STATE.set(index,0);
        notifyItemChanged(index);
    }
    private void toYellow(final int index){
        STATE.set(index,2);
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
        int counter = 0;
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
                }, counter);
                counter +=1000;
                if (list.get(curr).num > list.get(curr + 1).num) {
                    Collections.swap(list, curr, curr + 1);
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            Log.d("DEBUG","swap start " + curr + " " + (curr+1));
                            notifyItemsSwapped(curr, curr+1);
                        }
                    }, counter);
                    counter +=2500;
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
        counter = 0;
        quickSort(0,list.size()-1);
        //Log.d(TAG,"Длительность сортировки: " + seconds);
    }
    private int counter;
    public void quickSort(int low, int high) {
        if (low >= high)
            return;//завершить выполнение если уже нечего делить

        // выбрать опорный элемент
        int middle = low + (high - low) / 2;
        final int opora = list.get(middle).num;
        final int finMid_1 = middle;

        // разделить на подмассивы, который больше и меньше опорного элемента
        int i = low;
        int j = high;
        final int finLow = low;
        final int finHigh = high;
        final int finOpora = opora;
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                String msg = (finLow == 0 && finHigh == list.size()-1) ? "Начало сортировки массива"
                        : "Начало сортировки подмассива от " + finLow + " до " + finHigh;
                comment.setText(msg);
            }
        },counter);
        counter+=3000;
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                String msg = "Опорным элементом выбираем " + finOpora;
                comment.setText(msg);
            }
        },counter);
        counter+=1000;
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                toGreen(finMid_1);
            }
        },counter);
        counter+=1000;
        while (i < j) {
            final int finMid_2 = middle;
            final int finI_1 = i;
            if (isPartitioned(middle)) break;
            if (list.get(i).num < opora) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        toYellow(finI_1);
                    }
                }, counter);
                counter += 500;
            }
            while (list.get(i).num < opora) {
                final int finI_2 = i;
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        toBlack(finI_2);
                    }
                },counter);
                counter += 500;
                i++;
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        toYellow(finI_2+1);
                    }
                },counter);
                counter += 500;
            }
            final int finJ_1 = j;
            if (list.get(j).num > opora){
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        toYellow(finJ_1);
                    }
                },counter);
                counter += 500;
            }
            while (list.get(j).num > opora) {
                final int finJ_2 = j;
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        toBlack(finJ_2);
                    }
                },counter);
                counter += 500;
                j--;
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        toYellow(finJ_2-1);
                    }
                },counter);
                counter += 500;
            }
            if (i <= j) {//меняем местами
                Collections.swap(list,i,j);
                final int finI_2 = i,finJ_2 = j;
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        toBlack(finI_2);
                        toBlack(finJ_2);
                        if (finI_2 != finJ_2) notifyItemsSwapped(finI_2,finJ_2);
                    }
                },counter);
                counter += 2500;
                if (finJ_2 == finMid_2){
                    middle = finI_2;
                }
                if (finI_2 == finMid_2){
                    middle = finJ_2;
                }
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (finJ_2 == finMid_2){
                            toGreen(finI_2);
                        }
                        if (finI_2 == finMid_2){
                            toGreen(finJ_2);
                        }
                    }
                },counter);
                counter+=1000;
            }
        }
        final int finMid_3 = middle;
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                toBlack(finMid_3);
            }
        },counter);
        counter+=1000;
        // вызов рекурсии для сортировки левой и правой части
        quickSort(low, middle);
        quickSort(middle+1, high);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                String msg;
                if (finLow == 0&&finHigh == list.size()-1){
                    msg = "Mассив отсортирован";
                    shuffleButton.setEnabled(true);
                }
                else{
                    msg = "Сортировка подмассива от " + finLow + " до " + finHigh + " завершена";
                }
                comment.setText(msg);
            }
        },counter);
        counter += 3000;
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
//    private int doQuickSort(final int low, final int high,int counter) {
//        Log.d(TAG, String.format("Вызов doQuickSort(%d,%d,%d)", low, high, counter));
//        if (low >= high) {
//            return counter;
//        }
//        // выбрать опорный элемент
//        iter1 = low;
//        iter2 = high;
//        middle = iter1 + (iter2 - iter1) / 2;
//        Log.d(TAG, String.format("Подсветить зеленым элемент под номером %d (%d)",middle,list.get(middle).num));
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
////                groupIndexes1 = new Pair<>(low,finMid-1);
////                groupIndexes2 = new Pair<>(finMid+1,high);
//                toGreen(middle);
//                notifyItemRangeChanged(0,getItemCount());
//            }
//        },counter);
//        counter+=1000;
//        // разделить на подмассивы, который больше и меньше опорного элемента
//        while (iter1 < iter2) {
//
//            int opora = list.get(middle).num;
//            handler.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    toYellow(iter1);
//                }
//            },counter);
//            counter+=500;
//            while (iter1 < middle && list.get(iter1).num <= opora) {
//                handler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        toBlack(iter1);
//                    }
//                },counter);
//                counter+=500;
//                iter1++;
//                handler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        toYellow(iter1);
//                    }
//                },counter);
//                counter+=500;
//            }
//
//
//            /////////////////////////////////////////////////////
//            handler.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    toYellow(iter2);
//                }
//            },counter);
//            counter+=500;
//            while (iter2 > middle && list.get(iter2).num >= opora) {
//                handler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        toBlack(iter1);
//                    }
//                },counter);
//                counter+=500;
//                iter2--;
//                handler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        toYellow(iter1);
//                    }
//                },counter);
//                counter+=500;
//            }
//            final int finI = iter1;
//            final int finJ = iter2;
//            if (iter1 <= iter2) {//меняем местами
//                if (iter1 != iter2){
//                    Log.d(TAG, String.format("Поменять местами элементы под номерами %d и %d (%d,%d)", finI, finJ, list.get(finI).num, list.get(finJ).num));
//                    Collections.swap(list, iter1, iter2);
//                    handler.postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            Log.d("DEBUG","swap start " + finI + " " + finJ);
//                            notifyItemsSwapped(finI, finJ);
//                            notifyItemRangeChanged(0,getItemCount());
//                        }
//                    },counter);
//                    counter+=2500;
//                }
//            }
//        }
//
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                toBlack(middle);
//                notifyItemRangeChanged(0,getItemCount());
//            }
//        },counter+1000);
//        counter+=500;
//        // вызов рекурсии для сортировки левой и правой части
////        print_array();
////        if (low < iter2) doQuickSort(low, middle,counter);
////        print_array();
////        if (high > iter1) doQuickSort(middle+1, high,counter);
//        return counter;
//    }
    public void shuffle() {
        Random rnd = ThreadLocalRandom.current();

        for (int i = 0; i < list.size(); i++)
        {
            int index = rnd.nextInt(i + 1);
            // Simple swap
            Collections.swap(list,index,i);
            Collections.swap(DRAWABLE,index,i);
            Collections.swap(RED_DRAWABLE,index,i);
            Collections.swap(YELLOW_DRAWABLE,index,i);
            Collections.swap(GREEN_DRAWABLE,index,i);
            Collections.swap(STATE,index,i);

        }
        notifyItemRangeChanged(0,getItemCount());
    }
}
