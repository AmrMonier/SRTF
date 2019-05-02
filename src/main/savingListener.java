/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import static main.controller.theModel;
import static main.controller.theView;
import static main.controller.time;

/**
 *
 * @author Amr Monier
 */
class savingListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        int arrivalTime = time;
        try {
            String id = theView.newAddPanel.getId();
            int burstTime = theView.newAddPanel.getBurstTime();
            theView.newAddPanel.clear();
            process temp = new process(id, arrivalTime, burstTime);
            theModel.scheduler.add(temp);
            theView.newAddPanel.setStatus("process inserted succesfully size : " + theModel.scheduler.size());

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(theView, "Enter valid data");
        }
    }
}
