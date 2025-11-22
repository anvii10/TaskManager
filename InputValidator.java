package utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import model.Priority;  // Add this

/**
 * Small utility for input validation.
 */
public class InputValidator {
    private static final DateTimeFormatter DF = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    // Validate and parse date in dd/MM/yyyy format. Return null if empty.
    public static LocalDate parseDateOrNull(String s) {
        if (s == null) return null;
        s = s.trim();
        if (s.isEmpty()) return null;
        try {
            return LocalDate.parse(s, DF);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    // Validate priority string -> Priority enum
    public static Priority parsePriority(String s) {
        if (s == null) return Priority.MEDIUM;
        return Priority.fromString(s);
    }
}
