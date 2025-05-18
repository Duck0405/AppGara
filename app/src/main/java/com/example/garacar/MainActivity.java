package com.example.garacar;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNavMenu);

        // Mặc định hiển thị HomeFragment khi mở ứng dụng
        if (savedInstanceState == null) {
            loadFragment(new HomeFragment());
        }

        // Lắng nghe sự kiện chọn item trong BottomNavigationView
        bottomNavigationView.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;

            // Xác định Fragment tương ứng với item được chọn
                    if (item.getItemId() == R.id.homeMenu) {
                        selectedFragment = new HomeFragment();
                    } else if (item.getItemId() == R.id.shopMenu) {
                        selectedFragment = new NewsFragment();
                    } else if (item.getItemId() == R.id.bagMenu) {
                        selectedFragment = new BookingHistoryFragment();
//                    } else if (item.getItemId() == R.id.favMenu) {
//                        selectedFragment = new FavoritesFragment();
                    } else if (item.getItemId() == R.id.profileMenu) {
                        selectedFragment = new ProfileFragment();
                    }
                    if (selectedFragment != null) {
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.nav_fragment, selectedFragment)
                                .commit();
                    }

                    return true;
        });
    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.nav_fragment, fragment);
        transaction.commit();
    }
}
