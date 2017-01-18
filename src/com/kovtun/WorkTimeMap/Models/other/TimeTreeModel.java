/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kovtun.WorkTimeMap.Models.other;

import com.kovtun.WorkTimeMap.Models.db.Action;
import java.util.List;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

/**
 *
 * @author kovtun
 */
public class TimeTreeModel implements TreeModel{
    private List<ReportUser> list;
        private String root;
        private long projectTime=0;

        public TimeTreeModel(List<ReportUser> list, String root) {
            this.list = list;
            this.root = root;
            calcProjectTime();
        }

        private void calcProjectTime(){
            for (ReportUser ru:list){
                ru.calcAllTime();
                projectTime+=ru.getAllTime();
            }
        }

        @Override
        public Object getRoot() {
            return root+" ("+(projectTime/(1000*60))/60+" ч. "+(projectTime/(1000*60)%60+" м.")+")";
        }

        @Override
        public Object getChild(Object parent, int index) {
            if(parent instanceof String)
                return list.get(index);

            if (parent instanceof ReportUser)
                return ((ReportUser)parent).getAction(index);
            return null;
        }

        @Override
        public int getChildCount(Object parent) {
            if (parent instanceof String)
                return list.size();

            if (parent instanceof ReportUser)
                return ((ReportUser)parent).getActions().size();
            return 0;
        }

        @Override
        public boolean isLeaf(Object node) {
            return node instanceof Action;
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
