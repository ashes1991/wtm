/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kovtun.WorkTimeMap.CallBacks;

import com.kovtun.WorkTimeMap.Models.db.User;

/**
 *
 * @author kovtun
 */
public interface UserListCallBack {
    void onSelectedUser(User user);
}
