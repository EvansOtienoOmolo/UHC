package com.example.uhc;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class AdminDataBackup extends AppCompatActivity {

    private FirebaseFirestore db;
    private CollectionReference suggestionRef;

    private NoteAdapterBackUp adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_data);

        db = FirebaseFirestore.getInstance();
        suggestionRef = db.collection("Suggestion");


        setUpRecyclerView();
    }

    private void setUpRecyclerView() {
        Query query = suggestionRef.orderBy("serial",Query.Direction.DESCENDING);

        FirestoreRecyclerOptions<productAD> options = new FirestoreRecyclerOptions.Builder<productAD>()
                .setQuery(query, productAD.class)
                .build();

        adapter = new NoteAdapterBackUp(options);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}
