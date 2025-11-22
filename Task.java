package model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Task {
    private int id;
    private String title;
    private String description;
    private Priority priority;
    private LocalDate deadline;
    private TaskStatus status;
    private LocalDate createdDate;

    private static final DateTimeFormatter DF = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public Task(int id, String title, String description, Priority priority,
                LocalDate deadline, TaskStatus status, LocalDate createdDate) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.deadline = deadline;
        this.status = status;
        this.createdDate = createdDate;
    }

    // GETTERS
    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public Priority getPriority() { return priority; }
    public LocalDate getDeadline() { return deadline; }
    public TaskStatus getStatus() { return status; }
    public LocalDate getCreatedDate() { return createdDate; }

    // SETTERS
    public void setTitle(String t) { this.title = t; }
    public void setDescription(String d) { this.description = d; }
    public void setPriority(Priority p) { this.priority = p; }
    public void setDeadline(LocalDate d) { this.deadline = d; }
    public void setStatus(TaskStatus s) { this.status = s; }

    public String toStorageString() {
        String dl = (deadline != null ? deadline.format(DF) : "");
        return id + "|" + title + "|" + description + "|" +
                priority.name() + "|" + dl + "|" + status.name() +
                "|" + createdDate.format(DF);
    }

    public static String header() {
        return "ID|TITLE|DESCRIPTION|PRIORITY|DEADLINE|STATUS|CREATED";
    }
}
