package com.example.cafe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

public class NewlyAdded extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerNewlyAdapter recyclerNewlyAdapter;
    private List<NewlyModel> modelList;
    private String url = "http://192.168.30.62/caf/fetchnewmeals.php";

    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newly_added);

        imageView =findViewById(R.id.back);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        recyclerView = findViewById(R.id.newly_added_recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        modelList = new ArrayList<>();
        LoadAllMeals();

    }

    private void LoadAllMeals() {
        JsonArrayRequest request = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray array) {
                for (int i = 0; i < array.length() ; i++) {
                    try {
                        JSONObject object = array.getJSONObject(i);
                        String mealname = object.getString("mealname").trim();
                        String mealdesc = object.getString("description").trim();
                        String mealprice = object.getString("price").trim();
                        String mealtype = object.getString("mealtype").trim();

                        NewlyModel newlyModel = new NewlyModel();
                        newlyModel.setName(mealname);
                        newlyModel.setDescription(mealdesc);
                        newlyModel.setNewPrice(mealprice);
                        newlyModel.setMealtype(mealtype);

                        modelList.add(newlyModel);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                recyclerNewlyAdapter = new RecyclerNewlyAdapter(NewlyAdded.this, modelList);
                recyclerView.setAdapter(recyclerNewlyAdapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(NewlyAdded.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(NewlyAdded.this);
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