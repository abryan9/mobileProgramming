package edu.cosc4730.firstapp;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import edu.cosc4730.firstapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements MainFragment.NameListener {

    // mainFragment and TAG could be defined here, but have been moved to a more localized scope

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Android Studio initialized with this, so I opted to keep it even know it's not super necessary
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main_container), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // AS was dead set on redefining this line like this
        edu.cosc4730.firstapp.databinding.ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (savedInstanceState == null) {
            // requirement 1b
            MainFragment mainFragment = new MainFragment();
            getSupportFragmentManager().beginTransaction().add(binding.mainContainer.getId(), mainFragment).commit();
        }
    }

    @Override
    // requirement 1a
    public void onTextLogged(String text) {
        Log.v("MAIN_OUTPUT_LOG", "[+] ADDED: " + text);
    }
}