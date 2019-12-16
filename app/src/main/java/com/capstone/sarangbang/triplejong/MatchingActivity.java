package com.capstone.sarangbang.triplejong;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Collections;
import java.util.List;

public class MatchingActivity extends AppCompatActivity {

    private User currentUser;
    private static List<User> users = Collections.emptyList();

    private String UID;
    private String email;
    private String password;
    private String fname;
    private String lname;
    private String rank;

    private FirebaseAuth authentication;
    private FirebaseUser currentFbUser;
    private DatabaseReference dbref;
    private DatabaseReference usersRef;
    private DatabaseReference contactsRef;
    private DatabaseReference chatsRef;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.matching_wait);

        initFirebase();

        final String value1[] = {""};

        final FirebaseUser currentUser = authentication.getCurrentUser();

        dbref.child("request").child("uid").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Object value = dataSnapshot.getValue(Object.class);
                value1[0] = value.toString();
                if (value1[0].equals("empty")) {
                    dbref.child("request").child("uid").setValue(currentUser.getUid());
                    new Handler().postDelayed(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            Intent intent = new Intent(MatchingActivity.this, MatchingView.class);
                            startActivity(intent);
                            finish();
                        }
                    }, 5000);

                } else {
                    Toast.makeText(getApplicationContext(), "요청이 많으니 잠시 후에 시도해주시기 바랍니다.", Toast.LENGTH_LONG).show();
                }
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

    private void initFirebase() {
        authentication = FirebaseAuth.getInstance();
        currentFbUser = authentication.getCurrentUser();
        dbref = FirebaseDatabase.getInstance().getReference();
        usersRef = dbref.child("users");
        contactsRef = dbref.child("contacts/" + currentFbUser.getUid());
        chatsRef = dbref.child("chats");
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

