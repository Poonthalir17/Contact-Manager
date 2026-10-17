
public class MissedCallQueue {

    private static class Node {
        CallRecord data;
        Node next;
        Node(CallRecord data) { this.data = data; }
    }

    private Node front;
    private Node rear;
    private int size;

    public void enqueue(CallRecord record) {
        Node node = new Node(record);
        if (rear == null) {
            front = rear = node;
        } else {
            rear.next = node;
            rear = node;
        }
        size++;
    }

    public CallRecord dequeue() {
        if (front == null) return null;
        CallRecord r = front.data;
        front = front.next;
        if (front == null) rear = null;
        size--;
        return r;
    }

    public boolean isEmpty() { return size == 0; }

    public void display() {
        if (isEmpty()) {
            System.out.println("  (no missed calls)");
            return;
        }
        Node cur = front;
        int i = 1;
        while (cur != null) {
            System.out.println("  " + i++ + ". " + cur.data);
            cur = cur.next;
        }
    }
}
