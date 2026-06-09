package accountcreation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MessageTest {

    Message msg;
    StoredMessages stored;

    @BeforeEach
    public void setUp() {
        msg    = new Message();
        stored = new StoredMessages();

        // Test Data Message 1 - Send
        msg.sendMessage("+27834557896",
                "Did you get the cake?", "1");

        // Test Data Message 2 - Store
        msg.sendMessage("+27838884567",
                "Where are you? You are late! I have asked you to be on time.",
                "3");

        // Test Data Message 3 - Disregard
        msg.sendMessage("+27834484567",
                "Yohoooo, I am at your gate.", "2");

        // Test Data Message 4 - Send
        msg.sendMessage("0838884567",
                "It is dinner time!", "1");

        // Manually add stored messages
        stored.addStored("+27838884567",
                "Where are you? You are late! I have asked you to be on time.",
                "1111111111", "11:TIME");

        stored.addStored("+27838884567",
                "Ok, I am leaving without you.",
                "2222222222", "22:YOU");
    }

    // Test 1: Sent messages array populated correctly
    @Test
    public void testSentMessagesPopulated() {
        String[] sent = msg.getSentMessages();
        assertEquals("Did you get the cake?", sent[0]);
        assertEquals("It is dinner time!", sent[1]);
    }

    // Test 2: Display longest message
    @Test
    public void testLongestMessage() {
        assertEquals(
            "Where are you? You are late! I have asked you to be on time.",
            stored.longestMessage()
        );
    }

    // Test 3: Search by message ID
    @Test
    public void testSearchByID() {
        String result = stored.searchByID("1111111111");
        assertTrue(result.contains(
            "Where are you? You are late! I have asked you to be on time."
        ));
    }

    // Test 4: Search by recipient
    @Test
    public void testSearchByRecipient() {
        stored.searchByRecipient("+27838884567");
    }

    // Test 5: Delete by hash
    @Test
    public void testDeleteByHash() {
        String result = stored.deleteByHash("11:TIME");
        assertTrue(result.contains("successfully deleted"));
    }

    // Test 6: Display report
    @Test
    public void testDisplayReport() {
        stored.displayReport();
    }

    // Test 7: Total messages sent
    @Test
    public void testTotalMessagesSent() {
        assertEquals(2, msg.returnTotalMessages());
    }

    // Test 8: Stored messages populated
    @Test
    public void testStoredMessagesPopulated() {
        String[] s = stored.getStoredMessages();
        assertEquals(
            "Where are you? You are late! I have asked you to be on time.",
            s[0]
        );
        assertEquals("Ok, I am leaving without you.", s[1]);
    }

    // Test 9: Valid recipient cell number
    @Test
    public void testCheckRecipientCell_Valid() {
        assertEquals("Cell number accepted.",
                msg.checkRecipientCell("+27831234567"));
    }

    // Test 10: Invalid recipient cell number
    @Test
    public void testCheckRecipientCell_Invalid() {
        assertNotEquals("Cell number accepted.",
                msg.checkRecipientCell("0831234567"));
    }

    // Test 11: Valid message length
    @Test
    public void testValidateMessageLength_Valid() {
        String result = msg.validateMessageLength("Hello");
        assertTrue(result.contains("ready to send"));
    }

    // Test 12: Message too long
    @Test
    public void testValidateMessageLength_TooLong() {
        String result = msg.validateMessageLength(
                "a".repeat(300));
        assertTrue(result.contains("exceeds"));
    }
}