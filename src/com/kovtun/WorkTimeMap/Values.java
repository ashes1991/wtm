/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kovtun.WorkTimeMap;

import com.kovtun.WorkTimeMap.Models.db.User;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author kovtun
 */
public class Values {
    public static User user=null;
    public static int repeatTime=1;

    public static final String SQLException="Ошибка соединения с базой данных";
    public static final String IOException="Ошибка чтения файла настроек";

    public static final String PERFORMERS="performers";
    public static final String PROJECTS="projects";

    public static final String DAY="day";
    public static final String HOUR="hour";
    
    public static final String ALL_ACTIONS="all";
    public static final String NOT_END_ACTIONS="not_end";
    public static final String ENDED_ACTIONS="ended";
    public static final String THIS_DAY_ACTIONS="this_day";
    public static final String BY_PROJECT_ACTIONS="by_project";
    
}
