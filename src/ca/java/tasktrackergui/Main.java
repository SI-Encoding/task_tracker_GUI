package ca.java.tasktrackergui;

import ca.java.tasktrackergui.control.TasksArrayList;
import ca.java.tasktrackergui.view.UserInterface;

import javax.swing.*;

/**
 * This is where our program first starts running.
 * It hosts the model-view-controller paradigm
 * This program runs on a single thread to be safe.
 *
 * @author SI-Encoding
 */
public class Main {

    public static void main(String[] args) {

        TasksArrayList task = new TasksArrayList();
        TasksArrayList inCompleteTask = new TasksArrayList();
        SwingUtilities.invokeLater(() -> new UserInterface(task, inCompleteTask));
    }
}
