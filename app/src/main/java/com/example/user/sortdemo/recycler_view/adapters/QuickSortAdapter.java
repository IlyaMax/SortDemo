package com.example.user.sortdemo.recycler_view.adapters;

import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.example.user.sortdemo.recycler_view.ItemColor.BLACK;
import static com.example.user.sortdemo.recycler_view.ItemColor.BLUE;
import static com.example.user.sortdemo.recycler_view.ItemColor.ORANGE;
import static com.example.user.sortdemo.recycler_view.ItemColor.RED;
import static com.example.user.sortdemo.recycler_view.ItemColor.YELLOW;

public class QuickSortAdapter extends SortAdapter {
    public QuickSortAdapter(TextView comment, Button shuffleButton) {
        super(comment, shuffleButton);
    }

    @Override
    protected Observable<SortTask> getSortPlan() {
        return Observable.create(emitter -> {
            List<SortTask> tasks = quickSort(0, sortList.size() - 1);
            for (SortTask task : tasks) {
                emitter.onNext(task);
            }
        });
    }

    @Override
    public void sort() {
        print_array();
        Disposable disposable = getSortPlan()
                .zipWith(Observable.interval(SHORT_INTERVAL, TimeUnit.MILLISECONDS), ((sortTask, timer) -> sortTask))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(SortTask::execute, Throwable::printStackTrace);
    }

    private List<SortTask> quickSort(int low, int high) {
        List<SortTask> tasks = new ArrayList<>();
        if (low >= high)
            return tasks;
        int middle = low + (high - low) / 2;
        int pivot = sortList.get(middle);
        final String msgSortBoundaries = (low == 0 && high == sortList.size() - 1) ? "Начало сортировки массива"
                : "Начало сортировки подмассива от " + low + " до " + high;
        tasks.add(() -> comment.setText(msgSortBoundaries));
        final String msgPivot = "Опорным элементом выбираем " + pivot;
        tasks.add(() -> comment.setText(msgPivot));
        tasks.add(new HighlightTask(BLUE, middle));
        int i = low;
        int j = high;
        while (i < j) {
            if (isPartitioned(middle)) break;
            while (i < middle && sortList.get(i) < pivot) {
                tasks.add(new HighlightTask(YELLOW, i));
                tasks.add(new HighlightTask(BLACK, i));
                i++;
            }
            tasks.add(new HighlightTask(RED, i));
            while (j > middle && sortList.get(j) > pivot) {
                tasks.add(new HighlightTask(YELLOW, j));
                tasks.add(new HighlightTask(BLACK, j));
                j--;
            }
            tasks.add(new HighlightTask(RED, j));
            if (i < j) {
                Collections.swap(sortList, i, j);
                tasks.add(new SwapTask(i, j));
                if (i == middle) {
                    int finalJ = j;
                    int finalI = i;
                    //вовзвращаем первоначальный цвет
                    tasks.add(() -> {
                        changeColor(finalJ, BLUE);
                        changeColor(finalI, BLACK);
                    });
                    middle = j;
                } else if (j == middle) {
                    int finalJ = j;
                    int finalI = i;
                    //вовзвращаем первоначальный цвет
                    tasks.add(() -> {
                        changeColor(finalI, BLUE);
                        changeColor(finalJ, BLACK);
                    });
                    middle = i;
                } else {
                    tasks.add(new HighlightTask(BLACK, i, j));
                }
            }
        }
        tasks.add(new HighlightTask(ORANGE, middle));
        // вызов рекурсии для сортировки левой и правой части
        tasks.addAll(quickSort(low, middle - 1));
        tasks.addAll(quickSort(middle + 1, high));

        tasks.add(() -> {
            String msgSorted;
            if (low == 0 && high == sortList.size() - 1) {
                msgSorted = "Mассив отсортирован";
                shuffleButton.setEnabled(true);
                for (int item = low; item <= high; item++) changeColor(item, BLACK);
            } else {
                msgSorted = "Сортировка подмассива от " + low + " до " + high + " завершена";
                for (int item = low; item <= high; item++) changeColor(item, ORANGE);
            }
            comment.setText(msgSorted);
        });
        return tasks;
    }

    private boolean isPartitioned(int middle) {
        int pivot = sortList.get(middle);
        for (int i = 0; i < middle; i++) {
            if (sortList.get(i) > pivot) return false;
        }
        for (int i = sortList.size() - 1; i > middle; i--) {
            if (sortList.get(i) < pivot) return false;
        }
        return true;
    }
}
