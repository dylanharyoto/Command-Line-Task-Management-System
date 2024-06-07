package hk.edu.polyu.comp.comp2021.tms.model;
/**
 * Represents a Task with a name and a description.
 */
public class Task {
    private String name;
    private String description;
    /**
     * Constructs a new Task with the specified name and description.
     *
     * @param name The name of the task.
     * @param description The description of the task.
     */
    public Task(String name, String description) {
        this.name = name;
        this.description = description;
    }
    /**
     * Returns the name of this task.
     *
     * @return The name of this task.
     */
    public String getName() {
        return this.name;
    }
    /**
     * Returns the description of this task.
     *
     * @return The description of this task.
     */
    public String getDescription() {
        return this.description;
    }
    /**
     * Sets or changes the name of this task.
     * The name cannot be null or empty.
     *
     * @param name The new name for this task.
     */
    public void setName(String name) {
        if(name == null || name.isEmpty()) {
            System.out.println("name cannot be null.");
            return;
        }
        this.name = name;
    }
    /**
     * Sets or changes the description of this task.
     * The description cannot be null or empty.
     *
     * @param description The new description for this task.
     */
    public void setDescription(String description) {
        if(description == null || description.isEmpty()) {
            System.out.println("description cannot be null.");
            return;
        }
        this.description = description;
    }
}