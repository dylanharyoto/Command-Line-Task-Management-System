package hk.edu.polyu.comp.comp2021.tms;

import hk.edu.polyu.comp.comp2021.tms.model.TMS;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * This class provides a graphical user interface (GUI) for the Task Management System (TMS).
 */
public class TasklyGUI extends JFrame{
    private TMS tms;
    private JFrame frame;
    private JTextArea outputArea;

    /**
     * Constructs a new GUI for the given TMS.
     * @param tms The TMS to be managed by the GUI.
     */
    public TasklyGUI(TMS tms) {
        this.tms = tms;
        GUI();
    }

    /**
     * Sets up the main frame of the GUI, including the menu bar and the text area for displaying messages.
     */
    private void GUI() {
        frame = new JFrame("Taskly");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JMenuBar menuBar = new JMenuBar();

        //for Task
        JMenu m1 = new JMenu("Task");
        JMenuItem m1a = new JMenuItem("Create Simple Task");
        JMenuItem m1b = new JMenuItem("Create Composite Task");
        JMenuItem m1c = new JMenuItem("Change Task");
        JMenuItem m1d = new JMenuItem("Delete Task");
        JMenuItem m1e = new JMenuItem("Print Task");
        JMenuItem m1f = new JMenuItem("Print All Tasks");
        m1.add(m1a);
        m1.add(m1b);
        m1.add(m1c);
        m1.add(m1d);
        m1.add(m1e);
        m1.add(m1f);

        menuBar.add(m1);

        //for Duration
        JMenu m2 = new JMenu("Duration");
        JMenuItem m2a = new JMenuItem("Report Duration");
        JMenuItem m2b = new JMenuItem("Report Earlist Finish Time");
        m2.add(m2a);
        m2.add(m2b);

        menuBar.add(m2);

        //for Criteria
        JMenu m3 = new JMenu("Criteria");
        JMenuItem m3a = new JMenuItem("Define Basic Criterion");
        JMenuItem m3b = new JMenuItem("Define Negated Criterion");
        JMenuItem m3c = new JMenuItem("Define Binary Criterion");
        JMenuItem m3d = new JMenuItem("Print All Criteria");
        m3.add(m3a);
        m3.add(m3b);
        m3.add(m3c);
        m3.add(m3d);

        menuBar.add(m3);

        //for Interaction
        JMenu m4 = new JMenu("Interact");
        JMenuItem m4a = new JMenuItem("Search");
        JMenuItem m4b = new JMenuItem("Store");
        JMenuItem m4c = new JMenuItem("Load");
        JMenuItem m4d = new JMenuItem("Undo");
        JMenuItem m4e = new JMenuItem("Redo");
        JMenuItem m4f = new JMenuItem("Quit");
        m4.add(m4a);
        m4.add(m4b);
        m4.add(m4c);
        m4.add(m4d);
        m4.add(m4e);
        m4.add(m4f);

        menuBar.add(m4);

        //for help
        JMenu m5 = new JMenu("Help");
        JMenuItem m5a = new JMenuItem("User Manual");
        JMenuItem m5b = new JMenuItem("Info");
        m5a.addActionListener(this::UserManual);
        m5b.addActionListener(this::TeamMember);
        m5.add(m5a);
        m5.add(m5b);
        menuBar.add(m5);

        frame.setSize(500,500);
        frame.setLocationRelativeTo(null);

        JPanel jp = new JPanel();
        JLabel jl = new JLabel("Command:");
        JTextField jt  =new JTextField(20);
        JButton jb = new JButton("Confirm");

        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = jt.getText();
                processinput(input);
                jt.setText("");
            }
        };
        jb.addActionListener(actionListener);
        jt.addActionListener(actionListener);

        jp.add(jl);
        jp.add(jt);
        jp.add(jb);
        frame.add(jp);
        frame.setJMenuBar(menuBar);
        frame.setSize(400, 200);

        outputArea = new JTextArea(5, 20);
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);
        frame.getContentPane().add(scrollPane, BorderLayout.SOUTH);

        PrintStream out = new PrintStream(new CustomOutputStream(outputArea));
        System.setOut(out);
        frame.setVisible(true);
    }

    /**
     * A customOutputStream that redirects output to a JTextArea.
     */
    public class CustomOutputStream extends OutputStream {
        private JTextArea textArea;

        /**
         * Constructs a new CustomOutputStream that writes to the given JTextArea.
         *
         * @param textArea The JTextArea to which this CustomOutputStream will write.
         */
        public CustomOutputStream(JTextArea textArea) {
            this.textArea = textArea;
        }

        @Override
        public void write(int b) throws IOException {
            textArea.append(String.valueOf((char)b));
            textArea.setCaretPosition(textArea.getDocument().getLength());
        }
    }

    /**
     * Processes the user's input command and calls the appropriate method of the TMS.
     */
    private void processinput(String input) {
        String[] inputSliced = input.split(" ");

        if(input.equals("Quit")) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.exit(0);
        }
        switch (inputSliced[0]) {
            case "CreatePrimitiveTask" -> {
                if(inputSliced.length != 5) {
                    outputArea.append("Invalid input. The input should be in \"CreateSimpleTask name description duration prerequisites\" format.\n");
                    break;
                }
                tms.createPrimitiveTask(inputSliced[1], inputSliced[2], inputSliced[3], inputSliced[4], false, false);
            }
            case "CreateCompositeTask" -> {
                if(inputSliced.length != 4) {
                    outputArea.append("Invalid command. The command should be in \"CreateCompositeTask name0 description name1,name2,...,namek\" format.\n");
                    break;
                }
                tms.createCompositeTask(inputSliced[1], inputSliced[2], inputSliced[3], false, false);
                //getExecutedCommands().push(input);
            }
            case "DeleteTask" -> {
                if(inputSliced.length != 2) {
                    outputArea.append("Invalid command. The command should be in \"DeleteTask name\" format.\n");
                    break;
                }
                tms.deleteTask(inputSliced[1], false, false);
                //getExecutedCommands().push(input);
            }
            case "ChangeTask" -> {
                if(inputSliced.length != 4) {
                    outputArea.append("Invalid command. The command should be in \"ChangeTask name property newValue\" format.\n");
                    break;
                }
                tms.changeTask(inputSliced[1], inputSliced[2], inputSliced[3], false, false);
            }
            case "PrintTask" -> {
                if(inputSliced.length != 2) {
                    outputArea.append("Invalid input. The input should be in \"PrintTask name\" format.\n");
                    break;
                }
                tms.printTask(inputSliced[1]);
            }
            case "PrintAllTasks" -> {
                if(inputSliced.length != 1) {
                    outputArea.append("Invalid command. The command should be in \"PrintAllTasks\" format.\n");
                    break;
                }
                tms.printAllTasks();
            }
            case "ReportDuration" -> {
                if(inputSliced.length != 2) {
                    outputArea.append("Invalid command. The command should be in \"ReportDuration name\" format.\n");
                    break;
                }
                tms.reportDuration(inputSliced[1]);
            }
            case "ReportEarliestFinishTime" -> {
                if(inputSliced.length != 2) {
                    outputArea.append("Invalid command. The command should be in \"ReportEarliestFinishTime name\" format.\n");
                    break;
                }
                tms.reportEarliestFinishTime(inputSliced[1]);
            }
            case "DefineBasicCriterion" -> {
                if(inputSliced.length != 5) {
                    outputArea.append("Invalid command. The command should be in \"DefineBasicCriterion name1 property op value\" format.\n");
                    break;
                }
                tms.defineBasicCriterion(inputSliced[1], inputSliced[2], inputSliced[3], inputSliced[4], false, false);
            }
            case "DefineNegatedCriterion" -> {
                if(inputSliced.length != 3) {
                    outputArea.append("Invalid command. The command should be in \"DefineNegatedCriterion name1 name2\" format.\n");
                    break;
                }
                tms.defineNegatedCriterion(inputSliced[1],inputSliced[2], false, false);
            }
            case "DefineBinaryCriterion" -> {
                if(inputSliced.length != 5) {
                    outputArea.append("Invalid command. The command should be in \"DefineBinaryCriterion name1 name2 logicOp name3\" format.\n");
                    break;
                }
                tms.defineBinaryCriterion(inputSliced[1],inputSliced[2], inputSliced[3], inputSliced[4], false, false);
            }
            case "PrintAllCriteria" -> {
                if(inputSliced.length != 1) {
                    outputArea.append("Invalid command. The command should be in \"PrintAllCriteria\" format.\n");
                    break;
                }
                tms.printAllCriteria();
            }
            case "Search" -> {
                if(inputSliced.length != 2) {
                    outputArea.append("Invalid command. The command should be in \"Search name\" format.\n");
                    break;
                }
                tms.search(inputSliced[1]);
            }
            case "Store" -> {
                if(inputSliced.length != 2) {
                    outputArea.append("Invalid command. The command should be in \"Store path\" format.\n");
                    break;
                }
                tms.store(inputSliced[1]);
            }
            case "Load" -> {
                if(inputSliced.length != 2) {
                    outputArea.append("Invalid command. The command should be in \"Load path\" format.\n");
                    break;
                }
                tms.load(inputSliced[1]);
            }
            default -> {
                //undo redo
                if(inputSliced[0].equals("undo")) {
                    tms.undo();
                }
                else if (inputSliced[0].equals("redo")) {
                    tms.redo();
                }
                else {
                    outputArea.append("Invalid command.\n");
                }
            }


        }
    }

    /**
     * Displays a user manual when the "User Manual" menu item is selected.
     */
    private void UserManual(ActionEvent e) {
    }

    /**
     * Displays information about the team members when the "Info" menu item is selected.
     */
    private void TeamMember(ActionEvent e) {
        JFrame tm = new JFrame("Info");
        tm.setSize(300, 200);
        tm.setLocationRelativeTo(null);
        JTextArea mArea = new JTextArea();
        mArea.setEditable(false);

        String memberNames = "Special Acknowledgement\nDylan\nShusen\nChelsea\nJason";
        mArea.setText(memberNames);

        JScrollPane scrollPane = new JScrollPane(mArea);
        tm.add(scrollPane);
        tm.setVisible(true);
    }

    /**
     * Starts the GUI for a new TMS.
     * @param args The main method for the TasklyGUI class
     */
    public static void main(String[] args) {
        TMS tms = new TMS();
        new TasklyGUI(tms);
    }
}