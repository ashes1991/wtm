/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kovtun.WorkTimeMap.CallBacks;

import com.kovtun.WorkTimeMap.Models.db.Project;
import java.util.List;

/**
 *
 * @author kovtun
 */
public interface SelectProjectCallBack {
    void projectSelected(Project project);
    void projectsSelected(List<Project> projectList);
}
