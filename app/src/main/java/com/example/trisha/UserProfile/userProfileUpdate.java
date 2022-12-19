package com.example.trisha.UserProfile;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.trisha.DashBoard.DashBoard;
import com.example.trisha.R;
import com.example.trisha.databinding.ActivityUserProfileUpdateBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class userProfileUpdate extends AppCompatActivity {
    ActivityUserProfileUpdateBinding bind_userProfile;

    DatabaseReference databaseReference;
    StorageReference storageReference;

    Uri filePath;
    Bitmap bitmap;
    String userId= "";

    int IMAGE_REQUEST_CODE= 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_user_profile_update);
        bind_userProfile = ActivityUserProfileUpdateBinding.inflate(getLayoutInflater());
        setContentView(bind_userProfile.getRoot());

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        userId = user.getUid();

        databaseReference = FirebaseDatabase.getInstance().getReference().child("userProfile");
        storageReference = FirebaseStorage.getInstance().getReference();

        bind_userProfile.userProfileUserImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCamera();
            }
        });

        bind_userProfile.userProfileUserSaveBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveIntoFirebase();
            }
        });
    }


    // save into firebase.
    private void saveIntoFirebase() {
        /*
         * 1st upload the file then also get the downloadable url and send it to the realtime database.
         * */
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle("File Uploader");
        pd.show();

        final StorageReference uploader = storageReference.child("profileimages/"+"img"+System.currentTimeMillis());
        uploader.putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // get the download url from storage
                uploader.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        final Map<String, Object> map = new HashMap<>();
                        map.put("uImage", uri.toString());
                        map.put("uName",bind_userProfile.userProfileUserName.getText().toString());

                        // now upload it in the realtime database.
                        databaseReference.child(userId).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.exists()) // here we are checking if the user id already exists in the database, if true then update else remove.
                                    databaseReference.child(userId).updateChildren(map); // if value already exists we upate it
                                else
                                    databaseReference.child(userId).setValue(map); // if not we set it.

                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                        pd.dismiss();
                        Toast.makeText(userProfileUpdate.this, "Updated Successfully", Toast.LENGTH_SHORT).show();
                    }
                });


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(userProfileUpdate.this, "Storagee failed", Toast.LENGTH_SHORT).show();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                float percent = (100*snapshot.getBytesTransferred())/snapshot.getTotalByteCount();
                pd.setMessage("uploaded : "+(int)percent+"%");
            }
        });

    }

    // open camera and get image
    private void openCamera() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Please Select file"),IMAGE_REQUEST_CODE);
    }

    // set the image
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_REQUEST_CODE && resultCode == RESULT_OK){
            filePath = data.getData(); // this file path is collected, for firebase
            try {
                InputStream inputStream = getContentResolver().openInputStream(filePath);
                bitmap = BitmapFactory.decodeStream(inputStream);
                bind_userProfile.userProfileUserImage.setImageBitmap(bitmap);
            }
            catch (Exception ex){
                Toast.makeText(this, ex.getMessage().toString(), Toast.LENGTH_LONG).show();
            }
        }
    }

    // get the value from the firebase
    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        userId = user.getUid();

        databaseReference.child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    bind_userProfile.userNameTxt.setText(snapshot.child("uName").getValue().toString());
                    Glide.with(getApplicationContext()).load(snapshot.child("uImage").getValue().toString()).into(bind_userProfile.userProfileUserImage);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), DashBoard.class));
        finish();
    }
}