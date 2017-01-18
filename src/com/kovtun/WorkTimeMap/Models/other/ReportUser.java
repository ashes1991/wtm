/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kovtun.WorkTimeMap.Models.other;

import com.kovtun.WorkTimeMap.Models.db.Action;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author kovtun
 */
public class ReportUser {
    private int id;
    private String fio;
    private List<Action> actions;
    private long AllTime=0;

    public ReportUser(int id, String fio) {
        this.id = id;
        this.fio = fio;
        actions=new ArrayList<>();
    }

    public ReportUser(int id, String fio, List<Action> actions) {
        this.id = id;
        this.fio = fio;
        this.actions = actions;
    }
    public void calcAllTime(){
        for (Action w:actions){
            if(w.getStopTime()!=null)
            AllTime+=(w.getStopTime().getTime()-w.getStartTime().getTime());
        }
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public List<Action> getActions() {
        return actions;
    }
    public void removeAllActions(){
        actions.clear();
    }
    public void setActions(List<Action> actions) {
        this.actions = actions;
    }

    public void addAction(Action action){
        actions.add(action);
    }

    public long getAllTime() {
        return AllTime;
    }

    public void setAllTime(long allTime) {
        AllTime = allTime;
    }

    public Action getAction(int intdex){
        return actions.get(intdex);
    }

    @Override
    public String toString() {
        return fio+" ("+(AllTime/(1000*60))/60+" ч. "+(AllTime/(1000*60)%60+" м.")+")";
    } 
}
