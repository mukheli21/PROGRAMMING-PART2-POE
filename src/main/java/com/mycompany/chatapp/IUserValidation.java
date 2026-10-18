package com.mycompany.chatapp;

// Interface that lists the validation methods a user class must have
public interface IUserValidation {

    // Returns true if the username is valid
    boolean checkuserName();

    // Returns true if the password is complex enough
    boolean checkpasswordcomplexity();

    // Returns true if the phone number is valid
    boolean checkphoneNumber();
}