package accountcreation;

import java.util.Random;

public class Message {

    // Parallel arrays
    private String[] sentMessages      = new String[100];
    private String[] disregardMessages = new String[100];
    private String[] messageHashes     = new String[100];
    private String[] messageIDs        = new String[100];
    private String[] recipients        = new String[100];

    // Counters
    private int sentCount       = 0;
    private int disregardCount  = 0;
    private int totalSent       = 0;

    // Checks recipient cell number
    public String checkRecipientCell(String cellphone) {
        if (cellphone.startsWith("+27")
                && cellphone.length() == 12) {
            return "Cell number accepted.";
        }
        return "Invalid cellphone number. "
             + "Must start with +27 and be 12 digits.";
    }

    // Validates message length
    public String validateMessageLength(String message) {
        if (message.length() <= 250) {
            return "Message ready to send. ("
                    + message.length() + " characters)";
        }
        return "Message exceeds 250 characters by "
                + (message.length() - 250) + " characters.";
    }

    // Creates random 10-digit message ID
    public String createMessageID() {
        Random random = new Random();
        long number = 1000000000L
                + (long) (random.nextDouble() * 9000000000L);
        return String.valueOf(number);
    }

    // Creates message hash
    public String createMessageHash(String messageID,
                                    String message) {
        String firstTwo = messageID.substring(0, 2);
        String[] words  = message.split(" ");
        String lastWord = words[words.length - 1];
        return (firstTwo + ":" + lastWord).toUpperCase();
    }

    // Sends a message
    public String sendMessage(String recipient,
                              String message,
                              String option) {

        String id   = createMessageID();
        String hash = createMessageHash(id, message);

        messageIDs[sentCount]    = id;
        messageHashes[sentCount] = hash;
        recipients[sentCount]    = recipient;

        if (option.equals("1")) {
            sentMessages[sentCount] = message;
            sentCount++;
            totalSent++;
            return "Message successfully sent.";

        } else if (option.equals("2")) {
            disregardMessages[disregardCount] = message;
            disregardCount++;
            return "Message disregarded.";

        } else if (option.equals("3")) {
            return "store";
        }

        return "Invalid option.";
    }

    // Returns total messages sent
    public int returnTotalMessages() { return totalSent; }

    // Getters
    public String[] getSentMessages()    { return sentMessages; }
    public String[] getMessageHashes()   { return messageHashes; }
    public String[] getMessageIDs()      { return messageIDs; }
    public String[] getRecipients()      { return recipients; }
    public int getSentCount()            { return sentCount; }
}