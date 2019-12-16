package com.capstone.sarangbang.triplejong;

public class MatchingContact {
    public String email;
    public String fname;
    public String lname;
    public String UID;

    public MatchingContact() {}

    public MatchingContact(String email, String fname, String lname, String UID) {
        this.email = email;
        this.fname = fname;
        this.lname = lname;
        this.UID = UID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

}