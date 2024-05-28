package com.example.cafe;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;

import java.io.IOException;

public class ProfileFragment extends Fragment {

    private TextView showFirstname;
    private TextView showLastname;
    private TextView showUsername;
    private TextView showEmail;
    private TextView showUsertype;
    private Button editProfile;
    Bitmap bitmap;
    ImageView imageView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        // Initialize your views
        showFirstname = view.findViewById(R.id.showfirstname);
        showLastname = view.findViewById(R.id.show_lastname);
        showUsername = view.findViewById(R.id.showusername);
        showEmail = view.findViewById(R.id.show_email);
        showUsertype = view.findViewById(R.id.show_usertype);
        editProfile = view.findViewById(R.id.edit_profile);

        Button button= view.findViewById(R.id.edit_profile);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), EditProfile.class);
                startActivity(intent);

            }
        });

        imageView = view.findViewById(R.id.profile_image);
        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode()== Activity.RESULT_OK){
                    Intent data = result.getData();
                    Uri uri = data.getData();
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(requireActivity().getContentResolver(), uri);
                        imageView.setImageBitmap(bitmap);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                activityResultLauncher.launch(intent);
            }
        });

        // Call a method to fetch and display user information
        fetchAndDisplayUserData();

        return view;
    }

    private void fetchAndDisplayUserData() {
        // You'll implement this method to fetch user data and set it to the views

        // For simplicity, let's assume there's a UserDataFetcher class to handle this logic
        UserDataFetcher userDataFetcher = new UserDataFetcher();
        userDataFetcher.fetchUserData(requireContext(), "Tyson", new UserDataFetcher.UserDataCallback() {
            @Override
            public void onSuccess(UserData userData) {
                // Set user data to the corresponding views
                showFirstname.setText(userData.getFirstName());
                showLastname.setText(userData.getLastName());
                showUsername.setText(userData.getUsername());
                showEmail.setText(userData.getEmail());
                showUsertype.setText(userData.getUserType());
            }

            @Override
            public void onError(String errorMessage) {
                // Handle error cases
                // You can show a toast or log the error
            }
        });
    }
}
