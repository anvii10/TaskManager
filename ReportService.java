package service;

import model.Task;
import model.Priority;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class ReportService {
    private TaskService ts;

    public ReportService(TaskService ts) {
        this.ts = ts;
    }

    public List<Task> tasksByPriority(Priority p) {
        return ts.getTasksList().stream()
                .filter(t -> t.getPriority() == p)
                .collect(Collectors.toList());
    }

    public List<Task> todaysTasks() {
        LocalDate today = LocalDate.now();
        return ts.getTasksList().stream()
                .filter(t -> t.getDeadline() != null && t.getDeadline().isEqual(today))
                .collect(Collectors.toList());
    }

    public List<Task> overdueTasks() {
        LocalDate today = LocalDate.now();
        return ts.getTasksList().stream()
                .filter(t -> t.getDeadline() != null && t.getDeadline().isBefore(today) && t.getStatus() != model.TaskStatus.COMPLETED)
                .collect(Collectors.toList());
    }

    public String summaryCounts() {
        long pending = ts.getTasksList().stream().filter(t -> t.getStatus() != model.TaskStatus.COMPLETED).count();
        long completed = ts.getTasksList().stream().filter(t -> t.getStatus() == model.TaskStatus.COMPLETED).count();
        return "Pending: " + pending + ", Completed: " + completed;
    }

    public Task nextTask() {
        LocalDate today = LocalDate.now();
        return ts.getTasksList().stream()
                .filter(t -> t.getDeadline() != null && t.getDeadline().isAfter(today))
                .sorted((a, b) -> a.getDeadline().compareTo(b.getDeadline()))
                .findFirst()
                .orElse(null);
    }
}
