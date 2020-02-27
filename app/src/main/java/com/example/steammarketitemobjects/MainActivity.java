package com.example.steammarketitemobjects;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements DetailsListener{

    private static final String TAG = "MainActivity";
    private RecyclerViewAdapter recyclerAdapter;
    private ArrayList<String> itemNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            getInitListItems();
        } catch (Exception e) {
            e.printStackTrace();
        }
        getInitRecycleViewer();

        EditText editText = findViewById(R.id.search);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });

    }

    private void filter(String text) {
        ArrayList<String> filteredItems = new ArrayList<>();
        for (String item : itemNames) {
            if(item != null) {
                if (item.toLowerCase().contains(text.toLowerCase())) {
                    filteredItems.add(item);
                }
            }
        }
        recyclerAdapter.filterList(filteredItems);
    }

    private void getInitRecycleViewer(){
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setHasFixedSize(true);
        recyclerAdapter = new RecyclerViewAdapter(itemNames, this, this);
        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void openDetails(Context context, ArrayList<String> mData, int position) {
        Intent intent = new Intent(context, ResultActivity.class);
        intent.putExtra("name", mData.get(position));
        intent.putExtra("item", getMarketItemData(mData.get(position), context));
        context.startActivity(intent);
    }

    private void getInitListItems() throws Exception{
        Log.d(TAG, "Creating list of items");
        itemNames = new ArrayList<>();
        InputStream is;
        is = getAssets().open("items.txt");
        BufferedReader r = new BufferedReader(new InputStreamReader(is));
            while (r.readLine() != null) {
                itemNames.add(r.readLine());
            }
        r.close();
        is.close();
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
