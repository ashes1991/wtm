/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kovtun.WorkTimeMap.UI;

import com.kovtun.WorkTimeMap.CallBacks.SelectProjectCallBack;
import com.kovtun.WorkTimeMap.Models.db.Action;
import com.kovtun.WorkTimeMap.Models.db.Project;
import com.kovtun.WorkTimeMap.Models.db.User;
import com.kovtun.WorkTimeMap.Models.other.Day;
import com.kovtun.WorkTimeMap.Models.other.Month;
import com.kovtun.WorkTimeMap.Models.other.MyTreeModel;
import com.kovtun.WorkTimeMap.Models.other.UserT;
import com.kovtun.WorkTimeMap.SQLConnector;
import com.kovtun.WorkTimeMap.Values;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
/**
 *
 * @author kovtun
 */
public class HeaderForm extends javax.swing.JFrame{
    private JFrame logFrame;
    private EntityManagerFactory emf;
    private EntityManager em;
    private List<UserT> userList;
    private Map<String,Integer> months=new HashMap<String, Integer>();
    private List<Action> actionList;
    private SelectProjectCallBack filter=new SelectProjectCallBack() {
        @Override
        public void projectSelected(Project project) {
            tree1.setModel(initTreeModel(project));
        }

        @Override
        public void projectsSelected(List<Project> projectList) {
            /**
             * 
             */
        }
    };
    private SelectProjectCallBack timeReport=new SelectProjectCallBack() {
        @Override
        public void projectSelected(Project project) {
            TimeReport dialog = new TimeReport(new javax.swing.JFrame(), true,project);
            dialog.setLocationRelativeTo(null);
            dialog.setVisible(true);
        }

        @Override
        public void projectsSelected(List<Project> projectList) {
            /**
             * 
             */
        }
    };
    /**
     * Creates new form HeaderForm
     */
    public HeaderForm(JFrame frameLog) {
        logFrame=frameLog;
        initComponents();
        if(Values.user.getRights()!=1){
            jMenuItem8ActionPerformed(null);
            jMenu3.setEnabled(false);
        }else
            setMenuEnabled(false);
        createMap();
        ((JPanel)userForm1.getComponents()[2]).remove(((JPanel)userForm1.getComponents()[2]).getComponents()[2]);
    }
    
    private MyTreeModel initTreeModel(Project project){
        userList=new ArrayList<UserT>();
        List<User> list = null;
        if(project==null)
            list=em.createNamedQuery("User.findAllWorkers", User.class).getResultList();
        else
            list=project.getUserList();
        for(User u:list)
            if(project==null)
                userList.add(getMonth(u));
            else
                userList.add(getMonth(u,project));
        
        return new MyTreeModel(userList);
    }
    
