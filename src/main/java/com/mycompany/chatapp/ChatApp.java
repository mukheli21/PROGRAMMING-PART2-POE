package com.mycompany.chatapp;

import java.util.Scanner;

public class ChatApp {

    public static void main(String[] args) {

        // Scanner to read what the user types
        Scanner scanner = new Scanner(System.in);

        System.out.println("=================================");
        System.out.println("           CHAT APP");
        System.out.println("=================================");

        // Get the user's details
        System.out.print("Enter your first name: ");
        String firstName = scanner.nextLine();

        System.out.print("Enter your last name: ");
        String lastName = scanner.nextLine();

        System.out.print("Enter your username: ");
        String username = scanner.nextLine();

        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        System.out.print("Enter your South African cell phone number: ");
        String cellPhoneNumber = scanner.nextLine();

        // Create the Login object
        Login login = new Login(username, password, cellPhoneNumber, firstName, lastName);

        // Show the registration result
        System.out.println();
        System.out.println("Registration Status:");
        System.out.println(login.registerUser());

        // Only continue if everything is valid
        if (login.checkuserName() && login.checkpasswordcomplexity() && login.checkphoneNumber()) {

            // Store the details in an array
            String[] userDetails = {
                "First Name: " + firstName,
                "Last Name: " + lastName,
                "Username: " + username,
                "Cell Number: " + cellPhoneNumber
            };

            System.out.println();
            System.out.println("Stored Registration Details:");

            // Print each detail
            for (int i = 0; i < userDetails.length; i++) {
                System.out.println(userDetails[i]);
            }

            System.out.println();
            System.out.println("=================================");
            System.out.println("              LOGIN");
            System.out.println("=================================");

            System.out.print("Enter your username: ");
            String enteredUsername = scanner.nextLine();

            System.out.print("Enter your password: ");
            String enteredPassword = scanner.nextLine();

            // true if the login worked
            boolean loginSuccessful = login.loginUser(enteredUsername, enteredPassword);

            System.out.println();
            System.out.println(login.returnLoginStatus(enteredUsername, enteredPassword));

            // Part 2: only logged in users can send messages
            if (loginSuccessful) {

                System.out.println();
                System.out.println("Welcome to QuickChat.");

                // The user says how many messages they want to enter
                System.out.print("How many messages do you want to enter? ");
                int numMessages = Integer.parseInt(scanner.nextLine());

                // Becomes true once the user has entered all their messages
                boolean messagesDone = false;

                // The menu choice
                String choice = "";

                // Keep running until the user chooses 3 (Quit)
                while (!choice.equals("3")) {

                    System.out.println();
                    System.out.println("Option 1) Send Messages");
                    System.out.println("Option 2) Show recently sent messages");
                    System.out.println("Option 3) Quit");
                    System.out.print("Choose an option: ");
                    choice = scanner.nextLine();

                    if (choice.equals("1")) {

                        // Only allow the number of messages the user chose
                        if (messagesDone) {
                            System.out.println("You have already entered all your messages.");
                        } else {

                            // For loop: runs once for each message the user wants to enter
                            // i is the loop counter and is used as the message number (starts at 0)
                            for (int i = 0; i < numMessages; i++) {

                                System.out.println();
                                System.out.println("Message " + (i + 1) + " of " + numMessages);

                                System.out.print("Enter the recipient cell number: ");
                                String recipient = scanner.nextLine();

                                System.out.print("Enter your message: ");
                                String text = scanner.nextLine();

                                // Ask again while the message is too long
                                while (text.length() > 250) {
                                   System.out.println("Please enter a message of less than 250 characters.");
                                    System.out.print("Enter your message: ");
                                    text = scanner.nextLine();
                                }

                                // Create the message with a random ID and the loop counter as the number
                                String id = Message.generateMessageID();
                                Message msg = new Message(id, recipient, text, i);

                                System.out.println(msg.getMessageIDText());

                                // Check the cell number
                                String cellResult = msg.checkRecipientCell();
                                System.out.println(cellResult);

                                // Ask again until the cell number is correct
                                while (!cellResult.equals("Cell phone number successfully captured.")) {
                                    System.out.print("Enter the recipient cell number again: ");
                                    recipient = scanner.nextLine();
                                    msg = new Message(id, recipient, text, i);
                                    cellResult = msg.checkRecipientCell();
                                    System.out.println(cellResult);
                                }

                                System.out.println("Message sent");
                                System.out.println();
                                System.out.println("1) Send Message");
                                System.out.println("2) Disregard Message");
                                System.out.println("3) Store Message to send later");
                                System.out.print("Choose an option: ");
                                int sendChoice = Integer.parseInt(scanner.nextLine());

                                // Show the result of the choice
                                System.out.println(msg.SentMessage(sendChoice));

                                if (sendChoice == 1) {
                                    // Show the full message details
                                    System.out.println();
                                    System.out.println(msg.getMessageDetails());
                                } else if (sendChoice == 2) {
                                    // Wait for the user to press 0
                                    System.out.print("Press 0 to delete the message: ");
                                    String delete = scanner.nextLine();

                                    if (delete.equals("0")) {
                                        System.out.println("Message deleted.");
                                    }
                                }
                            }

                            // The loop is finished, so all the messages have been entered
                            messagesDone = true;

                            // Show the total once all the messages have been entered
                            System.out.println();
                            System.out.println("Total messages sent: " + Message.returnTotalMessagess());
                        }

                    } else if (choice.equals("2")) {

                            // This feature is still in development
                               System.out.println("Coming Soon.");

                    } else if (choice.equals("3")) {
                        System.out.println("Total messages sent: " + Message.returnTotalMessagess());

                    } else {
                        System.out.println("Invalid option, please choose 1, 2 or 3.");
                    }
                }
            }

        } else {
            System.out.println();
            System.out.println("Please correct your registration details and try again.");
        }

        
        scanner.close();
    }
}