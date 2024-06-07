package hk.edu.polyu.comp.comp2021.tms.model;

import hk.edu.polyu.comp.comp2021.tms.Application;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.util.concurrent.atomic.AtomicReference;


/**
 * The TMS (Task Management System) class represents the main functionality of the Task Management System.
 * It manages three HashMaps for primitiveTask, CompositeTask, and Criterion, respectively.
 * Each HashMap uses a String as the key and the corresponding class object as the value.
 */
public class TMS {
    private Stack<String> redoStack = new Stack<>();
    private Stack<String> undoStack = new Stack<>();
    private HashMap<String, PrimitiveTask> primitiveTask = new HashMap<String, PrimitiveTask>();
    private HashMap<String, CompositeTask> compositeTask = new HashMap<String, CompositeTask>();
    private HashMap<String, Criterion> criterion = new HashMap<String, Criterion>();
    /**
     * Retrieves the current mapping of primitive tasks in the task management system.
     * The returned map has task names as keys and corresponding PrimitiveTask objects as values.
     *
     * @return A HashMap with task names as keys and PrimitiveTask objects as values.
     */
    public HashMap<String, PrimitiveTask> getPrimitiveTask() {
        return primitiveTask;
    }

    /**
     * Retrieves the current mapping of composite tasks in the task management system.
     * The returned map has task names as keys and corresponding CompositeTask objects as values.
     *
     * @return A HashMap with task names as keys and CompositeTask objects as values.
     */
    public HashMap<String, CompositeTask> getCompositeTask() {
        return compositeTask;
    }

    /**
     * Retrieves the current mapping of criteria in the task management system.
     * The returned map has criterion names as keys and corresponding Criterion objects as values.
     *
     * @return A HashMap with criterion names as keys and Criterion objects as values.
     */
    public HashMap<String, Criterion> getCriterion() {
        return criterion;
    }

    /**
     * Sets the current mapping of primitive tasks in the task management system.
     * The provided map should have task names as keys and corresponding PrimitiveTask objects as values.
     * If the provided map is null, an error message is printed and the existing task mapping remains unchanged.
     *
     * @param primitiveTask A HashMap with task names as keys and PrimitiveTask objects as values to replace the current task mapping.
     */
    public void setPrimitiveTask(HashMap<String, PrimitiveTask> primitiveTask) {
        if(primitiveTask == null) {
            System.out.println("values cannot be null.");
        }
        this.primitiveTask = primitiveTask;
    }

    /**
     * Sets the current mapping of composite tasks in the task management system.
     * The provided map should have task names as keys and corresponding CompositeTask objects as values.
     * If the provided map is null, an error message is printed and the existing task mapping remains unchanged.
     *
     * @param compositeTask A HashMap with task names as keys and CompositeTask objects as values to replace the current task mapping.
     */
    public void setCompositeTask(HashMap<String, CompositeTask> compositeTask) {
        if(compositeTask == null) {
            System.out.println("values cannot be null.");
        }
        this.compositeTask = compositeTask;
    }

    /**
     * Sets the current mapping of criteria in the task management system.
     * The provided map should have criterion names as keys and corresponding Criterion objects as values.
     * If the provided map is null, an error message is printed and the existing criterion mapping remains unchanged.
     *
     * @param criterion A HashMap with criterion names as keys and Criterion objects as values to replace the current criterion mapping.
     */
    public void setCriterion(HashMap<String, Criterion> criterion) {
        if(criterion == null) {
            System.out.println("values cannot be null.");
        }
        this.criterion = criterion;
    }

    /**
     * Reverses the most recent change to the task management system by executing the undo command stored in the undo stack.
     * If the undo stack is empty, prints a message indicating there's nothing to undo and returns immediately.
     * The undo command is a string containing the operation to undo and the arguments it was originally called with.
     * The operation to undo is determined by the first word of the undo command.
     * Depending on the operation, the necessary method is called with the original arguments to reverse the change.
     * If the operation isn't recognized, nothing is done.
     */
    public void undo() {
        if(undoStack.isEmpty()) {
            System.out.println("nothing to undo.");
            return;
        }
        String[] undoCommand = undoStack.pop().split(" ");
        switch (undoCommand[0]) {
            case "CreatePrimitiveTask" -> { createPrimitiveTask(undoCommand[1], undoCommand[2], undoCommand[3], undoCommand[4], true, true); }
            case "CreateCompositeTask" -> { createCompositeTask(undoCommand[1], undoCommand[2], undoCommand[3], true, true); }
            case "DeleteTask" -> { deleteTask(undoCommand[1], true, true); }
            case "ChangeTask" -> { changeTask(undoCommand[1], undoCommand[2], undoCommand[3], true, true);}
            case "DefineBasicCriterion" -> { defineBasicCriterion(undoCommand[1], undoCommand[2], undoCommand[3], undoCommand[4], true, true);}
            case "DefineNegatedCriterion" -> { defineNegatedCriterion(undoCommand[1], undoCommand[2], true, true);}
            case "DefineBinaryCriterion" -> { defineBinaryCriterion(undoCommand[1], undoCommand[2], undoCommand[3], undoCommand[4], true, true);}
            case "DeleteCriterion" -> { deleteCriterion(undoCommand[1], true);}
        }
    }

    /**
     * Reapplies the most recent change to the task management system that was undone by executing the redo command stored in the redo stack.
     * If the redo stack is empty, prints a message indicating there's nothing to redo and returns immediately.
     * The redo command is a string containing the operation to redo and the arguments it was originally called with.
     * The operation to redo is determined by the first word of the redo command.
     * Depending on the operation, the necessary method is called with the original arguments to reapply the change.
     * If the operation isn't recognized, nothing is done.
     */
    public void redo() {
        if(redoStack.isEmpty()) {
            System.out.println("nothing to redo.");
            return;
        }
        String[] redoCommand = redoStack.pop().split(" ");
        switch (redoCommand[0]) {
            case "CreatePrimitiveTask" -> { createPrimitiveTask(redoCommand[1], redoCommand[2], redoCommand[3], redoCommand[4], true, false); }
            case "CreateCompositeTask" -> { createCompositeTask(redoCommand[1], redoCommand[2], redoCommand[3], true, false); }
            case "DeleteTask" -> { deleteTask(redoCommand[1], true, false); }
            case "ChangeTask" -> { changeTask(redoCommand[1], redoCommand[2], redoCommand[3], true, false);}
            case "DefineBasicCriterion" -> { defineBasicCriterion(redoCommand[1], redoCommand[2], redoCommand[3], redoCommand[4], true, false);}
            case "DefineNegatedCriterion" -> { defineNegatedCriterion(redoCommand[1], redoCommand[2], true, false);}
            case "DefineBinaryCriterion" -> { defineBinaryCriterion(redoCommand[1], redoCommand[2], redoCommand[3], redoCommand[4], true, false);}
            case "DeleteCriterion" -> { deleteCriterion(redoCommand[1], true);}
        }
    }

