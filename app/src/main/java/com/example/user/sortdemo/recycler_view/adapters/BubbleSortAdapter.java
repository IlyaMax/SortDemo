package com.example.user.sortdemo.recycler_view.adapters;

import android.widget.Button;
import android.widget.TextView;

import java.util.Collections;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;

import static com.example.user.sortdemo.recycler_view.ItemColor.BLACK;
import static com.example.user.sortdemo.recycler_view.ItemColor.ORANGE;
import static com.example.user.sortdemo.recycler_view.ItemColor.RED;
import static com.example.user.sortdemo.recycler_view.ItemColor.YELLOW;

public class BubbleSortAdapter extends SortAdapter {
    public BubbleSortAdapter(TextView comment, Button shuffleButton) {
        super(comment,shuffleButton);
    }

    protected Observable<SortTask> getSortPlan() {
        return Observable.create(emitter -> {
            int j;
            for (j = sortList.size() - 1; j >= 0; j--) {
                if (isSorted()) break;
                for (int i = 0; i < j; i++) {
                    emitter.onNext(new HighlightTask(YELLOW, i, i + 1));
                    emitter.onNext(new HighlightTask(BLACK, i, i + 1));
                    if (sortList.get(i) > sortList.get(i + 1)) {
                        emitter.onNext(new HighlightTask(RED, i, i + 1));
                        emitter.onNext(new SwapTask(i, i + 1));
                        Collections.swap(sortList, i, i + 1);
                        emitter.onNext(new HighlightTask(BLACK, i, i + 1));
                    }
                }
                emitter.onNext(new HighlightTask(ORANGE, j));
            }
            for (int i = j; i >= 0; i--) emitter.onNext(new HighlightTask(ORANGE, i));
            emitter.onNext(() -> {
                for (int i = 0; i < sortList.size(); i++) changeColor(i, BLACK);
                comment.setText("Mассив отсортирован");
                shuffleButton.setEnabled(true);
            });
        });
    }

    public void sort() {
        Disposable disposable = getSortPlan()
                .zipWith(Observable.interval(SHORT_INTERVAL, TimeUnit.MILLISECONDS), (item, interval) -> item)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(SortTask::execute, Throwable::printStackTrace);

    }
}
