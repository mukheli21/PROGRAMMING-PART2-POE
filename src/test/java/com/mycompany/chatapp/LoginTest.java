package com.mycompany.chatapp;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LoginTest {

    // Test 1: a correct username (has an underscore and is 5 characters or fewer)
    @Test
    public void testCheckUserNameCorrect() {
        Login user = new Login("kyl_1", "Ch&&sec@ke99!", "+27838969876", "Thakhie", "Mukheli");

        // assertTrue passes if the method returns true
        assertTrue(user.checkuserName());
    }

    // Test 2: an incorrect username (no underscore and too long)
    @Test
    public void testCheckUserNameIncorrect() {
        Login user = new Login("kyle!!!!!!*", "Ch&&sec@ke99!", "+27838969876", "Thakhie", "Mukheli");

        // assertFalse passes if the method returns false
        assertFalse(user.checkuserName());
    }

    // Test 3: a correct password (capital, number and special character)
    @Test
    public void testPasswordCorrect() {
        Login user = new Login("kyl_1", "Ch&&sec@ke99!", "+27838969876", "Thakhie", "Mukheli");

        assertTrue(user.checkpasswordcomplexity());
    }

    // Test 4: an incorrect password (no capital, number or special character)
    @Test
    public void testPasswordIncorrect() {
        Login user = new Login("kyl_1", "password", "+27838969876", "Thakhie", "Mukheli");

        assertFalse(user.checkpasswordcomplexity());
    }

    // Test 5: a correct phone number (starts with +27)
    @Test
    public void testPhoneNumberCorrect() {
        Login user = new Login("kyl_1", "Ch&&sec@ke99!", "+27838969876", "Thakhie", "Mukheli");

        assertTrue(user.checkphoneNumber());
    }

    // Test 6: an incorrect phone number (no international code)
    @Test
    public void testPhoneNumberIncorrect() {
        Login user = new Login("kyl_1", "Ch&&sec@ke99!", "08966553", "Thakhie", "Mukheli");

        assertFalse(user.checkphoneNumber());
    }

    // Test 7: login works when the username and password match
    @Test
    public void testLoginSuccessful() {
        Login user = new Login("kyl_1", "Ch&&sec@ke99!", "+27838969876", "Thakhie", "Mukheli");

        assertTrue(user.loginUser("kyl_1", "Ch&&sec@ke99!"));
    }

    // Test 8: login fails when the username and password do not match
    @Test
    public void testLoginFailed() {
        Login user = new Login("kyl_1", "Ch&&sec@ke99!", "+27838969876", "Thakhie", "Mukheli");

        assertFalse(user.loginUser("Wrong_1", "wrongpassword"));
    }

    // Test 9: Login must implement the IUserValidation interface
    @Test
    public void testLoginImplementsInterface() {
        Login user = new Login("kyl_1", "Ch&&sec@ke99!", "+27838969876", "Thakhie", "Mukheli");

        // instanceof checks that the object is of that type
        assertTrue(user instanceof IUserValidation);
    }
}