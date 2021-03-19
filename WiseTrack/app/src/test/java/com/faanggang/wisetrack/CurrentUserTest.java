package com.faanggang.wisetrack;

import com.faanggang.wisetrack.user.Users;

import org.junit.Assert;
import org.junit.Test;

/**
 * Tests for Users class
 * test if user can be created with correct user info and if it can be edited
 */
public class CurrentUserTest {

    private Users user = new Users("UserOne","FirstName","LastName",
            "testemail@gmail.com","userID1234567890", "7801234567");

    @Test
    public void testUser() {
        // try if created user has correct info

        String username = "UserOne";
        String firstName = "FirstName";
        String LastName = "LastName";
        String email = "testemail@gmail.com";
        String userID = "userID1234567890";
        String phoneNumber = "7801234567";

        Assert.assertEquals(username, user.getUserName());
        Assert.assertEquals(firstName, user.getFirstName());
        Assert.assertEquals(LastName, user.getLastName());
        Assert.assertEquals(email, user.getEmail());
        Assert.assertEquals(userID, user.getUserID());
        Assert.assertEquals(phoneNumber, user.getPhoneNumber());
    }

    @Test
    public void testEditUser() {
        // try editing user info
        String email = "newemail@gmail.com";
        user.setEmail(email);

        Assert.assertEquals(email, user.getEmail());
    }
}
