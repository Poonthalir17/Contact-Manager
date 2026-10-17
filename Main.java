import java.util.Scanner;

/**
 * Console menu for the Smart Contact and Call Manager.
 */
public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ContactManager manager = new ContactManager();

        System.out.println("=== Smart Contact and Call Manager ===");
        System.out.print("Load sample contacts for a quick demo? (y/n): ");
        if (sc.nextLine().trim().equalsIgnoreCase("y")) {
            loadSampleData(manager);
        }

        boolean running = true;
        while (running) {
            showMenu();
            System.out.print("Choose an option: ");
            String choice = sc.nextLine().trim();
            System.out.println();

            switch (choice) {
                case "1":
                    System.out.print("Name: ");
                    String name = sc.nextLine().trim();
                    System.out.print("Phone: ");
                    String phone = sc.nextLine().trim();
                    System.out.print("Group (Family/Work/Friends, or blank): ");
                    String group = sc.nextLine().trim();
                    manager.addContact(name, phone, group);
                    break;
                case "2":
                    System.out.print("Phone number to delete: ");
                    manager.deleteContact(sc.nextLine().trim());
                    break;
                case "3":
                    manager.undoDelete();
                    break;
                case "4":
                    System.out.print("Type name or first letters: ");
                    manager.searchByPrefix(sc.nextLine().trim());
                    break;
                case "5":
                    System.out.print("Phone number to call: ");
                    manager.makeCall(sc.nextLine().trim());
                    break;
                case "6":
                    System.out.print("Phone number of the missed call: ");
                    manager.addMissedCall(sc.nextLine().trim());
                    break;
                case "7":
                    manager.returnMissedCall();
                    break;
                case "8":
                    System.out.println("Recent calls (last 10):");
                    manager.showRecentCalls();
                    break;
                case "9":
                    System.out.println("Top favorites (most called):");
                    manager.showFavorites();
                    break;
                case "10":
                    System.out.println("Full call log (newest first):");
                    manager.showCallLog();
                    break;
                case "11":
                    System.out.println("All contacts:");
                    manager.showAllContacts();
                    break;
                case "12":
                    System.out.println("Missed calls waiting to be returned:");
                    manager.showMissedCalls();
                    break;
                case "13":
                    System.out.println("Contacts you have never called:");
                    manager.showCleanupSuggestions();
                    break;
                case "14":
                    System.out.print("Phone number to look up: ");
                    manager.findByPhone(sc.nextLine().trim());
                    break;
                case "0":
                    running = false;
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Invalid option, try again.");
            }
            System.out.println();
        }
        sc.close();
    }

    private static void showMenu() {
        System.out.println("-------------- MENU --------------");
        System.out.println(" 1. Add contact");
        System.out.println(" 2. Delete contact");
        System.out.println(" 3. Undo last delete");
        System.out.println(" 4. Search contact (type a prefix)");
        System.out.println(" 5. Make a call");
        System.out.println(" 6. Simulate a missed call");
        System.out.println(" 7. Return next missed call");
        System.out.println(" 8. View recent calls");
        System.out.println(" 9. View top favorites");
        System.out.println("10. View full call log");
        System.out.println("11. View all contacts");
        System.out.println("12. View missed calls");
        System.out.println("13. Cleanup suggestions");
        System.out.println("14. Find contact by phone number");
        System.out.println(" 0. Exit");
        System.out.println("----------------------------------");
    }

    private static void loadSampleData(ContactManager m) {
        m.addContact("Mom", "9876500001", "Family");
        m.addContact("Rahul", "9876500002", "Friends");
        m.addContact("Raj", "9876500003", "Work");
        m.addContact("Ravi", "9876500004", "Friends");
        m.addContact("Priya", "9876500005", "Work");
        m.addContact("Pizza Place", "9876500006", "General");
        m.addContact("Dad", "9876500007", "Family");
        System.out.println();
    }
}
