
public class ContactHashTable {

    private static class Entry {
        String key;
        Contact value;
        Entry next;
        Entry(String key, Contact value) {
            this.key = key;
            this.value = value;
        }
    }

    private Entry[] table = new Entry[101];
    private int size;

    private int hash(String key) {
        int h = 0;
        for (int i = 0; i < key.length(); i++) {
            h = (h * 31 + key.charAt(i)) % table.length;
        }
        return h;
    }

    public void put(String phone, Contact c) {
        int idx = hash(phone);
        Entry cur = table[idx];
        while (cur != null) {
            if (cur.key.equals(phone)) {
                cur.value = c;
                return;
            }
            cur = cur.next;
        }
        Entry e = new Entry(phone, c);
        e.next = table[idx];
        table[idx] = e;
        size++;
    }

    public Contact get(String phone) {
        Entry cur = table[hash(phone)];
        while (cur != null) {
            if (cur.key.equals(phone)) return cur.value;
            cur = cur.next;
        }
        return null;
    }

    public boolean contains(String phone) {
        return get(phone) != null;
    }

    public Contact remove(String phone) {
        int idx = hash(phone);
        Entry cur = table[idx];
        Entry prev = null;
        while (cur != null) {
            if (cur.key.equals(phone)) {
                if (prev == null) table[idx] = cur.next;
                else prev.next = cur.next;
                size--;
                return cur.value;
            }
            prev = cur;
            cur = cur.next;
        }
        return null;
    }

    public Contact[] toArray() {
        Contact[] all = new Contact[size];
        int k = 0;
        for (int i = 0; i < table.length; i++) {
            Entry cur = table[i];
            while (cur != null) {
                all[k++] = cur.value;
                cur = cur.next;
            }
        }
        return all;
    }
}
