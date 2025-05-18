package com.example.garacar;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AdminDashboardActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        bottomNavigationView = findViewById(R.id.bottomNavigationAdmin);

        // Mặc định load AdminServiceFragment
        if (savedInstanceState == null) {
            loadFragment(new AdminServiceFragment());
        }

        bottomNavigationView.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;

            if (item.getItemId() == R.id.nav_admin_service) {
                selectedFragment = new AdminServiceFragment();
//            } else if (item.getItemId() == R.id.nav_admin_user) {
//                selectedFragment = new AdminUserFragment();
            } else if (item.getItemId() == R.id.nav_admin_booking) {
                selectedFragment = new AdminBookingFragment();
            } else if (item.getItemId() == R.id.nav_admin_news) {
                selectedFragment = new AdminNewsFragment();
            }

            if (selectedFragment != null) {
                loadFragment(selectedFragment);
            }

            return true;
        });
    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.adminFragmentContainer, fragment);
        transaction.commit();
    }
}
