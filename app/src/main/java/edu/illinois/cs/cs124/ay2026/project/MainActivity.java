package edu.illinois.cs.cs124.ay2026.project;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);

        // Show the map screen by default when the app first opens.
        if (savedInstanceState == null) {
            loadFragment(new MapFragment());
            bottomNav.setSelectedItemId(R.id.nav_map);
        }

        bottomNav.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_map) {
                loadFragment(new MapFragment());
            } else if (itemId == R.id.nav_add) {
                loadFragment(new AddSightingFragment());
            } else if (itemId == R.id.nav_resources) {
                loadFragment(new ResourcesFragment());
            } else {
                return false;
            }
            return true;
        });
    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit();
    }
}
