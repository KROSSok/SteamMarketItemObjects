package com.example.steammarketitemobjects;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ItemsViewHolder> implements Filterable {

    private ArrayList<String> mData;
    private DetailsListener mListener;
    private Context mContext;
    private ArrayList<String> mDataSearch;

    class ItemsViewHolder extends RecyclerView.ViewHolder {

        TextView itemNames;
        RelativeLayout parentLayout;

        private ItemsViewHolder(View itemView) {
            super(itemView);
            itemNames = itemView.findViewById(R.id.item_names);
            parentLayout = itemView.findViewById(R.id.parent_layout);
        }
    }

    public RecyclerViewAdapter(ArrayList<String> mData, Context mContext, DetailsListener mListener) {
        this.mListener = mListener;
        this.mData = mData;
        this.mContext = mContext;
        mDataSearch = new ArrayList<>(mData);
    }

    @NonNull
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
                mListener.openDetails(mData.get(position));
            }
        });

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public Filter getFilter() {
        return dataFilter;
    }
    private Filter dataFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<String> filteredItems = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredItems.addAll(mDataSearch);
            } else {
                String text = constraint.toString().toLowerCase().trim();
                for(String item : mDataSearch){
                    if(item != null) {
                        if (item.toLowerCase().contains(text.toLowerCase())) {
                            filteredItems.add(item);
                        }
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredItems;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mData.clear();
            mData.addAll((ArrayList) results.values);
            notifyDataSetChanged();
        }
    };

}