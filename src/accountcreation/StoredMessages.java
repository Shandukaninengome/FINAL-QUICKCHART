package accountcreation;

import java.io.*;
import org.json.simple.*;
import org.json.simple.parser.*;

public class StoredMessages {

    // Parallel arrays
    private String[] storedMessages = new String[100];
    private String[] recipients     = new String[100];
    private String[] messageIDs     = new String[100];
    private String[] messageHashes  = new String[100];

    private int storedCount = 0;

    // Adds a stored message to arrays and saves to JSON
    public void addStored(String recipient,
                          String message,
                          String id,
                          String hash) {

        storedMessages[storedCount] = message;
        recipients[storedCount]     = recipient;
        messageIDs[storedCount]     = id;
        messageHashes[storedCount]  = hash;

        saveToFile(recipient, message, id, hash);
        storedCount++;

        System.out.println("Message successfully stored.");
         System.out.println("Message Hash: " + hash);
          System.out.println("Message ID: " + id);
    }

    // Saves message to JSON file
    public void saveToFile(String recipient,
                           String message,
                           String id,
                           String hash) {
        try {
            JSONArray list = new JSONArray();
            File file = new File("messages.json");

            if (file.exists()) {
                JSONParser parser = new JSONParser();
                list = (JSONArray) parser.parse(
                        new FileReader(file));
            }

            JSONObject obj = new JSONObject();
            obj.put("recipient", recipient);
            obj.put("message",   message);
            obj.put("id",        id);
            obj.put("hash",      hash);
            list.add(obj);

            FileWriter fw = new FileWriter("messages.json");
            fw.write(list.toJSONString());
            fw.flush();
            fw.close();

        } catch (Exception e) {
            System.out.println(
                    "Error saving: " + e.getMessage());
        }
    }

    // Loads messages from JSON file into arrays
    public void loadFromFile() {
        try {
            File file = new File("messages.json");
            if (!file.exists()) {
                System.out.println("No stored messages found.");
                return;
            }

            JSONParser parser = new JSONParser();
            JSONArray arr = (JSONArray) parser.parse(
                    new FileReader(file));

            storedCount = 0;
            for (Object o : arr) {
                JSONObject obj = (JSONObject) o;
                storedMessages[storedCount] =
                        (String) obj.get("message");
                recipients[storedCount] =
                        (String) obj.get("recipient");
                messageIDs[storedCount] =
                        (String) obj.get("id");
                messageHashes[storedCount] =
                        (String) obj.get("hash");
                storedCount++;
            }
            System.out.println(
                    "Stored messages loaded successfully.");

        } catch (Exception e) {
            System.out.println(
                    "Error loading: " + e.getMessage());
        }
    }

    // Displays sender and recipient of all stored messages
    public void displayAll() {
        if (storedCount == 0) {
            System.out.println("No stored messages.");
            return;
        }
        System.out.println("===== STORED MESSAGES =====");
        for (int i = 0; i < storedCount; i++) {
            System.out.println("Recipient: " + recipients[i]);
            System.out.println("Message:   " + storedMessages[i]);
            System.out.println("---------------------------");
        }
    }

    // Returns the longest stored message
    public String longestMessage() {
        String longest = "";
        for (int i = 0; i < storedCount; i++) {
            if (storedMessages[i] != null
                    && storedMessages[i].length()
                    > longest.length()) {
                longest = storedMessages[i];
            }
        }
        if (longest.isEmpty()) return "No messages found.";
        return longest;
    }

    // Searches for a message by ID
    public String searchByID(String id) {
        id = id.trim();
        for (int i = 0; i < storedCount; i++) {
            if (messageIDs[i] != null
                    && messageIDs[i].trim().equals(id)) {
                return "Recipient: " + recipients[i]
                     + "\nMessage:   " + storedMessages[i];
            }
        }
        return "Message ID not found.";
    }

    // Searches all messages for a recipient
    public void searchByRecipient(String recipient) {
    boolean found = false;
    recipient = recipient.trim();
    
    for (int i = 0; i < storedCount; i++) {
        if (recipients[i] != null
                && recipients[i].trim().equals(recipient)) {
            System.out.println("Recipient: " + recipients[i]);
            System.out.println("Message:   " + storedMessages[i]);
            System.out.println("--------------------------");
            found = true;
        }
    }
    if (!found) {
        System.out.println(
                "No messages found for: " + recipient);
    }
}

    // Deletes a message using its hash
    public String deleteByHash(String hash) {
    hash = hash.trim();
    for (int i = 0; i < storedCount; i++) {
        if (messageHashes[i] != null
                && messageHashes[i].trim().equals(hash)) {
            String deleted = storedMessages[i];
            storedMessages[i] =
                    storedMessages[storedCount - 1];
            messageHashes[i] =
                    messageHashes[storedCount - 1];
            recipients[i] =
                    recipients[storedCount - 1];
            storedCount--;
            return "Message: \"" + deleted
                 + "\" successfully deleted.";
        }
    }
    return "Hash not found.";
}

    // Displays full report
    public void displayReport() {
        System.out.println("===== MESSAGE REPORT =====");
        for (int i = 0; i < storedCount; i++) {
            System.out.println(
                    "Hash:      " + messageHashes[i]);
            System.out.println(
                    "Recipient: " + recipients[i]);
            System.out.println(
                    "Message:   " + storedMessages[i]);
            System.out.println(
                    "--------------------------");
        }
    }

    // Getters
    public String[] getStoredMessages() { return storedMessages; }
    public String[] getRecipients()     { return recipients; }
    public String[] getMessageIDs()     { return messageIDs; }
    public String[] getMessageHashes()  { return messageHashes; }
    public int getStoredCount()         { return storedCount; }
}