/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kovtun.WorkTimeMap.Models.other;

import com.kovtun.WorkTimeMap.Models.db.Action;
import java.util.List;

/**
 *
 * @author kovtun
 */
public class SubLabel {
    private String labelData;
    private List<Cell> cellList;

    public SubLabel(String labelData, List<Cell> cellList) {
        this.labelData = labelData;
        this.cellList = cellList;
    }

    public SubLabel() {
        
    }

    public String getLabelData() {
        return labelData;
    }

    public void setLabelData(String labelData) {
        this.labelData = labelData;
    }

    public List<Cell> getCellList() {
        return cellList;
    }

    public void setCellList(List<Cell> cellList) {
        this.cellList = cellList;
    }

    @Override
    public String toString() {
        return "SubLabel{" + "labelData=" + labelData + ", cellList=" + cellList + '}';
    }
    
    public int getTimeInMin(){
        int time=0;
        for(Cell c:cellList)
            for(Action a:c.getActions())
                time+=((a.getStopTime().getTime()-a.getStartTime().getTime())/(1000*60));
        
        return time;
    }
}
