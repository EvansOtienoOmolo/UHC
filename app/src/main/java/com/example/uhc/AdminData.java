package com.example.uhc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class AdminData extends AppCompatActivity {

    private static final String TAG = "AdminData";

    private FirebaseFirestore db;

    private NoteAdapter adapter;

    private List<productAD> products;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_data);

        db = FirebaseFirestore.getInstance();

        products = new ArrayList<>();


        setUpRecyclerView();
    }

    private void setUpRecyclerView() {

        adapter = new NoteAdapter(products, this);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

         db.collection("Suggestion")
                 .addSnapshotListener(
                         (querySnapshots, e) -> {

                             if(e != null){

                                 Log.e(TAG, "setUpRecyclerView: ", e);
                                 return;

                             }

                             if(!querySnapshots.isEmpty()){

                                 for(DocumentSnapshot doc: querySnapshots.getDocuments()){
                                     productAD product = doc.toObject(productAD.class);

                                     products.add(product);
                                     adapter.notifyDataSetChanged();
                                 }

                             }else{
                                 Toast.makeText(this, "No Products", Toast.LENGTH_SHORT).show();
                             }

                         }
                 );

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
