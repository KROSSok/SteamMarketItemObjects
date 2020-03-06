package com.example.steammarketitemobjects;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.SearchView;
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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        SearchView searchView = findViewById(R.id.action_search);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                recyclerAdapter.getFilter().filter(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText == null || newText.length() == 0) {
                    recyclerAdapter.getFilter().filter(newText);
                }
                return false;
            }
        });
        return true;
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
    public void openDetails(String item) {
        Intent intent = new Intent(this, ResultActivity.class);
        intent.putExtra("name", item);
        intent.putExtra("item", getMarketItemData(item));
        this.startActivity(intent);
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

    private MarketItem getMarketItemData(String itemName){
        MarketItem steamItem;
        JsonDataParser jsonDataParser;
        jsonDataParser = new JsonDataParser(this);
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
