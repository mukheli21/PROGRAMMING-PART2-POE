package com.mycompany.chatapp;

public class Login implements IUserValidation {

    // Variables to store the user's details
    String username;
    String password;
    String phoneNumber;
    String firstName;
    String lastName;

    // Constructor to store the details when a Login object is created
    public Login(String username, String password, String phoneNumber,
                 String firstName, String lastName) {
        this.username = username;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    // Checks that the username has an underscore and is 5 characters or fewer
    @Override
    public boolean checkuserName() {
        if (username.contains("_") && username.length() <= 5) {
            return true;
        } else {
            return false;
        }
    }

    // Checks that the password is complex enough
    @Override
    public boolean checkpasswordcomplexity() {

        // The password must be at least 8 characters long
        if (password.length() < 8) {
            return false;
        }

        // These start as false and change to true when we find a match
        boolean hasCapital = false;
        boolean hasNumber = false;
        boolean hasSpecialCharacter = false;

        // Loop through every character in the password
        for (int i = 0; i < password.length(); i++) {

            char character = password.charAt(i);

            // Is it a capital letter?
            if (Character.isUpperCase(character)) {
                hasCapital = true;
            }

            // Is it a number?
            if (Character.isDigit(character)) {
                hasNumber = true;
            }

            // If it is not a letter or a number, it is a special character
            if (!Character.isLetterOrDigit(character)) {
                hasSpecialCharacter = true;
            }
        }

        // All three must be true for the password to be valid
        if (hasCapital && hasNumber && hasSpecialCharacter) {
            return true;
        } else {
            return false;
        }
    }

    // Checks that the phone number starts with +27 followed by exactly 9 digits
    @Override
    public boolean checkphoneNumber() {
        if (phoneNumber.matches("^\\+27[0-9]{9}$")) {
            return true;
        } else {
            return false;
        }
    }

    // Registers the user and returns the correct message
    public String registerUser() {

        // Check the username first
        if (!checkuserName()) {
            return "Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.";
        }

        // Then check the password
        if (!checkpasswordcomplexity()) {
            return "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.";
        }

        // Then check the phone number
        if (!checkphoneNumber()) {
            return "Cell number is incorrectly formatted or does not contain an international code; please correct the number and try again.";
        }

        // Everything is valid
        return "Username successfully captured."
                + "Password successfully captured."
                + "Cell number successfully captured.";
    }

    // Checks that the entered username and password match the registered ones
    public boolean loginUser(String enteredUsername, String enteredPassword) {
        if (username.equals(enteredUsername) && password.equals(enteredPassword)) {
            return true;
        } else {
            return false;
        }
    }

    // Returns the message to show after the user tries to log in
    public String returnLoginStatus(String enteredUsername, String enteredPassword) {
        if (loginUser(enteredUsername, enteredPassword)) {
            return "Welcome " + firstName + " " + lastName + " it is great to see you.";
        } else {
            return "Username or password incorrect, please try again.";
        }
    }
}