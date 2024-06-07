package hk.edu.polyu.comp.comp2021.tms.model;

/**
 * The CompositeTask class represents a task that is composed of one or more subtasks in the Task Management System (TMS).
 * Each CompositeTask is a subclass of Task and has an array of subtasks.
 * The subtasks of a task cannot be null.
 */
public class CompositeTask extends Task {
    private String[] subtask;

    /**
     * Constructs a new CompositeTask with the specified name, description, and subtasks.
     *
     * @param name The name of the task.
     * @param description The description of the task.
     * @param subtask An array of names of the subtasks.
     */
    public CompositeTask(String name, String description, String[] subtask) {
        super(name, description);
        this.subtask = subtask;
    }

    /**
     * Returns the subtasks of this composite task.
     *
     * @return An array of names of the subtasks.
     */
    public String[] getSubtask() {
        return this.subtask;
    }

    /**
     * Sets or changes the subtasks of this composite task.
     * Subtasks cannot be null.
     *
     * @param subtask The new array of names for subtasks.
     */
    public void setSubtask(String[] subtask) {
        if (subtask == null) {
            System.out.println("subtask cannot be null.");
            return;
        }
        this.subtask = subtask;
    }
}
