package utils;

import model.Task;
import model.Priority;
import model.TaskStatus;

import java.util.*;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;



/**
 * Simple file-based persistence. Stores tasks in data/tasks.txt
 */
public class FileHandler {
    private static final String DATA_DIR = "data";
    private static final String TASK_FILE = DATA_DIR + File.separator + "tasks.txt";
    private static final DateTimeFormatter DF = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    // Ensure data directory and file exist
    public static void ensureDataFile() {
        try {
            Path dir = Paths.get(DATA_DIR);
            if (!Files.exists(dir)) Files.createDirectories(dir);

            Path file = Paths.get(TASK_FILE);
            if (!Files.exists(file)) {
                Files.createFile(file);
                // add header (optional)
                try (BufferedWriter bw = Files.newBufferedWriter(file, StandardOpenOption.APPEND)) {
                    bw.write(Task.header());
                    bw.newLine();
                }
            }
        } catch (IOException e) {
            System.err.println("Failed to create data file: " + e.getMessage());
        }
    }

    // Read tasks from file
    public static List<Task> readTasks() {
        ensureDataFile();
        List<Task> tasks = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(TASK_FILE))) {
            String line;
            boolean first = true;
            while ((line = br.readLine()) != null) {
                if (first && line.startsWith("id|")) { first = false; continue; } // skip header
                first = false;
                if (line.trim().isEmpty()) continue;
                String[] parts = splitPreserveEscapes(line);
                // expected fields: id|title|description|priority|deadline|status|createdDate
                if (parts.length < 7) continue;
                try {
                    int id = Integer.parseInt(parts[0]);
                    String title = unescape(parts[1]);
                    String desc = unescape(parts[2]);
                    Priority pr = Priority.fromString(parts[3]);
                    LocalDate dl = (parts[4] == null || parts[4].trim().isEmpty()) ? null : LocalDate.parse(parts[4], DF);
                    TaskStatus ts = TaskStatus.fromString(parts[5]);
                    LocalDate created = LocalDate.parse(parts[6], DF);
                    Task t = new Task(id, title, desc, pr, dl, ts, created);
                    tasks.add(t);
                } catch (Exception ex) {
                    // skip malformed line
                    System.err.println("Skipping malformed task line: " + line);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading tasks: " + e.getMessage());
        }
        return tasks;
    }

    // Write full list of tasks (overwrite)
    public static void writeTasks(List<Task> tasks) {
        ensureDataFile();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(TASK_FILE, false))) {
            bw.write(Task.header());
            bw.newLine();
            for (Task t : tasks) {
                bw.write(t.toStorageString());
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error writing tasks: " + e.getMessage());
        }
    }

    // Split line while preserving escaped pipes "/|"
    private static String[] splitPreserveEscapes(String line) {
        List<String> parts = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        boolean escape = false;
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if (c == '/' && i + 1 < line.length() && line.charAt(i + 1) == '|') {
                sb.append('|'); // treat as literal '|'
                i++; // skip next char
                continue;
            } else if (c == '|') {
                parts.add(sb.toString());
                sb.setLength(0);
            } else {
                sb.append(c);
            }
        }
        parts.add(sb.toString());
        return parts.toArray(new String[0]);
    }

    private static String unescape(String s) {
        if (s == null) return "";
        return s.replace("/|", "|");
    }
}
