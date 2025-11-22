package model;

public enum TaskStatus {
    PENDING,
    COMPLETED;

    // Add this method to parse from string
    public static TaskStatus fromString(String s) {
        if (s == null) return PENDING; // default
        switch(s.toUpperCase()) {
            case "PENDING": return PENDING;
            case "COMPLETED": return COMPLETED;
            default: return PENDING; // default if unknown
        }
    }
}
