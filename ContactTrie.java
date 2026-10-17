import java.util.ArrayList;
public class ContactTrie {
    
    private static class TrieNode {
        TrieNode[] children = new TrieNode[128];
        Contact contact; 
    }

    private final TrieNode root = new TrieNode();

   
    public boolean insert(String name, Contact c) {
        TrieNode cur = root;
        String key = name.toLowerCase();
        for (int i = 0; i < key.length(); i++) {
            int idx = key.charAt(i);
            if (idx >= 128) continue; 
            if (cur.children[idx] == null) {
                cur.children[idx] = new TrieNode();
            }
            cur = cur.children[idx];
        }
        if (cur.contact != null) return false;
        cur.contact = c;
        return true;
    }

    public boolean contains(String name) {
        TrieNode node = find(name.toLowerCase());
        return node != null && node.contact != null;
    }

    public boolean delete(String name) {
        TrieNode node = find(name.toLowerCase());
        if (node == null || node.contact == null) return false;
        node.contact = null;
        return true;
    }

    public ArrayList<Contact> searchPrefix(String prefix) {
        ArrayList<Contact> results = new ArrayList<>();
        TrieNode node = find(prefix.toLowerCase());
        if (node == null) return results;
        collect(node, results);


        for (int i = 1; i < results.size(); i++) {
            Contact key = results.get(i);
            int j = i - 1;
            while (j >= 0 && results.get(j).getCallCount() < key.getCallCount()) {
                results.set(j + 1, results.get(j));
                j--;
            }
            results.set(j + 1, key);
        }
        return results;
    }

    private TrieNode find(String key) {
        TrieNode cur = root;
        for (int i = 0; i < key.length(); i++) {
            int idx = key.charAt(i);
            if (idx >= 128) continue;
            if (cur.children[idx] == null) return null;
            cur = cur.children[idx];
        }
        return cur;
    }

    private void collect(TrieNode node, ArrayList<Contact> out) {
        if (node.contact != null) out.add(node.contact);
        for (int i = 0; i < 128; i++) {
            if (node.children[i] != null) {
                collect(node.children[i], out);
            }
        }
    }
}
