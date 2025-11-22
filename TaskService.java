package service;

import model.Task;
import model.Priority;
import model.TaskStatus;
import utils.FileHandler;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class TaskService {
    private List<Task> tasks;
    private int nextId;

    public TaskService() {
        FileHandler.ensureDataFile();
        this.tasks = FileHandler.readTasks();
        this.nextId = tasks.stream().mapToInt(Task::getId).max().orElse(0) + 1;
    }

    public Task addTask(String title, String desc, Priority priority, LocalDate deadline) {
        Task t = new Task(nextId++, title, desc, priority, deadline, TaskStatus.PENDING, LocalDate.now());
        tasks.add(t);
        FileHandler.writeTasks(tasks);
        return t;
    }

    public boolean editTask(int id, String title, String desc, Priority priority, LocalDate deadline) {
        Optional<Task> opt = findById(id);
        if (opt.isEmpty()) return false;
        Task t = opt.get();
        t.setTitle(title);
        t.setDescription(desc);
        t.setPriority(priority);
        t.setDeadline(deadline);
        FileHandler.writeTasks(tasks);
        return true;
    }

    public boolean deleteTask(int id) {
        boolean removed = tasks.removeIf(t -> t.getId() == id);
        if (removed) FileHandler.writeTasks(tasks);
        return removed;
    }

    public boolean markCompleted(int id) {
        Optional<Task> opt = findById(id);
        if (opt.isEmpty()) return false;
        Task t = opt.get();
        t.setStatus(TaskStatus.COMPLETED);
        FileHandler.writeTasks(tasks);
        return true;
    }

    public Optional<Task> findById(int id) {
        return tasks.stream().filter(t -> t.getId() == id).findFirst();
    }

    public List<Task> listAll() {
        List<Task> copy = new ArrayList<>(tasks);
        copy.sort(Comparator.comparing(Task::getPriority).reversed()
                .thenComparing(t -> t.getDeadline() == null ? LocalDate.MAX : t.getDeadline()));
        return copy;
    }

    public List<Task> getTasksList() {
        return tasks;
    }
}
