package com.example.cafe;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class OrderNow extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_now);

        ImageView imageView = findViewById(R.id.back);
        TextView name = findViewById(R.id.coffee_text);
        TextView price = findViewById(R.id.coffe_price);
        TextView desc = findViewById(R.id.coffee_description);

        imageView.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        });

        TextView addToCartTextView = findViewById(R.id.coffee_add_tocart);

        addToCartTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle the logic to add the coffee to the cart
                addToCart();
            }
        });

        TextView orderCoffeeNow = findViewById(R.id.ordercoffeenow);

        orderCoffeeNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the "Order Now" button click
                orderCoffee();
            }
        });
    }

    private void addToCart() {
        // Add the logic to add the coffee to the cart
        // For example, you can create a CartItem object and add it to a cart list
        // Replace the following example with your actual implementation

        // Sample code:
        CartItem cartItem = new CartItem("Coffee", "Each cup of coffee is a journey through a world of flavors and sensations.", 3.99, R.drawable.coff);
        Cart.getInstance().addItem(cartItem);

        // Show a toast to indicate that the coffee is added to the cart
        Toast.makeText(this, "Coffee added to cart", Toast.LENGTH_SHORT).show();
    }

    private void orderCoffee() {
        // Get relevant information like coffee name, price, description, etc.
        String coffeeName = "Coffee"; // Change this based on your actual data
        double coffeePrice = 3.9; // Change this based on your actual data
        String coffeeDescription = "Each cup of coffee is a journey through a world of flavors and sensations."; // Change this based on your actual data

        // Use Volley to send the order data to the server
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = "http://your_server_url/order.php"; // Replace with your server URL

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Handle the server response (if needed)
                        Toast.makeText(OrderNow.this, response, Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle errors
                        Toast.makeText(OrderNow.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                // Send parameters to the server (e.g., coffee name, price, description)
                Map<String, String> params = new HashMap<>();
                params.put("coffeeName", coffeeName);
                params.put("coffeePrice", String.valueOf(coffeePrice));
                params.put("coffeeDescription", coffeeDescription);
                // Add more parameters as needed

                return params;
            }
        };

        // Add the request to the RequestQueue
        requestQueue.add(stringRequest);
    }
}
