import java.time.LocalDate;
import java.time.LocalDateTime;

class Task {
    public int id;
    public String title;
    public String description;
    public LocalDate dueDate;
    public String status;

    public Task(int id, String title, String description, LocalDate dueDate, String status) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.status = status;
    }



    public String toString() {
        String statusColor = status.equals("done") ? "\u001B[32m" : "\u001B[31m";
        return "Task [ID=" + id + ", Title=" + title + ", Description=" + description + ", Due=" + dueDate +
                ", Status=" + statusColor + status + "\u001B[0m]";
    }
}