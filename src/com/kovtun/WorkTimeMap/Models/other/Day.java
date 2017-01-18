/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kovtun.WorkTimeMap.Models.other;

import java.util.List;
import com.kovtun.WorkTimeMap.Models.db.Action;
/**
 *
 * @author kovtun
 */
public class Day {
    private int day;
    private List<Action> actionList;

    public Day(int day) {
        this.day = day;
    }

    

    public List<Action> getActionList() {
        return actionList;
    }

    public void setActionList(List<Action> actionList) {
        this.actionList = actionList;
    }
    
    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    @Override
    public String toString() {
        return "Day{" + "day=" + day + ", actionList=" + actionList + '}';
    }
    
    
}
