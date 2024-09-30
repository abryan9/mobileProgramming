package edu.cosc4730.calculator;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.provider.Telephony;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.util.Arrays;

import edu.cosc4730.calculator.databinding.FragmentMainBinding;

public class MainFragment extends Fragment implements View.OnClickListener {

    FragmentMainBinding binding;

    String memString = "";
    String op1 = "";
    String op2 = "";
    int operand = 0;
    boolean negate = false;
    boolean decimal = false;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState
    ) {
        binding = FragmentMainBinding.inflate(inflater, container, false);

        binding.clearAll.setOnClickListener(this);
        binding.clearElement.setOnClickListener(this);
        binding.power.setOnClickListener(this);
        binding.divide.setOnClickListener(this);
        binding.seven.setOnClickListener(this);
        binding.eight.setOnClickListener(this);
        binding.nine.setOnClickListener(this);
        binding.multiply.setOnClickListener(this);
        binding.four.setOnClickListener(this);
        binding.five.setOnClickListener(this);
        binding.six.setOnClickListener(this);
        binding.subtract.setOnClickListener(this);
        binding.one.setOnClickListener(this);
        binding.two.setOnClickListener(this);
        binding.three.setOnClickListener(this);
        binding.add.setOnClickListener(this);
        binding.negate.setOnClickListener(this);
        binding.zero.setOnClickListener(this);
        binding.decimal.setOnClickListener(this);
        binding.enter.setOnClickListener(this);

        return binding.getRoot();
    }

    public void onClick(View v) {
        if (v == binding.clearAll) {
            op1 = "";
            op2 = "";
            operand = 0;
            negate = false;
            decimal = false;
            memString = "";
        } else if (v == binding.clearElement) {
            op2 = "";
            String[] memList = memString.split("/s");
            Log.v("ARRAY_LEN", Integer.toString(memList.length));
            Log.v("PARSE", Double.toString(Double.parseDouble(memList[memList.length-1])));
            if (memList.length >= 2) {
                if (Double.parseDouble(memList[memList.length-1]) != 0d) {
                    String[] trunked = Arrays.copyOf(memList, memList.length-2);
                    memString = Arrays.toString(trunked);
                } else {
                    memString = Arrays.toString(memList);
                }
            } else {
                memString = "";
                op1 = "";
                op2 = "";
                operand = 0;
                negate = false;
            }
            decimal = false;
        } else if (v == binding.add) {
            operand = 1;
            memString += " + ";
            decimal = false;
        } else if (v == binding.subtract) {
            operand = 2;
            memString += " - ";
            decimal = false;
        } else if (v == binding.multiply) {
            operand = 3;
            memString += " x ";
            decimal = false;
        } else if (v == binding.divide) {
            operand = 4;
            memString += " / ";
            decimal = false;
        } else if (v == binding.power) {
            operand = 5;
            memString += " ^ ";
            decimal = false;
        } else if (v == binding.zero) {
            if (operand == 0) {
                op1 += "0";
            } else {
                op2 += "0";
            }
            memString += "0";
        } else if (v == binding.one) {
            if (operand == 0) {
                op1 += "1";
            } else {
                op2 += "1";
            }
            memString += "1";
        } else if (v == binding.two) {
            if (operand == 0) {
                op1 += "2";
            } else {
                op2 += "2";
            }
            memString += "2";
        } else if (v == binding.three) {
            if (operand == 0) {
                op1 += "3";
            } else {
                op2 += "3";
            }
            memString += "3";
        } else if (v == binding.four) {
            if (operand == 0) {
                op1 += "4";
            } else {
                op2 += "4";
            }
            memString += "4";
        } else if (v == binding.five) {
            if (operand == 0) {
                op1 += "5";
            } else {
                op2 += "5";
            }
            memString += "5";
        } else if (v == binding.six) {
            if (operand == 0) {
                op1 += "6";
            } else {
                op2 += "6";
            }
            memString += "6";
        } else if (v == binding.seven) {
            if (operand == 0) {
                op1 += "7";
            } else {
                op2 += "7";
            }
            memString += "7";
        } else if (v == binding.eight) {
            if (operand == 0) {
                op1 += "8";
            } else {
                op2 += "8";
            }
            memString += "8";
        } else if (v == binding.nine) {
            if (operand == 0) {
                op1 += "9";
            } else {
                op2 += "9";
            }
            memString += "9";
        } else if (v == binding.negate) {
            if (operand == 0) {
                if (op1.compareTo("") != 0) {
                    op1 = Double.toString(-1 * Double.parseDouble(op1));
                }
            } else {
                if (op2.compareTo("") != 0) {
                    op2 = Double.toString(-1 * Double.parseDouble(op2));
                }
            }
            memString = " * -1 ";
        } else if (v == binding.decimal) {
            if (!decimal) {
                if (operand == 0) {
                    op1 += ".";
                } else {
                    op2 += ".";
                }
            }
            memString += ".";
        } else if (v == binding.enter) {
            setTotal();
        }

        if (operand == 0) {
            binding.active.setText(op1);
        } else {
            binding.active.setText(op2);
        }

        binding.memory.setText(memString);
    }

    @SuppressLint("SetTextI18n")
    public void setTotal() {
        double total;
        if (op1.compareTo("") == 0) {
            op1 = "0";
        }
        if (op2.compareTo("") == 0) {
            op2 = "0";
        }
        switch (operand) {
            case 0: default:
                total = Double.parseDouble(op1);
                break;
            case 1:
                total = Double.parseDouble(op1) + Double.parseDouble(op2);
                break;
            case 2:
                total = Double.parseDouble(op1) - Double.parseDouble(op2);
                break;
            case 3:
                total = Double.parseDouble(op1) * Double.parseDouble(op2);
                break;
            case 4:
                total = Double.parseDouble(op1) / Double.parseDouble(op2);
                break;
            case 5:
                total = Math.pow(Double.parseDouble(op1), Double.parseDouble(op2));
                break;
        }
        op1 = Double.toString(total);
        op2 = "";
        operand = 0;
    }

}
