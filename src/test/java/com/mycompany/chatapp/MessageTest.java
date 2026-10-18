package com.mycompany.chatapp;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MessageTest {

    // Test 1: Check that a message with fewer than 250 characters is accepted
    @Test
    public void testMessageLengthSuccess() {
        Message msg = new Message("0012345678", "+27718693002", "Hi Mike, can you join us for dinner tonight?", 0);

        // Check that the correct message is returned
        assertEquals("Message ready to send.", msg.checkMessageLength());
    }

    // Test 2: Check that a message with more than 250 characters is rejected
    @Test
    public void testMessageLengthFailure() {

        // Create a message with 260 characters
        String longText = "";
        for (int i = 0; i < 260; i++) {
            longText = longText + "a";
        }

        Message msg = new Message("0012345678", "+27718693002", longText, 0);

        // Check that the message is 10 characters over the limit
        assertEquals("Message exceeds 250 characters by 10; please reduce the size.", msg.checkMessageLength());
    }

    // Test 3: Check that a correctly formatted recipient number is accepted
    @Test
    public void testRecipientSuccess() {
        Message msg = new Message("0012345678", "+27718693002", "Hi Mike, can you join us for dinner tonight?", 0);

        assertEquals("Cell phone number successfully captured.", msg.checkRecipientCell());
    }

    // Test 4: Check that a recipient number without an international code is rejected
    @Test
    public void testRecipientFailure() {
        Message msg = new Message("0112345678", "08575975889", "Hi Keegan, did you receive the payment?", 1);

        assertEquals("Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.",
                msg.checkRecipientCell());
    }

    // Test 5: Check that the message hash is created correctly
    @Test
    public void testMessageHashCase1() {
        Message msg = new Message("0012345678", "+27718693002", "Hi Mike, can you join us for dinner tonight?", 0);

        assertEquals("00:0:HITONIGHT", msg.createMessageHash());
    }

    // Test 6: Check that the correct hash is created for each message in the array
    @Test
    public void testMessageHashLoop() {
        String[] ids = {"0012345678", "0112345678"};
        String[] texts = {"Hi Mike, can you join us for dinner tonight?", "Hi Keegan, did you receive the payment?"};
        String[] expected = {"00:0:HITONIGHT", "01:1:HIPAYMENT"};

        // Goes through the messages and check each hash
        for (int i = 0; i < ids.length; i++) {
            Message msg = new Message(ids[i], "+27718693002", texts[i], i);
            assertEquals(expected[i], msg.createMessageHash());
        }
    }

    // Test 7: The message ID is created and is 10 characters or fewer
    @Test
    public void testMessageIDCreated() {
        String id = Message.generateMessageID();
        Message msg = new Message(id, "+27718693002", "Hi Mike, can you join us for dinner tonight?", 0);

        // The ID must be exactly 10 characters long
        assertEquals(10, id.length());
        assertTrue(msg.checkMessageID());
        assertEquals("Message ID generated: " + id, msg.getMessageIDText());
    }

    // Test 8: Check that the message is sent when the user chooses option 1
    @Test
    public void testMessageSent() {
        Message msg = new Message("0012345678", "+27718693002", "Hi Mike, can you join us for dinner tonight?", 0);

        assertEquals("Message successfully sent.", msg.SentMessage(1));
    }

    // Test 9: Check that the correct response is returned when the user chooses option 2
    @Test
    public void testMessageDisregarded() {
        Message msg = new Message("0112345678", "08575975889", "Hi Keegan, did you receive the payment?", 1);

        assertEquals("Press 0 to delete the message.", msg.SentMessage(2));
    }

    // Test 10: user selected Store Message
    @Test
    public void testMessageStored() {
        Message msg = new Message("0012345678", "+27718693002", "Hi Mike, can you join us for dinner tonight?", 0);

        assertEquals("Message successfully stored.", msg.SentMessage(3));

        // Check that the JSON file was created
        java.io.File file = new java.io.File("messages.json");
        assertTrue(file.exists());
    }

    // Test 11: Check that the total number of messages increases by one after sending
    @Test
    public void testTotalMessages() {

        // Get the total number of messages before sending
        int before = Message.returnTotalMessagess();

        Message msg = new Message("0012345678", "+27718693002", "Hi Mike, can you join us for dinner tonight?", 0);
        msg.SentMessage(1);

        // Check that the total has increased by one
        assertEquals(before + 1, Message.returnTotalMessagess());
    }
}