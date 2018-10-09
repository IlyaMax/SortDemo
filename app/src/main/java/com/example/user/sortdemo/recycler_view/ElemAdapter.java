package com.example.user.sortdemo.recycler_view;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.example.user.sortdemo.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ElemAdapter extends RecyclerView.Adapter<ElemViewHolder> {

    private ArrayList<Elem> list;

    @NonNull
    @Override
    public ElemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ElemViewHolder elemViewHolder, int i) {
        Elem currElem = list.get(i);
        elemViewHolder.ivElem.setImageResource(R.drawable.icons8_1_24);
    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
