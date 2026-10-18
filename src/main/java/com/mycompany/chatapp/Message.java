package com.mycompany.chatapp;
import java.io.FileWriter;
public class Message {
    
    // Variables to store the message details
    private String messageID;
    private String recipient;
    private String message;
    private String messageHash;
    private int messageNumber;
    
    // An array to keep the sent messages (max 100)
    private static String[] sentMessages = new String[100];
    
    // Counts how many messages have been sent
    private static int totalMessages = 0;
    
    // An array to keep the stored messages as JSON text (max 100)
    private static String[] storedMessages = new String[100];
    
    // Counts how many messages have been stored
    private static int totalStored = 0;
    
    // Constructor to store the details
    public Message(String messageID, String recipient, String message, int messageNumber) {
        this.messageID = messageID;
        this.recipient = recipient;
        this.message = message;
        this.messageNumber = messageNumber;
    }
    // Makes a random ten-digit message ID
    public static String generateMessageID() {
        String digits = "";
        
        // Add one random digit (0 to 9) fifteen times
        for (int i = 0; i < 15; i++) {
            int digit = (int) (Math.random() * 10);
            digits = digits + digit;
        }
        // substring takes only the first ten digits
        String id = digits.substring(0, 10);
        return id;
    }
    // Returns the message ID text to display
    public String getMessageIDText() {
        return "Message ID generated: " + messageID;
    }
    // Checks that the message ID is not more than ten characters
    public boolean checkMessageID() {
        if (messageID.length() <= 10) {
            return true;
        } else {
            return false;
        }
    }
    // Checks the recipient number and returns a message
    public String checkRecipientCell() {
        // Must start with + and be no longer than 13 characters
        if (recipient.startsWith("+") && recipient.length() <= 13) {
            return "Cell phone number successfully captured.";
        } else {
            return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
        }
    }
    // Checks that the message is 250 characters or fewer
    public String checkMessageLength() {
        if (message.length() <= 250) {
            return "Message ready to send.";
        } else {
            int tooMany = message.length() - 250;
            return "Message exceeds 250 characters by " + tooMany + "; please reduce the size.";
        }
    }
    // Creates the hash, for example 00:0:HITONIGHT
    public String createMessageHash() {
        
        // First two numbers of the message ID
        String firstTwo = messageID.substring(0, 2);
        
        // Split the message into words
        String[] words = message.split(" ");
        String firstWord = words[0];
        String lastWord = words[words.length - 1];
        
        // Removes anything that is not a letter (like , or ?)
        firstWord = firstWord.replaceAll("[^A-Za-z]", "");
        lastWord = lastWord.replaceAll("[^A-Za-z]", "");
        
        // Joins it together and change it to capital letters
        messageHash = firstTwo + ":" + messageNumber + ":" + firstWord + lastWord;
        messageHash = messageHash.toUpperCase();
        return messageHash;
    }
    // Returns all the message details in the correct order
    public String getMessageDetails() {
        return "Message ID: " + messageID + "\n"
                + "Message Hash: " + createMessageHash() + "\n"
                + "Recipient: " + recipient + "\n"
                + "Message: " + message;
    }
    // The user's choice: 1 = send, 2 = disregard, 3 = store
    public String SentMessage(int choice) {
        if (choice == 1) {
            // Save the message in the array and add 1 to the total
            sentMessages[totalMessages] = getMessageDetails();
            totalMessages++;
            return "Message successfully sent.";
        } else if (choice == 2) {
            return "Press 0 to delete the message.";
        } else if (choice == 3) {
            storeMessage();
            return "Message successfully stored.";
        } else {
            return "Invalid choice.";
        }
    }
    // Returns all the messages sent while the program is running
    public static String printMessages() {
        String all = "";
        // Loop through every message that was sent
        for (int i = 0; i < totalMessages; i++) {
            all = all + sentMessages[i] + "\n\n";
        }
        return all;
    }
    // Returns the total number of messages sent
    public static int returnTotalMessagess() {
        return totalMessages;
    }
    // Stores the message in a JSON file 
     // Attribution: [https://www.w3schools.com/js/js_json.asp]
    
    public void storeMessage() {
        // Remove any quotation marks so the JSON text stays correct
        String safeMessage = message.replace("\"", "'");
        
        storedMessages[totalStored] = "  {\"messageID\": \"" + messageID + "\", "
                + "\"messageHash\": \"" + createMessageHash() + "\", "
                + "\"recipient\": \"" + recipient + "\", "
                + "\"message\": \"" + safeMessage + "\"}";
        totalStored++;
        
        try {
            // This writes the whole file again so it is always one correct JSON array
            FileWriter writer = new FileWriter("messages.json");
            writer.write("[\n");
            
            // Writes every stored message
            for (int i = 0; i < totalStored; i++) {
                writer.write(storedMessages[i]);
             
                if (i < totalStored - 1) {
                    writer.write(",");
                }
                writer.write("\n");
            }
            writer.write("]");
            writer.close();
        } catch (Exception e) {
            System.out.println("Could not store the message.");
        }
    }
}