package com.example.cafe;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;

import java.io.IOException;

import nl.joery.animatedbottombar.AnimatedBottomBar;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private ImageView profilepic;
    private AnimatedBottomBar animatedBottomBar;
    private static final String PREF_PROFILE_PIC_URI = "profile_pic_uri";
    private static final int REQUEST_READ_EXTERNAL_STORAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //locating the different elements
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        toolbar = findViewById(R.id.toolbar);
        animatedBottomBar = findViewById(R.id.bottom_bar);
        profilepic = findViewById(R.id.profile_picture);

        // Load the profile picture URI from SharedPreferences
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String profilePicUriString = preferences.getString(PREF_PROFILE_PIC_URI, null);
        if (profilePicUriString != null) {
            Uri profilePicUri = Uri.parse(profilePicUriString);
            loadProfilePicture(profilePicUri);
        }

        // Set up the Activity Result Launcher
        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        Uri uri = data.getData();
                        saveProfilePicture(uri);
                        loadProfilePicture(uri);
                    }
                });

        profilepic.setOnClickListener(v -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            REQUEST_READ_EXTERNAL_STORAGE);
                } else {
                    launchImagePicker(activityResultLauncher);
                }
            } else {
                launchImagePicker(activityResultLauncher);
            }
        });

        // Set up the Toolbar
        setSupportActionBar(toolbar);

        // Set up the ActionBarDrawerToggle
        actionBarDrawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
        );
        drawerLayout.addDrawerListener(actionBarDrawerToggle);

        // Enable the hamburger icon
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        // Set the default fragment to home
        replace(new HomeFragment());

        // When the tab is selected by the user
        animatedBottomBar.setOnTabSelectListener(new AnimatedBottomBar.OnTabSelectListener() {
            @Override
            public void onTabSelected(int i, AnimatedBottomBar.Tab tab, int i1, AnimatedBottomBar.Tab tab1) {
                if (tab1.getId() == R.id.home) {
                    replace(new HomeFragment());
                } else if (tab1.getId() == R.id.explore) {
                    replace(new ExploreFragment());
                } else if (tab1.getId() == R.id.orders) {
                    replace(new OrdersFragment());
                } else if (tab1.getId() == R.id.profile) {
                    replace(new ProfileFragment());
                }
            }

            @Override
            public void onTabReselected(int i, AnimatedBottomBar.Tab tab) {
            }
        });

        // Set up navigation item click listener
        navigationView.setNavigationItemSelectedListener(item -> {
            // Handle navigation item clicks here
            if (item.getItemId() == R.id.events) {
                startActivity(new Intent(MainActivity.this, Events.class));
            } else if (item.getItemId() == R.id.history) {
                startActivity(new Intent(MainActivity.this, History.class));
            } else if (item.getItemId() == R.id.settings) {
                startActivity(new Intent(MainActivity.this, Settings.class));
            } else if (item.getItemId() == R.id.cartlist) {
                startActivity(new Intent(MainActivity.this, Cartlist.class));
            } else if (item.getItemId() == R.id.logout) {
                startActivity(new Intent(MainActivity.this, Login.class));
            } else if (item.getItemId() == R.id.aboutus) {
            startActivity(new Intent(MainActivity.this, AboutUs.class));
            }

            // Close the drawer
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });
    }

    private void saveProfilePicture(Uri uri) {
        // Save the selected profile picture URI to SharedPreferences
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(PREF_PROFILE_PIC_URI, uri.toString());
        editor.apply();
    }

    private void loadProfilePicture(Uri uri) {
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
            profilepic.setImageBitmap(bitmap);
        } catch (IOException e) {
            Log.e("ImagePicker", "Error loading image", e);
        }
    }

    private void launchImagePicker(ActivityResultLauncher<Intent> launcher) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        launcher.launch(intent);
    }

    private void replace(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(android.content.res.Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        actionBarDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void recreate() {
        super.recreate();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

}
