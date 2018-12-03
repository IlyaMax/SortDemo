package com.example.user.sortdemo.recycler_view;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.example.user.sortdemo.R;

public class ElemViewHolder extends RecyclerView.ViewHolder {
    public ImageView ivElem;
    public ElemViewHolder(@NonNull View itemView) {
        super(itemView);
        ivElem = itemView.findViewById(R.id.ivItem);
    }
}