    /**
     * REQ1
     * Creates a new primitive task and adds it to the primitiveTask HashMap.
     * This method validates the name, description, duration, and prerequisite(s) of the task before creating it.
     * The name must contain only English letters and digits, cannot start with a digit, and must be 8 characters or less.
     * The description may only contain English letters, digits, and the hyphen character (-).
     * The duration must be a positive real number.
     * The prerequisite(s) must already exist in either the primitiveTask or compositeTask HashMaps.
     * If the validation fails or if a task with the given name already exists, the method will print an error message
     * and return without creating the task.
     *
     * @param name The name of the task.
     * @param description The description of the task.
     * @param duration The duration of the task as a string.
     * @param prerequisite The prerequisite(s) of the task as a string, with multiple prerequisites separated by commas.
     * @param undoRedo Whether undoRedo is enabled
     * @param undo Whether undo should be performed
     */
    public void createPrimitiveTask(String name, String description, String duration, String prerequisite, boolean undoRedo, boolean undo) {
        if(getPrimitiveTask().containsKey(name) || getCompositeTask().containsKey(name)) {
            System.out.println(name + " already existed.");
            return;
        }
        // name validation
        if(name.length() > 8) {
            System.out.println("name is too long, only 8 characters at most.");
            return;
        } else if (Character.isDigit(name.charAt(0))) {
            System.out.println("name cannot start with digits.");
            return;
        }
        for(int i = 0; i < name.length(); i++) {
            if(!(Character.isLetter(name.charAt(i)) || Character.isDigit(name.charAt(i)))) {
                System.out.println("name may contain only English letters and digits.");
                return;
            }
        }
        // description validation
        for(int i = 0; i < description.length(); i++) {
            if(!(Character.isLetter(description.charAt(i)) || Character.isDigit(description.charAt(i)) || description.charAt(i) == '-')) {
                System.out.println("description may only contain English letters, digits, and the hyphen letter (-).");
                return;
            }
        }
        try {
            Double.parseDouble(duration);
        } catch (NumberFormatException e) {
            System.out.println("duration must be in a 'double' format.");
            return;
        }
        double durationInDouble = Double.parseDouble(duration);
        // duration validation
        if(durationInDouble < 0.0) {
            System.out.println("duration must be a positive real number.");
            return;
        }
        String[] listOfPrerequisite = null;
        if(!prerequisite.equals(",")) {
            listOfPrerequisite = prerequisite.split(",");
        }
        // prerequisite(s) validation
        if(listOfPrerequisite != null) {
            for(String task : listOfPrerequisite) {
                if(!(getPrimitiveTask().containsKey(task) || getCompositeTask().containsKey(task))) {
                    System.out.println("task(s) in prerequisite have not been defined yet.");
                    return;
                }
            }
        }
        PrimitiveTask newPrimitiveTask = new PrimitiveTask(name, description, durationInDouble, listOfPrerequisite);
        if(undoRedo) {
            if(undo) {
                redoStack.push("DeleteTask " + name);
            } else {
                undoStack.push("DeleteTask " + name);
            }
        } else {
            undoStack.push("DeleteTask " + name);
        }
        getPrimitiveTask().put(name, newPrimitiveTask);
        System.out.println(name + " has been successfully created.");
    }

    /**
     * REQ2
     * Creates a new composite task and adds it to the compositeTask HashMap.
     * This method validates the name, description, and subtasks of the task before creating it.
     * The name must contain only English letters and digits, cannot start with a digit, and must be 8 characters or less.
     * The description may only contain English letters, digits, and the hyphen character (-).
     * The subtasks must already exist in either the primitiveTask or compositeTask HashMaps.
     * If the validation fails or if a task with the given name already exists, the method will print an error message
     * and return without creating the task.
     *
     * @param name0 The name of the task.
     * @param description The description of the task.
     * @param subtask The subtasks of the task as a string, with multiple subtasks separated by commas.
     * @param undoRedo Whether undoRedo is enabled
     * @param undo Whether undo should be performed
     */
    public void createCompositeTask(String name0, String description, String subtask, boolean undoRedo, boolean undo) {
        if(getPrimitiveTask().containsKey(name0) || getCompositeTask().containsKey(name0)) {
            System.out.println(name0 + " already existed.");
            return;
        }
        // name validation
        if(name0.length() > 8) {
            System.out.println("name is too long, only 8 characters at most.");
            return;
        } else if (Character.isDigit(name0.charAt(0))) {
            System.out.println("name cannot start with digits.");
            return;
        }
        for(int i = 0; i < name0.length(); i++) {
            if(!(Character.isLetter(name0.charAt(i)) || Character.isDigit(name0.charAt(i)))) {
                System.out.println("name may contain only English letters and digits.");
                return;
            }
        }
        // description validation
        for(int i = 0; i < description.length(); i++) {
            if(!(Character.isLetter(description.charAt(i)) || Character.isDigit(description.charAt(i)) || description.charAt(i) == '-')) {
                System.out.println("description may only contain English letters, digits, and the hyphen letter (-).");
                return;
            }
        }
        String[] listOfSubtasks = null;
        if(!subtask.equals(",")) {
            listOfSubtasks = subtask.split(",");
        }
        // subtask(s) validation
        if(listOfSubtasks != null) {
            for(String task : listOfSubtasks) {
                if(!(getPrimitiveTask().containsKey(task) || getCompositeTask().containsKey(task))) {
                    System.out.println("task(s) in subtask have not been defined yet");
                    return;
                }
            }
        }
        CompositeTask newCompositeTask = new CompositeTask(name0, description, listOfSubtasks);
        if(undoRedo) {
            if(undo) {
                redoStack.push("DeleteTask " + name0);
            } else {
                undoStack.push("DeleteTask " + name0);
            }
        } else {
            undoStack.push("DeleteTask " + name0);
        }
        getCompositeTask().put(name0, newCompositeTask);
        System.out.println(name0 + " has been successfully created.");
    }

