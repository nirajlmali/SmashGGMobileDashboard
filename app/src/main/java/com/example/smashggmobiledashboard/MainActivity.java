package com.example.smashggmobiledashboard;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.net.ProtocolException;

//Local

public class MainActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = (Button)findViewById(R.id.button);
        button.setOnClickListener(mainListener);

    }

    private View.OnClickListener mainListener = new View.OnClickListener() {
        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        public void onClick(View v) {
            EditText et = (EditText)findViewById(R.id.editText);
            et.setText("Hello");
            SmashGG obj = new SmashGG();
            try {
                Log.d("REQUEST", "onCreate: TESTING REQUEST");
                String x = obj.testRequest();
                et.setText(x);
            } catch (ProtocolException e) {
                e.printStackTrace();
            }
        }
    };
}
