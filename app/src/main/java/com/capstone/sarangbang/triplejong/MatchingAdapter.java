package com.capstone.sarangbang.triplejong;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class MatchingAdapter extends RecyclerView.Adapter<MatchingAdapter.ViewHolder>  {

    private List<MatchingContact> userList;

    private FirebaseAuth authentication;
    private FirebaseUser currentFbUser;
    private DatabaseReference dbref;
    private DatabaseReference usersRef;
    private DatabaseReference contactsRef;
    private DatabaseReference chatsRef;

    public MatchingAdapter(List<MatchingContact> userList) {
        this.userList = userList;
    }

    @NonNull
    @Override
    public MatchingAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.matching_item, viewGroup, false);
        return  new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final MatchingContact contact = userList.get(i);

        viewHolder.emailText.setText(contact.getEmail());
        viewHolder.nameText.setText(contact.getFname() + contact.getLname());
        viewHolder.avatar().setInfo(contact.getEmail(), contact.getFname(), contact.getLname());
        viewHolder.avatar().invalidateSelf();
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView nameText;
        private TextView emailText;
        private Button addButton;

        private ImageView imageView;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);

            final AvatarDrawable avatarDrawable = new AvatarDrawable(itemView.getContext());
            avatarDrawable.setInfo("Test", "Unknown", "User");

            emailText = itemView.findViewById(R.id.item_title);
            nameText = itemView.findViewById(R.id.item_subtitle);
            addButton = itemView.findViewById(R.id.addButton);
            imageView = itemView.findViewById(R.id.item_image);
            imageView.setImageDrawable(avatarDrawable);
            initFirebase();

            addButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    String searchEmail = userList.get(pos).getEmail();
                    for (int i = 0; i < userList.size(); i++) {
                            if (searchEmail.equals(userList.get(i).getEmail())) {
                                contactsRef.child(userList.get(i).getUID()).setValue(true);
                                Toast.makeText(itemView.getContext(), userList.get(i).getEmail() + " 사용자가 추가되었습니다.", Toast.LENGTH_LONG).show();
                                break;
                            }
                    }
                }
            });
        }

        public AvatarDrawable avatar(){
            return (AvatarDrawable) imageView.getDrawable();
        }
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