    /**
     * REQ3
     * Deletes a task from the task management system.
     * This method tries to delete a task, either primitive or composite, by its name.
     * If the task is a prerequisite for another task or if it is a subtask of a composite task,
     * it will not be deleted and an error message will be printed.
     * If the task to be deleted is a composite task, this method will also try to delete all its subtasks recursively.
     * If any subtask cannot be deleted (because it's a prerequisite for another task),
     * the deletion of the composite task will be aborted and an error message will be printed.
     * If the task does not exist, an error message will be printed.
     *
     * @param name The name of the task to be deleted.
     * @param undoRedo Whether undoRedo is enabled
     * @param undo Whether undo should be performed
     * @return true if the task is successfully deleted, false otherwise.
     */
    public boolean deleteTask(String name, boolean undoRedo, boolean undo) {
        if(getPrimitiveTask().containsKey(name)) { // if name is a simple task
            for(String task : getPrimitiveTask().keySet()) {
                if(task.equals(name) || getPrimitiveTask().get(task).getPrerequisite() == null) {
                    continue;
                }
                for(String j : getPrimitiveTask().get(task).getPrerequisite()) {
                    if(j.equals(name)) {
                        System.out.println(name + " is a prerequisite of other task(s).");
                        return false;
                    }
                }
            }
            for(String task : getCompositeTask().keySet()) {
                if(task.equals(name) || getCompositeTask().get(task).getSubtask() == null) {
                    continue;
                }
                for(String j : getCompositeTask().get(task).getSubtask()) {
                    if(j.equals(name)) {
                        String[] temp = new String[getCompositeTask().get(task).getSubtask().length - 1];
                        int k = -1;
                        for(int i = 0; i < getCompositeTask().get(task).getSubtask().length; i++) {
                            if(getCompositeTask().get(task).getSubtask()[i].equals(name)) { continue; }
                            temp[++k] = getCompositeTask().get(task).getSubtask()[i];
                        }
                        getCompositeTask().get(task).setSubtask(temp);
                    }
                }
            }
            String tempPrerequisite = ",";
            if(getPrimitiveTask().get(name).getPrerequisite() != null) {
                tempPrerequisite = String.join(",", getPrimitiveTask().get(name).getPrerequisite());
            }
            if(undoRedo) {
                if(undo) {
                    redoStack.push("CreatePrimitiveTask " + name + " " + getPrimitiveTask().get(name).getDescription() + " " + getPrimitiveTask().get(name).getDuration() + " " + tempPrerequisite);
                } else {
                    undoStack.push("CreatePrimitiveTask " + name + " " + getPrimitiveTask().get(name).getDescription() + " " + getPrimitiveTask().get(name).getDuration() + " " + tempPrerequisite);
                }
            }
            else {
                undoStack.push("CreatePrimitiveTask " + name + " " + getPrimitiveTask().get(name).getDescription() + " " + getPrimitiveTask().get(name).getDuration() + " " + tempPrerequisite);
            }
            System.out.println(name + " has been successfully deleted.");
            getPrimitiveTask().remove(name);
        } else if (getCompositeTask().containsKey(name)) { // if name is a composite task
            boolean deletionCheck = true;
            for(String task : getCompositeTask().get(name).getSubtask()) {
                if(!deletionCheck) {
                    System.out.println("composite task contains one or more subtask(s) with a prerequisite");
                    return false;
                }
                deletionCheck = deleteTask(task, false, false);
            }
            String tempSubtask = ",";
            if(getCompositeTask().get(name).getSubtask() != null) {
                tempSubtask = String.join(",", getCompositeTask().get(name).getSubtask());
            }
            if(undoRedo) {
                if(undo) {
                    redoStack.push("CreateCompositeTask " + name + " " + getCompositeTask().get(name).getDescription() + " " + tempSubtask);
                } else {
                    undoStack.push("CreateCompositeTask " + name + " " + getCompositeTask().get(name).getDescription() + " " + tempSubtask);
                }
            }
            else {
                undoStack.push("CreateCompositeTask " + name + " " + getCompositeTask().get(name).getDescription() + " " + tempSubtask);
            }
            System.out.println(name + " has been successfully deleted.");
            getCompositeTask().remove(name);
        } else { // if name does not exist
            System.out.println(name + " does not exist.");
            return false;
        }
        return true;
    }

    /**
     * REQ4
     * Modifies a property of a task in the task management system.
     * This method changes a specified property of a task (either primitive or composite) to a new value.
     * The properties that can be changed are: "name", "description", "duration" (for primitive tasks), and "subtasks" (for composite tasks).
     * If the task or property does not exist, or if the new name already exists as a task, an error message will be printed.
     * If the new duration is not a positive real number, an error message will be printed.
     * Name changes are also propagated to all tasks where the task being modified is a prerequisite or a subtask.
     *
     * @param name The name of the task to be modified.
     * @param property The property to be changed.
     * @param newValue The new value for the property.
     * @param undoRedo Whether undoRedo is enabled
     * @param undo Whether undo should be performed
     */
    public void changeTask(String name, String property, String newValue, boolean undoRedo, boolean undo) {
        if(getPrimitiveTask().containsKey(name)) { // if name is a simple task
            switch (property) {
                case "name" -> {
                    if(getPrimitiveTask().containsKey(newValue) || getCompositeTask().containsKey(newValue)) {
                        System.out.println(newValue + " already existed.");
                        return;
                    }
                    for(String i : getPrimitiveTask().keySet()) { // if name is a simple task
                        String[] listOfPrerequisite = getPrimitiveTask().get(i).getPrerequisite();
                        if(i.equals(name) || listOfPrerequisite == null) { continue; }
                        for(int j = 0; j < listOfPrerequisite.length; j++) {
                            if(listOfPrerequisite[j].equals(name)) {
                                listOfPrerequisite[j] = newValue;
                            }
                        }
                        getPrimitiveTask().get(i).setPrerequisite(listOfPrerequisite);
                    }
                    for(String i : getCompositeTask().keySet()) {
                        String[] listOfSubtask = getCompositeTask().get(i).getSubtask();
                        if(listOfSubtask == null) { continue; }
                        for(int j = 0; j < listOfSubtask.length; j++) {
                            if(listOfSubtask[j].equals(name)) {
                                listOfSubtask[j] = newValue;
                            }
                        }
                    }
                    String tempDescription = getPrimitiveTask().get(name).getDescription();
                    String tempDuration = getPrimitiveTask().get(name).getDuration() + "";
                    String tempPrerequisite = ",";
                    if(getPrimitiveTask().get(name).getPrerequisite() != null) {
                        tempPrerequisite = String.join(",", getPrimitiveTask().get(name).getPrerequisite());
                    }
                    createPrimitiveTask(newValue, tempDescription, tempDuration, tempPrerequisite, false, false);
                    getPrimitiveTask().remove(name);
                    System.out.println(name + "'s " + property + " has been successfully changed.");
                    if(undoRedo && undo) {
                        redoStack.push("ChangeTask " + newValue + " " + property + " " + name);
                    } else {
                        undoStack.push("ChangeTask " + newValue + " " + property + " " + name);
                    }
                }
                case "description" -> {
                    if(undoRedo && undo) {
                        redoStack.push("ChangeTask " + name + " " + property + " " + getPrimitiveTask().get(name).getDescription());
                    } else {
                        undoStack.push("ChangeTask " + name + " " + property + " " + getPrimitiveTask().get(name).getDescription());
                    }
                    getPrimitiveTask().get(name).setDescription(newValue);
                    System.out.println(name + "'s " + property + " has been successfully changed.");
                }
                case "duration" -> {
                    try {
                        Double.parseDouble(newValue);
                    } catch (NumberFormatException e) {
                        System.out.println("duration must be in a 'double' format.");
                        return;
                    }
                    if (Double.parseDouble(newValue) < 0.0) {
                        System.out.println("duration must be a positive real number.");
                        return;
                    }
                    if(undoRedo && undo) {
                        redoStack.push("ChangeTask " + name + " " + property + " " + getPrimitiveTask().get(name).getDuration());
                    } else {
                        undoStack.push("ChangeTask " + name + " " + property + " " + getPrimitiveTask().get(name).getDuration());
                    }
                    getPrimitiveTask().get(name).setDuration(Double.parseDouble(newValue));
                    System.out.println(name + "'s " + property + " has been successfully changed.");
                }
                case "prerequisites" -> {
                    String[] listOfPrerequisite = null;
                    if (!newValue.equals(",")) {
                        listOfPrerequisite = newValue.split(",");
                    }
                    if(listOfPrerequisite != null) {
                        for (String s : listOfPrerequisite) {
                            if (!getPrimitiveTask().containsKey(s)) {
                                System.out.println("some prerequisite(s) might not exist.");
                                return;
                            }
                        }
                    }
                    String tempPrerequisite = ",";
                    if(getPrimitiveTask().get(name).getPrerequisite() != null) {
                        tempPrerequisite = String.join(",", getPrimitiveTask().get(name).getPrerequisite());
                    }
                    if(undoRedo && undo) {
                        redoStack.push("ChangeTask " + name + " " + property + " " + tempPrerequisite);
                    } else {
                        undoStack.push("ChangeTask " + name + " " + property + " " + tempPrerequisite);
                    }
                    getPrimitiveTask().get(name).setPrerequisite(listOfPrerequisite);
                    System.out.println(name + "'s " + property + " has been successfully changed.");
                }
                default -> System.out.println(property + " does not exist");
            }

        } else if (getCompositeTask().containsKey(name)) { // if name is a composite task
            switch (property) {
                case "name" -> {
                    if(getPrimitiveTask().containsKey(newValue) || getCompositeTask().containsKey(newValue)) {
                        System.out.println(newValue + " already existed.");
                        return;
                    }
                    for(String task : getCompositeTask().keySet()) {
                        if(task.equals(name)) {
                            continue;
                        }
                        String[] listOfSubtask = getCompositeTask().get(task).getSubtask();
                        for(int j = 0; j < listOfSubtask.length; j++) {
                            if(listOfSubtask[j].equals(name)) {
                                listOfSubtask[j] = newValue;
                            }
                        }
                        getCompositeTask().get(task).setSubtask(listOfSubtask);
                    }
                    String tempDescription = getCompositeTask().get(name).getDescription();
                    String tempSubtask = String.join(",", getCompositeTask().get(name).getSubtask());
                    createCompositeTask(newValue, tempDescription, tempSubtask, false, false);
                    if(undoRedo && undo) {
                        redoStack.push("ChangeTask " + newValue + " " + property + " " + name);
                    } else {
                        undoStack.push("ChangeTask " + newValue + " " + property + " " + name);
                    }
                    getCompositeTask().remove(name);
                    System.out.println(name + "'s " + property + " has been successfully changed.");
                }
                case "description" -> {
                    if(undoRedo && undo) {
                        redoStack.push("ChangeTask " + name + " " + property + " " + getCompositeTask().get(name).getDescription());
                    } else {
                        undoStack.push("ChangeTask " + name + " " + property + " " + getCompositeTask().get(name).getDescription());
                    }
                    getCompositeTask().get(name).setDescription(newValue);
                    System.out.println(name + "'s " + property + " has been successfully changed.");
                }
                case "subtasks" -> {
                    String[] listOfSubtask = null;
                    if (!newValue.equals(",")) {
                        listOfSubtask = newValue.split(",");
                    }
                    if(listOfSubtask != null) {
                        for(String s : listOfSubtask) {
                            if(!getPrimitiveTask().containsKey(s)) {
                                System.out.println("some subtask(s) might not exist.");
                                return;
                            }
                        }
                    }
                    String tempSubtask = ",";
                    if(getCompositeTask().get(name).getSubtask() != null) {
                        tempSubtask = String.join(",", getCompositeTask().get(name).getSubtask());
                    }
                    if(undoRedo && undo) {
                        redoStack.push("ChangeTask " + name + " " + property + " " + tempSubtask);
                    } else {
                        undoStack.push("ChangeTask " + name + " " + property + " " + tempSubtask);
                    }
                    getCompositeTask().get(name).setSubtask(listOfSubtask);
                    System.out.println(name + "'s " + property + " has been successfully changed.");
                }
                default -> System.out.println(property + " does not exist");
            }
        } else {
            System.out.println("task does not exist");
        }
    }

