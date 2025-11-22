package Main;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

// IMPORT YOUR PROJECT CLASSES
import model.Task;
import model.Priority;
import service.TaskService;
import service.ReportService;
import utils.FileHandler;
import utils.InputValidator;

/**
 * Console UI - menu driven.
 */
public class Main {
    private static TaskService taskService;
    private static ReportService reportService;
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("=== Personal Task Manager ===");

        FileHandler.ensureDataFile();

        taskService = new TaskService();
        reportService = new ReportService(taskService);

        boolean running = true;
        while (running) {
            printMenu();
            String choice = sc.nextLine().trim();
            switch (choice) {
                case "1": addTaskFlow(); break;
                case "2": viewAllFlow(); break;
                case "3": markCompletedFlow(); break;
                case "4": editTaskFlow(); break;
                case "5": deleteTaskFlow(); break;
                case "6": viewByPriorityFlow(); break;
                case "7": todaysTasksFlow(); break;
                case "8": overdueTasksFlow(); break;
                case "9": summaryFlow(); break;
                case "0": running = false; break;
                default:
                    System.out.println("Invalid choice. Please choose again.");
            }
        }

        System.out.println("Exiting. Goodbye!");
        sc.close();
    }

    private static void printMenu() {
        System.out.println("\nMenu:");
        System.out.println("1. Add Task");
        System.out.println("2. View All Tasks");
        System.out.println("3. Mark Task as Completed");
        System.out.println("4. Edit Task");
        System.out.println("5. Delete Task");
        System.out.println("6. View Tasks by Priority");
        System.out.println("7. View Today's Tasks");
        System.out.println("8. View Overdue Tasks");
        System.out.println("9. Summary");
        System.out.println("0. Exit");
        System.out.print("Enter choice: ");
    }

    private static void addTaskFlow() {
        System.out.print("Enter title: ");
        String title = sc.nextLine().trim();

        System.out.print("Enter description: ");
        String desc = sc.nextLine().trim();

        System.out.print("Enter priority (HIGH/MEDIUM/LOW) [default MEDIUM]: ");
        String p = sc.nextLine().trim();
        Priority priority = InputValidator.parsePriority(p);

        System.out.print("Enter deadline (dd/MM/yyyy) or press Enter to skip: ");
        String dl = sc.nextLine().trim();
        LocalDate deadline = InputValidator.parseDateOrNull(dl);

        if (!dl.isEmpty() && deadline == null) {
            System.out.println("Invalid date format. Task not added.");
            return;
        }

        Task t = taskService.addTask(title, desc, priority, deadline);
        System.out.println("Task added: ID " + t.getId());
    }

    private static void viewAllFlow() {
        List<Task> list = taskService.listAll();
        if (list.isEmpty()) {
            System.out.println("No tasks found.");
            return;
        }
        for (Task t : list) {
            System.out.println(t);
            System.out.println("-----");
        }
    }

    private static void markCompletedFlow() {
        System.out.print("Enter task ID to mark completed: ");
        String s = sc.nextLine().trim();
        try {
            int id = Integer.parseInt(s);
            boolean ok = taskService.markCompleted(id);
            System.out.println(ok ? "Marked completed." : "Task not found.");
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID.");
        }
    }

    private static void editTaskFlow() {
        System.out.print("Enter task ID to edit: ");
        String s = sc.nextLine().trim();
        try {
            int id = Integer.parseInt(s);
            Task t = taskService.findById(id).orElse(null);
            if (t == null) { System.out.println("Task not found."); return; }

            System.out.println("Current: " + t);

            System.out.print("New title (press Enter to keep): ");
            String title = sc.nextLine().trim();
            if (title.isEmpty()) title = t.getTitle();

            System.out.print("New description (press Enter to keep): ");
            String desc = sc.nextLine().trim();
            if (desc.isEmpty()) desc = t.getDescription();

            System.out.print("New priority (HIGH/MEDIUM/LOW) (press Enter to keep): ");
            String p = sc.nextLine().trim();
            Priority priority = p.isEmpty() ? t.getPriority() : InputValidator.parsePriority(p);

            System.out.print("New deadline (dd/MM/yyyy) or blank to keep/remove: ");
            String dl = sc.nextLine().trim();
            LocalDate deadline;
            if (dl.isEmpty()) {
                deadline = t.getDeadline();
            } else {
                deadline = InputValidator.parseDateOrNull(dl);
                if (deadline == null) {
                    System.out.println("Invalid date. Edit cancelled.");
                    return;
                }
            }

            boolean ok = taskService.editTask(id, title, desc, priority, deadline);
            System.out.println(ok ? "Edited successfully." : "Edit failed.");

        } catch (NumberFormatException e) {
            System.out.println("Invalid ID.");
        }
    }

    private static void deleteTaskFlow() {
        System.out.print("Enter task ID to delete: ");
        String s = sc.nextLine().trim();
        try {
            int id = Integer.parseInt(s);
            boolean ok = taskService.deleteTask(id);
            System.out.println(ok ? "Deleted." : "Task not found.");
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID.");
        }
    }

    private static void viewByPriorityFlow() {
        System.out.print("Enter priority (HIGH/MEDIUM/LOW): ");
        String p = sc.nextLine().trim();
        Priority priority = InputValidator.parsePriority(p);

        List<Task> list = reportService.tasksByPriority(priority);
        if (list.isEmpty()) {
            System.out.println("No tasks with priority " + priority);
        } else {
            for (Task t : list) System.out.println(t + "\n-----");
        }
    }

    private static void todaysTasksFlow() {
        List<Task> list = reportService.todaysTasks();
        if (list.isEmpty()) {
            System.out.println("No tasks for today.");
        } else {
            for (Task t : list) System.out.println(t + "\n-----");
        }
    }

    private static void overdueTasksFlow() {
        List<Task> list = reportService.overdueTasks();
        if (list.isEmpty()) {
            System.out.println("No overdue tasks.");
        } else {
            for (Task t : list) System.out.println(t + "\n-----");
        }
    }

    private static void summaryFlow() {
        System.out.println(reportService.summaryCounts());
        Task next = reportService.nextTask();
        if (next != null) {
            System.out.println("Next upcoming task:\n" + next);
        } else {
            System.out.println("No upcoming tasks with deadline.");
        }
    }
}

