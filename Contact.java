
public class Contact {
    private String name;
    private String phone;
    private String group;
    private int callCount;

    public Contact(String name, String phone, String group) {
        this.name = name;
        this.phone = phone;
        this.group = group;
        this.callCount = 0;
    }

    public String getName() { return name; }
    public String getPhone() { return phone; }
    public int getCallCount() { return callCount; }

    public void incrementCallCount() { callCount++; }

    @Override
    public String toString() {
        return String.format("%-15s %-12s %-8s calls: %d", name, phone, group, callCount);
    }
}