    /**
     * REQ5
     * Prints the information about a task in the task management system.
     * This method prints the name, description, duration (for primitive tasks),
     * prerequisites (for primitive tasks), or subtasks (for composite tasks) of a specified task.
     * If the task does not exist, an error message will be printed.
     *
     * @param taskName The name of the task to be printed.
     */
    public void printTask(String taskName) {
        if(getPrimitiveTask().containsKey(taskName)) {
            System.out.print(taskName + " -> ");
            System.out.print("description: " + getPrimitiveTask().get(taskName).getDescription() + ", ");
            System.out.print("duration: " + getPrimitiveTask().get(taskName).getDuration() + ", ");
            System.out.print("prerequisite(s): ");
            if(getPrimitiveTask().get(taskName).getPrerequisite() == null) {
                System.out.print("null");
            } else {
                for(String j : getPrimitiveTask().get(taskName).getPrerequisite()) {
                    System.out.print(j + " ");
                }
            }
            System.out.println();
        } else if(getCompositeTask().containsKey(taskName)) {
            System.out.print(taskName + " -> ");
            System.out.print("description: " + getCompositeTask().get(taskName).getDescription() + ", ");
            System.out.print("subtasks: ");
            if(getCompositeTask().get(taskName).getSubtask() == null) {
                System.out.print("null");
            } else {
                for(String j : getCompositeTask().get(taskName).getSubtask()) {
                    System.out.print(j + " ");
                }
            }
            System.out.println();
        } else {
            System.out.println(taskName + " does not exist.");
        }
    }

    /**
     * REQ6
     * Prints the information about all tasks in the task management system.
     * This method prints the information about all tasks, both simple and composite, currently in the task management system.
     * For each task, it prints its name, description, duration (for primitive tasks),
     * prerequisites (for primitive tasks), or subtasks (for composite tasks).
     * The tasks are grouped by their type (simple or composite) and each group is preceded by a header.
     */
    public void printAllTasks() {
        System.out.println("Here is the list of Simple Task(s)");
        for(String i : getPrimitiveTask().keySet()) {
            printTask(i);
        }
        System.out.println("\nHere is the list of Composite Task(s)");
        for(String i : getCompositeTask().keySet()) {
            printTask(i);
        }
    }

    /**
     * REQ7
     * This method reports the duration of a task given its name.
     * If the task is a primitive task, it prints out the duration.
     * If it is a composite task, it prints out the least time required to complete the task.
     * If the task does not exist, it outputs a message indicating that the task was not found.
     *
     * @param taskName The name of the task whose duration is to be reported
     */
    public void reportDuration(String taskName){
        System.out.println(getDuration(taskName));
    }
    private double getDuration(String taskName){
        if(primitiveTask.containsKey(taskName)){
            return primitiveTask.get(taskName).getDuration();
        }
        else if(compositeTask.containsKey(taskName)) {
            return getCompositeTaskDuration(taskName);
        }
        else {
            System.out.println("task not found : " + taskName);
            return 0d;
        }
    }
    private double getCompositeTaskDuration(String taskName){
        List<String> breakedTasks = breakTasksIntoPrimitiveTasks(taskName);
        if(breakedTasks.size() == 1){
            return primitiveTask.get(breakedTasks.get(0)).getDuration();
        }
        AtomicReference<Double> maxDuration = new AtomicReference<>(0d);
        Set<String> compositeTasks = new HashSet<>(breakedTasks);
        breakedTasks.stream().forEach(str -> {
            if(primitiveTask.get(str).getPrerequisite() == null || primitiveTask.get(str).getPrerequisite().length == 0) {
                maxDuration.set(Math.max(maxDuration.get(), primitiveTask.get(str).getDuration()));
            }
            else{
                if(primitiveTask.get(str).getPrerequisite() != null && primitiveTask.get(str).getPrerequisite().length != 0){
                    maxDuration.set(Math.max(maxDuration.get(),primitiveTask.get(str).getDuration()));
                    for(String sub : primitiveTask.get(str).getPrerequisite()){
                        if(compositeTasks.contains(sub)){
                            double taskDuration = calculateDuration(sub , compositeTasks) + primitiveTask.get(str).getDuration();
                            maxDuration.set(Math.max(taskDuration, maxDuration.get()));
                        }
                    }
                }

            }
        });
        return maxDuration.get();
    }
    private List<String> breakTasksIntoPrimitiveTasks(String taskName){
        List<String> res = new ArrayList<>();
        breakTask(taskName , res);
        return res;
    }
    private void breakTask(String taskName , List<String> res){
        if(primitiveTask.containsKey(taskName)){
            res.add(taskName);
        }
        else if(compositeTask.containsKey(taskName)){
            for(String str : compositeTask.get(taskName).getSubtask()){
                breakTask(str , res);
            }
        }
    }
    //using recursion to calculate the valid duration;
    private double calculateDuration(String task , Set<String> compositeTasks){
        double taskDuration = primitiveTask.get(task).getDuration();
        double res = taskDuration;
        List<String> taskList = new ArrayList<>();
        if(primitiveTask.get(task).getPrerequisite() == null || primitiveTask.get(task).getPrerequisite().length == 0){
            return primitiveTask.get(task).getDuration();
        }
        for(String str : primitiveTask.get(task).getPrerequisite()){
            List<String> strings = breakTasksIntoPrimitiveTasks(str);
            taskList.addAll(strings);
        }
        for (String s : taskList) {
            if(compositeTasks.contains(s)){
                res = Math.max(res , taskDuration + calculateDuration(s , compositeTasks));
            }
        }
        return res;
    }

