package com.example.steammarketitemobjects;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        TextView data = findViewById(R.id.data);
        MarketItem marketItem = (MarketItem)getIntent().getSerializableExtra("item");
        data.setText(getIntent().getSerializableExtra("name").toString());
        if(marketItem != null) {

            TextView successItem = findViewById(R.id.success);
            successItem.setText(marketItem.getSuccess().toString());

            TextView lowestPriceItem = findViewById(R.id.lowestPrice);
            lowestPriceItem.setText(marketItem.getLowest_price().toString());

            TextView volumeItem = findViewById(R.id.volume);
            volumeItem.setText("" + marketItem.getVolume());

            TextView medianPriceItem = findViewById(R.id.medianPrice);
            medianPriceItem.setText(marketItem.getMedian_price().toString());

            TextView currencyItem = findViewById(R.id.currency);
            currencyItem.setText(marketItem.getCurrency());

            TextView timeItem = findViewById(R.id.time);
            timeItem.setText(marketItem.getTime());

        } else {

            MarketItem item = new MarketItem(false, 0.0, 0, 0.0, "no item on Steam Market right now");
            TextView successItem = findViewById(R.id.success);
            successItem.setText(item.getSuccess().toString());

            TextView lowestPriceItem = findViewById(R.id.lowestPrice);
            lowestPriceItem.setText(item.getLowest_price().toString());

            TextView volumeItem = findViewById(R.id.volume);
            volumeItem.setText("" + item.getVolume());

            TextView medianPriceItem = findViewById(R.id.medianPrice);
            medianPriceItem.setText(item.getMedian_price().toString());

            TextView currencyItem = findViewById(R.id.currency);
            currencyItem.setText(item.getCurrency());

            TextView timeItem = findViewById(R.id.time);
            timeItem.setText(item.getTime());

        }
    }
}
