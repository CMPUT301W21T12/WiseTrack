package com.faanggang.wisetrack.user;

import static com.google.android.gms.common.internal.safeparcel.SafeParcelable.NULL;

public class UserProfile {
    private String userName;
    private String firstName;
    private String lastName;
    private String email;
    private String userID;
    private int phoneNumber;

    public UserProfile() {
        this.userName = userName;
        this.firstName = "FirstName";
        this.lastName = "LastName";
        this.email = "Email";
        this.userID = NULL;
        this.phoneNumber = 0;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
