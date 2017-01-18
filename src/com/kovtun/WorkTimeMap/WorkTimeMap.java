/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kovtun.WorkTimeMap;

import com.kovtun.WorkTimeMap.UI.LogIn;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.swing.JFrame;

/**
 *
 * @author kovtun
 */
public class WorkTimeMap {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                LogIn log=new LogIn();
                log.setVisible(true);
                log.setLocationRelativeTo(null);
            }
        });
        
    }
    
}
