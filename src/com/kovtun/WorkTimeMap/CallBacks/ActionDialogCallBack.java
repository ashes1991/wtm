/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kovtun.WorkTimeMap.CallBacks;

import com.kovtun.WorkTimeMap.Models.db.Action;

/**
 *
 * @author kovtun
 */
public interface ActionDialogCallBack {
    void updateAction(Action action);
    void createAction(Action action);
}