    /**
     * REQ8
     * This method reports the earliest finish time of a task given its name.
     * It prints out the minimum time required to complete the task.
     *
     * @param taskName The name of the task whose earliest finish time is to be reported
     */
    public void reportEarliestFinishTime(String taskName){
        System.out.println(getEarliestFinishTime(taskName));
    }
    private double getEarliestFinishTime(String taskName){
        if(primitiveTask.containsKey(taskName)){
            if(primitiveTask.get(taskName).getPrerequisite() == null || primitiveTask.get(taskName).getPrerequisite().length == 0){
                return handleSingleTaskForInput(taskName);
            }
            else{
                AtomicReference<Double> maxTime = new AtomicReference<>(0d);
                Arrays.stream(primitiveTask.get(taskName).getPrerequisite()).forEach(str -> {
                    maxTime.set(Math.max(maxTime.get(), getEarliestFinishTime(str)));
                });
                return primitiveTask.get(taskName).getDuration() + maxTime.get();
            }
        }
        if(compositeTask.containsKey(taskName)){
            AtomicReference<Double> maxTime = new AtomicReference<>(0d);
            Arrays.stream(compositeTask.get(taskName).getSubtask()).forEach(str -> {
                maxTime.set(Math.max(maxTime.get(), getEarliestFinishTime(str)));
            });
            return maxTime.get();
        }
        System.out.println("Task Does Not Exist: " + taskName);
        return -1d;
    }
    private double handleSingleTaskForInput(String taskName){
        return primitiveTask.get(taskName).getDuration();
    }

    /**
     * REQ9
     * Defines a basic criterion in the task management system.
     * <p>
     * This method defines a new basic criterion by specifying a name, property, operation (op), and value.
     * The criterion can be applied to tasks to filter or sort them.
     * <p>
     * If a criterion or task with the provided name already exists, an error message is printed.
     * The name must be at most 8 characters long, must not start with a digit, and can only contain English letters and digits.
     * <p>
     * The property must be one of: "name", "description", "prerequisites", "subtasks", or "duration".
     * The operation must be "contains" for the first four properties and a comparison operator for "duration".
     * The value must be a positive real number for "duration".
     * <p>
     * The new criterion is saved in the criterion list of the task management system.
     *
     * @param name1 The name of the criterion to be defined.
     * @param property The property to which the criterion applies.
     * @param op The operation of the criterion.
     * @param value The value used in the criterion.
     * @param undoRedo Whether undoRedo is enabled
     * @param undo Whether undo should be performed
     */
    public void defineBasicCriterion(String name1, String property, String op, String value, boolean undoRedo, boolean undo) {
        if(getPrimitiveTask().containsKey(name1) || getCompositeTask().containsKey(name1) || getCriterion().containsKey(name1)) {
            System.out.println(name1 + " already existed.");
            return;
        }
        // name validation
        if(name1.length() > 8) {
            System.out.println("name is too long, only 8 characters at most.");
            return;
        } else if (Character.isDigit(name1.charAt(0))) {
            System.out.println("name cannot start with digits.");
            return;
        }
        for(int i = 0; i < name1.length(); i++) {
            if(!(Character.isLetter(name1.charAt(i)) || Character.isDigit(name1.charAt(i)))) {
                System.out.println("name may contain only English letters and digits.");
                return;
            }
        }
        // property validation
        if(property.equals("name") || property.equals("description") || property.equals("prerequisites") || property.equals("subtasks")) {
            if(!op.equals("contains")) {
                System.out.println(op + " is not a valid op. op must be \"contains\".");
                return;
            }
        } else if(property.equals("duration")) {
            if(!(op.equals("<") || op.equals(">") || op.equals("<=") || op.equals(">=") || op.equals("==") || op.equals("!="))) {
                System.out.println(op + " is not a valid op. op must be either \"<\", \">\", \"<=\", \">=\", \"==\", \"!=\".");
                return;
            }
            try {
                Double.parseDouble(value);
            } catch (NumberFormatException e) {
                System.out.println("duration must be in a 'double' format.");
                return;
            }
        } else {
            System.out.println(property + " is not a property.");
            return;
        }
        // define new criterion
        Criterion newCriterion = new Criterion(name1, property, op, value);
        newCriterion.setIsBasic(true);
        newCriterion.setIsNegated(false);
        newCriterion.setSubCriterion(null);
        getCriterion().put(name1, newCriterion);
        System.out.println(name1 + " has been successfully defined.");
        if(undoRedo && undo) {
            redoStack.push("DeleteCriterion " + name1);
        } else {
            undoStack.push("DeleteCriterion " + name1);
        }
    }
    private void deleteCriterion(String name, boolean undo) {
        Criterion temp = getCriterion().get(name);
        if(temp.getIsBasic()) {
            if(temp.getIsNegated()) {
                if(undo) {
                    redoStack.push("DefineNegatedCriterion " + temp.getName() + " " + temp.getSubCriterion()[0]);
                } else {
                    undoStack.push("DefineNegatedCriterion " + temp.getName() + " " + temp.getSubCriterion()[0]);
                }
            } else {
                if(undo) {
                    redoStack.push("DefineBasicCriterion " + temp.getName() + " " + temp.getProperty() + " " + temp.getOp() + " " + temp.getValue());
                } else {
                    undoStack.push("DefineBasicCriterion " + temp.getName() + " " + temp.getProperty() + " " + temp.getOp() + " " + temp.getValue());
                }
            }
        } else {
            if(undo) {
                redoStack.push("DefineBinaryCriterion " + temp.getName() + " " + temp.getSubCriterion()[0] + " " + temp.getSubCriterion()[2] + " " + temp.getSubCriterion()[1]);
            } else {
                undoStack.push("DefineBinaryCriterion " + temp.getName() + " " + temp.getSubCriterion()[0] + " " + temp.getSubCriterion()[2] + " " + temp.getSubCriterion()[1]);
            }
        }
        getCriterion().remove(name);
    }

