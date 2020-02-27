package com.example.steammarketitemobjects;

import android.content.Context;
import java.util.ArrayList;

interface DetailsListener {
    void openDetails(Context context, ArrayList<String> mData, int position);
}
