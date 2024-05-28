package com.example.cafe;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class LunchFragment extends Fragment {

    private List<FoodModel> lunchItems;
    private RecyclerView recyclerView;
    private FoodItemAdapter adapter;
    private String url = "http://192.168.43.127/caf/fetchlunch.php";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lunch, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext());
        recyclerView.setLayoutManager(layoutManager);

        // Fetch data from the server using Volley
        fetchData();

        return view;
    }

    private void fetchData() {
        JsonObjectRequest request = new JsonObjectRequest(url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Log the raw JSON response for debugging
                        Log.d("JSON Response", response.toString());

                        // Parse the JSON response and initialize the adapter
                        FoodItemAdapter foodItemAdapter = new FoodItemAdapter(requireContext(), parseJsonResponse(response));
                        recyclerView.setAdapter(foodItemAdapter);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Log the error for debugging
                Log.e("VolleyError", "Error fetching data", error);

                // Show the error message in a toast
                Toast.makeText(requireContext(), "Error fetching data", Toast.LENGTH_SHORT).show();
            }
        });

        // Log the URL for debugging
        Log.d("VolleyRequest", "Requesting data from: " + url);

        RequestQueue requestQueue = Volley.newRequestQueue(requireContext());
        requestQueue.add(request);
    }

    private List<FoodModel> parseJsonResponse(JSONObject response) {
        List<FoodModel> items = new ArrayList<>();

        try {
            // Assuming your JSON response is an array with key "items"
            JSONArray jsonArray = response.getJSONArray("items");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject itemObject = jsonArray.getJSONObject(i);

                String itemName = itemObject.getString("mealname");
                String itemDesc = itemObject.getString("description");
                String itemPrice = itemObject.getString("price");

                // Create a FoodModel object
                FoodModel foodItem = new FoodModel();
                foodItem.setItemName(itemName);
                foodItem.setItemdesc(itemDesc);
                foodItem.setItemprice(itemPrice);

                // Add the FoodModel object to the list
                items.add(foodItem);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return items;
    }
}
