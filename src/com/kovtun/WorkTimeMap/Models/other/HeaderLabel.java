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
public class HeaderLabel {
    private String labelData;
    private List<SubLabel> sublabelList=new ArrayList<>();
    private List<Cell> cellList;

    public HeaderLabel(String labelData, List<SubLabel> sublabelList, List<Cell> cellList) {
        this.labelData = labelData;
        this.sublabelList = sublabelList;
        this.cellList = cellList;
    }

    public HeaderLabel() {
    }
    
    public String getLabelData() {
        return labelData;
    }

    public void setLabelData(String labelData) {
        this.labelData = labelData;
    }

    public List<SubLabel> getSublabelList() {
        return sublabelList;
    }

    public void setSublabelList(List<SubLabel> sublabelList) {
        this.sublabelList = sublabelList;
    }

    public List<Cell> getCellList() {
        return cellList;
    }

    public void setCellList(List<Cell> cellList) {
        this.cellList = cellList;
    }
    
     public void addSubLabel(SubLabel subLabel){
         sublabelList.add(subLabel);
     }

    @Override
    public String toString() {
        return "HeaderLabel{" + "labelData=" + labelData + ", sublabelList=" + sublabelList + ", cellList=" + cellList + '}';
    }
     
    public int getTimeInMin(){
        int time=0;
        for(Cell c:cellList)
            for(Action a:c.getActions())
                time+=((a.getStopTime().getTime()-a.getStartTime().getTime())/(1000*60));
        
        return time;
    }
}
