package com.capstone.sarangbang.triplejong;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MatchingView extends AppCompatActivity {

    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    MatchingAdapter matchingAdapter;

    private FirebaseAuth authentication;
    private FirebaseUser currentFbUser;
    private DatabaseReference dbref;
    private DatabaseReference usersRef;
    private DatabaseReference contactsRef;

    private static List<User> users = Collections.emptyList();
    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.matching_recycler);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView = findViewById(R.id.matchingRecyclerView);
        recyclerView.addItemDecoration(
                new DividerItemDecoration(this,linearLayoutManager.getOrientation()));
        recyclerView.setLayoutManager(linearLayoutManager);

        initFirebase();

        final List<MatchingContact> userList = new ArrayList<>();
        final int aa[] = {0};

        usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                initUsers(dataSnapshot);
                initCurrentUser();

                for(int i=0; i<users.size(); i++) {
                    if(currentUser.getRank().equals(users.get(i).getRank())){
                        if(currentUser.getEmail().equals(users.get(i).getEmail())) {
                            continue;
                        } else {
                            userList.add(new MatchingContact(users.get(i).getEmail(), users.get(i).getFname(), users.get(i).getLname(), users.get(i).getUID()));
                            aa[0]++;
                        }
                    }
                }
                if (aa[0] == 0) {
                    Toast.makeText(getApplicationContext(), "추천 사용자가 없습니다.", Toast.LENGTH_LONG).show();
                }
                matchingAdapter = new MatchingAdapter(userList);
                recyclerView.setAdapter(matchingAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("FIREBASE", "usersRef:onCancelled", databaseError.toException());
            }
        });
    }

    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

    private void initFirebase () {

        authentication = FirebaseAuth.getInstance();
        currentFbUser = authentication.getCurrentUser();
        dbref = FirebaseDatabase.getInstance().getReference();
        usersRef = dbref.child("users");
        contactsRef = dbref.child("contacts/" + currentFbUser.getUid());
    }

    private void initUsers (DataSnapshot dataSnapshot){

        users = new ArrayList<User>();

        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

            User user = snapshot.getValue(User.class);
            users.add(user);

        }
    }

    private void initCurrentUser() {

        currentUser = new User();
        currentUser.setUID(currentFbUser.getUid());
        Log.d("UID", currentUser.getUID());

        for (int i = 0; i < users.size(); i++) {

            if (currentUser.getUID().equals(users.get(i).getUID())) {

                currentUser.setFname(users.get(i).getFname());
                currentUser.setLname(users.get(i).getLname());
                currentUser.setEmail(users.get(i).getEmail());
                currentUser.setRank(users.get(i).getRank());
            }
        }
    }
}