    /**
     * REQ11
     * This method defines a new negated criterion based on an existing criterion.
     * The new criterion has the opposite effect of the original criterion.
     * <p>
     * If a criterion with the provided new name already exists, or if the original criterion does not exist, an error message is printed.
     * The name of the new criterion must be at most 8 characters long, must not start with a digit, and can only contain English letters and digits.
     * <p>
     * The operations are negated as follows: ">" becomes "<=", "<" becomes ">=", ">=" becomes "<", "<=" becomes ">", "==" becomes "!=",
     * "||" becomes "&&", "&&" becomes "||", "contains" becomes "not-contains", "not-contains" becomes "contains", "IsPrimitive" becomes "IsComposite", "IsComposite" becomes "IsPrimitive".
     * <p>
     * The new negated criterion is saved in the criterion list of the task management system.
     *
     * @param name1 The name of the negated criterion to be defined.
     * @param name2 The name of the original criterion on which the negated criterion is based.
     * @param undoRedo Whether undoRedo is enabled
     * @param undo Whether undo should be performed
     */
    public void defineNegatedCriterion(String name1, String name2, boolean undoRedo, boolean undo) {
        if(getCriterion().containsKey(name1)) {
            System.out.println(name1 + " already existed.");
            return;
        }
        if(!getCriterion().containsKey(name2)) {
            System.out.println(name2 + " does not exists");
            return;
        }
        if(name1.length() > 8) {
            System.out.println("name is too long, only 8 characters at most.");
            return;
        } else if (Character.isDigit(name1.charAt(0))) {
            System.out.println("name cannot start with digits.");
            return;
        }
        for(int i = 0; i < name1.length(); i++) {
            if(!(Character.isLetter(name1.charAt(i)) || Character.isDigit(name1.charAt(i)))) {
                System.out.println("name may contain only English letters and digits.");
                return;
            }
        }
        if(name2.equals("IsPrimitive") && (!name1.equals("IsCompos"))) {
            System.out.println("We prefer 'IsCompos' rather than " + name1 + ". IsCompos has been created for you.");
        }
        String tempProperty = getCriterion().get(name2).getProperty();
        String[] tempOp = getCriterion().get(name2).getOp().split(" ");
        String tempValue = getCriterion().get(name2).getValue();
        StringBuilder updatedOp = new StringBuilder();
        for (int i = 0; i < tempOp.length; i++) {
            switch (tempOp[i]) {
                case ">" -> tempOp[i] = "<=";
                case "<" -> tempOp[i] = ">=";
                case ">=" -> tempOp[i] = "<";
                case "<=" -> tempOp[i] = ">";
                case "==" -> tempOp[i] = "!=";
                case "||" -> tempOp[i] = "&&";
                case "&&" -> tempOp[i] = "||";
                case "contains" -> tempOp[i] = "not-contains";
                case "not-contains" -> tempOp[i] = "contains";
                case "IsPrimitive" -> tempOp[i] = "IsComposite";
                case "IsComposite" -> tempOp[i] = "IsPrimitive";
            }
            updatedOp.append(tempOp[i]).append(" ");
        }

        if(name2.equals("IsPrimitive")) {
            Criterion newNegatedCriterion = new Criterion("IsCompos");
            getCriterion().put("IsCompos", newNegatedCriterion);
        } else {
            Criterion newNegatedCriterion = new Criterion(name1, tempProperty, updatedOp.toString(), tempValue);
            newNegatedCriterion.setIsNegated(true);
            newNegatedCriterion.setIsBasic(true);
            String[] subCriterion = {name2, null, null};
            newNegatedCriterion.setSubCriterion(subCriterion);
            getCriterion().put(name1, newNegatedCriterion);
        }
        System.out.println(name1 + " has been successfully defined.");
        if(undoRedo && undo) {
            redoStack.push("DeleteCriterion " + name1);
        } else {
            undoStack.push("DeleteCriterion " + name1);
        }
    }

    /**
     * REQ11
     * This method defines a new binary criterion based on two existing criteria.
     * The new criterion is the result of applying the given operation to the two original criteria.
     * <p>
     * If a criterion with the provided new name already exists, or if either of the original criteria does not exist, an error message is printed.
     * The name of the new criterion must be at most 8 characters long, must not start with a digit, and can only contain English letters and digits.
     * <p>
     * If the new name is "IsPrimitive" or "IsCompos", the binary criterion is created by combining one of the original criteria with a built-in criterion.
     * <p>
     * The properties, operations, and values of the binary criterion are derived from those of the original criteria and the operation.
     * <p>
     * The new binary criterion is saved in the criterion list of the task management system.
     *
     * @param name1 The name of the binary criterion to be defined.
     * @param name2 The name of the first original criterion.
     * @param op The operation to apply to the original criteria.
     * @param name3 The name of the second original criterion.
     * @param undoRedo Whether undoRedo is enabled
     * @param undo Whether undo should be performed
     */
    public void defineBinaryCriterion(String name1, String name2, String op, String name3, boolean undoRedo, boolean undo) {
        // name1 check
        if(getCriterion().containsKey(name1)) {
            System.out.println(name1 + " already existed.");
            return;
        }
        // name2 and name3 check
        if(!getCriterion().containsKey(name2) && !getCriterion().containsKey(name3)) {
            System.out.println(name2 + " and " + name3 + " do not exist.");
            return;
        } else if(!(getCriterion().containsKey(name2))) {
            System.out.println(name2 + " does not exist.");
            return;
        } else if(!(getCriterion().containsKey(name3))) {
            System.out.println(name3 + " does not exist.");
            return;
        }
        // name1 validation
        else if(name1.length() > 8) {
            System.out.println("name is too long, only 8 characters at most.");
            return;
        } else if (Character.isDigit(name1.charAt(0))) {
            System.out.println("name cannot start with digits.");
            return;
        }
        for(int i = 0; i < name1.length(); i++) {
            if(!(Character.isLetter(name1.charAt(i)) || Character.isDigit(name1.charAt(i)))) {
                System.out.println("name may contain only English letters and digits.");
                return;
            }
        }
        if(name2.equals(name3)) {
            System.out.println(name2 + " and " + name3 + " cannot be the same.");
            return;
        }
        Criterion criterion1 = getCriterion().get(name2);
        Criterion criterion2 = getCriterion().get(name3);
        String tempProperty = criterion1.getProperty() + " " + op + " " + criterion2.getProperty();
        String tempOp = criterion1.getOp() + " " + op + " " + criterion2.getOp();
        String tempValue = criterion1.getValue() + " " + op + " " + criterion2.getValue();
        Criterion newBinaryCriterion = new Criterion(name1, tempProperty, tempOp, tempValue);
        newBinaryCriterion.setIsBasic(false);
        newBinaryCriterion.setIsNegated(false);
        String[] subCriterion = {name2, name3, op};
        newBinaryCriterion.setSubCriterion(subCriterion);
        System.out.println(name1 + " has been successfully defined.");
        getCriterion().put(name1, newBinaryCriterion);
        if(undoRedo && undo) {
            redoStack.push("DeleteCriterion " + name1);
        } else {
            undoStack.push("DeleteCriterion " + name1);
        }
    }

