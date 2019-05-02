/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Amr Monier
 */
public class controller {

    static model theModel = new model();
    static view theView = new view();
    static int time = 0;
    static int currentPosition = -1;
    static process current;
    static process temp;
    static boolean fresh = true;
    static int reported = 0;

    static Thread realTimeThread = new Thread() {
        public void run() {
            try {
                timeGrowth();
            } catch (InterruptedException ex) {
                Logger.getLogger(controller.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    };

    static Thread progressThread = new Thread() {
        public void run() {
            try {
                progress();
            } catch (InterruptedException ex) {
                Logger.getLogger(controller.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    };

    public controller() {
    }

    static void timeGrowth() throws InterruptedException {
        while (true) {
            time++;
            Thread.sleep(1000);
        }
    }

    static void findNext() throws InterruptedException {

        if (!(theModel.scheduler.isEmpty()) && currentPosition != theModel.min()) {
            if (theModel.scheduler.size() == 1 && fresh) {
                currentPosition = theModel.min();
                current = theModel.scheduler.get(currentPosition);
                fresh = false;
            } else {
                currentPosition = theModel.min();
                theView.newRuPanel.setReportText("Swaping from process " + temp.getId() + " to " + theModel.scheduler.get(currentPosition).getId());
                current = theModel.scheduler.get(currentPosition);
            }
        }
    }

    static void progress() throws InterruptedException {
        while (true) {
            if (theModel.scheduler.size() == 0) {
                if (reported == 0) {
                    theView.newRuPanel.setReportText("The ready queue is empty");
                    fresh = true;
                    reported = 1;
                }

                Thread.sleep(2000);
            } else {
                reported = 0;
                findNext();
                temp = current;
                if (current.getBurstTime() > 0) {
                    current.setBurstTime(current.getBurstTime() - 1);
                    theModel.scheduler.set(currentPosition, current);
                    theView.newRuPanel.setReportText("process " + current.getId() + " is in progress...");
                    Thread.sleep(1000);
                } else {
                    theView.newRuPanel.setReportText("the process " + current.getId() + " is finished at time " + time);
                    theModel.scheduler.remove(current);
                    current = null;
                    currentPosition = -1;
                }
            }
        }
    }

    public static void main(String[] args) {
        theView.setVisible(true);
        realTimeThread.start();
        progressThread.start();
    }
}
