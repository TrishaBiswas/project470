package com.example.trisha.DashBoard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.trisha.CommentPanel.CommentPanel;
import com.example.trisha.MainActivity;
import com.example.trisha.Modal.RealTimeDataBase_VideoModal;
import com.example.trisha.Payment.Payment;
import com.example.trisha.R;
import com.example.trisha.UserProfile.userProfileUpdate;
import com.example.trisha.addVideo.addVideo;
import com.example.trisha.logRegister.Login;
import com.example.trisha.viewHolder.dashBoardViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DashBoard extends AppCompatActivity {

    FloatingActionButton floatingActionButton;
    FloatingActionButton floatingActionButton1;
    RecyclerView recyclerView;

    DatabaseReference likeReference;
    Boolean testClick = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
        likeReference = FirebaseDatabase.getInstance().getReference("likes");// create the root node and  get the like reference from the firebaseViewHolder

        floatingActionButton = findViewById(R.id.dashboard_floating_actionButton);
        floatingActionButton1 = findViewById(R.id.dashboard_floating_actionButton_payment);
        recyclerView = findViewById(R.id.dashBoard_recView);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), addVideo.class));
                finish();
            }
        });

        floatingActionButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Payment.class));
                finish();
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        DatabaseReference query = FirebaseDatabase.getInstance().getReference().child("myVideos");
        FirebaseRecyclerOptions<RealTimeDataBase_VideoModal> options =
                new FirebaseRecyclerOptions.Builder<RealTimeDataBase_VideoModal>()
                        .setQuery(query, RealTimeDataBase_VideoModal.class)// query is the path, which indicates from which node you are going to fetch the data.
                        .build();


        FirebaseRecyclerAdapter<RealTimeDataBase_VideoModal, dashBoardViewHolder> firebaseRecyclerAdapter= new FirebaseRecyclerAdapter<RealTimeDataBase_VideoModal, dashBoardViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull dashBoardViewHolder holder, int position, @NonNull RealTimeDataBase_VideoModal model) {
                //Toast.makeText(getApplicationContext(), model.getV_title().toString(), Toast.LENGTH_SHORT).show();
                holder.prepareExoplayer(getApplicationContext(), model.getTitle(), model.getVurl());
                //Toast.makeText(getApplicationContext(), model.getVurl().toString(), Toast.LENGTH_SHORT).show();

                /* like option :
                 * every video will have an id. If any user press on the like btn, then that userId or info will be added in that videoId.
                 * This will be helpful when user again open the app and he will see the things that he liked.
                 * We will check if the video id contains the userId of the current user. Based on that,
                 * the like or unlike button will show.*/
                // get the userId
                FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                String userId = firebaseUser.getUid();
                // get the video Id
                String postKey_videoId = getRef(position).getKey();
                // now get
                holder.getLikeButtonStatus(postKey_videoId, userId); // this will create the method in the viewHolder.

                holder.like_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        testClick = true;
                        likeReference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if(testClick == true){
                                    // check first if the following video already liked by the user
                                    if(snapshot.child(postKey_videoId).hasChild(userId)){
                                        /*
                                         * this portion of the code is for removing the like if pressed again.
                                         * if the userId is already inside the videoId and userPress the button then the
                                         * the userId will be gone from there.
                                         * */
                                        likeReference.child(postKey_videoId).removeValue();
                                        testClick = false;
                                    }
                                    else
                                    {
                                        /*
                                         * if not then
                                         * push the userId under the videoId,
                                         * */
                                        likeReference.child(postKey_videoId).child(userId).setValue(true);
                                        testClick = false;
                                    }
                                }
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                    }
                });

                // comment panel
                holder.comment_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(DashBoard.this, "comment", Toast.LENGTH_SHORT).show();
                        try {
                            Intent intent = new Intent(getApplicationContext(), CommentPanel.class);
                            intent.putExtra("postKey", postKey_videoId);
                            startActivity(intent);
                        }
                        catch (Exception e){
                            Toast.makeText(DashBoard.this, e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }

            @NonNull
            @Override
            public dashBoardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sample_video_row_dashboard,parent, false);
                return new dashBoardViewHolder(view);
            }
        };


        firebaseRecyclerAdapter.startListening();
        recyclerView.setAdapter(firebaseRecyclerAdapter);

    }

    // for menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.appmenu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    // for menu item selected

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_logOut:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), Login.class));
                finish();
                break;
            case R.id.menu_manageProfile:
                startActivity(new Intent(getApplicationContext(), userProfileUpdate.class));
                finish();


        }
        return super.onOptionsItemSelected(item);
    }
}