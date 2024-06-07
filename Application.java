package hk.edu.polyu.comp.comp2021.tms;
import java.util.*;
import hk.edu.polyu.comp.comp2021.tms.model.TMS;

/**
 * The main Application class for the Task Management System (TMS).
 * It provides an interactive command-line interface for users to input commands
 * for managing tasks and manipulating the TMS.
 * This class also supports the 'undo' and 'redo' operations for the executed commands.
 * <p>
 * The following operations are supported:
 * - CreatePrimitiveTask
 * - CreateCompositeTask
 * - DeleteTask
 * - ChangeTask
 * - PrintTask
 * - PrintAllTasks
 * - ReportDuration
 * - ReportEarliestFinishTime
 * - DefineBasicCriterion
 * - DefineNegatedCriterion
 * - DefineBinaryCriterion
 * - PrintAllCriteria
 * - Search
 * - Store
 * - Load
 * - undo
 * - redo
 */
public class Application {
    /**
     * @param args The main method for the Application class.
     *             It starts the Task Management System (TMS) with the command line for user to interact.
     *             The system will keep running until the input "Quit" is found.
     */
    public static void main(String[] args){
        TMS tms = new TMS();
        // Initialize and run the system
        while(true) {
            Scanner inputObject = new Scanner(System.in);
            System.out.print("TaskManagementSystem@COMP2021 ~ % ");
            String input = inputObject.nextLine();
            if(input.equals("Quit")) {
                System.out.println("Thank you for using the TMS. See you again!");
                break;
            }
            String[] inputSliced = input.split(" ");
            switch (inputSliced[0]) {
                case "CreatePrimitiveTask" -> {
                    if(inputSliced.length != 5) {
                        System.out.println("Invalid command. The command should be in \"CreatePrimitiveTask name description duration prerequisites\" format.");
                        continue;
                    }
                    tms.createPrimitiveTask(inputSliced[1], inputSliced[2], inputSliced[3], inputSliced[4], false, false);
                }
                case "CreateCompositeTask" -> {
                    if(inputSliced.length != 4) {
                        System.out.println("Invalid command. The command should be in \"CreateCompositeTask name0 description name1,name2,...,namek\" format.");
                        continue;
                    }
                    tms.createCompositeTask(inputSliced[1], inputSliced[2], inputSliced[3], false, false);
                }
                case "DeleteTask" -> {
                    if(inputSliced.length != 2) {
                        System.out.println("Invalid command. The command should be in \"DeleteTask name\" format.");
                        continue;
                    }
                    tms.deleteTask(inputSliced[1], false,false);
                }
                case "ChangeTask" -> {
                    if(inputSliced.length != 4) {
                        System.out.println("Invalid command. The command should be in \"ChangeTask name property newValue\" format.");
                        continue;
                    }
                    tms.changeTask(inputSliced[1], inputSliced[2], inputSliced[3], false, false);
                }
                case "PrintTask" -> {
                    if(inputSliced.length != 2) {
                        System.out.println("Invalid command. The command should be in \"PrintTask name\" format.");
                        continue;
                    }
                    tms.printTask(inputSliced[1]);
                }
                case "PrintAllTasks" -> {
                    if(inputSliced.length != 1) {
                        System.out.println("Invalid command. The command should be in \"PrintAllTasks\" format.");
                        continue;
                    }
                    tms.printAllTasks();
                }
                case "ReportDuration" -> {
                    if(inputSliced.length != 2) {
                        System.out.println("Invalid command. The command should be in \"ReportDuration name\" format.");
                        continue;
                    }
                    tms.reportDuration(inputSliced[1]);
                }
                case "ReportEarliestFinishTime" -> {
                    if(inputSliced.length != 2) {
                        System.out.println("Invalid command. The command should be in \"ReportEarliestFinishTime name\" format.");
                        continue;
                    }
                    tms.reportEarliestFinishTime(inputSliced[1]);
                }
                case "DefineBasicCriterion" -> {
                    if(inputSliced.length != 5) {
                        System.out.println("Invalid command. The command should be in \"DefineBasicCriterion name1 property op value\" format.");
                        continue;
                    }
                    tms.defineBasicCriterion(inputSliced[1], inputSliced[2], inputSliced[3], inputSliced[4], false, false);
                }
                case "DefineNegatedCriterion" -> {
                    if(inputSliced.length != 3) {
                        System.out.println("Invalid command. The command should be in \"DefineNegatedCriterion name1 name2\" format.");
                        continue;
                    }
                    tms.defineNegatedCriterion(inputSliced[1],inputSliced[2], false, false);
                }
                case "DefineBinaryCriterion" -> {
                    if(inputSliced.length != 5) {
                        System.out.println("Invalid command. The command should be in \"DefineBinaryCriterion name1 name2 logicOp name3\" format.");
                        continue;
                    }
                    tms.defineBinaryCriterion(inputSliced[1],inputSliced[2], inputSliced[3], inputSliced[4], false, false);
                }
                case "PrintAllCriteria" -> {
                    if(inputSliced.length != 1) {
                        System.out.println("Invalid command. The command should be in \"PrintAllCriteria\" format.");
                        continue;
                    }
                    tms.printAllCriteria();
                }
                case "Search" -> {
                    if(inputSliced.length != 2) {
                        System.out.println("Invalid command. The command should be in \"Search name\" format.");
                        continue;
                    }
                    tms.search(inputSliced[1]);
                }
                case "Store" -> {
                    if(inputSliced.length != 2) {
                        System.out.println("Invalid command. The command should be in \"Store path\" format.");
                        continue;
                    }
                    tms.store(inputSliced[1]);
                }
                case "Load" -> {
                    if(inputSliced.length != 2) {
                        System.out.println("Invalid command. The command should be in \"Load path\" format.");
                        continue;
                    }
                    tms.load(inputSliced[1]);
                }
                default -> {
                    if(inputSliced[0].equals("undo")) {
                        tms.undo();
                    }
                    else if(inputSliced[0].equals("redo")) {
                        tms.redo();
                    }
                    else {
                        System.out.println("command not found.");
                    }
                }
            }

        }
    }
}

