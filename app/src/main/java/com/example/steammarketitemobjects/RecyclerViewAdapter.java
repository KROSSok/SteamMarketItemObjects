package com.example.steammarketitemobjects;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ItemsViewHolder>{

    private ArrayList<String> mData;

    private Context mContext;

    class ItemsViewHolder extends RecyclerView.ViewHolder {

        TextView itemNames;
        RelativeLayout parentLayout;

        private ItemsViewHolder(View itemView) {
            super(itemView);
            itemNames = itemView.findViewById(R.id.item_names);
            parentLayout = itemView.findViewById(R.id.parent_layout);
        }
    }

    public RecyclerViewAdapter(ArrayList<String> mData, Context mContext) {
        this.mData = mData;
        this.mContext = mContext;
    }

    @Override
    public ItemsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_list_items, parent, false);
        return new ItemsViewHolder(view);
    }

    @SuppressLint("ClickableViewAccessibility")

    @Override
    public void onBindViewHolder(final RecyclerViewAdapter.ItemsViewHolder holder, final int position) {
        holder.itemNames.setText(mData.get(position));

        holder.parentLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        holder.parentLayout.setBackgroundColor(mContext.getResources().getColor(R.color.colorSelected));
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        holder.parentLayout.setBackgroundColor(mContext.getResources().getColor(R.color.colorDefault));
                        break;
                }
                return false;
            }
        });

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ResultActivity.class);
                intent.putExtra("name", mData.get(position));
                intent.putExtra("item", getMarketItemData(mData.get(position), mContext));
                v.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void filterList(ArrayList<String> filteredList) {
        mData = filteredList;
        notifyDataSetChanged();
    }

    private MarketItem getMarketItemData(String itemName, Context mContext){
        MarketItem steamItem;
        JsonDataParser jsonDataParser;
        jsonDataParser = new JsonDataParser(mContext);
        URLDataWriter urlDataWriter = new URLDataWriter();
        try {
            String url = jsonDataParser.getStringObject("multi_url");
            steamItem = JsonDataParser.getDataByKey(urlDataWriter.execute(url+itemName).get());
        } catch (Exception e) {
            e.printStackTrace();
            steamItem = null;
        }
        return steamItem;
    }
}