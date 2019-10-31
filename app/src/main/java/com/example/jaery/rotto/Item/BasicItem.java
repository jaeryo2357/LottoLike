package com.example.jaery.rotto.Item;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class BasicItem  {

    int type;

    public BasicItem(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }
}
