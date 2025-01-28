package com.example.exacuity;

import android.os.Bundle;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 5)); // 5 colunas

        String[] symbols = {"E", "H", "Ш", "E", "M", "E", "H", "Ш", "E", "M", "E", "Ш", "E", "M", "G"};
        GridAdapter adapter = new GridAdapter(this, symbols);
        recyclerView.setAdapter(adapter);
    }
}
