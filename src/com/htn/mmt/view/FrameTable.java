package com.htn.mmt.view;

import javax.swing.*;

public class FrameTable {
    // frame
    private JFrame f;
    // Table
    private JTable j;

    private String[][] data;

    // Constructor
    public FrameTable(String[][] data)
    {
        this.data = data;
        // Frame initialization
        f = new JFrame();

        // Frame Title
        f.setTitle("Students list");

//        // Data to be displayed in the JTable
//        String[][] data = {
//                { "Kundan Kumar Jha", "4031", "CSE" },
//                { "Anand Jha", "6014", "IT" }
//        };

        // Column Names
        String[] columnNames = { "Name", "Roll ID", "Average" };

        // Initializing the JTable
        j = new JTable(this.data, columnNames);
        j.setBounds(30, 40, 200, 300);

        // adding it to JScrollPane
        JScrollPane sp = new JScrollPane(j);
        f.add(sp);
        // Frame Size
        f.setSize(500, 500);
        // Frame Visible = true
        f.setVisible(true);
        f.setLocationRelativeTo(null);
    }

    // Driver  method
    public static void main(String[] args)
    {
    }
}
