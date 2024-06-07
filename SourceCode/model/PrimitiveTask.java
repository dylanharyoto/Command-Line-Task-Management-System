package hk.edu.polyu.comp.comp2021.tms.model;

/**
 * The primitiveTask class represents a basic task in the Task Management System (TMS).
 * Each primitiveTask is a subclass of Task and has a duration and prerequisite tasks.
 * The duration of a task cannot be less than or equal to 0.
 * Similarly, the prerequisite tasks of a task cannot be null.
 */
public class PrimitiveTask extends Task {
    private double duration;
    private String[] prerequisite;

    /**
     * Constructs a new primitiveTask with the specified name, description, duration, and prerequisite tasks.
     *
     * @param name         The name of the task.
     * @param description  The description of the task.
     * @param duration     The duration of the task.
     * @param prerequisite An array of names of the prerequisite tasks.
     */
    public PrimitiveTask(String name, String description, double duration, String[] prerequisite) {
        super(name, description);
        this.duration = duration;
        this.prerequisite = prerequisite;
    }

    /**
     * Returns the duration of this task.
     *
     * @return The duration of this task.
     */
    public double getDuration() {
        return this.duration;
    }

    /**
     * Returns the prerequisite tasks of this task.
     *
     * @return An array of names of the prerequisite tasks.
     */
    public String[] getPrerequisite() {
        return this.prerequisite;
    }

    /**
     * Sets or changes the duration of this task.
     * The duration cannot be less than or equal to 0.
     *
     * @param duration The new duration for this task.
     */
    public void setDuration(double duration) {
        if (duration <= 0.0) {
            System.out.println("duration cannot be less than 0.0.");
            return;
        }
        this.duration = duration;
    }

    /**
     * Sets or changes the prerequisite tasks of this task.
     * Prerequisite tasks cannot be null.
     *
     * @param prerequisite The new array of names for prerequisite tasks.
     */
    public void setPrerequisite(String[] prerequisite) {
        this.prerequisite = prerequisite;
    }
}
