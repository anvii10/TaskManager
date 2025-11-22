package model;

public enum Priority {
    HIGH,
    MEDIUM,
    LOW;

    // Add this method to parse from string
    public static Priority fromString(String s) {
        if (s == null) return MEDIUM; // default
        switch(s.toUpperCase()) {
            case "HIGH": return HIGH;
            case "MEDIUM": return MEDIUM;
            case "LOW": return LOW;
            default: return MEDIUM; // default if unknown
        }
    }
}