    /**
     * REQ12
     * This method retrieves all criteria from the criterion list and prints them.
     * For each criterion, the name, property, value, and operation are printed.
     * <p>
     * If the name of a criterion is "IsPrimitive" or "IsCompos", only the name is printed.
     * Otherwise, the name is printed followed by the property, value, and operation of the criterion.
     */
    public void printAllCriteria() {
        System.out.println("Here is the list of Criteria(s)");
        for(String i : getCriterion().keySet()) {
            if(getCriterion().get(i).getName().equals("IsPrimitive")) {
                System.out.println(i);
            } else if(getCriterion().get(i).getName().equals("IsCompos")) {
                System.out.println(i);
            } else {
                System.out.print(i + " -> ");
                System.out.print("property: " + getCriterion().get(i).getProperty() + ", ");
                System.out.print("value: " + getCriterion().get(i).getValue() + ", ");
                System.out.print("op: " + getCriterion().get(i).getOp());
                System.out.println();
            }
        }
    }

    /**
     * REQ13
     * This method searches for tasks in the task management system that meet the criterion specified by the given name.
     * <p>
     * If the criterion does not exist, an error message is printed.
     * If the criterion is a basic criterion, tasks that meet this criterion are searched for and printed.
     * If the criterion is a binary criterion, tasks that meet both of the sub-criteria are searched for and printed.
     * <p>
     * If no tasks meet the criterion, a message is printed to indicate this.
     *
     * @param name The name of the criterion to search for tasks.
     */
    public void search(String name){
        if(!getCriterion().containsKey(name)) {
            System.out.println(name + " does not exist.");
            return;
        }
        if(getCriterion().get(name).getIsBasic()) {
            if(basicCriteriaSearch(name).isEmpty()) {
                System.out.println(name + " does not contain any task(s).");
                return;
            }
            System.out.println("Here is the list of task(s) of " + name);
            for(String element : basicCriteriaSearch(name)) {
                System.out.print(element + " ");
            }
        }
        else {
            Criterion criterion1 = getCriterion().get(name);
            ArrayList<String> result = binaryCriteriaSearch(criterion1.getSubCriterion()[0], criterion1.getSubCriterion()[1], criterion1.getSubCriterion()[2]);
            assert result != null;
            if(result.isEmpty()) {
                System.out.println(name + " does not contain any task(s).");
                return;
            }
            System.out.println("Here is the list of task(s) of " + name);
            for (String element : result) {
                System.out.print(element + " ");
            }
        }
        System.out.println();
    }
    private ArrayList<String> binaryCriteriaSearch(String name1, String name2, String op){
        Criterion criterion1 = getCriterion().get(name1);
        Criterion criterion2 = getCriterion().get(name2);
        ArrayList<String> result = new ArrayList<String>();
        ArrayList<String> result1;
        ArrayList<String> result2;

        if(!criterion1.getIsBasic()){
            result1 = binaryCriteriaSearch(criterion1.getSubCriterion()[0], criterion1.getSubCriterion()[1], criterion1.getSubCriterion()[2]);
        }
        else result1 = basicCriteriaSearch(name1);

        if(!criterion2.getIsBasic()){
            result2 = binaryCriteriaSearch(criterion2.getSubCriterion()[0], criterion2.getSubCriterion()[1], criterion1.getSubCriterion()[2]);
        }
        else result2 = basicCriteriaSearch(name2);

        if(op.equals("&&")){
            assert result1 != null;
            for(String task1 : result1){
                assert result2 != null;
                for(String task2 : result2){
                    if(task1.equals(task2)){
                        result.add(task1);
                        break;
                    }
                }
            }
        } else if (op.equals("||")) {
            HashSet<String> unionSet = new HashSet<>();
            unionSet.addAll(basicCriteriaSearch(name1));
            unionSet.addAll(basicCriteriaSearch(name2));
            result = new ArrayList<>(unionSet);
        } else {
            System.out.println(op + " is not a valid op.");
            return null;
        }
        return result;
    }
    private ArrayList<String> basicCriteriaSearch(String name1){
        ArrayList<String> result = new ArrayList<>();
        if(getCriterion().get(name1).getName().equals("IsPrimitive")){
            for (String task : getPrimitiveTask().keySet()){
                if(getPrimitiveTask().containsKey(task)){
                    result.add(task);
                }
            }
        } else if(getCriterion().get(name1).getName().equals("IsCompos")){
            for (String task : getCompositeTask().keySet()){
                if(getCompositeTask().containsKey(task)){
                    result.add(task);
                }
            }
        } else if(!getCriterion().get(name1).getIsNegated()){
            switch (getCriterion().get(name1).getProperty()) {
                case "name" -> {
                    for (String task : getPrimitiveTask().keySet()) {
                        if (task.contains(getCriterion().get(name1).getValue())) {
                            result.add(task);
                        }
                    }
                    for (String task : getCompositeTask().keySet()) {
                        if (task.contains(getCriterion().get(name1).getValue())) {
                            result.add(task);
                        }
                    }
                }
                case "description" -> {
                    for (String task : getPrimitiveTask().keySet()) {
                        if (getPrimitiveTask().get(task).getDescription().contains(getCriterion().get(name1).getValue())) {
                            result.add(task);
                        }
                    }
                    for (String task : getCompositeTask().keySet()) {
                        if (getCompositeTask().get(task).getDescription().contains(getCriterion().get(name1).getValue())) {
                            result.add(task);
                        }
                    }
                }
                case "prerequisites" -> {
                    for (String task : getPrimitiveTask().keySet()) {
                        boolean matchCheck = false;
                        for (String prerequisiteTask : getCriterion().get(name1).getValue().split(",")) {
                            if (getPrimitiveTask().get(task).getPrerequisite() == null) {
                                break;
                            }
                            for (String taskPrerequisite : getPrimitiveTask().get(task).getPrerequisite()) {
                                if (prerequisiteTask.equals(taskPrerequisite)) {
                                    matchCheck = true;
                                    break;
                                }
                            }
                            if (matchCheck) {
                                result.add(task);
                                break;
                            }
                        }
                    }
                }
                case "duration" -> {
                    Criterion cur = getCriterion().get(name1);
                    for (String task : getPrimitiveTask().keySet()) {
                        switch (cur.getOp()) {
                            case ">" -> {
                                if(Double. parseDouble(cur.getValue()) < getPrimitiveTask().get(task).getDuration()) result.add(task);
                            }
                            case "<" -> {
                                if(Double. parseDouble(cur.getValue()) > getPrimitiveTask().get(task).getDuration()) result.add(task);
                            }
                            case ">=" -> {
                                if(Double. parseDouble(cur.getValue()) <= getPrimitiveTask().get(task).getDuration()) result.add(task);
                            }
                            case "<=" -> {
                                if(Double. parseDouble(cur.getValue()) >= getPrimitiveTask().get(task).getDuration()) result.add(task);
                            }
                            case "!=" -> {
                                if(Double. parseDouble(cur.getValue()) != getPrimitiveTask().get(task).getDuration()) result.add(task);
                            }
                            case "==" -> {
                                if(Double. parseDouble(cur.getValue()) == getPrimitiveTask().get(task).getDuration()) result.add(task);
                            }
                        }
                    }
                }
                case "subtasks" -> {
                    for (String task : getCompositeTask().keySet()) {
                        boolean matchCheck = false;
                        for (String subtaskCriterion : getCriterion().get(name1).getValue().split(",")) {
                            for (String subtask : getCompositeTask().get(task).getSubtask()) {
                                if (subtaskCriterion.equals(subtask)) {
                                    matchCheck = true;
                                    break;
                                }
                            }
                            if (!matchCheck) {
                                break;
                            }
                        }
                        if (matchCheck) {
                            result.add(task);
                        }
                    }
                }
            }
        } else {
            Criterion cur = getCriterion().get(getCriterion().get(name1).getSubCriterion()[0]);
            if(!cur.getIsBasic()){//if the subtask of the negated one is binary criterion
                for(String task : getPrimitiveTask().keySet()) {
                    boolean matchCheck = true;
                    for(String matchedTask : binaryCriteriaSearch(cur.getSubCriterion()[0], cur.getSubCriterion()[1], cur.getSubCriterion()[2])) {
                        if (task.equals(matchedTask)) {
                            matchCheck = false;
                            break;
                        }
                    }
                    if(matchCheck) {
                        result.add(task);
                    }
                }
            }
            else {//if the subtask of the negated one is basic criterion
                for(String task : getPrimitiveTask().keySet()) {
                    boolean matchCheck = true;
                    for(String matchedTask : basicCriteriaSearch(getCriterion().get(name1).getSubCriterion()[0])) {
                        if (task.equals(matchedTask)) {
                            matchCheck = false;
                            break;
                        }
                    }
                    if(matchCheck) {
                        result.add(task);
                    }
                }
            }
        }
        return result;
    }

