package com.example.cafe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {
    private EditText email, password;
    private Button loginButton;
    private ProgressBar progressBar;

    TextView reset, register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        loginButton = findViewById(R.id.login);
        progressBar = findViewById(R.id.progress);

        reset = findViewById(R.id.reset_pass);
        register = findViewById(R.id.register);

        //hide_show password
        ImageView imageView = findViewById(R.id.show_hide_password);
        imageView.setImageResource(R.drawable.baseline_visibility_off_24);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (password.getTransformationMethod().equals(HideReturnsTransformationMethod.getInstance())){
                    //if password is visible the hide it
                    password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    //change the eye icon
                    imageView.setImageResource(R.drawable.baseline_visibility_24);
                }
                else {
                    password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    imageView.setImageResource(R.drawable.baseline_visibility_off_24);
                }
            }
        });

        reset.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), ResetPassword.class);
            startActivity(intent);
        });

        register = findViewById(R.id.register);
        register.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), Register.class);
            startActivity(intent);
        });

        loginButton.setOnClickListener(v -> loginUser());
    }
    private void loginUser() {
        progressBar.setVisibility(View.VISIBLE);
        loginButton.setVisibility(View.GONE);

        final String Email = email.getText().toString().trim();
        final String Password = password.getText().toString().trim();

        if (TextUtils.isEmpty(Email) || TextUtils.isEmpty(Password)) {
            Toast.makeText(getApplicationContext(), "Email and password are required", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
            loginButton.setVisibility(View.VISIBLE);
            return;
        }

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://192.168.30.62/caf/login.php", response -> {
            try {
                Log.e("RESPONSE", response);

                if (isJSONValid(response)) {
                    JSONObject jsonObject = new JSONObject(response);
                    String status = jsonObject.getString("status");
                    String message = jsonObject.getString("message");

                    if (status.equals("1")) {
                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                        // You can redirect to the main activity or perform other actions upon successful login.
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        Toast.makeText(this, "Logged in Successfully", Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Handle non-JSON response
                    Toast.makeText(getApplicationContext(), "Invalid server response.", Toast.LENGTH_SHORT).show();
                }

                progressBar.setVisibility(View.GONE);
                loginButton.setVisibility(View.VISIBLE);

            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
                loginButton.setVisibility(View.VISIBLE);
            }
        }, error -> {
            error.printStackTrace();
            Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
            loginButton.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("email", Email);
                params.put("password", Password);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    private boolean isJSONValid(String json) {
        try {
            new JSONObject(json);
            return true;
        } catch (JSONException e) {
            return false;
        }
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
