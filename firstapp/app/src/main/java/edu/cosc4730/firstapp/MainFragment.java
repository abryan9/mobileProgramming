package edu.cosc4730.firstapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import edu.cosc4730.firstapp.databinding.FragmentMainBinding;

public class MainFragment extends Fragment implements Button.OnClickListener {
    private FragmentMainBinding binding;
    private NameListener listener;

    public interface NameListener {
        void OnTextLogged(String text);
    }

    String TAG = "FRAGMENT_OUTPUT_LOG";

    public MainFragment() {

    }

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMainBinding.inflate(inflater, container, false);

        binding.enterButton.setOnClickListener(this);
        binding.output.setText("Hello, World!");



        return binding.getRoot();
    }

    @SuppressLint("SetTextI18n")
    public void onClick(View v) {
        if (v == binding.enterButton) {
            String box_contents = binding.nameInput.getText().toString();
            if (binding.nameInput.getText().toString().compareTo("") != 0) {
                binding.output.setText("Hello, " + box_contents + "!");
            } else {
                binding.output.setText("Hello, World!");
            }
            Log.v(TAG, "[!] SET: " + box_contents);
            if (listener != null) {
                listener.OnTextLogged(box_contents);
            }

        }
    }
}
