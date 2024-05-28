package com.example.cafe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private Button register;
    private EditText username, email, pass, confrirmpass;
    private ProgressBar progressBar;
    private TextView login;
    private String selectedUserType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        register = findViewById(R.id.reg);
        username = findViewById(R.id.username);
        email = findViewById(R.id.email);
        pass = findViewById(R.id.password);
        confrirmpass = findViewById(R.id.confirm_pass);
        progressBar = findViewById(R.id.progress);

        login = findViewById(R.id.login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
            }
        });
        //hide_show password
        ImageView imageView = findViewById(R.id.showhidepass);
        ImageView imageView1 = findViewById(R.id.showhideconfirmpass);
        imageView.setImageResource(R.drawable.baseline_visibility_off_24);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pass.getTransformationMethod().equals(HideReturnsTransformationMethod.getInstance())){
                    //if password is visible the hide it
                    pass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    //change the eye icon
                    imageView.setImageResource(R.drawable.baseline_visibility_24);
                }
                else {
                    pass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    imageView.setImageResource(R.drawable.baseline_visibility_off_24);
                }
            }
        });
        //show hide password for confirm
        imageView1.setImageResource(R.drawable.baseline_visibility_off_24);
        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (confrirmpass.getTransformationMethod().equals(HideReturnsTransformationMethod.getInstance())){
                    //if password is visible the hide it
                    confrirmpass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    //change the eye icon
                    imageView1.setImageResource(R.drawable.baseline_visibility_24);
                }
                else {
                    confrirmpass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    imageView1.setImageResource(R.drawable.baseline_visibility_off_24);
                }
            }
        });

        // Spinner codes
        Spinner spinner = findViewById(R.id.usertypes);
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(
                this, R.array.usertype, R.layout.spinner_item_color);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(this);

        register.setOnClickListener(v -> register());
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        selectedUserType = parent.getItemAtPosition(position).toString();
        saveCredentials(username.getText().toString(), email.getText().toString(), selectedUserType);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // Handle when nothing is selected in the spinner
    }

    private void saveCredentials(String username, String email, String userType) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("username", username);
        editor.putString("email", email);
        editor.putString("userType", userType);
        editor.apply();
    }

    public void register() {
        progressBar.setVisibility(View.VISIBLE);
        register.setVisibility(View.GONE);

        final String Username = username.getText().toString().trim();
        final String Email = email.getText().toString().trim();
        final String Password = pass.getText().toString().trim();

        if (TextUtils.isEmpty(Username) || TextUtils.isEmpty(Email) || TextUtils.isEmpty(Password)) {
            Toast.makeText(getApplicationContext(), "All fields are required", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
            register.setVisibility(View.VISIBLE);
            return;
        }

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://192.168.30.62/caf/register.php", response -> {
            try {
                Log.e("RESPONSE", response);

                if (isJSONValid(response)) {
                    JSONObject jsonObject = new JSONObject(response);
                    String status = jsonObject.getString("status");
                    String message = jsonObject.getString("message");

                    if (status.equals("1")) {
                        saveCredentials(Username, Email, selectedUserType);
                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), Login.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Invalid server response.", Toast.LENGTH_SHORT).show();
                }

                progressBar.setVisibility(View.GONE);
                register.setVisibility(View.VISIBLE);

            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
                register.setVisibility(View.VISIBLE);
            }
        }, error -> {
            error.printStackTrace();
            Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
            register.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("username", Username);
                params.put("email", Email);
                params.put("password", Password);
                params.put("user_type", selectedUserType);
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
}