    private UserT getMonth(User user) {
        UserT ut=new UserT(user.getId(),user.getFio());
        List<Integer> yearList=em.createQuery("SELECT FUNC('YEAR',a.startTime) FROM Action a JOIN a.userId u WHERE u.id=:id GROUP BY FUNC('YEAR',a.startTime)")
                .setParameter("id", user.getId())
                .getResultList();
        for (int y:yearList){
            
            List<Integer> mlist=em.createQuery("SELECT FUNC('MONTH',a.startTime) FROM Action a JOIN a.userId u WHERE u.id=:id AND FUNC('YEAR',a.startTime)=:year GROUP BY FUNC('MONTH',a.startTime)")
                    .setParameter("id", user.getId())
                    .setParameter("year", y)
                    .getResultList();
            for(int m:mlist){
                Month month=new Month(y, m);
                List<Integer> dList=em.createQuery("SELECT FUNC('DAY',a.startTime) FROM Action a JOIN a.userId u WHERE u.id=:id AND FUNC('YEAR',a.startTime)=:year "
                        + "AND FUNC('MONTH',a.startTime)=:month GROUP BY FUNC('DAY',a.startTime)")
                        .setParameter("id", user.getId())
                        .setParameter("year", y)
                        .setParameter("month", m)
                        .getResultList();
                List<Day> dayList=new ArrayList();
                for(int d:dList){
                    Day day=new Day(d);
                    day.setActionList(em.createNamedQuery("Action.findByStartTimeAndUser",Action.class).setParameter("id", user.getId())
                    .setParameter("year", y)
                    .setParameter("month", m)
                    .setParameter("day", d)
                    .getResultList());
                    dayList.add(day);
                }
                month.setDayList(dayList);
                ut.addMonth(month);
            }
            
        }
        return ut;
    }
    private UserT getMonth(User user,Project project) {
        UserT ut=new UserT(user.getId(),user.getFio());
        List<Integer> yearList=em.createQuery("SELECT FUNC('YEAR',a.startTime) FROM Action a JOIN a.userId u JOIN a.projectId p WHERE u.id=:id AND p.id=:idp GROUP BY FUNC('YEAR',a.startTime)")
                .setParameter("id", user.getId())
                .setParameter("idp", project.getId())
                .getResultList();
        for (int y:yearList){
            
            List<Integer> mlist=em.createQuery("SELECT FUNC('MONTH',a.startTime) FROM Action a JOIN a.userId u JOIN a.projectId p WHERE u.id=:id AND p.id=:idp AND FUNC('YEAR',a.startTime)=:year GROUP BY FUNC('MONTH',a.startTime)")
                    .setParameter("id", user.getId())
                    .setParameter("idp", project.getId())
                    .setParameter("year", y)
                    .getResultList();
            for(int m:mlist){
                Month month=new Month(y, m);
                List<Integer> dList=em.createQuery("SELECT FUNC('DAY',a.startTime) FROM Action a JOIN a.userId u JOIN a.projectId p WHERE u.id=:id AND p.id=:idp AND FUNC('YEAR',a.startTime)=:year "
                        + "AND FUNC('MONTH',a.startTime)=:month GROUP BY FUNC('DAY',a.startTime)")
                        .setParameter("id", user.getId())
                        .setParameter("idp", project.getId())
                        .setParameter("year", y)
                        .setParameter("month", m)
                        .getResultList();
                List<Day> dayList=new ArrayList();
                for(int d:dList){
                    Day day=new Day(d);
                    day.setActionList(em.createNamedQuery("Action.findByStartTimeAndUserANDProject",Action.class)
                            .setParameter("id", user.getId())
                            .setParameter("idp", project.getId())
                            .setParameter("year", y)
                            .setParameter("month", m)
                            .setParameter("day", d)
                            .getResultList());
                    dayList.add(day);
                }
                month.setDayList(dayList);
                ut.addMonth(month);
            }
            
        }
        return ut;
    }
    
