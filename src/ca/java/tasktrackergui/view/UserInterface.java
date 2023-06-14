package ca.java.tasktrackergui.view;

import ca.java.tasktrackergui.control.DueDateComparison;
import ca.java.tasktrackergui.control.TasksArrayList;
import ca.java.tasktrackergui.model.Tasks;
import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import com.github.lgooddatepicker.components.TimePicker;
import com.github.lgooddatepicker.components.TimePickerSettings;
import com.github.lgooddatepicker.optionalusertools.DateChangeListener;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.GregorianCalendar;

/**
 * The userInterface class is where the GUI will be rendered to display.
 * It provides user interactivity with buttons and a combo box to select from.
 * Image taken from: https://thenounproject.com/term/calendar/404/
 * LGoodDatePicker from https://github.com/LGoodDatePicker/LGoodDatePicker/blob/master/Project/src/main/java/com/github/lgooddatepicker/demo/FullDemo.java
 * InputStream from https://zetcode.com/java/inputstream/
 *
 * @author SI-Encoding
 */
//
public class UserInterface implements ActionListener {

    private final Color selected = new Color(51, 153, 255);

    // Frames
    private final JFrame applicationFrame = new JFrame("My To-Do List");

    private final JFrame dialogFrame = new JFrame();

    private final JFrame dialogWarningFrame = new JFrame();

    private final JLabel nameLabel = new JLabel("Name:");

    private final JLabel notesLabel = new JLabel("Notes:");

    private final JLabel dueDateLabel = new JLabel("Due Date:");

    // Image Icon
    private final ImageIcon imgIcon = new ImageIcon("./src/calendar.png");

