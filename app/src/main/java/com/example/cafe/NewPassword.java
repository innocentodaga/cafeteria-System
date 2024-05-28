package com.example.cafe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class NewPassword extends AppCompatActivity {
    EditText otp, newPassword, confirmPassword;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_password);

        String email = getIntent().getExtras().getString("email");
        otp = findViewById(R.id.otp);
        newPassword = findViewById(R.id.new_password);
        confirmPassword = findViewById(R.id.confirmPassword);
        button = findViewById(R.id.reset);
        ProgressBar progressBar = findViewById(R.id.progress);

        button.setOnClickListener(v -> {
            progressBar.setVisibility(View.VISIBLE);
            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
            String url = "http://192.168.30.62/caf/newpassword.php";  // Fix the URL

            // Request a string response from the provided URL.
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            progressBar.setVisibility(View.GONE);
                            if (response.equals("success")) {
                                Toast.makeText(getApplicationContext(), "Password has been reset", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), Login.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                    progressBar.setVisibility(View.GONE);  // Hide the progress bar in case of an error
                    Toast.makeText(getApplicationContext(), "" + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("email", email);
                    params.put("otp", otp.getText().toString());
                    params.put("new_password", newPassword.getText().toString());

                    return params;
                }
            };

            // Add the request to the RequestQueue.
            queue.add(stringRequest);
        });
    }
}
