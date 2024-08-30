package edu.cosc4730.firstapp;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.name_input), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        EditText name_input = (EditText)findViewById(R.id.name_input);
        Button enter_button = (Button)findViewById(R.id.enter_button);
        TextView display_box = (TextView)findViewById(R.id.display_box);

//        enter_button.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                String inner_text = String.valueOf(name_input.getText());
//
//                if (inner_text.equals("null")) {
//                    display_box.setText("Hello World!");
//                } else {
//                    display_box.setText("Hello " + inner_text);
//                }
//
//            }
//        });
    }
}