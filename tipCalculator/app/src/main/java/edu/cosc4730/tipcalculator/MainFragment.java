package edu.cosc4730.tipcalculator;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.text.NumberFormat;

import edu.cosc4730.tipcalculator.databinding.FragmentMainBinding;

public class MainFragment extends Fragment {

    FragmentMainBinding binding;

    double bill = 0.00;
    double tip = 0.00;
    int switch_progress = 1;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState
    ) {
        binding = FragmentMainBinding.inflate(inflater, container, false);
        binding.billEnter.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                binding.billEnter.removeTextChangedListener(this);
                String clean = s.toString().replaceAll("[$.,]", "");
                double parsed = Double.parseDouble(clean);
                String formatted = NumberFormat.getCurrencyInstance().format(parsed/100);
                bill = parsed;
                binding.billEnter.setText(formatted);
                binding.billEnter.setSelection(formatted.length());
                binding.billEnter.addTextChangedListener(this);
                setTotal(binding);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void afterTextChanged(Editable s) { }
        });
        binding.tipEnter.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                binding.tipEnter.removeTextChangedListener(this);
                String clean = s.toString().replaceAll("[$.,]", "");
                double parsed = Double.parseDouble(clean);
                String formatted = NumberFormat.getCurrencyInstance().format(parsed/100);
                tip = parsed;
                binding.tipEnter.setText(formatted);
                binding.tipEnter.setSelection(formatted.length());
                binding.tipEnter.addTextChangedListener(this);
                setTotal(binding);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void afterTextChanged(Editable s) { }
        });
        binding.tenPercent.setOnClickListener(new View.OnClickListener() {

            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                tip = (double) Math.round((bill * 0.1));
                binding.tipEnter.setText(Double.toString(tip/10));
                setTotal(binding);
            }
        });
        binding.fifteenPercent.setOnClickListener(new View.OnClickListener() {

            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                tip = (double) Math.round((bill * 0.15));
                binding.tipEnter.setText(Double.toString(tip/10));
                setTotal(binding);
            }
        });
        binding.tipCustom.addTextChangedListener(new TextWatcher() {

            @SuppressLint("SetTextI18n")
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tip = bill*(Double.parseDouble(s.toString())/100);
                binding.tipEnter.setText(Double.toString(tip));
                setTotal(binding);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void afterTextChanged(Editable s) { }
        });

        binding.threeSwitch.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @SuppressLint("SetTextI18n")
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                switch_progress = progress;
                setTotal(binding);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });
        return binding.getRoot();
    }

    @SuppressLint("SetTextI18n")
    public void setTotal(FragmentMainBinding binding) {
        Log.v("BILL", Double.toString(bill));
        Log.v("TIP", Double.toString(tip));
        String output = "";
        switch(switch_progress) {
            case 0:
                output = Double.toString(Math.ceil((bill+tip)/100));
                break;
            case 1: default:
                output = Double.toString((bill+tip)/100);
                break;
            case 2:
                output = Double.toString((bill/100)+Math.ceil((tip/100)));
                break;
        }

        String[] split_out = output.split("\\.", 2);
        if (split_out[1].length() == 1) {
            binding.totalOutput.setText(output+"0");
        } else {
            binding.totalOutput.setText(output);
        }
    }
}

