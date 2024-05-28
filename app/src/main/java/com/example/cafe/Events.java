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

public class Events extends AppCompatActivity {

    private RecyclerEventsAdapter recyclerEventsAdapter;
    private RecyclerView recyclerView;
    private List<EventsModel> eventsModelList;
    private String url = "http://192.168.30.62/caf/fetchevents.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);

        ImageView imageView = findViewById(R.id.back);
        imageView.setOnClickListener(v -> {
            Intent intent = new Intent(Events.this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        recyclerView = findViewById(R.id.eventsRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        eventsModelList = new ArrayList<>();
        LoadAllEvents();

        ImageView back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });


    }

    private void LoadAllEvents() {
        JsonArrayRequest request = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray array) {
                for (int i = 0; i < array.length(); i++) {
                    try {
                        JSONObject object = array.getJSONObject(i);
                        String eventname = object.getString("eventname").trim();
                        String eventdesc = object.getString("description").trim();
                        String eventdate = object.getString("eventdate").trim();

                        EventsModel eventsModel = new EventsModel();
                        eventsModel.setName(eventname);
                        eventsModel.setDate(eventdate);
                        eventsModel.setDescription(eventdesc);

                        eventsModelList.add(eventsModel);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                //Initialize the adapter and set it to the RecyclerView
                recyclerEventsAdapter = new RecyclerEventsAdapter(Events.this, eventsModelList);
                recyclerView.setAdapter(recyclerEventsAdapter);
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Events.this, error.toString(), Toast.LENGTH_SHORT).show();

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(Events.this);
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