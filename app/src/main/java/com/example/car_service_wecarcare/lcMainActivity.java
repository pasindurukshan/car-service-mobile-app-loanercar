package com.example.car_service_wecarcare;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.FirebaseDatabase;

public class lcMainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    lcMainAdapter lcmainAdpater;

    FloatingActionButton floatingActionButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lcactivity_main);

        recyclerView = (RecyclerView)findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        FirebaseRecyclerOptions<lcMainModel> options =
                new FirebaseRecyclerOptions.Builder<lcMainModel>()
                        .setQuery(FirebaseDatabase.getInstance("https://carserviceapp-fb926-default-rtdb.firebaseio.com/")
                                .getReference().child("vehicles"), lcMainModel.class)
                        .build();

        lcmainAdpater = new lcMainAdapter(options);
        recyclerView.setAdapter(lcmainAdpater);

        floatingActionButton=(FloatingActionButton)findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),lcAddActivity.class));
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        lcmainAdpater.startListening();

    }

    @Override
    protected void onStop() {
        super.onStop();
        lcmainAdpater.stopListening();
    }


    //Search operation
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.lcsearch,menu);
        MenuItem item = menu.findItem(R.id.search);
        SearchView searchView = (SearchView)item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                txtSearch(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                txtSearch(query);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    private void txtSearch(String str)
    {
        FirebaseRecyclerOptions<lcMainModel> options =
                new FirebaseRecyclerOptions.Builder<lcMainModel>()
                        .setQuery(FirebaseDatabase.getInstance("https://carserviceapp-fb926-default-rtdb.firebaseio.com/")
                                .getReference().child("vehicles").orderByChild("sertype").startAt(str).endAt(str+"~"), lcMainModel.class)
                        .build();

        lcmainAdpater = new lcMainAdapter(options);
        lcmainAdpater.startListening();
        recyclerView.setAdapter(lcmainAdpater);

    }
}



































