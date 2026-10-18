import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class CallRecord {
    private String name;
    private String phone;
    private String time;
    private String type; 

    public CallRecord(String name, String phone, String type) {
        this.name = name;
        this.phone = phone;
        this.type = type;
        this.time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MMM HH:mm:ss"));
    }

    public String getName() { return name; }
    public String getPhone() { return phone; }

    @Override
    public String toString() {
        return String.format("[%s] %-8s %-15s %s", time, type, name, phone);
    }
}
