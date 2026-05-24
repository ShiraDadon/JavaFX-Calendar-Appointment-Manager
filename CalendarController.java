import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.control.ComboBox;

import java.util.Calendar;

/**
 * Controller for the Calendar application.
 * Manages the UI interaction and updates the calendar grid based on user selections.
 */
public class CalendarController {

    @FXML
    private ComboBox<Integer> yearCombo;

    @FXML
    private ComboBox<String> monthCombo;

    @FXML
    private GridPane calendarGrid;

    private Appointment model = new Appointment();

    private final String[] months = {
        "January", "February", "March", "April", "May", "June",
        "July", "August", "September", "October", "November", "December"
    };

    private final String[] days = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};

    /**
     * Initializes the controller after the FXML components are loaded.
     * Sets up the year and month selection ComboBoxes and builds the initial calendar view.
     */
    @FXML
    public void initialize() {
        // Populate year dropdown
        for (int y = 2022; y <= 2025; y++) {
            yearCombo.getItems().add(y);
        }
        yearCombo.setValue(Calendar.getInstance().get(Calendar.YEAR));

        // Populate month dropdown
        for (String month : months) {
            monthCombo.getItems().add(month);
        }
        monthCombo.setValue(months[Calendar.getInstance().get(Calendar.MONTH)]);

        // Set event listeners
        yearCombo.setOnAction(e -> buildCalendar());
        monthCombo.setOnAction(e -> buildCalendar());

        // Build calendar view
        buildCalendar();
    }

    /**
     * Builds and displays the calendar for the selected month and year.
     * Adds buttons for each day, and allows the user to enter/view appointments.
     */
    private void buildCalendar() {
        calendarGrid.getChildren().clear();

        // Add day-of-week headers in the first row
        for (int i = 0; i < days.length; i++) {
            Label lbl = new Label(days[i]);
            lbl.setStyle("-fx-font-weight: bold; -fx-alignment: center;");
            lbl.setMaxWidth(Double.MAX_VALUE);
            calendarGrid.add(lbl, i, 0);
        }

        int year = yearCombo.getValue();
        int month = monthCombo.getSelectionModel().getSelectedIndex();

        Calendar cal = Calendar.getInstance();
        cal.set(year, month, 1);

        int firstDay = cal.get(Calendar.DAY_OF_WEEK); // Sunday=1, Monday=2, ...
        int daysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

        int col = (firstDay - 1) % 7; // Column to start from (0=Sunday)
        int row = 1; // Row index starts from 1 (row 0 is for headers)

        // Create buttons for each day of the month
        for (int day = 1; day <= daysInMonth; day++) {
            Button dayButton = new Button(String.valueOf(day));
            dayButton.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
            int currentDay = day;

            dayButton.setOnAction(e -> {
                String dateKey = year + "-" + (month + 1) + "-" + currentDay;
                TextInputDialog dialog = new TextInputDialog(model.getAppointment(dateKey));
                dialog.setTitle("Appointments");
                dialog.setHeaderText("Appointments for " + dateKey);
                dialog.setContentText("Enter your appointments:");

                dialog.showAndWait().ifPresent(text -> {
                    model.saveAppointment(dateKey, text);
                });
            });

            calendarGrid.add(dayButton, col, row);
            col++;
            if (col > 6) {
                col = 0;
                row++;
            }
        }
    }
}
