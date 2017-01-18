/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kovtun.WorkTimeMap.Models.other;

import java.util.List;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

/**
 *
 * @author kovtun
 */
public class MyTreeModel implements TreeModel{

    private List<UserT> list;

    public MyTreeModel(List<UserT> list) {
        this.list = list;
    }

    @Override
    public Object getRoot() {
        return "User name";
    }

    @Override
    public Object getChild(Object parent, int index) {
        if (parent instanceof String)
            return list.get(index);

        if (parent instanceof UserT)
            return ((UserT)parent).getMonth(index);

        if (parent instanceof Month)
            return ((Month)parent).getDay(index).getDay();

        return null;
    }

    @Override
    public int getChildCount(Object parent) {
        if(parent instanceof String)
            return list.size();

        if (parent instanceof UserT)
            return ((UserT)parent).getListSize();

        if (parent instanceof Month)
            return ((Month)parent).getDayList().size();

        return 0;
    }

    @Override
    public boolean isLeaf(Object node) {
        return node instanceof Integer;
    }

    @Override
    public void valueForPathChanged(TreePath path, Object newValue) {

    }

    @Override
    public int getIndexOfChild(Object parent, Object child) {
        return 0;
    }

    @Override
    public void addTreeModelListener(TreeModelListener l) {

    }

    @Override
    public void removeTreeModelListener(TreeModelListener l) {

    }
}
