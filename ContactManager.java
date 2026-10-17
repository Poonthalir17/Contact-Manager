import java.util.ArrayList;

public class ContactManager {

    private final ContactHashTable contacts = new ContactHashTable(); 
    private final ContactTrie trie = new ContactTrie();             
    private final FavoritesPQ favorites = new FavoritesPQ();          
    private final CallLogList callLog = new CallLogList();          
    private final RecentCallsDeque recent = new RecentCallsDeque(10); 
    private final MissedCallQueue missed = new MissedCallQueue();     
    private final UndoStack undoStack = new UndoStack();              


    public void addContact(String name, String phone, String group) {
        if (name.isEmpty() || phone.isEmpty()) {
            System.out.println("Name and phone cannot be empty.");
            return;
        }
        if (contacts.contains(phone)) {
            System.out.println("A contact with this number already exists.");
            return;
        }
        if (trie.contains(name)) {
            System.out.println("A contact with this name already exists. Use a different name.");
            return;
        }
        Contact c = new Contact(name, phone, group.isEmpty() ? "General" : group);
        register(c);
        System.out.println("Added: " + name);
    }

    public void deleteContact(String phone) {
        Contact c = contacts.remove(phone);
        if (c == null) {
            System.out.println("No contact with that number.");
            return;
        }
        trie.delete(c.getName());
        favorites.remove(c);
        undoStack.push(c);
        System.out.println("Deleted: " + c.getName() + " (you can undo this)");
    }

    public void undoDelete() {
        Contact c = undoStack.pop();
        if (c == null) {
            System.out.println("Nothing to undo.");
            return;
        }
        if (contacts.contains(c.getPhone()) || trie.contains(c.getName())) {
            System.out.println("Cannot restore " + c.getName()
                    + ": the number or name is now used by another contact.");
            return;
        }
        register(c);
        System.out.println("Restored: " + c.getName() + " (call count kept: " + c.getCallCount() + ")");
    }

   
    private void register(Contact c) {
        contacts.put(c.getPhone(), c);
        trie.insert(c.getName(), c);
        favorites.insert(c);
    }

    public void showAllContacts() {
        Contact[] all = contacts.toArray();
        if (all.length == 0) {
            System.out.println("  (no contacts)");
            return;
        }
        for (Contact c : all) {
            System.out.println("  " + c);
        }
    }



    
    public void searchByPrefix(String prefix) {
        if (prefix.isEmpty()) {
            System.out.println("Type at least one letter.");
            return;
        }
        ArrayList<Contact> results = trie.searchPrefix(prefix);
        if (results.isEmpty()) {
            System.out.println("  No contacts start with \"" + prefix + "\".");
            return;
        }
        System.out.println("  Matches (most called first):");
        for (Contact c : results) {
            System.out.println("  " + c);
        }
    }

    public void findByPhone(String phone) {
        Contact c = contacts.get(phone);
        System.out.println(c == null ? "  Not found." : "  " + c);
    }




    

    public void makeCall(String phone) {
        Contact c = contacts.get(phone);
        if (c == null) {
            System.out.println("No contact with that number.");
            return;
        }
        /
        favorites.remove(c);
        c.incrementCallCount();
        favorites.insert(c);

        CallRecord rec = new CallRecord(c.getName(), c.getPhone(), "Dialed");
        callLog.addRecord(rec);
        recent.addFront(rec);
        System.out.println("Calling " + c.getName() + "... (total calls: " + c.getCallCount() + ")");
    }

    public void addMissedCall(String phone) {
        Contact c = contacts.get(phone);
        if (c == null) {
            System.out.println("No contact with that number.");
            return;
        }
        CallRecord rec = new CallRecord(c.getName(), c.getPhone(), "Missed");
        callLog.addRecord(rec);
        missed.enqueue(rec);
        System.out.println("Missed call from " + c.getName() + " added to the queue.");
    }

    public void returnMissedCall() {
        CallRecord rec = missed.dequeue();
        if (rec == null) {
            System.out.println("No missed calls to return.");
            return;
        }
        if (!contacts.contains(rec.getPhone())) {
            System.out.println("Missed call from " + rec.getName() + " skipped (contact was deleted).");
            return;
        }
        System.out.println("Returning missed call from " + rec.getName());
        makeCall(rec.getPhone());
    }



    

    public void showMissedCalls() {
        missed.display();
    }

    public void showRecentCalls() {
        recent.display();
    }

    public void showCallLog() {
        callLog.display();
    }

    public void showFavorites() {
        Contact[] top = favorites.topK(5);
        boolean any = false;
        int rank = 1;
        for (Contact c : top) {
            if (c.getCallCount() > 0) {
                System.out.println("  #" + rank++ + " " + c);
                any = true;
            }
        }
        if (!any) System.out.println("  (no favorites yet, make some calls first)");
    }





    
    public void showCleanupSuggestions() {
        Contact[] all = contacts.toArray();
        boolean any = false;
        for (Contact c : all) {
            if (c.getCallCount() == 0) {
                System.out.println("  " + c);
                any = true;
            }
        }
        if (!any) System.out.println("  (you have called every contact at least once)");
    }
}