    // button containers
    private final JPanel menuButtonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 25));

    private final JPanel bottomPanel = new JPanel(new BorderLayout());

    private final JPanel addTaskButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 15));

    private final JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 14));

    // buttons components
    private final JButton allButton = new JButton("All");

    private final JButton overdueButton = new JButton("Overdue");

    private final JButton upcomingButton = new JButton("Upcoming");

    private final JButton addTaskButton = new JButton("Add Task");

    // Dialog buttons

    private final JButton generateButton = new JButton("Generate");

    private final JButton createButton = new JButton("Create");

    private final JButton cancelButton = new JButton("Cancel");

    private JButton imgButton;

    // changing cardboard panels
    private final GridBagLayout gridBagLayout = new GridBagLayout();

    private final JPanel allTasksPanel = new JPanel(gridBagLayout);

    private final JPanel upcomingTasksPanel = new JPanel(gridBagLayout);

    private final JPanel overdueTasksPanel = new JPanel(gridBagLayout);

    private final JPanel mainPanel = new JPanel();

    private final CardLayout cardLayout = new CardLayout();

    private final GridBagConstraints gridBagConstraints = new GridBagConstraints();

    private final JPanel panel = new JPanel();

    private final JPanel panel2 = new JPanel();

    private final JPanel panel3 = new JPanel();

    private final JScrollPane allTaskscontainer = new JScrollPane(panel);

    private final JScrollPane upcomingTaskscontainer = new JScrollPane(panel3);

    private final JScrollPane overDueTaskscontainer = new JScrollPane(panel2);

    // Dialog panels
    private final JPanel namePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 25));

    private final JPanel notesPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));

    private final JPanel dueDatePanel = new JPanel(new FlowLayout(FlowLayout.LEADING, 14, 25));

    private final JPanel textAreaPanel = new JPanel(new BorderLayout());

    private final JPanel dialogButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));

    private final JPanel dialogFooterPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 14));

    private final JPanel dialogBottomPanel = new JPanel(new BorderLayout());

    private final JPanel generatePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 0));

    private final JPanel dueDateLabelPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));

    private final JPanel dueDateIndentPanel = new JPanel();

    // JDialog

    private final JDialog dialog = new JDialog(dialogFrame, "Add Task");

    // Text Fields

    private final JTextField nameTextField = new JTextField();

    private final JTextField notesTextField = new JTextField();

    private final JTextField duesDateTextField = new JTextField("YYYY-MM-DD");

    // DateTimePicker

    private final DatePickerSettings datePickerSettings = new DatePickerSettings();

    private final DatePicker datePicker = new DatePicker(datePickerSettings);

    private final TimePickerSettings timePickerSettings = new TimePickerSettings();

    private TimePicker timePicker;

    // storing information

    private final TasksArrayList task;

    private final TasksArrayList incompleteTask;

    private String taskNameInput;

    private String taskNotesInput;

    private int taskYearInput;

    private int taskMonthInput;

    private int taskDayInput;

    private int taskHourInput;

    private int taskMinuteInput;

    private LocalDate date;

    private GregorianCalendar calendar;

    private final GregorianCalendar now = new GregorianCalendar();

    // constants

    private final int DECREMENT = 1;

    private final int CALENDAR_COMPARISON = 0;

    // displaying tasks

    private JPanel[] taskPanel;

    private JLabel[] tasksDescriptionLabel;

    private JPanel[] tasksDescriptionPanel;

    private JPanel[] tasksDescriptionBottomPanel;

    private JCheckBox[] checkBox;

    private JLabel[] completed;

    private JPanel[] tasksDescriptionBottomIndent;

    private JButton[] removeTask;

    // overdue tasks
    private JPanel[] overdueTaskPanel;

    private JLabel[] tasksDescriptionLabel2;

    private JPanel[] tasksDescriptionPanel2;

    private JPanel[] tasksDescriptionBottomPanel2;

    private JPanel[] tasksDescriptionBottomIndent2;

    private JButton[] removeTask2;

    // upcoming tasks
    private JPanel[] upcomingTaskPanel;

    private JLabel[] tasksDescriptionLabel3;

    private JPanel[] tasksDescriptionPanel3;

    private JPanel[] tasksDescriptionBottomPanel3;

    private JPanel[] tasksDescriptionBottomIndent3;

    private JButton[] removeTask3;

    public UserInterface(TasksArrayList task, TasksArrayList incompleteTask) {

        this.task = task;
        this.incompleteTask = incompleteTask;

        initializeAndConstruct();

        addActionListenersForTaskRelatedPanels();

        addButtonContainersForTaskRelatedPanels();

        addConstraintsForCenterContainer();

        addTaskButtonContainer();

        packageAllTheContainers();

        setToAllTaskPageByDefault();

        constructAddTaskWindow();

        displayTasks();

        applicationFrame.setVisible(true);
    }

    private void initializeAndConstruct() {

        task.loadData();

        applicationFrame.setSize(540, 580);
        applicationFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        applicationFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent ev) {
                task.saveData();
                System.exit(0);
            }
        });

        dialog.setSize(410, 340);

    }

    private void addActionListenersForTaskRelatedPanels() {

        allButton.addActionListener(this);

        upcomingButton.addActionListener(this);

        overdueButton.addActionListener(this);

        addTaskButton.addActionListener(this);

    }

    private void addButtonContainersForTaskRelatedPanels() {

        menuButtonsPanel.add(allButton);

        menuButtonsPanel.add(overdueButton);

        menuButtonsPanel.add(upcomingButton);

        addTaskButtonPanel.add(addTaskButton);

    }

    private void addConstraintsForCenterContainer() {

        addGridConstraints();

        addTaskRelatedContainers();

    }

    private void addTaskRelatedContainers() {

        allTasksPanel.add(allTaskscontainer, gridBagConstraints);

        overdueTasksPanel.add(overDueTaskscontainer, gridBagConstraints);

        upcomingTasksPanel.add(upcomingTaskscontainer, gridBagConstraints);

        mainPanel.setLayout(cardLayout);

    }

    private void addGridConstraints() {

        gridBagConstraints.fill = GridBagConstraints.BOTH;

        gridBagConstraints.weighty = 1;

        gridBagConstraints.weightx = 1;

        gridBagConstraints.insets = new Insets(0, 15, 0, 15);

        allTaskscontainer.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        overDueTaskscontainer.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        upcomingTaskscontainer.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

    }

    private void addTaskButtonContainer() {

        addTaskButton.setPreferredSize(new Dimension(160, 40));

        bottomPanel.add(addTaskButtonPanel, BorderLayout.NORTH);

        bottomPanel.add(footerPanel, BorderLayout.SOUTH);

        footerPanel.setBorder(BorderFactory.createLineBorder(Color.black));

    }

    private void packageAllTheContainers() {

        applicationFrame.add(menuButtonsPanel, BorderLayout.NORTH);
        applicationFrame.add(mainPanel, BorderLayout.CENTER);
        applicationFrame.add(bottomPanel, BorderLayout.SOUTH);

        mainPanel.add(allTasksPanel, "allTasksPanel");

        mainPanel.add(upcomingTasksPanel, "upcomingTasksPanel");

        mainPanel.add(overdueTasksPanel, "overdueTasksPanel");

        cardLayout.show(mainPanel, "allTasksPanel");

    }

    private void setToAllTaskPageByDefault() {

        allButton.setBackground(selected);
        allButton.setFocusPainted(false);
        upcomingButton.setFocusPainted(false);
        overdueButton.setFocusPainted(false);

    }


    private void constructAddTaskWindow() {

        addingContainersToAddTaskWindow();

        settingUpImage();

        settingUpCalendar();

    }

    private void addingContainersToAddTaskWindow() {

        namePanel.add(nameLabel);
        namePanel.add(nameTextField);

        notesPanel.add(notesLabel);
        notesPanel.add(notesTextField);

        dueDateLabelPanel.add(dueDateLabel);

        dueDatePanel.add(dueDateIndentPanel);
        dueDatePanel.add(dueDateLabelPanel);
        dueDatePanel.add(duesDateTextField);

        duesDateTextField.setEditable(false);
        duesDateTextField.setBackground(Color.WHITE);

    }

    private void settingUpImage() {

        imgButton = datePicker.getComponentToggleCalendarButton();

        imgButton.setPreferredSize(new Dimension(25, 25));
        imgButton.setIcon(imgIcon);
        imgButton.setBorderPainted(false);
        imgButton.setContentAreaFilled(false);
        imgButton.setFocusPainted(false);
        imgButton.setOpaque(false);
        imgButton.addActionListener(this);
        imgButton.setText("");

    }


    private void settingUpCalendar() {

        constructingDatePicker();

        constructingTimePicker();

        constructingTextFields();

        addDialogButtonContainers();

        addAllContainersToDialog();

        addActionListenersForDialogButtons();

    }

    private void constructingDatePicker() {

        datePickerSettings.setVisibleDateTextField(false);

        datePickerSettings.setGapBeforeButtonPixels(0);

        datePicker.addDateChangeListener(dateChangeListener);

        dueDatePanel.add(datePicker, getConstraints());

    }

    private void constructingTimePicker() {

        timePickerSettings.use24HourClockFormat();

        timePickerSettings.generatePotentialMenuTimes(TimePickerSettings.TimeIncrement.FifteenMinutes, null, null);
        timePickerSettings.setSizeTextFieldMinimumWidth(5);
        timePicker = new TimePicker(timePickerSettings);
        dueDatePanel.add(timePicker, getConstraints());
    }

    private static GridBagConstraints getConstraints() {
        return getConstraints(GridBagConstraints.WEST);
    }

    private static GridBagConstraints getConstraints(int anchor) {
        GridBagConstraints gc = new GridBagConstraints();
        gc.fill = GridBagConstraints.NONE;
        gc.anchor = anchor;
        gc.gridx = 1;
        gc.gridy = 16;
        gc.gridwidth = 1;
        return gc;
    }

    private void constructingTextFields() {

        nameTextField.setPreferredSize(new Dimension(250, 27));

        notesTextField.setPreferredSize(new Dimension(250, 27));

        duesDateTextField.setPreferredSize(new Dimension(100, 27));

        textAreaPanel.add(namePanel, BorderLayout.NORTH);

        textAreaPanel.add(notesPanel, BorderLayout.CENTER);

        textAreaPanel.add(dueDatePanel, BorderLayout.SOUTH);

    }

    private void addDialogButtonContainers() {

        generatePanel.add(generateButton);

        dialogButtonPanel.add(generatePanel);

        dialogButtonPanel.add(createButton);

        dialogButtonPanel.add(cancelButton);

        dialogBottomPanel.add(dialogButtonPanel, BorderLayout.NORTH);

        dialogBottomPanel.add(dialogFooterPanel, BorderLayout.SOUTH);

        dialogFooterPanel.setBorder(BorderFactory.createLineBorder(Color.black));

    }

    private void addAllContainersToDialog() {

        dialog.add(textAreaPanel, BorderLayout.NORTH);

        dialog.add(dialogBottomPanel, BorderLayout.SOUTH);
    }

    private void addActionListenersForDialogButtons() {

        generateButton.addActionListener(this);
        createButton.addActionListener(this);
        cancelButton.addActionListener(this);

    }

    private void displayTasks() {

        displayAllTasks();
        displayOverdueTasks();
        displayUpcomingTasks();

    }

    private void displayAllTasks() {

        if (task.taskIsEmpty()) {
            panel.setLayout(new FlowLayout());
            panel.add(new JLabel("No tasks to show."));
        } else {
            panel.removeAll();
            listAllTasks();
        }

    }

    private void displayOverdueTasks() {

        if (task.taskIsEmpty()) {
            panel2.setLayout(new FlowLayout());
            panel2.removeAll();
            panel2.add(new JLabel("No tasks to show."));

        } else if (!task.checkForIncompleteTasks() || !task.checkForOverdueTasks()) {
            panel2.setLayout(new FlowLayout());
            panel2.removeAll();
            panel2.add(new JLabel("No overdue incomplete tasks to show."));

        } else {
            panel2.removeAll();
            constructIncompleteTasks();
            listOverdueIncompleteTasks();
        }
    }

    private void constructIncompleteTasks() {

        incompleteTask.getIncompleteList().clear();
        for (int i = 0; i < task.taskSize(); i++) {
            if (!task.getTask(i).getIsComplete() && !incompleteTask.contains(task.getTask(i))) {
                incompleteTask.addIncompleteToList(task.getTask(i));
            }
        }
        incompleteTask.sortIncompleteTasks();
    }

    private void displayUpcomingTasks() {

        if (task.taskIsEmpty()) {
            panel3.setLayout(new FlowLayout());
            panel3.removeAll();
            panel3.add(new JLabel("No tasks to show."));

        } else if (!task.checkForIncompleteTasks() || !task.checkForUpcomingIncompleteTasks()) {
            panel3.setLayout(new FlowLayout());
            panel3.removeAll();
            panel3.add(new JLabel("No upcoming incomplete tasks to show."));

        } else {
            panel3.removeAll();
            constructIncompleteTasks();
            listUpcomingIncompleteTasks();
        }
    }

    DateChangeListener dateChangeListener = dateChangeEvent -> {
        if (datePicker.getDate() != null) {
            duesDateTextField.setText(datePicker.getDate().toString());
        }
    };

    private void listAllTasks() {
        if (task.taskSize() >= 3) {
            panel.setLayout(new GridLayout(task.taskSize(), 0));

        } else {
            panel.setLayout(new GridLayout(task.taskSize() + 2, 0));

        }

        int taskNumber = 0;
        taskPanel = new JPanel[task.taskSize()];
        tasksDescriptionLabel = new JLabel[task.taskSize()];
        tasksDescriptionBottomPanel = new JPanel[task.taskSize()];
        tasksDescriptionPanel = new JPanel[task.taskSize()];
        checkBox = new JCheckBox[task.taskSize()];
        completed = new JLabel[task.taskSize()];
        tasksDescriptionBottomIndent = new JPanel[task.taskSize()];
        removeTask = new JButton[task.taskSize()];

        for (int i = 0; i < task.taskSize(); i++) {

            checkBox[i] = new JCheckBox();
            if (task.getTask(i).getIsComplete()) {
                checkBox[i].setSelected(true);
            }
            removeTask[i] = new JButton("Remove Task");
            removeTask[i].addActionListener(this);
            tasksDescriptionBottomIndent[i] = new JPanel(new FlowLayout(FlowLayout.LEFT, 115, 20));
            completed[i] = new JLabel("Completed");

            taskPanel[i] = new JPanel(new FlowLayout(FlowLayout.CENTER, 2, 2));
            taskPanel[i].setPreferredSize(new Dimension(456, 140));

            tasksDescriptionLabel[i] = new JLabel(task.getTask(i).toString());

            tasksDescriptionBottomPanel[i] = new JPanel(new FlowLayout(FlowLayout.LEFT, 2, 2));
            tasksDescriptionBottomPanel[i].setPreferredSize(new Dimension(446, 59));

            tasksDescriptionPanel[i] = new JPanel(new FlowLayout(FlowLayout.LEFT, 2, 2));
            tasksDescriptionPanel[i].setPreferredSize(new Dimension(446, 50));

            tasksDescriptionPanel[i].add(tasksDescriptionLabel[i]);

            tasksDescriptionBottomPanel[i].add(checkBox[i]);

            tasksDescriptionBottomPanel[i].add(completed[i]);

            tasksDescriptionBottomPanel[i].add(tasksDescriptionBottomIndent[i]);

            tasksDescriptionBottomPanel[i].add(removeTask[i]);

            taskPanel[i].setBorder(
                    BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLUE, 1), "Task #" + ++taskNumber));

            taskPanel[i].add(tasksDescriptionPanel[i]);
            taskPanel[i].add(tasksDescriptionBottomPanel[i]);
            panel.add(taskPanel[i]);
            int finalI = i;

            checkBox[i].addItemListener(e -> {

                task.getTask(finalI).setIsComplete(e.getStateChange() == ItemEvent.SELECTED);
                displayPastAndFutureTasks();
            });
        }
    }

    private void displayPastAndFutureTasks() {

        displayOverdueTasks();
        displayUpcomingTasks();

    }

    private void listOverdueIncompleteTasks() {
        if (incompleteTask.incompleteTaskSize() >= 3) {
            panel2.setLayout(new GridLayout(incompleteTask.incompleteTaskSize(), 0));
        } else {
            panel2.setLayout(new GridLayout(incompleteTask.incompleteTaskSize() + 2, 0));
        }

        int taskNumber = 0;

        overdueTaskPanel = new JPanel[incompleteTask.incompleteTaskSize()];

        tasksDescriptionLabel2 = new JLabel[incompleteTask.incompleteTaskSize()];
        tasksDescriptionBottomPanel2 = new JPanel[incompleteTask.incompleteTaskSize()];
        tasksDescriptionPanel2 = new JPanel[incompleteTask.incompleteTaskSize()];

        tasksDescriptionBottomIndent2 = new JPanel[incompleteTask.incompleteTaskSize()];

        removeTask2 = new JButton[incompleteTask.incompleteTaskSize()];

        for (int i = 0; i < incompleteTask.incompleteTaskSize(); i++) {
            if (incompleteTask.getIncompleteTask(i).getDueDate().compareTo(now) < CALENDAR_COMPARISON) {

                removeTask2[i] = new JButton("Remove Task");
                removeTask2[i].addActionListener(this);
                tasksDescriptionBottomIndent2[i] = new JPanel(new FlowLayout(FlowLayout.LEFT, 158, 20));

                overdueTaskPanel[i] = new JPanel(new FlowLayout(FlowLayout.CENTER, 2, 2));
                overdueTaskPanel[i].setPreferredSize(new Dimension(456, 140));

                tasksDescriptionLabel2[i] = new JLabel(incompleteTask.getIncompleteTask(i).toString());

                tasksDescriptionBottomPanel2[i] = new JPanel(new FlowLayout(FlowLayout.LEFT, 2, 2));
                tasksDescriptionBottomPanel2[i].setPreferredSize(new Dimension(446, 59));

                tasksDescriptionPanel2[i] = new JPanel(new FlowLayout(FlowLayout.LEFT, 2, 2));
                tasksDescriptionPanel2[i].setPreferredSize(new Dimension(446, 50));

                tasksDescriptionPanel2[i].add(tasksDescriptionLabel2[i]);

                tasksDescriptionBottomPanel2[i].add(tasksDescriptionBottomIndent2[i]);

                tasksDescriptionBottomPanel2[i].add(removeTask2[i]);

                overdueTaskPanel[i].setBorder(
                        BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLUE, 1), "Task #" + ++taskNumber));

                overdueTaskPanel[i].add(tasksDescriptionPanel2[i]);
                overdueTaskPanel[i].add(tasksDescriptionBottomPanel2[i]);
                panel2.add(overdueTaskPanel[i]);

            }
        }
    }

    private void listUpcomingIncompleteTasks() {

        if (incompleteTask.incompleteTaskSize() >= 3) {
            panel3.setLayout(new GridLayout(incompleteTask.incompleteTaskSize(), 0));
        } else {
            panel3.setLayout(new GridLayout(incompleteTask.incompleteTaskSize() + 2, 0));
        }

        int taskNumber = 0;

        upcomingTaskPanel = new JPanel[incompleteTask.incompleteTaskSize()];

        tasksDescriptionLabel3 = new JLabel[incompleteTask.incompleteTaskSize()];
        tasksDescriptionBottomPanel3 = new JPanel[incompleteTask.incompleteTaskSize()];
        tasksDescriptionPanel3 = new JPanel[incompleteTask.incompleteTaskSize()];

        tasksDescriptionBottomIndent3 = new JPanel[incompleteTask.incompleteTaskSize()];

        removeTask3 = new JButton[incompleteTask.incompleteTaskSize()];

        for (int i = 0; i < incompleteTask.incompleteTaskSize(); i++) {
            if (incompleteTask.getIncompleteTask(i).getDueDate().compareTo(now) > CALENDAR_COMPARISON) {

                removeTask3[i] = new JButton("Remove Task");
                removeTask3[i].addActionListener(this);
                tasksDescriptionBottomIndent3[i] = new JPanel(new FlowLayout(FlowLayout.LEFT, 158, 20));

                upcomingTaskPanel[i] = new JPanel(new FlowLayout(FlowLayout.CENTER, 2, 2));
                upcomingTaskPanel[i].setPreferredSize(new Dimension(456, 140));

                tasksDescriptionLabel3[i] = new JLabel(incompleteTask.getIncompleteTask(i).toString());

                tasksDescriptionBottomPanel3[i] = new JPanel(new FlowLayout(FlowLayout.LEFT, 2, 2));
                tasksDescriptionBottomPanel3[i].setPreferredSize(new Dimension(446, 59));

                tasksDescriptionPanel3[i] = new JPanel(new FlowLayout(FlowLayout.LEFT, 2, 2));
                tasksDescriptionPanel3[i].setPreferredSize(new Dimension(446, 50));

                tasksDescriptionPanel3[i].add(tasksDescriptionLabel3[i]);

                tasksDescriptionBottomPanel3[i].add(tasksDescriptionBottomIndent3[i]);

                tasksDescriptionBottomPanel3[i].add(removeTask3[i]);

                upcomingTaskPanel[i].setBorder(
                        BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLUE, 1), "Task #" + ++taskNumber));

                upcomingTaskPanel[i].add(tasksDescriptionPanel3[i]);
                upcomingTaskPanel[i].add(tasksDescriptionBottomPanel3[i]);
                panel3.add(upcomingTaskPanel[i]);

            }
        }
    }

    private void disposeDialog() {

        dialog.dispose();

    }

    private void revalidatePanels() {

        panel.revalidate();

        panel2.revalidate();

        panel3.revalidate();

    }

    private void revalidateAndRemovePanels() {
        panel.removeAll();
        panel.revalidate();
        panel2.removeAll();
        panel2.revalidate();
        panel3.removeAll();
        panel3.revalidate();

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        switch (e.getActionCommand()) {
            case "All":
                cardLayout.show(mainPanel, "allTasksPanel");
                allButton.setBackground(selected);
                allButton.setOpaque(true);

                overdueButton.setOpaque(false);
                upcomingButton.setOpaque(false);

                SwingUtilities.getWindowAncestor(overdueButton).repaint();
                SwingUtilities.getWindowAncestor(upcomingButton).repaint();
                break;
            case "Overdue":
                cardLayout.show(mainPanel, "overdueTasksPanel");
                overdueButton.setBackground(selected);
                overdueButton.setOpaque(true);

                allButton.setOpaque(false);
                upcomingButton.setOpaque(false);

                SwingUtilities.getWindowAncestor(allButton).repaint();
                SwingUtilities.getWindowAncestor(upcomingButton).repaint();
                break;
            case "Upcoming":
                cardLayout.show(mainPanel, "upcomingTasksPanel");
                upcomingButton.setBackground(selected);
                upcomingButton.setOpaque(true);

                allButton.setOpaque(false);
                overdueButton.setOpaque(false);

                SwingUtilities.getWindowAncestor(allButton).repaint();
                SwingUtilities.getWindowAncestor(overdueButton).repaint();
                break;
            case "Generate":

                String command = "curl -X GET https://www.boredapi.com/api/activity";

                ProcessBuilder processBuilder = new ProcessBuilder(command.split(" "));

                processBuilder.directory(new File("./src"));
                try {
                    Process process = processBuilder.start();

                    InputStream inputStream = process.getInputStream();

                    int status = process.waitFor();


                    int exitCode = process.exitValue();

                    String string = new String(inputStream.readAllBytes());

                    JsonObject jsonObj = new JsonParser().parse(string).getAsJsonObject();

                    String name = jsonObj.get("activity").toString();

                    String type = jsonObj.get("type").toString();

                    String participants = jsonObj.get("participants").toString();

                    String price = jsonObj.get("price").toString();

                    nameTextField.setText(name);

                    notesTextField.setText("type: " + type + ", participants: " + participants + ", price: " + price);
                } catch (IOException | InterruptedException ioException) {
                    ioException.printStackTrace();
                }
                break;
            case "Create":

                taskNameInput = nameTextField.getText();

                if (taskNameInput == null || taskNameInput.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(dialogWarningFrame, "Error: the task cannot be left blank. This includes entering whitespaces.");
                } else if (datePicker.getDate() == null || timePicker.getTime() == null) {
                    JOptionPane.showMessageDialog(dialogWarningFrame, "Error: the due dates cannot be left blank. Please choose a date and time.");
                } else {

                    duesDateTextField.setText(datePicker.getDate().toString());

                    taskNotesInput = notesTextField.getText();

                    // dates
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    date = LocalDate.parse(datePicker.getDate().toString(), formatter);

                    taskYearInput = date.getYear();
                    taskDayInput = date.getDayOfMonth();

                    taskMonthInput = date.getMonthValue();

                    // time
                    String[] splitTime = timePicker.getTime().toString().split(":");
                    taskHourInput = Integer.parseInt(splitTime[0]);
                    taskMinuteInput = Integer.parseInt(splitTime[1]);

                    // add
                    calendar = new GregorianCalendar(taskYearInput, taskMonthInput - DECREMENT, taskDayInput, taskHourInput, taskMinuteInput);
                    task.addToList(new Tasks(taskNameInput, taskNotesInput, calendar, false));
                    task.getList().sort(new DueDateComparison());

                    // reset textfields
                    nameTextField.setText("");
                    notesTextField.setText("");
                    duesDateTextField.setText("YYYY-MM-DD");

                    datePicker.setDate(null);
                    timePicker.setTime(null);

                    revalidatePanels();

                    displayTasks();

                    disposeDialog();
                }

                break;
            case "Cancel":

                disposeDialog();
                break;
            case "Add Task":
                dialog.setVisible(true);
                break;
            case "Remove Task":

                for (int i = 0; i < task.taskSize(); i++) {

                    if (e.getSource() == removeTask[i]) {
                        task.removeFromList(task.getTask(i));

                        revalidateAndRemovePanels();
                        displayTasks();
                    }
                    if (task.checkForOverdueTasks()) {
                        for (int j = 0; j < incompleteTask.incompleteTaskSize(); j++) {

                            if (e.getSource() == removeTask2[j]) {
                                task.removeFromList(incompleteTask.getIncompleteTask(j));

                                revalidateAndRemovePanels();
                                displayTasks();
                            }
                        }
                    }
                    if (task.checkForUpcomingIncompleteTasks()) {
                        for (int k = 0; k < incompleteTask.incompleteTaskSize(); k++) {

                            if (e.getSource() == removeTask3[k]) {
                                task.removeFromList(incompleteTask.getIncompleteTask(k));

                                revalidateAndRemovePanels();
                                displayTasks();
                            }
                        }
                    }
                }
                break;
        }
    }
}
