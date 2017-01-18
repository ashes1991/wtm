/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kovtun.WorkTimeMap.Models.other;

import com.kovtun.WorkTimeMap.Models.db.Action;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 *
 * @author kovtun
 */
public class Cell {
    private Calendar date;
    private List<Action> actions=new ArrayList();
    private boolean visible;

    public Cell(Calendar data) {
        this.date = data;
    }

    public Calendar getData() {
        return date;
    }

    public void setData(Calendar data) {
        this.date = data;
    }

    public List<Action> getActions() {
        return actions;
    }

    public void setActions(List<Action> actions) {
        this.actions = actions;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
    
    public Date getStartDate(){
        date.set(Calendar.HOUR_OF_DAY, 00);
        date.set(Calendar.MINUTE, 00);
        date.set(Calendar.SECOND, 00);
        return date.getTime();
    }
    public Date getStopDate(){
        date.set(Calendar.HOUR_OF_DAY, 23);
        date.set(Calendar.MINUTE, 59);
        date.set(Calendar.SECOND, 59);
        return date.getTime();
    }
    
    public void addAction(Action action){
        actions.add(action);
    }

    @Override
    public String toString() {
        return "Cell{" + "date=" + date + ", actions=" + actions + ", visible=" + visible + '}';
    }
    
    
}
