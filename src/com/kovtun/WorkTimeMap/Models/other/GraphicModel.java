/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kovtun.WorkTimeMap.Models.other;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author kovtun
 */
public class GraphicModel {
    private List<HeaderLabel> listHeaderLabel;
    private List<Cell> cellList;
    private String type;

    public GraphicModel(List<HeaderLabel> listHeaderLabel, List<Cell> cellList, String type) {
        this.listHeaderLabel = listHeaderLabel;
        this.cellList = cellList;
        this.type = type;
    }

    public GraphicModel() {
        listHeaderLabel=new ArrayList<>();
    }

    public List<HeaderLabel> getListHeaderLabel() {
        return listHeaderLabel;
    }

    public void setListHeaderLabel(List<HeaderLabel> listHeaderLabel) {
        this.listHeaderLabel = listHeaderLabel;
    }

    public List<Cell> getCellList() {
        return cellList;
    }

    public void setCellList(List<Cell> cellList) {
        this.cellList = cellList;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
    public void addHeaderLabel(HeaderLabel hl){
        listHeaderLabel.add(hl);
    }

    @Override
    public String toString() {
        return "GraphicModel{" + "listHeaderLabel=" + listHeaderLabel + ", cellList=" + cellList + ", type=" + type + '}';
    }
    
    
}
