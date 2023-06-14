package ca.java.tasktrackergui.control;

import ca.java.tasktrackergui.model.Tasks;

import java.util.Comparator;

/**
 * This DueDateComparison class implements a comparator to specifically
 * compare the dates in the Tasks objects, so we can make decisions accordingly
 * regarding orders of tasks.
 * source of reference :
 * https://www.tutorialspoint.com/java/util/calendar_compareto.htm#:~:text=The%20method%20returns%200%20if,is%20after%20the%20time%20represented.
 *
 * @author SI-Encoding
 */
//
public class DueDateComparison implements Comparator<Tasks> {

    @Override
    public int compare(Tasks o1, Tasks o2) {
        if (o1.getDueDate().compareTo(o2.getDueDate()) == 1) {
            return 1;
        }
        if (o1.getDueDate().compareTo(o2.getDueDate()) == 0) {
            return 0;
        }
        return -1;
    }
}