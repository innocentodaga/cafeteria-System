package com.example.cafe;

import android.content.Context;
import android.util.Log;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class UserDataFetcher {

    private static final String TAG = UserDataFetcher.class.getSimpleName();

    // This method should be modified based on how you fetch user data (e.g., from a database, API, etc.)
    public void fetchUserData(Context context, String username, final UserDataCallback callback) {
        // TODO: Replace the URL with your actual API endpoint
        String url = "http://192.168.17.62/caf/edituser.php" + username;

        RequestQueue queue = Volley.newRequestQueue(context);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            // Parse the JSON response
                            String firstName = response.getString("firstname");
                            String lastName = response.getString("lastname");
                            String email = response.getString("email");
                            String userType = response.getString("usertype");

                            // Create a UserData object
                            UserData userData = new UserData(firstName, lastName, username, email, userType);

                            // Pass the UserData object to the callback
                            callback.onSuccess(userData);
                        } catch (JSONException e) {
                            Log.e(TAG, "JSON parsing error: " + e.getMessage());
                            callback.onError("Error parsing JSON");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "Volley error: " + error.getMessage());
                        callback.onError("Volley error");
                    }
                }
        );

        queue.add(jsonObjectRequest);
    }

    // Callback interface to handle success and error cases
    public interface UserDataCallback {
        void onSuccess(UserData userData);

        void onError(String errorMessage);
    }
}
