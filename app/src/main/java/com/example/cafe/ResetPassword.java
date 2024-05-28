package com.example.cafe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class ResetPassword extends AppCompatActivity {

    TextView textView;
    EditText email;
    Button reset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        email = findViewById(R.id.email);
        textView = findViewById(R.id.already);
        reset = findViewById(R.id.get_otp);
        ProgressBar progressBar = findViewById(R.id.progress);

        reset.setOnClickListener(v -> {
            progressBar.setVisibility(View.VISIBLE);
            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
            String url = "http://192.168.30.62/caf/resetpassword.php";  // Fix the URL

            // Request a string response from the provided URL.
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    response -> {
                        progressBar.setVisibility(View.GONE);
                        if (response.equals("success")) {
                            Toast.makeText(ResetPassword.this, "OTP sent successfully", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(ResetPassword.this, NewPassword.class);
                            startActivity(intent);
                            intent.putExtra("email", email.getText().toString());
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                        }
                    }, error -> {
                error.printStackTrace();
                progressBar.setVisibility(View.GONE);  // Hide the progress bar in case of an error
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("email", email.getText().toString());
                    return params;
                }
            };
            // Set a timeout for the request (e.g., 20 seconds)
            int timeoutMilliseconds = 20000; // 20 seconds
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                    timeoutMilliseconds,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
            ));

            // Add the request to the RequestQueue.
            queue.add(stringRequest);
        });

        textView.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
        });
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
