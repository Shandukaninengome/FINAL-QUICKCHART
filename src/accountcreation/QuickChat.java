package accountcreation;

import java.util.Scanner;

public class QuickChat {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        Login login         = new Login();
        Message msg         = new Message();
        StoredMessages stored = new StoredMessages();

        // ── WELCOME ───────────────────────────────────────
        System.out.println("=================================");
        System.out.println("   WELCOME TO QUICKCHAT");
        System.out.println("=================================");

        // ── REGISTRATION ──────────────────────────────────
        System.out.println("\n===== REGISTRATION =====");

        System.out.print("Enter firstname: ");
        String firstname = input.nextLine();
        
        System.out.print("Enter lastname: ");
        String lastname = input.nextLine();
        
        
        System.out.print("Enter username: ");
        String username = input.nextLine();

        System.out.print("Enter password: ");
        String password = input.nextLine();

        System.out.print("Enter cellphone number (+27): ");
        String cellphone = input.nextLine();

        String reg = login.registerUser(
                username, password, cellphone);
        System.out.println(reg);

        if (reg.equals("User registered successfully.")) {

            // LOGIN 
            System.out.println("\n===== LOGIN =====");

            System.out.print("Enter username: ");
            String loginUser = input.nextLine();

            System.out.print("Enter password: ");
            String loginPass = input.nextLine();

            boolean status = login.loginUser(
                    loginUser, loginPass);

            System.out.println(
                    login.returnLoginStatus(status, loginUser));

            if (status) {

                boolean running = true;

                while (running) {

                    // ── MAIN MENU ─────────────────────────
                    System.out.println("\n===== MAIN MENU =====");
                    System.out.println("1. Send Messages");
                    System.out.println("2. Show Recently Sent Messages");
                    System.out.println("3. Stored Messages");
                    System.out.println("4. Quit");
                    System.out.print("Select option: ");

                    String menu = input.nextLine();

                    // ── OPTION 1: Send ────────────────────
                    if (menu.equals("1")) {

                        System.out.print(
                                "How many messages? ");
                        int count = Integer.parseInt(
                                input.nextLine());

                        for (int i = 1; i <= count; i++) {

                            System.out.println(
                                    "\n=== MESSAGE " + i
                                    + " ===");

                            System.out.print(
                                    "Enter recipient (+27): ");
                            String recipient =
                                    input.nextLine();

                            System.out.println(
                                    msg.checkRecipientCell(
                                            recipient));

                            System.out.print(
                                    "Enter message: ");
                            String message = input.nextLine();

                            System.out.println(
                                    msg.validateMessageLength(
                                            message));

                            String id = msg.createMessageID();
                            String hash = msg.createMessageHash(
                                    id, message);

                            System.out.println(
                                    "Message ID:   " + id);
                            System.out.println(
                                    "Message Hash: " + hash);

                            System.out.println(
                                    "1. Send Message");
                            System.out.println(
                                    "2. Disregard Message");
                            System.out.println(
                                    "3. Store Message");
                            System.out.print(
                                    "Select option: ");

                            String option = input.nextLine();

                            String result = msg.sendMessage(
                                    recipient, message, option);

                            if (result.equals("store")) {
                                stored.addStored(
                                        recipient,
                                        message,
                                        id,
                                        hash);
                            } else {
                                System.out.println(result);
                            }
                        }

                    // ── OPTION 2: Sent messages ───────────
                    } else if (menu.equals("2")) {

                        int count = msg.getSentCount();

                        if (count == 0) {
                            System.out.println(
                                    "No messages sent yet.");
                        } else {
                            System.out.println(
                                    "\n===== SENT MESSAGES =====");
                            for (int i = 0; i < count; i++) {
                                System.out.println(
                                        "ID:        "
                                        + msg.getMessageIDs()[i]);
                                System.out.println(
                                        "Hash:      "
                                        + msg.getMessageHashes()[i]);
                                System.out.println(
                                        "Recipient: "
                                        + msg.getRecipients()[i]);
                                System.out.println(
                                        "Message:   "
                                        + msg.getSentMessages()[i]);
                                System.out.println(
                                        "---------------------");
                            }
                        }

                    // ── OPTION 3: Stored Messages ─────────
                    } else if (menu.equals("3")) {

                        stored.loadFromFile();
                        boolean storedMenu = true;

                        while (storedMenu) {

                            System.out.println(
                                    "\n===== STORED MESSAGES"
                                  + " MENU =====");
                            System.out.println(
                                    "1. Display all stored messages");
                            System.out.println(
                                    "2. Display longest message");
                            System.out.println(
                                    "3. Search by Message ID");
                            System.out.println(
                                    "4. Search by Recipient");
                            System.out.println(
                                    "5. Delete by Message Hash");
                            System.out.println(
                                    "6. Display report");
                            System.out.println(
                                    "7. Back to main menu");
                            System.out.print(
                                    "Select option: ");

                            String sub = input.nextLine();

                            if (sub.equals("1")) {
                                stored.displayAll();

                            } else if (sub.equals("2")) {
                                System.out.println(
                                        stored.longestMessage());

                           } else if (sub.equals("3")) {
                               System.out.print("Enter Message ID: ");
                               String searchID = input.nextLine().trim();
                               System.out.println(stored.searchByID(searchID));

                                               

                            } else if (sub.equals("4")) {
                                System.out.print(
                                        "Enter recipient: ");
                                stored.searchByRecipient(
                                        input.nextLine());

                            } else if (sub.equals("5")) {
                                System.out.print(
                                        "Enter Message Hash: ");
                                System.out.println(
                                        stored.deleteByHash(
                                                input.nextLine()));

                            } else if (sub.equals("6")) {
                                stored.displayReport();

                            } else if (sub.equals("7")) {
                                storedMenu = false;
                            }
                        }

                    // ── OPTION 4: Quit ────────────────────
                    } else if (menu.equals("4")) {

                        System.out.println(
                                "Total messages sent: "
                                + msg.returnTotalMessages());
                        System.out.println("Goodbye hope you enjoyed our chat app!");
                        running = false;
                    }
                }
            }
        }

        input.close();
    }
}