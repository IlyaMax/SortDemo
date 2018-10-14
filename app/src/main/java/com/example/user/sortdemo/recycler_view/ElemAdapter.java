package com.example.user.sortdemo.recycler_view;

import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.user.sortdemo.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class ElemAdapter extends RecyclerView.Adapter<ElemViewHolder>{

    private List<Integer> STATE = Arrays.asList(0,0,0,0,0,0,0,0,0);
    private List<Integer> DRAWABLE = Arrays.asList(R.drawable.icons8_1_24,R.drawable.icons8_2_24,R.drawable.icons8_3_24,R.drawable.icons8_4_24,R.drawable.icons8_5_24,
    R.drawable.icons8_6_24,R.drawable.icons8_7_24,R.drawable.icons8_8_24,R.drawable.icons8_9_24);
    private List<Integer> RED_DRAWABLE = Arrays.asList(R.drawable.icons8_1_24_red,R.drawable.icons8_2_24_red,R.drawable.icons8_3_24_red,R.drawable.icons8_4_24_red,R.drawable.icons8_5_24_red,
    R.drawable.icons8_6_24_red,R.drawable.icons8_7_24_red,R.drawable.icons8_8_24_red,R.drawable.icons8_9_24_red);
    private List<Integer> YELLOW_DRAWABLE = Arrays.asList(R.drawable.icons8_1_24_yellow,R.drawable.icons8_2_24_yellow,R.drawable.icons8_3_24_yellow,R.drawable.icons8_4_24_yellow,R.drawable.icons8_5_24_yellow,
    R.drawable.icons8_6_24_yellow,R.drawable.icons8_7_24_yellow,R.drawable.icons8_8_24_yellow,R.drawable.icons8_9_24_yellow);
    private ArrayList<Item> list;
    private Thread swapThread;
    //private Thread
    Handler handler;
    int counter;
    public ElemAdapter()
    {
        list = new ArrayList<>();
        for (int i=1;i<=9;i++){
            Item item = new Item(i);
            list.add(item);
        }
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
        Item currElem = list.get(i);
        switch(STATE.get(i)){
            case 0:
                elemViewHolder.ivElem.setImageResource(DRAWABLE.get(i));
                break;
            case 1:
                elemViewHolder.ivElem.setImageResource(RED_DRAWABLE.get(i));
                break;
            case 2:
                elemViewHolder.ivElem.setImageResource(YELLOW_DRAWABLE.get(i));
                break;

        }

    }
    @Override
    public int getItemCount() {
        return list.size();
    }




    public void notifyItemsSwapped(final int fromPosition,final int toPosition){
        //int fromItem = (int)getItemId(fromPosition);
        //int toItem = (int)getItemId(toPosition);
        STATE.set(fromPosition,1);
        STATE.set(toPosition,1);
        notifyItemChanged(fromPosition);
        notifyItemChanged(toPosition);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Collections.swap(DRAWABLE, fromPosition, toPosition);
                Collections.swap(RED_DRAWABLE, fromPosition, toPosition);
                Collections.swap(YELLOW_DRAWABLE, fromPosition, toPosition);
                Collections.swap(STATE, fromPosition, toPosition);
                notifyItemMoved(fromPosition, toPosition);
                notifyItemMoved(toPosition - 1, fromPosition);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        STATE.set(fromPosition,0);
                        STATE.set(toPosition,0);
                        notifyItemChanged(fromPosition);
                        notifyItemChanged(toPosition);
                        Log.d("DEBUG","swap end " + fromPosition + " " + toPosition);
                    }
                },1000);
            }
        },1000);
    }
    public void highlight(final int index1,final int index2){


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

    public void sort()
    {
        counter = 0;
        int c = 0;
        while(!isSorted()) {
            Log.d("DEBUG", "Iteration:" + c++);
            for (int i = 0; i < list.size()-1; i++) {
                final int curr = i;
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("DEBUG","highlight start " + curr + " " + (curr+1));
                        highlight(curr,curr+1);
                    }
                },counter);
                counter+=1000;
                if (list.get(curr).num > list.get(curr + 1).num) {
                    Collections.swap(list, curr, curr + 1);
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            Log.d("DEBUG","swap start " + curr + " " + (curr+1));
                            notifyItemsSwapped(curr, curr+1);
                        }
                    },counter);
                    counter+=2500;
                }
            }
        }
    }
    private boolean isSorted() {
        for (int i = 1; i < list.size(); i++) {
            if (list.get(i - 1).num > list.get(i).num) {
                return false;
            }
        }
        return true;
    }
//    Collections.swap(list,index,i);
//            Collections.swap(DRAWABLE,index,i);
//            Collections.swap(RED_DRAWABLE,index,i);
//            Collections.swap(YELLOW_DRAWABLE,index,i);
//            Collections.swap(STATE,index,i);
    public void shuffleSort()
    {
        Random rnd = ThreadLocalRandom.current();

        for (int i = 0; i < 5; i++)
        {
            int index = rnd.nextInt(i + 1);
            // Simple swap
            Collections.swap(list,index,i);
            Collections.swap(DRAWABLE,index,i);
            Collections.swap(RED_DRAWABLE,index,i);
            Collections.swap(YELLOW_DRAWABLE,index,i);
            Collections.swap(STATE,index,i);

        }
        notifyItemRangeChanged(0,9);
    }

//    private void moveItem(int fromPosition, int toPosition) {
//        final Item item = list.remove(fromPosition);
//        list.add(toPosition, item);
//        notifyItemMoved(fromPosition, toPosition);
//    }
//    private void update(){
//        for (int position = 0;position<9;position++){
//            notifyItemChanged(position);
//        }
//    }
}
