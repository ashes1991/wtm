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
public class UserT {
    private int id;
    private String username;
    private List<Month> monthList;

    public UserT(int id,String username) {
        this.id=id;
        this.username = username;
        monthList=new ArrayList<Month>();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Month> getMonthList() {
        return monthList;
    }

    public void setMonthList(List<Month> monthList) {
        this.monthList = monthList;
    }

    public void addMonth(Month m){
        monthList.add(m);
    }

    public void addMonthList(List<Month> list){
        for (Month month:list)
            monthList.add(month);
    }

    public int getListSize(){
        return monthList.size();
    }

    public Month getMonth(int index){
        return monthList.get(index);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return  username;
    }
}
