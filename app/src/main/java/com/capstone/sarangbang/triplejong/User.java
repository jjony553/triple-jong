package com.capstone.sarangbang.triplejong;

import java.util.ArrayList;
import java.util.List;

//User class extends Contact
//contains conversations List

public class User extends Contact{

    private List<Conversation> chats;

    public User() {this.chats = new ArrayList<Conversation>();}

    public User(String UID, String fname, String lname, String email, String rank, String testing) {

        super(UID, email, fname, lname, rank, testing);
    }

    public void setChats(List<Conversation> chats) {
        this.chats = chats;
    }

    public List<Conversation> getChats() { return chats; }
}
