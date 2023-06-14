package ca.java.tasktrackergui.model;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * The Task object is used to store task related information for the user.
 * It acts as a model and communicates with the UI.
 *
 * @author SI-Encoding
 */
public class Tasks {

    private String name;

    private String notes;

    private final GregorianCalendar dueDate;

    private boolean isComplete;

    public Tasks(String name, String notes, GregorianCalendar dueDate, boolean isComplete) {
        this.name = name;
        this.notes = notes;
        this.dueDate = dueDate;
        this.isComplete = isComplete;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getNotes() {
        return this.notes;
    }

    public GregorianCalendar getDueDate() {
        return this.dueDate;
    }

    public void setIsComplete(boolean isComplete) {

        this.isComplete = isComplete;
    }

    public boolean getIsComplete() {

        return this.isComplete;

    }

    private String decimalFormat(String pattern, int modify) {
        DecimalFormat decimal = new DecimalFormat(pattern);

        return decimal.format(modify);

    }

    @Override
    public String toString() {
        return "<html>Name: " + getName() + "<br />" + "Notes: " + getNotes() + "<br />" + "Due date: " + decimalFormat("0000", dueDate.get(Calendar.YEAR)) + "-"
                + decimalFormat("00", dueDate.get(Calendar.MONTH) + 1) + "-" + decimalFormat("00", dueDate.get(Calendar.DATE)) + " " + decimalFormat("00", dueDate.get(Calendar.HOUR_OF_DAY)) +
                ":" + decimalFormat("00", dueDate.get(Calendar.MINUTE)) + "</html>";
    }
}
