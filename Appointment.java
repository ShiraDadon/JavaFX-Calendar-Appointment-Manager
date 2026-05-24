import java.util.HashMap;

/**
 * Model class that stores appointments for specific dates.
 * Each appointment is associated with a date key in the format "YYYY-M-D".
 */
public class Appointment {

    // Stores appointments using a date key (e.g., "2025-5-20") mapped to a text description
    private HashMap<String, String> appointments = new HashMap<>();

    /**
     * Saves or updates the appointment for a specific date.
     *
     * @param dateKey The date in the format "YYYY-M-D" (e.g., "2025-5-20").
     * @param text    The appointment text to store for that date.
     */
    public void saveAppointment(String dateKey, String text) {
        appointments.put(dateKey, text);
    }

    /**
     * Retrieves the appointment text for a given date.
     *
     * @param dateKey The date in the format "YYYY-M-D" (e.g., "2025-5-20").
     * @return The stored appointment text, or an empty string if none exists.
     */
    public String getAppointment(String dateKey) {
        return appointments.getOrDefault(dateKey, "");
    }
}
