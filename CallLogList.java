 public class CallLogList {

    private static class Node {
        CallRecord data;
        Node next;
        Node(CallRecord data) { this.data = data; }
    }

    private Node head;
    private int size;

    /** Adds a record at the head in O(1). */
    public void addRecord(CallRecord record) {
        Node node = new Node(record);
        node.next = head;
        head = node;
        size++;
    }

    public boolean isEmpty() { return size == 0; }

    public void display() {
        if (isEmpty()) {
            System.out.println("  (call log is empty)");
            return;
        }
        Node cur = head;
        while (cur != null) {
            System.out.println("  " + cur.data);
            cur = cur.next;
        }
    }
}
