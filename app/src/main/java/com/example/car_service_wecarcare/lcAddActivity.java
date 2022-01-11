package com.example.car_service_wecarcare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class lcAddActivity extends AppCompatActivity {

    EditText sertype, cartype, fuelexpert, picktime, delitime, extrachar;
    Button btnAdd, btnBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lc_add);

        sertype = (EditText) findViewById(R.id.sertypetext);
        cartype = (EditText) findViewById(R.id.cartypetext);
        fuelexpert = (EditText) findViewById(R.id.fuelexperttext);
        picktime = (EditText) findViewById(R.id.picktimetext);
        delitime = (EditText) findViewById(R.id.delitimetext);
        //extrachar = (EditText)findViewById(R.id.extrachartext);


        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnBack = (Button) findViewById(R.id.btnBack);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertData();
//                clearAll();

            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void insertData() {

        String psertype = sertype.getText().toString().trim();
        String pcartype = cartype.getText().toString().trim();
        String pfuelexpert = fuelexpert.getText().toString().trim();
        String ppicktime = picktime.getText().toString().trim();
        String pdelitime = delitime.getText().toString().trim();


        if (psertype.isEmpty()) {
            sertype.setError("Serviced Type is required");
            sertype.requestFocus();
        } else if (pcartype.isEmpty()) {
            cartype.setError("Car Type is required");
            cartype.requestFocus();
        } else if (pfuelexpert.isEmpty()) {
            fuelexpert.setError("Fuel expected required");
            fuelexpert.requestFocus();
        } else if (ppicktime.isEmpty()) {
            picktime.setError("Pick time required");
            picktime.requestFocus();
        } else if (pdelitime.isEmpty()) {
            delitime.setError("Delivery Time required");
            delitime.requestFocus();
        } else {


            Map<String, Object> map = new HashMap<>();
            map.put("sertype", sertype.getText().toString());
            map.put("cartype", cartype.getText().toString());
            map.put("fuelexpert", fuelexpert.getText().toString());
            map.put("picktime", picktime.getText().toString());
            map.put("delitime", delitime.getText().toString());
            //map.put("extrachar",extrachar.getText().toString());


            //Calculation
            Integer deltimeno = Integer.valueOf(delitime.getText().toString());
            Integer picktimeno = Integer.valueOf(picktime.getText().toString());

            String extrachar = String.valueOf((deltimeno - picktimeno) * 500);
            map.put("extrachar", String.valueOf(extrachar));




            FirebaseDatabase.getInstance("https://carserviceapp-fb926-default-rtdb.firebaseio.com/")
                    .getReference().child("vehicles").push()
                    .setValue(map)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(lcAddActivity.this, "Data Inserted Successfully", Toast.LENGTH_SHORT).show();
                            clearAll();
                        }
                    })

                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(Exception e) {
                            Toast.makeText(lcAddActivity.this, "Error While Creating a Loaner Car Request", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    private void clearAll() {

            sertype.setText("");
            cartype.setText("");
            fuelexpert.setText("");
            picktime.setText("");
            delitime.setText("");
            //extrachar.setText("");

        }

}