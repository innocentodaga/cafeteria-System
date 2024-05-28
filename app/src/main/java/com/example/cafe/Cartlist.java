// Cartlist.java
package com.example.cafe;

import android.content.Intent;
import android.os.Bundle;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class Cartlist extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CartlistAdapter adapter;
    private TextView totalPriceText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cartlist);


        ImageView imageView = findViewById(R.id.back);
        imageView.setOnClickListener(v -> {
            Intent intent = new Intent(Cartlist.this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        // Find views
        recyclerView = findViewById(R.id.cartlistRecyclerView);
        totalPriceText = findViewById(R.id.total_price_text);

        // Set up RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CartlistAdapter(Cart.getInstance().getItems());
        recyclerView.setAdapter(adapter);

        // Update the total price TextView
        updateTotalPrice();
    }

    public void updateTotalPrice() {
        double totalPrice = Cart.getInstance().getTotalPrice();
        totalPriceText.setText(String.format("Total: $%.2f", totalPrice));
    }

    // Your existing methods for the "Add To Cart" button and recreate()
    @Override
    protected void onResume() {
        super.onResume();
        updateTotalPrice();
        // Update the RecyclerView data if needed
        adapter.notifyDataSetChanged();
    }

}
