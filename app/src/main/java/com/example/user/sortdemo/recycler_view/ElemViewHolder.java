package com.example.user.sortdemo.recycler_view;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.sortdemo.R;

public class ElemViewHolder extends RecyclerView.ViewHolder {
    public TextView tvElem;
    public ElemViewHolder(@NonNull View itemView) {
        super(itemView);
        tvElem = itemView.findViewById(R.id.tvElem);
    }
}
