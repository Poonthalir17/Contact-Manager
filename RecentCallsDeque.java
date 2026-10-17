
public class RecentCallsDeque {

    private static class Node {
        CallRecord data;
        Node prev, next;
        Node(CallRecord data) { this.data = data; }
    }

    private Node front;
    private Node rear;
    private int size;
    private final int capacity;

    public RecentCallsDeque(int capacity) {
        this.capacity = capacity;
    }

    /** Adds at the front. If full, the oldest record is dropped from the rear first. */
    public void addFront(CallRecord record) {
        if (size == capacity) {
            removeRear();
        }
        Node node = new Node(record);
        if (front == null) {
            front = rear = node;
        } else {
            node.next = front;
            front.prev = node;
            front = node;
        }
        size++;
    }

    public CallRecord removeRear() {
        if (rear == null) return null;
        CallRecord r = rear.data;
        rear = rear.prev;
        if (rear == null) {
            front = null;
        } else {
            rear.next = null;
        }
        size--;
        return r;
    }

    public boolean isEmpty() { return size == 0; }

    public void display() {
        if (isEmpty()) {
            System.out.println("  (no recent calls)");
            return;
        }
        Node cur = front;
        while (cur != null) {
            System.out.println("  " + cur.data);
            cur = cur.next;
        }
    }
}
