package com.example.trisha.addVideo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.MediaController;
import android.widget.Toast;

import com.example.trisha.DashBoard.DashBoard;
import com.example.trisha.Modal.videoFileModel;
import com.example.trisha.R;
import com.example.trisha.databinding.ActivityAddVideoBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class addVideo extends AppCompatActivity {
    
    ActivityAddVideoBinding binding_addVideo;

    int VIDEO_REQUEST_CODE = 101;
    Uri video_URI ;
    StorageReference storageReference;
    DatabaseReference databaseReference;
    ProgressDialog progressDialog;

    MediaController mediaController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_video);
        binding_addVideo = ActivityAddVideoBinding.inflate(getLayoutInflater());
        setContentView(binding_addVideo.getRoot());


        // firebase instance
        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference("myVideos"); // this is the root node name where the video will store


        //media controller to control the video
        mediaController = new MediaController(this);
        binding_addVideo.videoViewAddvideo.setMediaController(mediaController);
        binding_addVideo.videoViewAddvideo.start();

        //progress dialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please wait");
        //progressDialog.setMessage("Uploading Video....");


        //brows video in gallery
        binding_addVideo.btnBrowse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(addVideo.this, "clicked", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.setType("video/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, VIDEO_REQUEST_CODE);
            }
        });


        // upload the video in the firebase
        binding_addVideo.btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                process_firebase_videoUpload();

            }
        });
    }


    // gets the format of the video, weather its .mp4, or .mov etc
    public  String getExtension_of_Video(){
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return  mimeTypeMap.getExtensionFromMimeType(getContentResolver().getType(video_URI));
    }


    private void process_firebase_videoUpload() {

        StorageReference uploader = storageReference.child("uploadedVideos/"+System.currentTimeMillis()+"."+getExtension_of_Video()); // System.currentTimeMillis() -> generates a unique name for each vide
        // pushing the file in firabse storage
        uploader.putFile(video_URI).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // at this point the file successfully uploaded in the firebase storage, now the main task is to get the url.
                        uploader.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Toast.makeText(addVideo.this, "Storage Upload success", Toast.LENGTH_SHORT).show();
                                // now its time ot upload the video url in the firebase realtime database.
                                // we are going to provide the object/ modal to the firebase Realtime database, which is helpful for not providing one info at one time.
                                String video_title = binding_addVideo.txtVtitle.getText().toString();
                                videoFileModel object_of_Video = new videoFileModel(video_title, uri.toString());
                                videoInfo_to_RealTimeDataBase(object_of_Video);
                            }
                        });
                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                        float per = (100*snapshot.getBytesTransferred()) / snapshot.getTotalByteCount();
                        progressDialog.setMessage("Uploaded :"+(int)per+"%");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(addVideo.this, "Storage Upload failed", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    // information uploading in the realtime Database
    private void videoInfo_to_RealTimeDataBase(videoFileModel object_of_video) {
        databaseReference.child(databaseReference.push().getKey())
                .setValue(object_of_video)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), "Realtime Database success", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), DashBoard.class));
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), "Realtime Database failed", Toast.LENGTH_SHORT).show();
                    }
                });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == VIDEO_REQUEST_CODE && resultCode == RESULT_OK){
            video_URI = data.getData();
            binding_addVideo.videoViewAddvideo.setVideoURI(video_URI);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), DashBoard.class));
    }
}