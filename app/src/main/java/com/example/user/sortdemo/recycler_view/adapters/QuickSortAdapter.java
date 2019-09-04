package com.example.user.sortdemo.recycler_view.adapters;

import android.widget.Button;
import android.widget.TextView;

import java.util.Collections;

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
    public void sort() {
        print_array();
        time = 0;
        quickSort(0, list.size() - 1);
        //Log.d(TAG,"Длительность сортировки: " + seconds);
    }

    private void quickSort(int low, int high) {
        if (low >= high)
            return;//завершить выполнение если уже нечего делить

        // выбрать опорный элемент
        int middle = low + (high - low) / 2;
        final int pivot = list.get(middle).getValue();
        final int finMid_1 = middle;

        // разделить на подмассивы, который больше и меньше опорного элемента
        int i = low;
        int j = high;
        final int finLow = low;
        final int finHigh = high;
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                String msg = (finLow == 0 && finHigh == list.size() - 1) ? "Начало сортировки массива"
                        : "Начало сортировки подмассива от " + finLow + " до " + finHigh;
                comment.setText(msg);
            }
        }, time);
        time += 3000;
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                String msg = "Опорным элементом выбираем " + pivot;
                comment.setText(msg);
            }
        }, time);
        time += 1000;
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                changeColor(finMid_1, BLUE);
            }
        }, time);
        time += 1000;

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
                if (list.get(i).getValue() < pivot) {
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            changeColor(finI_1, BLACK);
                        }
                    }, time);
                    time += 500;
                } else {
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            changeColor(finI_1, RED);
                        }
                    }, time);
                    time += 500;
                }
            } while (list.get(i++).getValue() < pivot);
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
                if (list.get(j).getValue() > pivot) {
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            changeColor(finJ_1, BLACK);
                        }
                    }, time);
                    time += 500;
                } else {
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            changeColor(finJ_1, RED);
                        }
                    }, time);
                    time += 500;
                }
            } while (list.get(j--).getValue() > pivot);
            j++;
            if (i <= j) {//меняем местами
                Collections.swap(list, i, j);
                final int finI_2 = i, finJ_2 = j;
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (finI_2 != finJ_2) notifyItemsSwapped(finI_2, finJ_2);
                    }
                }, time);
                time += 2500;
                if (finJ_2 == finMid_2) {
                    middle = finI_2;
                }
                if (finI_2 == finMid_2) {
                    middle = finJ_2;
                }
                final int finMid_3 = middle;
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        changeColor(finMid_3, BLUE);
                    }
                }, time);
                time += 1000;
            }
        }
        final int finMid_4 = middle;
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                changeColor(finMid_4, ORANGE);
            }
        }, time);
        time += 1000;
        // вызов рекурсии для сортировки левой и правой части
        quickSort(low, middle - 1);
        quickSort(middle + 1, high);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                String msg;
                if (finLow == 0 && finHigh == list.size() - 1) {
                    msg = "Mассив отсортирован";
                    shuffleButton.setEnabled(true);
                    for (int item = finLow; item <= finHigh; item++) changeColor(item, BLACK);
                } else {
                    msg = "Сортировка подмассива от " + finLow + " до " + finHigh + " завершена";
                    for (int item = finLow; item <= finHigh; item++) changeColor(item, ORANGE);
                }
                comment.setText(msg);
            }
        }, time);
        time += 3000;
    }

    private boolean isPartitioned(int mid) {
        int opora = list.get(mid).getValue();
        for (int i = 0; i < mid; i++) {
            if (list.get(i).getValue() > opora) return false;
        }
        for (int i = list.size() - 1; i > mid; i--) {
            if (list.get(i).getValue() < opora) return false;
        }
        return true;
    }
}
