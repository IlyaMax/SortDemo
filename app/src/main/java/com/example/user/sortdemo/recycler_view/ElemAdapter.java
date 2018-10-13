package com.example.user.sortdemo.recycler_view;

import android.content.ClipData;
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
import java.util.concurrent.TimeUnit;

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
    boolean firstSwap;
    public ElemAdapter()
    {
        counter = 0;
        firstSwap = false;
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
    public void swap(final int fromPosition,final int toPosition){
        //int fromItem = (int)getItemId(fromPosition);
        //int toItem = (int)getItemId(toPosition);
        Item item = list.get(fromPosition);
        Collections.swap(list, fromPosition, toPosition);
        STATE.set(fromPosition,1);
        STATE.set(toPosition,1);
        notifyItemChanged(fromPosition);
        notifyItemChanged(toPosition);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                moveItem(fromPosition,toPosition);
                moveItem(toPosition-1,fromPosition);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Collections.swap(DRAWABLE,fromPosition,toPosition);
                        Collections.swap(RED_DRAWABLE,fromPosition,toPosition);
                        Collections.swap(YELLOW_DRAWABLE,fromPosition,toPosition);
                        STATE.set(fromPosition,0);
                        STATE.set(toPosition,0);
                        notifyItemChanged(fromPosition);
                        notifyItemChanged(toPosition);
                    }
                },1000);
            }
        },1000);
        counter++;
        Log.d("DEBUG","in adapter");
    }

    public void shuffleSort()
    {

    }
    public void sort()
    {
        boolean isSorted = false;
        int buf;
        while(!isSorted) {
            isSorted = true;
            for (int i = 0; i < list.size()-1; i++) {
                final int curr = i;
                if(list.get(curr).num < list.get(curr+1).num){
                    isSorted = false;
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            swap(curr, curr + 1);
                        }
                    },(firstSwap)?0:3000*(counter));
                }
            }
        }
    }
    private void moveItem(int fromPosition, int toPosition) {
        final Item item = list.remove(fromPosition);
        list.add(toPosition, item);
        notifyItemMoved(fromPosition, toPosition);
    }
//    private void update(){
//        for (int position = 0;position<9;position++){
//            notifyItemChanged(position);
//        }
//    }
}
