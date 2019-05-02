/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.util.ArrayList;

/**
 *
 * @author Amr Monier
 */
public class model {

    ArrayList<process> scheduler = new ArrayList<>();

    int min() {
        if (scheduler.size() == 1) {
            return 0;
        } else {
            process temp = scheduler.get(0);
            for (process porcessTemp : scheduler) {
                if (porcessTemp.getBurstTime() < temp.getBurstTime()) {
                    temp = porcessTemp;
                }
            }
            return scheduler.indexOf(temp);
        }

    }
}
