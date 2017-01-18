/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kovtun.WorkTimeMap.Models.other;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author kovtun
 */
public class Month {
    private int year;
    private int month;
    private List<Day> dayList;
    private Map<Integer,String> months=new HashMap<Integer, String>();


    public Month(int year, int month) {
        this.year = year;
        this.month = month;
        dayList=new ArrayList<Day>();
        setMap();
    }

    private void setMap(){
        months.put(1,"Январь");
        months.put(2,"Февраль");
        months.put(3,"Март");
        months.put(4,"Апрель");
        months.put(5,"Май");
        months.put(6,"Июнь");
        months.put(7,"Июль");
        months.put(8,"Август");
        months.put(9,"Сентябрь");
        months.put(10,"Октябрь");
        months.put(11,"Ноябрь");
        months.put(12,"Декабрь");
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public List<Day> getDayList() {
        return dayList;
    }

    public Month setDayList(List<Day> dayList) {
        this.dayList = dayList;
        return this;
    }

    public void addDay(Day day){
        dayList.add(day);
    }

    public Day getDay(int index){
        return dayList.get(index);
    }

    @Override
    public String toString() {
        return months.get(month)+" "+year;
    }
}
