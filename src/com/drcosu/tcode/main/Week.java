package com.drcosu.tcode.main;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Week {
    private JButton button1;
    public JPanel panel1;

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
    public void setOnclick(ActionListener actionListener){
        button1.addActionListener(actionListener);
    }
}
