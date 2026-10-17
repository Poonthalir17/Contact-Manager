
public class UndoStack {

    private Contact[] items = new Contact[10];
    private int top = -1;

    public void push(Contact c) {
        if (top == items.length - 1) {
            Contact[] bigger = new Contact[items.length * 2];
            for (int i = 0; i < items.length; i++) {
                bigger[i] = items[i];
            }
            items = bigger;
        }
        items[++top] = c;
    }

    public Contact pop() {
        if (isEmpty()) return null;
        Contact c = items[top];
        items[top--] = null;
        return c;
    }

    public boolean isEmpty() { return top == -1; }
}
