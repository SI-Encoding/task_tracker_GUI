package ca.java.tasktrackergui.control;

import ca.java.tasktrackergui.model.Tasks;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;


/**
 * The TasksArrayList keeps track of all the Tasks objects and Tasks objects that are incomplete
 * inside an array list to use. It acts as a storage to communicate information with the UI as the
 * user makes selections and it acts accordingly.
 *
 * @author SI-Encoding
 */

public class TasksArrayList {

    private List<Tasks> tasks = new ArrayList<>();

    private final List<Tasks> inCompleteTasks = new ArrayList<>();

    private final Gson gson = new Gson();

    private String json;

    private final File file = new File("./test.json");

    private final GregorianCalendar now = new GregorianCalendar();

    private final int CALENDAR_COMPARISON = 0;

    public void saveData() {

        json = gson.toJson(tasks);

        try {
            FileWriter save = new FileWriter(file);
            save.write(json);
            save.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadData() {
        if (file.exists()) {
            try {
                JsonReader reader = new JsonReader(new FileReader(file));

                JsonElement jsonElement = new JsonParser().parse(reader);

                JsonArray jsonArray = jsonElement.getAsJsonArray();

                Type listType = new TypeToken<List<Tasks>>() {
                }.getType();
                tasks = gson.fromJson(jsonArray.toString(), listType);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public void addToList(Tasks task) {
        tasks.add(task);
    }

    public void removeFromList(Tasks task) {
        tasks.remove(task);
    }

    public void addIncompleteToList(Tasks task) {
        inCompleteTasks.add(task);
    }

    public boolean taskIsEmpty() {
        return tasks.isEmpty();
    }

    public int taskSize() {
        return tasks.size();
    }

    public int incompleteTaskSize() {
        return inCompleteTasks.size();
    }

    public Tasks getTask(int i) {
        return tasks.get(i);
    }

    public Tasks getIncompleteTask(int i) {
        return inCompleteTasks.get(i);
    }

    public boolean contains(Tasks task) {
        return inCompleteTasks.contains(task);
    }

    public List<Tasks> getList() {
        return tasks;
    }

    public List<Tasks> getIncompleteList() {
        return inCompleteTasks;
    }

    public void sortIncompleteTasks() {
        inCompleteTasks.sort(new DueDateComparison());
    }

    public boolean checkForIncompleteTasks() {
        for (int i = 0; i < tasks.size(); i++) {
            if (!tasks.get(i).getIsComplete()) {
                return true;
            }
        }
        return false;
    }

    public boolean checkForOverdueTasks() {
        for (int i = 0; i < tasks.size(); i++) {
            if (!tasks.get(i).getIsComplete() && tasks.get(i).getDueDate().compareTo(now) < CALENDAR_COMPARISON) {
                return true;
            }
        }
        return false;
    }

    public boolean checkForUpcomingIncompleteTasks() {
        for (int i = 0; i < tasks.size(); i++) {
            if (!tasks.get(i).getIsComplete() && tasks.get(i).getDueDate().compareTo(now) > CALENDAR_COMPARISON) {
                return true;
            }
        }
        return false;
    }

}
