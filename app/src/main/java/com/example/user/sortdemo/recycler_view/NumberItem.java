package com.example.user.sortdemo.recycler_view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.support.v4.content.ContextCompat;

import com.example.user.sortdemo.R;

public class NumberItem {

    private final int value;
    private State state;

    public NumberItem(int value) {
        this.value = value;
        state = State.DEFAULT;
    }

    enum State {
        DEFAULT, COMPARE, MOVE, SUCCESS
    }

    public ColorStateList getMonoColorStateList(Context context) {
        int colorId;

        switch (state) {
            case MOVE:
                colorId = R.color.state_item_move;
            case COMPARE:
                colorId = R.color.state_item_compare;
            case SUCCESS:
                colorId = R.color.state_item_success;
            case DEFAULT:
            default:
                colorId = R.color.state_item_default;
        }

        return ContextCompat.getColorStateList(context, colorId);
    }


    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public int getValue() {
        return value;
    }
}