    private void createMap(){
        months.put("Январь",1);
        months.put("Февраль",2);
        months.put("Март",3);
        months.put("Апрель",4);
        months.put("Май",5);
        months.put("Июнь",6);
        months.put("Июль",7);
        months.put("Август",8);
        months.put("Сентябрь",9);
        months.put("Октябрь",10);
        months.put("Ноябрь",11);
        months.put("Декабрь",12);
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tree1 = new javax.swing.JTree();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();
        userForm1 = new com.kovtun.WorkTimeMap.UI.UserForm();
        jLabel2 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem6 = new javax.swing.JMenuItem();
        jMenuItem7 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem9 = new javax.swing.JMenuItem();
        jMenuItem10 = new javax.swing.JMenuItem();
        jMenuItem8 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Действия сотрудников:");

        tree1.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
            public void valueChanged(javax.swing.event.TreeSelectionEvent evt) {
                tree1ValueChanged(evt);
            }
        });
        jScrollPane3.setViewportView(tree1);

        jList1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        jList1.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                jList1ValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(jList1);

        jLabel2.setText("Действия:");

        jMenu1.setText("Файл");

        jMenuItem1.setText("Менеджер проектов");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem6.setText("Временной отчет по проекту");
        jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem6ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem6);

        jMenuItem7.setText("Графический отчет");
        jMenuItem7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem7ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem7);

        jMenuItem3.setText("Сменить пользователя");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem3);

        jMenuItem4.setText("Выход");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem4);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Фильтр");

        jMenuItem2.setText("Все");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem2);

        jMenuItem5.setText("По проекту");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem5);

        jMenuBar1.add(jMenu2);

        jMenu3.setText("Админ");

        jMenuItem9.setText("Работа с базой данных");
        jMenuItem9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem9ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem9);

        jMenuItem10.setText("Менеджер пользователей");
        jMenuItem10.setEnabled(false);
        jMenuItem10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem10ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem10);

        jMenuItem8.setText("Подключится");
        jMenuItem8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem8ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem8);

        jMenuBar1.add(jMenu3);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 252, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(userForm1, javax.swing.GroupLayout.PREFERRED_SIZE, 472, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(userForm1, javax.swing.GroupLayout.DEFAULT_SIZE, 587, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane3)
                            .addComponent(jScrollPane1))))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tree1ValueChanged(javax.swing.event.TreeSelectionEvent evt) {//GEN-FIRST:event_tree1ValueChanged
        if (evt.getPath().getPathCount()==4){
            
            for (UserT user:userList){
                if (user.getUsername().equals(evt.getPath().getPath()[1].toString())){
                    for(Month m:user.getMonthList())
                        if(m.getMonth()==months.get(evt.getPath().getPath()[2].toString().split(" ")[0])&&m.getYear()==Integer.valueOf(evt.getPath().getPath()[2].toString().split(" ")[1]))
                            for(Day d:m.getDayList())
                                if(d.getDay()==Integer.valueOf(evt.getPath().getPath()[3].toString())){
                                    setList(d.getActionList());
                                    return;
                                }
                }
            }
            
            
        }
    }//GEN-LAST:event_tree1ValueChanged

    private void jList1ValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jList1ValueChanged
        if(!evt.getValueIsAdjusting()&&jList1.getSelectedIndex()!=-1&&actionList.size()>jList1.getSelectedIndex())
                    setDataToComponents(actionList.get(jList1.getSelectedIndex()));
    }//GEN-LAST:event_jList1ValueChanged

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        logFrame.setVisible(true);
        dispose();
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        System.exit(0);
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        List<Project> projects=em.createNamedQuery("Project.findAll", Project.class).getResultList();
        ProjectManagerDialog dialog = new ProjectManagerDialog(this, true,projects);   
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        tree1.setModel(initTreeModel(null));
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
        List<Project> projects=em.createNamedQuery("Project.findAll", Project.class).getResultList();
        if (!(projects.size()>0)){
            JOptionPane.showMessageDialog(this, "Нет проектов","Ошибка", JOptionPane.ERROR_MESSAGE);
            return;
        }
        ProjectDialog dialog = new ProjectDialog(this, true,projects, filter,javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }//GEN-LAST:event_jMenuItem5ActionPerformed

    private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem6ActionPerformed
        List<Project> projects=em.createNamedQuery("Project.findAll", Project.class).getResultList();
        if (!(projects.size()>0)){
            JOptionPane.showMessageDialog(this, "Нет проектов","Ошибка", JOptionPane.ERROR_MESSAGE);
            return;
        }
        ProjectDialog dialog = new ProjectDialog(this, true,projects, timeReport,javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }//GEN-LAST:event_jMenuItem6ActionPerformed

    private void jMenuItem7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem7ActionPerformed
        GraphReportOptions dialog = new GraphReportOptions(new javax.swing.JFrame(), true,em);
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }//GEN-LAST:event_jMenuItem7ActionPerformed

    private void jMenuItem9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem9ActionPerformed
        WorkWithDBDialog dialog=new WorkWithDBDialog(this,true);
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }//GEN-LAST:event_jMenuItem9ActionPerformed

    private void jMenuItem8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem8ActionPerformed
        emf=Persistence.createEntityManagerFactory("Work_Time_MapPU");
        try{
            em=emf.createEntityManager();
            tree1.setModel(initTreeModel(null));
            setMenuEnabled(true);
        }catch(Exception e){
            JOptionPane.showMessageDialog(this, "Ошибка подключения к базе данных","Ошибка", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jMenuItem8ActionPerformed

    private void jMenuItem10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem10ActionPerformed
        UserManagerDialog dialog = new UserManagerDialog(new javax.swing.JFrame(), true,em);
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
        tree1.setModel(initTreeModel(null));
    }//GEN-LAST:event_jMenuItem10ActionPerformed
    
    private void setList(List<Action> list){
        actionList=list;
        setDataToComponents(null);
        jList1.clearSelection();
        DefaultListModel<String> model=new DefaultListModel();
        for(Action a:list)
            model.addElement(a.getName());
        jList1.setModel(model);
    }
    
    private void setDataToComponents(Action action){
        userForm1.nameTextField.setText(action==null?"":action.getName());
        userForm1.descriptionArea.setText(action==null?"":action.getDescription());
        userForm1.CommentArea.setText(action==null?"":action.getComment());
        userForm1.startTimeLabel.setText(action==null?"":new SimpleDateFormat("HH:mm").format(action.getStartTime()));
        userForm1.stopTimeLabel.setText(action==null?"":action.getStopTime()!=null?new SimpleDateFormat("HH:mm").format(action.getStopTime()):"Еще не закончино!");
        userForm1.projectLabel.setText(action==null?"":action.getProjectId().getName());
    }
    
    private void setMenuEnabled(boolean enabled){
        jMenuItem10.setEnabled(enabled);
        jMenu1.setEnabled(enabled);
        jMenu2.setEnabled(enabled);
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JList<String> jList1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem10;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JMenuItem jMenuItem8;
    private javax.swing.JMenuItem jMenuItem9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTree tree1;
    private com.kovtun.WorkTimeMap.UI.UserForm userForm1;
    // End of variables declaration//GEN-END:variables
    @Override
    public void dispose(){
        em.close();
        emf.close();
        super.dispose();
    }

    
}
