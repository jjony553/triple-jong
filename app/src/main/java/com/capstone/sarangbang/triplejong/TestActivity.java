package com.capstone.sarangbang.triplejong;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Collections;
import java.util.List;

public class TestActivity extends AppCompatActivity {

    private String uid;

    private User currentUser;
    private static List<User> users = Collections.emptyList();

    private String UID;
    private String email;
    private String password;
    private String fname;
    private String lname;
    private String rank;
    private String testing;

    private FirebaseAuth authentication;
    private FirebaseUser currentFbUser;
    private DatabaseReference dbref;
    private DatabaseReference usersRef;
    private DatabaseReference contactsRef;
    private DatabaseReference chatsRef;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        final Button btn1 = (Button) findViewById(R.id.button4);
        final Button btn2 = (Button) findViewById(R.id.button5);
        final Button btn3 = (Button) findViewById(R.id.button6);
        final TextView text1 = (TextView) findViewById(R.id.testTextView);
        final ImageView imgView1 = (ImageView) findViewById(R.id.testImageView);

        String pkgName = getPackageName();

        int max = 10;
        final int[] imgs = new int[max];

        for (int i = 0; i < max; i++) {
            imgs[i] = getResources().getIdentifier("img"+i, "drawable", pkgName);
            //리소스 획득 (R.drawable.img0)
        }
        initFirebase();

        FirebaseUser currentUser = authentication.getCurrentUser();

        UID = currentUser.getUid();
        final User user = new User(UID, fname, lname, email, rank, testing);
        final String test_text[] = {"놀이동산", "미술관", "스키", "워터파크", "영화", "음악", "독서", "연애", "운동", "음식"};
        final int[] i = {0};
        final int[] j = {0};
        text1.setText(test_text[0]);
        imgView1.setImageResource(imgs[0]);
        usersRef.child(user.getUID()).child("testing").setValue("false"); // 테스트를 다시 하는 경우 도중에 나가거나 했을 때 다시 할 수 있도록 유도

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usersRef.child(user.getUID()).child(test_text[i[0]]).setValue(50);
                i[0]++;
                j[0] += 50;
                if (i[0] >= 10) {
                    Toast.makeText(getApplicationContext(), "테스트가 완료되었습니다.", Toast.LENGTH_SHORT).show();
                    usersRef.child(user.getUID()).child("평균").setValue(j[0] / 10);
                    usersRef.child(user.getUID()).child("testing").setValue("true");
                    finish();
                } else {
                    text1.setText(test_text[i[0]]);
                    imgView1.setImageResource(imgs[i[0]]);
                }

            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usersRef.child(user.getUID()).child(test_text[i[0]]).setValue(30);
                i[0]++;
                j[0] += 30;
                if (i[0] >= 10) {
                    Toast.makeText(getApplicationContext(), "테스트가 완료되었습니다.", Toast.LENGTH_SHORT).show();
                    usersRef.child(user.getUID()).child("평균").setValue(j[0] / 10);
                    usersRef.child(user.getUID()).child("testing").setValue("true");
                    finish();
                } else {
                    text1.setText(test_text[i[0]]);
                    imgView1.setImageResource(imgs[i[0]]);
                }

            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usersRef.child(user.getUID()).child(test_text[i[0]]).setValue(80);
                i[0]++;
                j[0] += 80;
                if (i[0] >= 10) {
                    Toast.makeText(getApplicationContext(), "테스트가 완료되었습니다.", Toast.LENGTH_SHORT).show();
                    usersRef.child(user.getUID()).child("평균").setValue(j[0] / 10);
                    usersRef.child(user.getUID()).child("testing").setValue("true");
                    finish();
                } else {
                    text1.setText(test_text[i[0]]);
                    imgView1.setImageResource(imgs[i[0]]);
                }
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
}