/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kovtun.WorkTimeMap.UI;

import com.kovtun.WorkTimeMap.CallBacks.ActionDialogCallBack;
import com.kovtun.WorkTimeMap.CallBacks.SelectProjectCallBack;
import com.kovtun.WorkTimeMap.Models.db.Action;
import com.kovtun.WorkTimeMap.Models.db.Project;
import com.kovtun.WorkTimeMap.Values;
import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.Timer;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author kovtun
 */
public class MainForm extends javax.swing.JFrame implements ActionDialogCallBack,SelectProjectCallBack{
    private UserForm userForm=null;
    private JFrame logInFrame;
    private EntityManagerFactory emf;
    private EntityManager em;
    private String filter=Values.ALL_ACTIONS;
    private MenuItem show;
    private MenuItem newAction;
    private SystemTray tray;
    private TrayIcon trayIcon;
    private Timer timer;
    /**
     * Creates new form MainForm
     */
    public MainForm(JFrame login) {
        logInFrame=login;
        emf=Persistence.createEntityManagerFactory("Work_Time_MapPU");
        em=emf.createEntityManager();
        initComponents();
        
        setListSelectedListener();
        UserLogIn();
        if(actionList.size()>0)
            jList1.setSelectedIndex(0);
        setTray();
        setWindowListener();
    }
    
    private void UserLogIn(){
        userForm=new UserForm();
        this.add(userForm, BorderLayout.CENTER);
        userForm.changeButton.addActionListener(changeListener);
        userForm.completeButton.addActionListener(compliteListener);
        userForm.deleteButton.addActionListener(deleteListener);
        userForm.continueButton.addActionListener(continueListener);
    }
    
    private DefaultListModel convertMassive(){
        DefaultListModel<String> model=new DefaultListModel();
        
        for(Action a:actionList)
            model.addElement(a.getName());
        
        return model;
    }
    
