
package com.example.trisha.CommentPanel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.trisha.Modal.commentModel;
import com.example.trisha.R;
import com.example.trisha.viewHolder.commentViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Random;

public class CommentPanel extends AppCompatActivity {
    EditText commentTxt;
    Button comment_btn;
    DatabaseReference userRef, commentRef;
    String postKey;

    String userId;
    RecyclerView comment_recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_panel);

        comment_btn = findViewById(R.id.comment_submit_Btn);
        commentTxt = findViewById(R.id.comment_text_txt);
        comment_recyclerView  = findViewById(R.id.comment_recView);
        postKey = getIntent().getStringExtra("postKey").toString(); // here we are getting the postKey or the uid of the video through intent from the last activity.
        // Toast.makeText(this, postKey, Toast.LENGTH_SHORT).show();

        userRef = FirebaseDatabase.getInstance().getReference().child("userProfile");
        commentRef = FirebaseDatabase.getInstance().getReference().child("myVideos").child(postKey).child("comments");

        // get the current userOperation
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        userId = user.getUid();

        comment_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // checking if the userExist in the database

                userRef.child(userId).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            // if the user Exist, we are getting the name and image from the database.
                            String userName = snapshot.child("uName").getValue().toString();
                            String uImage = snapshot.child("uImage").getValue().toString();
                            processComment(userName, uImage);
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });



    }

    private void processComment(String userName, String uImage) {
        String commnetPost = commentTxt.getText().toString();
        String randomPostKey = userId+""+new Random().nextInt(1000); // this random key will be used if the user wants to multiple comment.
        // if the mainUser id was given, it would create or replace the value while adding the comment in the database.

        //get the dates
        Calendar dateValue = Calendar.getInstance();
        SimpleDateFormat dateFormat  = new SimpleDateFormat("dd-mm-yy");
        String comment_Date = dateFormat.format(dateValue.getTime());

        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        String comment_Time = timeFormat.format(dateValue.getTime());

        HashMap cmnt = new HashMap();
        cmnt.put("uid", userId);
        cmnt.put("username",userName);
        cmnt.put("userImage",uImage);
        cmnt.put("usermsg",commnetPost);
        cmnt.put("date",comment_Date);
        cmnt.put("time",comment_Time);

        // adding the comment in the database
        commentRef.child(randomPostKey).updateChildren(cmnt)
                .addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {

                        if(task.isSuccessful()){
                            Toast.makeText(CommentPanel.this, "comment added", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(CommentPanel.this, "comment not addeded", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<commentModel> options =
                new FirebaseRecyclerOptions.Builder<commentModel>()
                        .setQuery(commentRef, commentModel.class) // while using this we have to provide the query to find the database or node to fetch.
                        .build(); // this will get the

        // after creating the viewholder (commentViewHolder)
        FirebaseRecyclerAdapter<commentModel, commentViewHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<commentModel, commentViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull commentViewHolder holder, int position, @NonNull commentModel model) {
                        holder.cuname.setText(model.getUsername().toString()); // make cname public if you cant acceess it
                        holder.cumessage.setText(model.getUsermsg().toString());
                        holder.cudt.setText("Date: "+ model.getDate().toString()+ " Time :"+model.getTime().toString());
                        Glide.with(holder.cuimage.getContext()).load(model.getUserImage()).into(holder.cuimage);
                    }
                    @NonNull
                    @Override
                    public commentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.sample_comment_row, parent, false);
                        return new commentViewHolder(view);
                    }
                };
        comment_recyclerView.setLayoutManager(new LinearLayoutManager(this));
        firebaseRecyclerAdapter.startListening();
        comment_recyclerView.setAdapter(firebaseRecyclerAdapter);
    }
}

