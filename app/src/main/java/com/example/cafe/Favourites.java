package com.example.cafe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Favourites extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerFavAdapter recyclerFavAdapter;
    private List<FavModel> favModelList;

    private String url = "http://192.168.30.62/caf/fetchfavourites.php";

    ImageView imageView;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_favourites);

            // Initialize the RecyclerView
            recyclerView = findViewById(R.id.recyclerView);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));

            favModelList = new ArrayList<>();
            LoadAllFavmeals();

            imageView = findViewById(R.id.back);
            imageView.setOnClickListener(v -> {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            });
        }

        private void LoadAllFavmeals() {
            JsonArrayRequest request = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray array) {
                    for (int i = 0; i < array.length(); i++) {
                        try {
                            JSONObject object = array.getJSONObject(i);
                            String mealname = object.getString("mealname").trim();
                            String mealdesc = object.getString("description").trim();
                            String mealprice = object.getString("price").trim();
                            String mealtype = object.getString("mealtype").trim();

                            FavModel favModel = new FavModel();
                            favModel.setName(mealname);
                            favModel.setDescription(mealdesc);
                            favModel.setNewPrice(mealprice);
                            favModel.setMealtype(mealtype);

                            favModelList.add(favModel);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    // Initialize the adapter and set it to the RecyclerView
                    recyclerFavAdapter = new RecyclerFavAdapter(Favourites.this, favModelList);
                    recyclerView.setAdapter(recyclerFavAdapter);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(Favourites.this, error.toString(), Toast.LENGTH_SHORT).show();
                }
            });

            RequestQueue requestQueue = Volley.newRequestQueue(Favourites.this);
            requestQueue.add(request);
        }

    //removing eye flickers
    @Override
    public void recreate(){
        finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        startActivity(getIntent());
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}