    private void setTray(){
        if(! SystemTray.isSupported() ) {
            return;
        }


        PopupMenu trayMenu = new PopupMenu();
        MenuItem exit = new MenuItem("Выход");
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        show = new MenuItem("Открыть главное окно");
        show.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(true);
                show.setEnabled(false);
                newAction.setEnabled(false);
            }
        });
        show.setEnabled(false);
        newAction=new MenuItem("Новое действие");
        newAction.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createUpdateAction(null);
            }
        });
        newAction.setEnabled(false);

        trayMenu.add(newAction);
        trayMenu.add(show);
        trayMenu.add(exit);

        Image icon=Toolkit.getDefaultToolkit().getImage(new File("tray_ico.png").getPath());
        trayIcon = new TrayIcon(icon, "WorkTime Map", trayMenu);
        trayIcon.setImageAutoSize(true);

        tray = SystemTray.getSystemTray();
        try {
            tray.add(trayIcon);
        } catch (AWTException e) {
            e.printStackTrace();
        }
        trayIcon.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Show");
                setVisible(true);
                show.setEnabled(false);
                newAction.setEnabled(false);
            }
        });
        trayIcon.displayMessage("WorkTime Map", "Вы вошли как "+Values.user.getFio(),
                TrayIcon.MessageType.INFO);

        setTimer();
    }
    private void setTimer(){
        ActionListener task = new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                int size=em.createNamedQuery("Action.findByUserIdAndStopTimeNull", Action.class).setParameter("id", Values.user.getId()).getResultList().size();
                if (size>0)
                    trayIcon.displayMessage("WorkTime Map", "У Вас "+size+
                            " незаконченых действий!",
                            TrayIcon.MessageType.INFO);
            }  
        };

        timer = new Timer(Values.repeatTime*60*1000, task);
        timer.setRepeats(true);
        timer.start();
    }
    
    private void setWindowListener(){
        addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
                System.out.println("windowOpened");
            }

            @Override
            public void windowClosing(WindowEvent e) {
                setVisible(false);
                show.setEnabled(true);
                newAction.setEnabled(true);
                System.out.println("windowClosing");
            }

            @Override
            public void windowClosed(WindowEvent e) {
                tray.remove(trayIcon);
                System.out.println("");
            }

            @Override
            public void windowIconified(WindowEvent e) {
                System.out.println("windowClosed");
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
                System.out.println("");
            }

            @Override
            public void windowActivated(WindowEvent e) {
                System.out.println("windowDeiconified");
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
                System.out.println("windowDeactivated");
            }
        });
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        actionQuery = java.beans.Beans.isDesignTime() ? null : em.createNamedQuery("Action.findByUserId",Action.class).setParameter("id", Values.user.getId());
        actionList = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : actionQuery.getResultList();
        buttonGroup1 = new javax.swing.ButtonGroup();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem7 = new javax.swing.JMenuItem();
        jMenuItem6 = new javax.swing.JMenuItem();
        jMenuItem8 = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenuItem9 = new javax.swing.JMenuItem();

        em.close();
        emf.close();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Work Time Map");
        setPreferredSize(new java.awt.Dimension(1024, 768));

        jScrollPane1.setMaximumSize(new java.awt.Dimension(350, 32767));
        jScrollPane1.setPreferredSize(new java.awt.Dimension(350, 131));

        if(actionList!=null&&actionList.size()>0){
            jList1.setModel(convertMassive());
        }
        jList1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        jList1.setName(""); // NOI18N

        org.jdesktop.beansbinding.ELProperty eLProperty = org.jdesktop.beansbinding.ELProperty.create("${actionList}");
        org.jdesktop.swingbinding.JListBinding jListBinding = org.jdesktop.swingbinding.SwingBindings.createJListBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, actionList, eLProperty, jList1);
        jListBinding.setDetailBinding(org.jdesktop.beansbinding.ELProperty.create("${name}"));
        bindingGroup.addBinding(jListBinding);

        jScrollPane1.setViewportView(jList1);

        getContentPane().add(jScrollPane1, java.awt.BorderLayout.WEST);

        jMenu1.setText("Файл");

        jMenuItem2.setText("Создать новое действие");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuItem1.setText("Менеджер проектов");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

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

        jMenuItem7.setText("Все действия");
        jMenuItem7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem7ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem7);

        jMenuItem6.setText("Незавершенные");
        jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem6ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem6);

        jMenuItem8.setText("Завершенные");
        jMenuItem8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem8ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem8);

        jMenuItem5.setText("Текущий день");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem5);

        jMenuItem9.setText("По проектам");
        jMenuItem9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem9ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem9);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        bindingGroup.bind();

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        
        Set<Project> projectsSet=new HashSet<>();
        projectsSet.addAll(Values.user.getProjectList());
        projectsSet.addAll(Values.user.getProjectList1());
        ProjectManagerDialog dialog = new ProjectManagerDialog(this, true,new ArrayList<Project>(projectsSet));   
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
        
        emf=Persistence.createEntityManagerFactory("Work_Time_MapPU");
        em=emf.createEntityManager();
        refresh(0);
        em.close();
        emf.close();
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        createUpdateAction(null);
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        logInFrame.setVisible(true);
        dispose();
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        System.exit(0);
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jMenuItem7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem7ActionPerformed
        emf=Persistence.createEntityManagerFactory("Work_Time_MapPU");
        em=emf.createEntityManager();
        filter=Values.ALL_ACTIONS;
        refresh(actionList.size()>0?0:-1);
        em.close();
        emf.close();
    }//GEN-LAST:event_jMenuItem7ActionPerformed

    private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem6ActionPerformed
        emf=Persistence.createEntityManagerFactory("Work_Time_MapPU");
        em=emf.createEntityManager();
        filter=Values.NOT_END_ACTIONS;
        refresh(actionList.size()>0?0:-1);
        em.close();
        emf.close();
    }//GEN-LAST:event_jMenuItem6ActionPerformed

    private void jMenuItem8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem8ActionPerformed
        emf=Persistence.createEntityManagerFactory("Work_Time_MapPU");
        em=emf.createEntityManager();
        filter=Values.ENDED_ACTIONS;
        refresh(actionList.size()>0?0:-1);
        em.close();
        emf.close();
    }//GEN-LAST:event_jMenuItem8ActionPerformed

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
        emf=Persistence.createEntityManagerFactory("Work_Time_MapPU");
        em=emf.createEntityManager();
        filter=Values.THIS_DAY_ACTIONS;
        refresh(actionList.size()>0?0:-1);
        em.close();
        emf.close();
    }//GEN-LAST:event_jMenuItem5ActionPerformed

    private void jMenuItem9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem9ActionPerformed
        emf=Persistence.createEntityManagerFactory("Work_Time_MapPU");
        em=emf.createEntityManager();
        List<Project> pr=em.createNamedQuery("Project.findByUserId",Project.class).setParameter("id",Values.user.getId()).getResultList();
        pr.size();
        em.close();
        emf.close();
        ProjectDialog dialog = new ProjectDialog(this, true,pr,this,javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }//GEN-LAST:event_jMenuItem9ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private java.util.List<com.kovtun.WorkTimeMap.Models.db.Action> actionList;
    private javax.persistence.Query actionQuery;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JList<String> jList1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JMenuItem jMenuItem8;
    private javax.swing.JMenuItem jMenuItem9;
    private javax.swing.JScrollPane jScrollPane1;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

    private void setListSelectedListener() {
        jList1.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent lse) {
                
                if(!lse.getValueIsAdjusting()&&jList1.getSelectedIndex()!=-1)
                    setDataToViews(jList1.getSelectedIndex());
            }
        });
    }
    
    private void setDataToViews(int index){
        userForm.projectLabel.setText(actionList.get(index).getProjectId().getName());
        userForm.nameTextField.setText(actionList.get(index).getName());
        userForm.descriptionArea.setText(actionList.get(index).getDescription());
        userForm.CommentArea.setText(actionList.get(index).getComment());
        userForm.startTimeLabel.setText(new SimpleDateFormat("dd.MM.yyyy HH:mm").format(actionList.get(index).getStartTime()));
        userForm.stopTimeLabel.setText(actionList.get(index).getStopTime()!=null?new SimpleDateFormat("dd.MM.yyyy HH:mm").format(actionList.get(index).getStopTime()):"Еще не завершено!");
        if(actionList.get(index).getStopTime()!=null){
            userForm.completeButton.setEnabled(false);
            userForm.continueButton.setEnabled(true);
        }else{
            userForm.completeButton.setEnabled(true);
            userForm.continueButton.setEnabled(false);
        }
        userForm.changeButton.setEnabled(true);
        userForm.deleteButton.setEnabled(true);
    }
    
    private void createUpdateAction(Action action){
        emf=Persistence.createEntityManagerFactory("Work_Time_MapPU");
        em=emf.createEntityManager();
        List<Project> pr=em.createNamedQuery("Project.findByStopTimeNotNull",Project.class).setParameter("id",Values.user.getId()).getResultList();
        pr.size();
        em.close();
        emf.close();
        CreateActionDialog dialog = new CreateActionDialog(this, true,this,action,pr);
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);   
    }

    @Override
    public void updateAction(Action action) {
        emf=Persistence.createEntityManagerFactory("Work_Time_MapPU");
        em=emf.createEntityManager();
        em.getTransaction().begin();
        em.merge(action);
        em.getTransaction().commit();
        int position=jList1.getSelectedIndex();
        refresh(position);
        em.close();
        emf.close();
    }

    @Override
    public void createAction(Action action) {
        emf=Persistence.createEntityManagerFactory("Work_Time_MapPU");
        em=emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(action);
        em.getTransaction().commit();
        refresh(0);
        em.close();
        emf.close();
    }
    
    private ActionListener changeListener=new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent ae) {
            createUpdateAction(actionList.get(jList1.getSelectedIndex()));
            
        }
    };
    
    private ActionListener compliteListener=new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent ae) {
            emf=Persistence.createEntityManagerFactory("Work_Time_MapPU");
            em=emf.createEntityManager();
            int position=jList1.getSelectedIndex();
            actionList.get(position).setStopTime(new Date());
            em.getTransaction().begin();
            em.merge(actionList.get(position));
            em.getTransaction().commit();
            refresh(position);
            em.close();
            emf.close();
        }
    };
    private ActionListener deleteListener=new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent ae) {
            emf=Persistence.createEntityManagerFactory("Work_Time_MapPU");
            em=emf.createEntityManager();
            int position=jList1.getSelectedIndex();
            em.getTransaction().begin();
            Action action=em.createNamedQuery("Action.findById",Action.class).setParameter("id", actionList.get(position).getId()).getSingleResult();
            em.remove(action);
            em.getTransaction().commit();
            refresh((position-1)>=0?position-1:0);
            em.close();
            emf.close();
        }
    };
    private ActionListener continueListener=new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent ae) {
            Action oldAction=actionList.get(jList1.getSelectedIndex());
            Action newAction=new Action();
            newAction.setName(oldAction.getName());
            newAction.setComment(oldAction.getComment());
            newAction.setDescription(oldAction.getDescription());
            newAction.setProjectId(oldAction.getProjectId());
            newAction.setUserId(oldAction.getUserId());
            newAction.setStartTime(new Date());
            newAction.setStopTime(null);
            createAction(newAction); 
        }
        
    };
    private void refresh(int listSelecterdIndex){
        jList1.clearSelection();
        if(filter.equals(Values.ALL_ACTIONS))
            actionList=em.createNamedQuery("Action.findByUserId", Action.class).setParameter("id", Values.user.getId()).getResultList();
        if(filter.equals(Values.NOT_END_ACTIONS))
            actionList=em.createNamedQuery("Action.findByUserIdAndStopTimeNull", Action.class).setParameter("id", Values.user.getId()).getResultList();
        if(filter.equals(Values.ENDED_ACTIONS))
            actionList=em.createNamedQuery("Action.findByUserIdAndStopTimeNotNull", Action.class).setParameter("id", Values.user.getId()).getResultList();
        if(filter.equals(Values.THIS_DAY_ACTIONS)){
            Calendar calendar=Calendar.getInstance();
            
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            Date dataFrom=calendar.getTime();
            
            calendar.set(Calendar.HOUR_OF_DAY, 23);
            calendar.set(Calendar.MINUTE, 59);
            calendar.set(Calendar.SECOND, 59);
            Date dataTo=calendar.getTime();
            actionList=em.createNamedQuery("Action.findByUserIdAndStartTime", Action.class).setParameter("id", Values.user.getId()).
                    setParameter("fromTime", dataFrom).setParameter("toTime", dataTo).getResultList();
        }
        
        jList1.setModel(convertMassive());
        if(listSelecterdIndex>=0&&actionList.size()>0)
            jList1.setSelectedIndex(listSelecterdIndex);
        else
            clearViews();
    }

    @Override
    public void projectSelected(Project project) {
        emf=Persistence.createEntityManagerFactory("Work_Time_MapPU");
        em=emf.createEntityManager();
        filter=Values.BY_PROJECT_ACTIONS;
        actionList=em.createNamedQuery("Action.findByUserIdAndProject", Action.class).setParameter("idu", Values.user.getId()).setParameter("idp", project.getId()).getResultList();
        refresh(0);
        em.close();
        emf.close();
    }
    
    private void clearViews(){
        userForm.projectLabel.setText("");
        userForm.nameTextField.setText("");
        userForm.descriptionArea.setText("");
        userForm.CommentArea.setText("");
        userForm.startTimeLabel.setText("");
        userForm.stopTimeLabel.setText("");
        userForm.changeButton.setEnabled(false);
        userForm.completeButton.setEnabled(false);
        userForm.deleteButton.setEnabled(false);
    }

    @Override
    public void projectsSelected(List<Project> projectList) {
        /**
        *
        */
    }
    
}
