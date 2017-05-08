package com.example.ivaylo.project_x_app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button sendBTN;
    EditText scoreInput;
    EditText scoreInput2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sendBTN = (Button) findViewById(R.id.button);
        sendBTN.setOnClickListener(this);
        scoreInput = (EditText) findViewById(R.id.score_input);
        scoreInput2 = (EditText) findViewById(R.id.score_input2);
    }

    @Override
    public void onClick(View v) {
        double  input1 = Double.parseDouble(scoreInput.getText().toString());
        double  input2 = Double.parseDouble(scoreInput2.getText().toString());
        double input = (input1 + input2)/2;
        final String data = Double.toString(input);
        Log.d("dd",data);

        Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    System.out.println("Starting Connection");
                    Socket s = new Socket("192.168.1.103", 3306);
                    System.out.println("Connection DONE");
                    DataOutputStream dos = new DataOutputStream(s.getOutputStream());
                    dos.writeUTF(data);
                    dos.flush();
                    dos.close();
                    s.close();
                    System.out.println("Closing socket");
                } catch (UnknownHostException e) {
                    System.out.println("There was an Unknown Erorr:");
                    e.printStackTrace();
                } catch (IOException e) {
                    System.out.println("There was an IOException:");
                    e.printStackTrace();
                }
            }
        };
        t.start();
        Toast.makeText(this, "Messagge Sent...", Toast.LENGTH_SHORT).show();
    }
}