    /**
     * REQ14
     * This method stores the current tasks and criteria to a file.
     * <p>
     * This method stores the current state of the primitive tasks, composite tasks, and criteria to a file with the specified path.
     * <p>
     * The tasks and criteria are formatted in a specific way for easy retrieval:
     * - Primitive tasks are denoted with `$` before and after their details.
     * - Composite tasks are denoted with `%` before and after their details.
     * - Criteria are denoted with `^` before their details.
     * <p>
     * Each detail of the tasks and criteria is written on a new line, and multiple values are comma-separated.
     * <p>
     * If the method succeeds, a success message is printed to the console. If an exception occurs during the write operation, the stack trace is printed to the console.
     *
     * @param path The file path where the tasks and criteria are to be stored.
     */
    public void store(String path) {
        File file = new File(path);
        if(file.getParentFile() == null){
            System.out.println("Failed to store tasks and criteria.");
            return;
        }
        if (file.getParentFile().exists() || file.getParentFile().mkdirs()) {
            try (PrintWriter writer = new PrintWriter(file)) {
                for(String classes : getPrimitiveTask().keySet()){
                    writer.println("$");
                    writer.println(getPrimitiveTask().get(classes).getName() + ",");
                    writer.println(getPrimitiveTask().get(classes).getDescription() + ",");
                    writer.println(getPrimitiveTask().get(classes).getDuration() + ",");
                    if (getPrimitiveTask().get(classes).getPrerequisite() != null) {
                        writer.println(String.join(",", getPrimitiveTask().get(classes).getPrerequisite()));
                    } else {
                        writer.println("NULL");
                    }
                    writer.println("$");
                }
                for(String classes : getCompositeTask().keySet()){
                    writer.println("%");
                    writer.println(getCompositeTask().get(classes).getName() + ",");
                    writer.println(getCompositeTask().get(classes).getDescription() + ",");
                    if(getCompositeTask().get(classes).getSubtask() != null) {
                        writer.println(String.join(",", getCompositeTask().get(classes).getSubtask()));
                    } else {
                        writer.println("null");
                    }
                    writer.println("%");
                }
                for(String classes : getCriterion().keySet()){
                    writer.println("^");
                    if(criterion.get(classes).getIsPrimitive()) {
                        writer.println("1");
                    } else {
                        writer.println("0");
                    }
                    writer.println(getCriterion().get(classes).getName()+",");
                    writer.println(getCriterion().get(classes).getProperty()+",");
                    writer.println(getCriterion().get(classes).getOp()+",");
                    writer.println(getCriterion().get(classes).getValue()+",");

                    if(criterion.get(classes).getIsPrimitive()) { writer.println("1"); }
                    else writer.println("0");
                    writer.println("^");
                }
                System.out.println("Tasks and criteria stored successfully.");
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Failed to store tasks and criteria.");
                return;
            }
        } else {
            System.out.println("Failed to store tasks and criteria.");
            return;
        }
    }

    /**
     * REQ15
     * Loads tasks and criteria from a file.
     * <p>
     * This method reads a file with the specified path and loads the primitive tasks, composite tasks, and criteria from it.
     * <p>
     * The file should be formatted in a specific way for correct parsing:
     * - Primitive tasks are denoted with `$` before and after their details.
     * - Composite tasks are denoted with `%` before and after their details.
     * - Criteria are denoted with `^` before their details.
     * <p>
     * Each detail of the tasks and criteria is read from a new line, and multiple values are comma-separated.
     * <p>
     * If the method succeeds, a success message is printed to the console. If a `FileNotFoundException` occurs, an error message and the stack trace are printed to the console. If another `IOException` occurs, a different error message and the stack trace are printed to the console.
     *
     * @param path The file path from where the tasks and criteria are to be loaded.
     */
    public void load(String path) {
        setPrimitiveTask(new HashMap<String, PrimitiveTask>());
        setCompositeTask(new HashMap<String, CompositeTask>());
        setCriterion(new HashMap<String, Criterion>());

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                switch (line) {
                    case "$" -> {
                        String name = bufferedReader.readLine().split(",")[0];
                        String description = bufferedReader.readLine().split(",")[0];
                        double duration = Double.parseDouble(bufferedReader.readLine().split(",")[0]);
                        String nextLine = bufferedReader.readLine();
                        String[] prerequisite = "NULL".equals(nextLine) ? null : nextLine.split(",");

                        PrimitiveTask task = new PrimitiveTask(name, description, duration, prerequisite);
                        getPrimitiveTask().put(name, task);

                        bufferedReader.readLine(); // Read the closing "$"

                    }
                    case "%" -> {
                        String name = bufferedReader.readLine().split(",")[0];
                        String description = bufferedReader.readLine().split(",")[0];
                        String[] subtask = bufferedReader.readLine().split(",");

                        CompositeTask compositeTaskNode = new CompositeTask(name, description, subtask);
                        getCompositeTask().put(name, compositeTaskNode);

                        bufferedReader.readLine(); // Read the closing "%"

                    }
                    case "^" -> {
                        String judgePrimitive = bufferedReader.readLine();
                        String name = bufferedReader.readLine().split(",")[0];
                        String property = bufferedReader.readLine().split(",")[0];
                        String op = bufferedReader.readLine().split(",")[0];
                        String value = bufferedReader.readLine().split(",")[0];
                        Criterion newCriterion = new Criterion(name, property, op, value);
                        getCriterion().put(name, newCriterion);
                        bufferedReader.readLine(); //Read the 0/1 that's used to judge whether it is primitive;
                        bufferedReader.readLine(); // Read the closing "^"

                    }
                }
            }
            System.out.println("Tasks and criteria read successfully.");
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + path);
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("An IOException occurred.");
            e.printStackTrace();
        }
    }

    /**
     * Constructs a new TMS object.
     * <p>
     * This constructor initializes a new TMS object and sets the 'IsPrimitive' criterion
     * using the value returned by the 'getIsPrimitive' method.
     */
    public TMS(){
        // set your isPrimitive here
        Criterion IsPri = new Criterion("IsPrimitive");
        IsPri.setIsBasic(true);
        IsPri.setIsNegated(false);
        getCriterion().put("IsPrimitive", IsPri);
    }
}