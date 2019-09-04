package com.example.user.sortdemo.recycler_view.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.user.sortdemo.R;
import com.example.user.sortdemo.recycler_view.NumberItem;

import java.util.Collections;
import java.util.List;

public class CSortAdapter extends RecyclerView.Adapter<CSortAdapter.ViewHolder> {

    private List<NumberItem> items;

    public CSortAdapter(List<NumberItem> items) {
        super();
        this.items = items;
    }

    // INFO: методы для работы с адаптером
    public void updateItem(int id) {
        notifyItemChanged(id);
    }

    public void swapItems(int firstId, int secondId) {
        Collections.swap(items, firstId, secondId);
//        NumberItem temp = items.get(secondId);
//        items.set(secondId, items.get(firstId));
//        items.set(firstId, temp);

        // TODO перепроверить правильный swap
        int minId = Math.min(firstId, secondId);
        int maxId = Math.max(firstId, secondId);
        notifyItemMoved(minId, maxId);
        notifyItemMoved(maxId - 1, minId);

    }


    // INFO: методы RecyclerView.Adapter
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater
                .from(viewGroup.getContext())
                .inflate(R.layout.item_number, viewGroup, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.bind(items.get(i));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    // INFO: внутренний класс для SortAdapter
    static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvTxtItem;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTxtItem = itemView.findViewById(R.id.tv_text_item);
        }

        void bind(NumberItem item) {
            tvTxtItem.setText(Integer.toString(item.getValue()));
            tvTxtItem.setBackgroundTintList(item.getMonoColorStateList(itemView.getContext()));
        }
    }
}
