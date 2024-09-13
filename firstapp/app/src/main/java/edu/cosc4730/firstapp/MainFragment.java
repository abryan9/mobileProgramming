package edu.cosc4730.firstapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.app.Activity;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import edu.cosc4730.firstapp.databinding.FragmentMainBinding;

public class MainFragment extends Fragment implements Button.OnClickListener {
    private FragmentMainBinding binding;
    private NameListener listener;

    public interface NameListener {
        void onTextLogged(String text);
    }

    // Init and Destroy functions
    // ---------------------------------------------------------------------------------------------
    // SuppressLint gets rid of the stupid "can't translate" warnings
    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // pack binding and define listeners
        // requirement 2a
        binding = FragmentMainBinding.inflate(inflater, container, false);
        binding.enterButton.setOnClickListener(this);
        // initial variables
        // init for requirement 5
        binding.output.setText("Hello, World!");

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Activity activity = requireActivity();
        try {
            listener = (MainActivity) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity
                    + " [-] CLASS ERROR: Must implement NameListener.");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }
    // ---------------------------------------------------------------------------------------------

    // event listeners
    // ---------------------------------------------------------------------------------------------
    @SuppressLint("SetTextI18n")
    public void onClick(View v) {
        // I define this listener here instead of inside of the function call in line 34
        // there is no reason, I just felt like it
        // requirement 5
        if (v == binding.enterButton) {
            String box_contents = binding.nameInput.getText().toString();
            if (box_contents.compareTo("") != 0) {
                binding.output.setText("Hello, " + box_contents + "!");
            } else {
                binding.output.setText("Hello, World!");
            }
            Log.v("FRAGMENT_OUTPUT_LOG", "[!] SET: " + box_contents);
            if (listener != null) {
                // requirement 2b
                listener.onTextLogged(box_contents);
            }

        }
    }
    // ---------------------------------------------------------------------------------------------
}
