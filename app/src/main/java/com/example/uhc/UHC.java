package com.example.uhc;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

    public class UHC extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    String[] options;

    Spinner spinner;
    Button buttonAdmin;
    RatingBar rating_bar;
    TextView rating_display_text_View;
    EditText UHCComment;
    Button button2;
    TextView textViewA;

    private FirebaseFirestore db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uhc);


        //initializing firebase storage and firestore
        db = FirebaseFirestore.getInstance();



        //initializing my widgets
        spinner = (Spinner) findViewById(R.id.spinner);
        buttonAdmin = (Button) findViewById(R.id.buttonAdmin);
        textViewA = (TextView) findViewById(R.id.textViewA);

        textViewA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                texttoAdmin();
            }
        });


        buttonAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Admin();
            }
        });


        Spinner spinner = (Spinner) findViewById(R.id.spinner);

        rating_bar = (RatingBar) findViewById(R.id.rating_bar);
        rating_display_text_View = (TextView) findViewById(R.id.rating_display_text_View);
        buttonAdmin = (Button) findViewById(R.id.buttonAdmin);
        UHCComment = (EditText) findViewById(R.id.UHCComment);





        //implements the rating bar
        final RatingBar ratingRatingBar = (RatingBar) findViewById(R.id.rating_bar);
        Button submitButton = (Button) findViewById(R.id.submit_button);
        final TextView ratingDisplayTextView = (TextView) findViewById(R.id.rating_display_text_View);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ratingDisplayTextView.setText("Universal Health Care Rating is:" + ratingRatingBar.getRating());
                postrating(); //method that posts the ratings to firebase
            }
        });


        // Creating ArrayAdapter using the string array and default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.Counties, android.R.layout.simple_spinner_item);

        // Specify layout to be used when list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Applying the adapter to our spinner
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        options = UHC.this.getResources().getStringArray(R.array.Counties);

    }

        private void texttoAdmin() {
        startActivity(new Intent(this, AdminData.class));
        }


        private void postrating() {

        String County = spinner.getSelectedItem().toString().trim();
        String Uhcrating = rating_display_text_View.getText().toString().trim();
        String Uhccomment = UHCComment.getText().toString().trim();
      ;

        if (!validateInputs(County, Uhcrating, Uhccomment)) {

            CollectionReference dbProducts = db.collection("Universal Health");

            Product2 product2 = new Product2(
                    County,
                    Uhcrating,
                    Uhccomment

            );

            dbProducts.add(product2)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Toast.makeText(UHC.this, "Rating Added", Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(UHC.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });

        }
    }

    private boolean validateInputs(String county, String uhcrating, String uhccomment) {


        if (TextUtils.isEmpty(uhccomment)) {
            rating_display_text_View.setError("Department required");
            rating_display_text_View.requestFocus();
            return true;
        }

        return false;

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        Toast.makeText(this, " You select >> "+options[position], Toast.LENGTH_SHORT).show();

    }





    private void Admin() {

        startActivity(new Intent(this, Adminpage.class));

    }


}
