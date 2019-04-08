package com.example.uhc;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{


    String[] options;
    private Button btnChoose, btnUpload;
    public static final String TAG = "MainActivity";
    private EditText editText_Hospital;
    private EditText editText_Department;
    private EditText editText_Comment;
    private EditText editText_Serial;
    private TextView mDIsplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetLister;
    Button button_save;
    TextView tvDate;
    Spinner spinner;

    private ImageView imageView;
    private Uri filePath;
    private final int PICK_IMAGE_REQUEST = 71;
    private TextView textViewUHC;


    //Firebase
    FirebaseStorage storage;
    StorageReference storageReference;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //initializing firebase storage and firestore
        db = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();


        //Declaring Image items
        imageView = (ImageView) findViewById(R.id.imgView);
        btnUpload = (Button) findViewById(R.id.btnUpload);
        btnChoose = (Button) findViewById(R.id.btnChoose);
        textViewUHC = (TextView) findViewById(R.id.textViewUHC);

        btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();

            }
        });
        textViewUHC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uhc();

            }
        });


        editText_Hospital = (EditText) findViewById(R.id.edittext_Hospital);
        editText_Department = (EditText) findViewById(R.id.edittext_Department);
        editText_Comment = (EditText) findViewById(R.id.edittext_Comment);
        editText_Serial = (EditText) findViewById(R.id.edittext_Serial);
        tvDate = (TextView) findViewById(R.id.tvDate);
        spinner = (Spinner) findViewById(R.id.spinner);

        button_save = (Button) findViewById(R.id.button_save);
        button_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postcomment();
            }
        });



        //Declaring date textview
        mDIsplayDate = (TextView) findViewById(R.id.tvDate);

        mDIsplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(MainActivity.this, android.R.style.Theme_Holo_Dialog_MinWidth,
                        mDateSetLister, year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();


            }
        });

        //Initializing on date set listener
        mDateSetLister = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                Log.d(TAG, "onDateSet: dd/mm/yyy: " + dayOfMonth + "/" + month + "/" + year);

                String date = dayOfMonth + "/" + month + "/" + year;
                mDIsplayDate.setText(date);

            }
        };






        Spinner spinner = (Spinner) findViewById(R.id.spinner);

        // Creating ArrayAdapter using the string array and default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.Counties, android.R.layout.simple_spinner_item);

        // Specify layout to be used when list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Applying the adapter to our spinner
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        options = MainActivity.this.getResources().getStringArray(R.array.Counties);

    }

    private void uhc() {
        startActivity(new Intent(this, UHC.class));
    }



    private void uploadImage() {

        if(filePath != null){
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            StorageReference ref = storageReference.child("images/"+ UUID.randomUUID().toString());
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Toast.makeText(MainActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(MainActivity.this, "Failed"+e.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                            progressDialog.setMessage("Uploaded"+(int)progress+"%");
                        }
                    });
        }
    }

    private void chooseImage() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Picture"), PICK_IMAGE_REQUEST);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }


        }

    }

    private void postcomment() {


        String County = spinner.getSelectedItem().toString().trim();
        String Hospital = editText_Hospital.getText().toString().trim();
        String Department = editText_Department.getText().toString().trim();
        String Comment = editText_Comment.getText().toString().trim();
        String Serial = editText_Serial.getText().toString().trim();
        String Date = tvDate.getText().toString().trim();

        if (!validateInputs(County, Hospital, Department, Comment, Serial, Date)) {

            CollectionReference dbProducts = db.collection("Suggestion");

            Product product = new Product(
                    County,
                    Hospital,
                    Department,
                    Comment,
                    Double.parseDouble(Serial),
                    Date
            );

            dbProducts.add(product)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Toast.makeText(MainActivity.this, "Suggestion/Complaint Added", Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });

        }
    }



    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        Toast.makeText(this, " You select >> "+options[position], Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    private boolean validateInputs(String County, String Hospital, String Department, String Comment, String Serial, String Date) {


        if (TextUtils.isEmpty(Hospital)) {
            editText_Hospital.setError("Hospital required");
            editText_Hospital.requestFocus();
            return true;
        }

        if (TextUtils.isEmpty(Department)) {
            editText_Department.setError("Department required");
            editText_Department.requestFocus();
            return true;
        }

        if (TextUtils.isEmpty(Comment)) {
            editText_Comment.setError("Suggestion/Complaint required");
            editText_Comment.requestFocus();
            return true;
        }

        if (TextUtils.isEmpty(Serial)) {
            editText_Serial.setError("Card/Booklet Serial required");
            editText_Serial.requestFocus();
            return true;
        }


        return false;
    }
}
