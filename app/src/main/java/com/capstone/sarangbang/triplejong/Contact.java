package com.capstone.sarangbang.triplejong;

public class Contact {

    private String UID;
    private String email;
    private String fname;
    private String lname;
    private String rank;
    private String testing;

    public Contact() {}

    public Contact(String UID, String email, String fname, String lname, String rank, String testing) {
        this.UID = UID;
        this.email = email;
        this.fname = fname;
        this.lname = lname;
        this.rank = rank;
        this.testing = testing;
    }

    public String getUID() {
        return UID;
    }

    public String getEmail() {
        return email;
    }

    public String getFname() {
        return fname;
    }

    public String getLname() {
        return lname;
    }

    public String getRank() {
        return rank;
    }

    public String getTesting() {
        return testing;
    }


    public void setUID(String UID) {
        this.UID = UID;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public void setTesting(String testing) {
        this.testing = testing;
    }



}
