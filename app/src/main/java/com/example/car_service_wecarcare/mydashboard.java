package com.example.car_service_wecarcare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class mydashboard extends AppCompatActivity {

    Button btnloaner,btntaxi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mydashboard);


        btnloaner=(Button)findViewById(R.id.loaner);
        btnloaner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),lcMainActivity.class));
            }
        });

        btntaxi=(Button)findViewById(R.id.taxi);
        btntaxi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),taxicar.class));
            }
        });

    }
}
