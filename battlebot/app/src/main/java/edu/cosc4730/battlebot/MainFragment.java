package edu.cosc4730.battlebot;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import edu.cosc4730.battlebot.databinding.FragmentMainBinding;

public class MainFragment extends Fragment implements View.OnClickListener {
    Pattern ip_pattern = Pattern.compile("^[0-9]{1,3}.[0-9]{1,3}.[0-9]{1,3}.[0-9]{1,3}$", Pattern.CASE_INSENSITIVE);
    Pattern op_pattern = Pattern.compile("^Status ([0-9]* [0-9]* )([-0-9]*) ([-0-9]*) [0-9]*$", Pattern.CASE_INSENSITIVE);

    int port = 3012;
    NetConnection network = new NetConnection();
    Thread netThread = new Thread(network);
    String ip;
    boolean is_connected = false;

    String command = "cowpokes 0 4 1";
    private FragmentMainBinding binding;

    @Override
    public View onCreateView(
        @NonNull LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState
    ) {
        binding = FragmentMainBinding.inflate(inflater, container, false);

        binding.moveUp.setOnClickListener(this);
        binding.moveDown.setOnClickListener(this);
        binding.moveLeft.setOnClickListener(this);
        binding.moveRight.setOnClickListener(this);

        binding.shootUp.setOnClickListener(this);
        binding.shootDown.setOnClickListener(this);
        binding.shootLeft.setOnClickListener(this);
        binding.shootRight.setOnClickListener(this);

        binding.connect.setOnClickListener(this);
        binding.noop.setOnClickListener(this);

        binding.ipAddr.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String text = binding.ipAddr.getText().toString();
                if (text.compareTo("") != 0) {
                    Matcher ip_matcher = ip_pattern.matcher(text);
                    boolean matchFound = ip_matcher.find();
                    if (matchFound) {
                        binding.connect.setVisibility(View.VISIBLE);
                        binding.connect.setClickable(true);
                        binding.ipAddr.setFreezesText(true);
                    }
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void afterTextChanged(Editable s) { }
        });

        return binding.getRoot();
    }

    public void onClick(View v) {
        if (v.getTag().toString().compareTo("connect") == 0) {
            ip = binding.ipAddr.getText().toString();
            netThread.start();
            binding.ipAddr.setFreezesText(true);
        } else {
            command = (String) v.getTag();
            Log.v("COMMAND", command);
            binding.currentop.setText(command);
        }
    }

    class NetConnection implements Runnable {
        public void run() {
            try {
                InetAddress serverAddr = InetAddress.getByName(ip);
                Socket socket = new Socket(serverAddr, port);

                PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                String serverComm;
                boolean game = true;
                while (game) {
                    serverComm = in.readLine();
                    Matcher server_match = op_pattern.matcher(serverComm);
                    if (serverComm.startsWith("Status")) {
                        if (command.startsWith("move") && (Integer.parseInt(server_match.group(1)) >= 0)) {
                            out.println(command);
                        } else if (command.startsWith("shoot") && (Integer.parseInt(server_match.group(2)) >= 0)) {
                            out.println(command);
                        }
                        out.println("noop");
                    } else if (serverComm.startsWith("Info Dead") || serverComm.startsWith("Info GameOver")) {
                        game = false;
                    } else if (serverComm.startsWith("setup")) {
                        out.println("cowpokes 4 1 0");
                    }
                }

                socket.close();
                in.close();
                out.close();

                try {
                    out.println("cowpokes 4 1 0");
                    in.readLine();

                } catch (Exception e) {
                    Log.v("COMM_ERR", "error when trying to communicate with the server.");
                }

                is_connected = true;
            } catch (Exception e) {
                Log.v("NET_ERR", "Unable to connect.");
            }
        }
    }
}
