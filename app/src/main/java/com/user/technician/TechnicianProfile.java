package com.user.technician;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.user.main.MainActivity;
import com.user.main.R;
import com.user.main.databinding.ActivityTechnicianProfileBinding;
import com.user.technician.database.TechnicianDatabase;

import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

public class TechnicianProfile extends DrawerBase {

    ActivityTechnicianProfileBinding activityTechnicianProfileBinding;
    private DatabaseReference technicianRef;
    private EditText Name,Email,Address,NIC;
    private ImageView r1,r2,r3,r4,r5,Profile;
    private TextView TechnicianPhoto,Delete;
    private Button Update;

    private Uri filePath;

    // request code
    private final int PICK_IMAGE_REQUEST = 22;

    // instance for firebase storage and StorageReference
    FirebaseStorage storage;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityTechnicianProfileBinding = ActivityTechnicianProfileBinding.inflate(getLayoutInflater());
        setContentView(activityTechnicianProfileBinding.getRoot());
        allocateActivityTitle("Profile");

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        Name = findViewById(R.id.register_name);
        Email = findViewById(R.id.register_email);
        Address = findViewById(R.id.register_address);
        NIC = findViewById(R.id.register_nic);
        TechnicianPhoto = findViewById(R.id.technician_photo_add);
        Profile = findViewById(R.id.imgView);
        Update = findViewById(R.id.update_btn);
        Delete = findViewById(R.id.delete_btn);
        r1 = findViewById(R.id.r1);
        r2 = findViewById(R.id.r2);
        r3 = findViewById(R.id.r3);
        r4 = findViewById(R.id.r4);
        r5 = findViewById(R.id.r5);

        Email.setEnabled(false);

        TechnicianPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SelectImage();
            }
        });

        Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
                AlertDialog.Builder builder = new AlertDialog.Builder(TechnicianProfile.this);

                builder.setMessage("Do you want to delete account ?");

                // Set Alert Title
                builder.setTitle("Alert !");

                // Set Cancelable false for when the user clicks on the outside the Dialog Box then it will remain show
                builder.setCancelable(true);

                builder.setPositiveButton("Yes", (DialogInterface.OnClickListener) (dialog, which) -> {
                    TechnicianDatabase technicianDatabase = new TechnicianDatabase();
                    technicianDatabase.technicianDelete(currentFirebaseUser.getUid())
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(TechnicianProfile.this, "Deleted Successfully", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(TechnicianProfile.this, TechnicianLogin.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        Toast.makeText(TechnicianProfile.this, "Deleting Failed!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                });

                // Set the Negative button with No name Lambda OnClickListener method is use of DialogInterface interface.
                builder.setNegativeButton("No", (DialogInterface.OnClickListener) (dialog, which) -> {
                    // If user click no then dialog box is canceled.
                    dialog.cancel();
                });

                // Create the Alert dialog
                AlertDialog alertDialog = builder.create();
                // Show the Alert Dialog box
                alertDialog.show();
            }
        });

        Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadImage();

                String name = Name.getText().toString().trim();
                String address = Address.getText().toString().trim();
                String nic = NIC.getText().toString().trim();
                boolean success = true;

                HashMap<String,Object> hashMap = new HashMap<>();
                hashMap.put("name",name);
                hashMap.put("address",address);
                hashMap.put("nic",nic);

                if(name.isEmpty()){
                    Name.setError("Please enter name ! ");
                    Name.requestFocus();
                    success=false;
                }
                if(address.isEmpty()){
                    Address.setError("Please enter address ! ");
                    Address.requestFocus();
                    success=false;
                }
                if(nic.isEmpty()){
                    NIC.setError("Please enter NIC ! ");
                    NIC.requestFocus();
                    success=false;
                }

                if(success){

                    FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;

                    TechnicianDatabase technicianDatabase = new TechnicianDatabase();

                    technicianDatabase.technicianUpdate(currentFirebaseUser.getUid(),hashMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(TechnicianProfile.this, "Updated Successfully", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(TechnicianProfile.this, "Updating Failed!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
                else{
                    Toast.makeText(TechnicianProfile.this, "Updating Failed!(Invalid inputs)", Toast.LENGTH_SHORT).show();
                }
            }
        });



        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();


        TechnicianDatabase technicianDatabase = new TechnicianDatabase();

        technicianRef = FirebaseDatabase.getInstance().getReference("Technician").child(currentFirebaseUser.getUid());

        technicianRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {

                if(task.isSuccessful()){
                    if(task.getResult().exists()){
                        DataSnapshot dataSnapshot = task.getResult();
                        String address = String.valueOf(dataSnapshot.child("address").getValue());
                        String email = String.valueOf(dataSnapshot.child("email").getValue());
                        String name = String.valueOf(dataSnapshot.child("name").getValue());
                        String password = String.valueOf(dataSnapshot.child("password").getValue());
                        String nic = String.valueOf(dataSnapshot.child("nic").getValue());
                        String rating = String.valueOf(dataSnapshot.child("rating").getValue());

                        Name.setText(name);
                        Email.setText(email);
                        Address.setText(address);
                        NIC.setText(nic);

                        if(rating.equals("0")){

                            r1.setVisibility(View.GONE);
                            r2.setVisibility(View.GONE);
                            r3.setVisibility(View.GONE);
                            r4.setVisibility(View.GONE);
                            r5.setVisibility(View.GONE);

                        }

                        if(rating.equals("1")){
                            r1.setVisibility(View.VISIBLE);
                            r2.setVisibility(View.GONE);
                            r3.setVisibility(View.GONE);
                            r4.setVisibility(View.GONE);
                            r5.setVisibility(View.GONE);
                        }
                        if(rating.equals("2")){
                            r1.setVisibility(View.VISIBLE);
                            r2.setVisibility(View.VISIBLE);
                            r3.setVisibility(View.GONE);
                            r4.setVisibility(View.GONE);
                            r5.setVisibility(View.GONE);
                        }
                        if(rating.equals("3")){
                            r1.setVisibility(View.VISIBLE);
                            r2.setVisibility(View.VISIBLE);
                            r3.setVisibility(View.VISIBLE);
                            r4.setVisibility(View.GONE);
                            r5.setVisibility(View.GONE);
                        }
                        if(rating.equals("4")){
                            r1.setVisibility(View.VISIBLE);
                            r2.setVisibility(View.VISIBLE);
                            r3.setVisibility(View.VISIBLE);
                            r4.setVisibility(View.VISIBLE);
                            r5.setVisibility(View.GONE);
                        }
                        if(rating.equals("5")){
                            r1.setVisibility(View.VISIBLE);
                            r2.setVisibility(View.VISIBLE);
                            r3.setVisibility(View.VISIBLE);
                            r4.setVisibility(View.VISIBLE);
                            r5.setVisibility(View.VISIBLE);
                        }


                    }else{
                        Toast.makeText(TechnicianProfile.this,"Error fetch data result",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(TechnicianProfile.this,"Error fetch data unsuccessful",Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    private void SelectImage(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(
                Intent.createChooser(
                        intent,
                        "Select Image from here..."),
                PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode,
                                    Intent data)
    {

        super.onActivityResult(requestCode,
                resultCode,
                data);

        // checking request code and result code
        // if request code is PICK_IMAGE_REQUEST and
        // resultCode is RESULT_OK
        // then set image in the image view
        if (requestCode == PICK_IMAGE_REQUEST
                && resultCode == RESULT_OK
                && data != null
                && data.getData() != null) {

            // Get the Uri of data
            filePath = data.getData();
            try {

                // Setting image on image view using Bitmap
                Bitmap bitmap = MediaStore
                        .Images
                        .Media
                        .getBitmap(
                                getContentResolver(),
                                filePath);
                Profile.setImageBitmap(bitmap);
            }

            catch (IOException e) {
                // Log the exception
                e.printStackTrace();
            }
        }
    }


    private void uploadImage()
    {
        if (filePath != null) {

            // Defining the child of storageReference
            StorageReference ref
                    = storageReference
                    .child(
                            "images/"
                                    + UUID.randomUUID().toString());

            // adding listeners on upload
            // or failure of image
            ref.putFile(filePath)
                    .addOnSuccessListener(
                            new OnSuccessListener<UploadTask.TaskSnapshot>() {

                                @Override
                                public void onSuccess(
                                        UploadTask.TaskSnapshot taskSnapshot)
                                {

                                    // Image uploaded successfully
                                    // Dismiss dialog
                                    Toast
                                            .makeText(TechnicianProfile.this,
                                                    "Image Uploaded!!",
                                                    Toast.LENGTH_SHORT)
                                            .show();
                                }
                            })

                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e)
                        {

                            // Error, Image not uploaded
                            Toast
                                    .makeText(TechnicianProfile.this,
                                            "Failed " + e.getMessage(),
                                            Toast.LENGTH_SHORT)
                                    .show();
                        }
                    })
                    .addOnProgressListener(
                            new OnProgressListener<UploadTask.TaskSnapshot>() {

                                // Progress Listener for loading
                                // percentage on the dialog box
                                @Override
                                public void onProgress(
                                        UploadTask.TaskSnapshot taskSnapshot)
                                {
                                    double progress
                                            = (100.0
                                            * taskSnapshot.getBytesTransferred()
                                            / taskSnapshot.getTotalByteCount());

                                }
                            });
        }
    }


}
