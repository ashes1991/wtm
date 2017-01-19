/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kovtun.WorkTimeMap.UI;

import com.kovtun.WorkTimeMap.CallBacks.UserListCallBack;
import com.kovtun.WorkTimeMap.Models.db.Action;
import com.kovtun.WorkTimeMap.Models.db.Project;
import com.kovtun.WorkTimeMap.Models.db.User;
import com.kovtun.WorkTimeMap.Models.db.User_;
import com.kovtun.WorkTimeMap.Values;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import com.kovtun.WorkTimeMap.CallBacks.AddNewProjectCallBack;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
/**
 *
 * @author kovtun
 */
public class ProjectManagerDialog extends javax.swing.JDialog implements UserListCallBack,AddNewProjectCallBack{
    private List<Project> projectList;
    private Project selectedProject;
    private Project project;
    private EntityManager em;
    private EntityManagerFactory emf;
    
    /**
     * Creates new form ProjectManagerDialog
     */
    public ProjectManagerDialog(java.awt.Frame parent, boolean modal,List<Project> projectList) {
        super(parent, modal);
        this.projectList=projectList;
        emf=Persistence.createEntityManagerFactory("Work_Time_MapPU");
        em=emf.createEntityManager();
        initComponents();
        if(Values.user.getRights()!=1)
            jButton1.setEnabled(false);
        setListSelectedListener();
    }
    
    private DefaultListModel<String> initProjectsModel(){
        DefaultListModel<String> model=new DefaultListModel();
        
        for(Project a:projectList)
            model.addElement(a.getName());
        
        return model;
    }
    
    private DefaultListModel<String> initUsersModel(){
        DefaultListModel<String> model=new DefaultListModel();
        
        for(User u:project.getUserList())
            model.addElement(u.getFio());
        
        return model;
    }
    
    private DefaultListModel<String> initActionsModel(){
        DefaultListModel<String> model=new DefaultListModel();
        
        for(Action a:project.getActionList())
            model.addElement(a.getName());
        
        return model;
    }
    
    private void setListSelectedListener() {
        projectjList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent lse) {
                
                if(!lse.getValueIsAdjusting()){
                    if(project==null){
                        selectProject();
                        selectProject();
                    }else
                        selectProject();
                }
            }
        });
        nameTextField.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
              checkSaveAndCancelBuuton();
            }
            public void removeUpdate(DocumentEvent e) {
              checkSaveAndCancelBuuton();
            }
            public void insertUpdate(DocumentEvent e) {
              checkSaveAndCancelBuuton();
            }
        });
    }
    
    private void selectProject(){
        if(projectjList.getSelectedIndex()!=-1){
            project=projectList.get(projectjList.getSelectedIndex()).clone();
            selectedProject=projectList.get(projectjList.getSelectedIndex()).clone();
            setDataToViews();
            if(projectList.get(projectjList.getSelectedIndex()).getName().equals("Без проекта")){
                setAllButtons(false);
            }else{
                setAllButtons(true);
            }       
            checkSaveAndCancelBuuton();
        }
    }
    private void setDataToViews() {
        final long stTime=project.getStartTime().getTime();
        creatorNameLabel.setText(project.getCreatorId().getFio());
        nameTextField.setText(project.getName());
        Calendar startCalendar=Calendar.getInstance();
        startCalendar.setTimeInMillis(stTime);
        creatingData.setSelectedDate(startCalendar);
        if(project.getStopTime()!=null){
            Calendar stop=Calendar.getInstance();
            final long stpTime=project.getStopTime().getTime();
            stop.setTimeInMillis(stpTime);
            closeData.setSelectedDate(stop);
            closeCheck.setSelected(true);
        }
        userjList.setModel(initUsersModel());
        actionjList.setModel(initActionsModel());
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        projectjList = new javax.swing.JList<>();
        jPanel1 = new javax.swing.JPanel();
        nameTextField = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        creatorNameLabel = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        creatingData = new datechooser.beans.DateChooserCombo();
        closeData = new datechooser.beans.DateChooserCombo();
        closeCheck = new javax.swing.JCheckBox();
        jLabel1 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        saveProjectButton = new javax.swing.JButton();
        cancelProjectButton = new javax.swing.JButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        userjList = new javax.swing.JList<>();
        addUserButton = new javax.swing.JButton();
        deleteUserButton = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        actionjList = new javax.swing.JList<>();
        CloseButton = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Менеджер проектов");

        jScrollPane1.setMaximumSize(new java.awt.Dimension(350, 32767));

        projectjList.setModel(initProjectsModel());
        projectjList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        jScrollPane1.setViewportView(projectjList);

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        nameTextField.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        nameTextField.setEnabled(false);

        jLabel2.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel2.setText("Создатель:");

        creatorNameLabel.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        creatorNameLabel.setText("name");

        jLabel3.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel3.setText("Дата создания:");

        jLabel4.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel4.setText("Дата завершения:");

        creatingData.setEnabled(false);
        creatingData.setBehavior(datechooser.model.multiple.MultyModelBehavior.SELECT_SINGLE);
        creatingData.addCommitListener(new datechooser.events.CommitListener() {
            public void onCommit(datechooser.events.CommitEvent evt) {
                creatingDataOnCommit(evt);
            }
        });

        closeData.setEnabled(false);
        closeData.setBehavior(datechooser.model.multiple.MultyModelBehavior.SELECT_SINGLE);
        closeData.addCommitListener(new datechooser.events.CommitListener() {
            public void onCommit(datechooser.events.CommitEvent evt) {
                closeDataOnCommit(evt);
            }
        });

        closeCheck.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        closeCheck.setText("Завершенный");
        closeCheck.setEnabled(false);
        closeCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeCheckActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel1.setText("Название: ");

        jLabel5.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel5.setText("Участники:");

        jLabel6.setText("Действия:");

        saveProjectButton.setText("Сохранить");
        saveProjectButton.setEnabled(false);
        saveProjectButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveProjectButtonActionPerformed(evt);
            }
        });

        cancelProjectButton.setText("Отмена");
        cancelProjectButton.setEnabled(false);
        cancelProjectButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelProjectButtonActionPerformed(evt);
            }
        });

        userjList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        jScrollPane5.setViewportView(userjList);

        addUserButton.setText("Добавить");
        addUserButton.setEnabled(false);
        addUserButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addUserButtonActionPerformed(evt);
            }
        });

        deleteUserButton.setText("Удалить");
        deleteUserButton.setEnabled(false);
        deleteUserButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteUserButtonActionPerformed(evt);
            }
        });

        actionjList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        jScrollPane2.setViewportView(actionjList);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(creatingData, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(closeData, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(creatorNameLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(nameTextField))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(cancelProjectButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(saveProjectButton))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel4))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addGap(79, 79, 79))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 242, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(addUserButton, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(deleteUserButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 141, Short.MAX_VALUE)
                                .addComponent(closeCheck))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                .addGap(108, 108, 108)))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(creatorNameLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(nameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(creatingData, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(closeData, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(jLabel5))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(closeCheck)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 358, Short.MAX_VALUE)
                    .addComponent(jScrollPane5)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(addUserButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(deleteUserButton)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(saveProjectButton)
                    .addComponent(cancelProjectButton))
                .addContainerGap())
        );

        CloseButton.setText("OK");
        CloseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CloseButtonActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel7.setText("Проекты:");

        jButton1.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jButton1.setText("-");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jButton2.setText("+");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(94, 94, 94)
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(CloseButton, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton1)
                            .addComponent(jButton2)
                            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(CloseButton)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void CloseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CloseButtonActionPerformed
        em.close();
        emf.close();
        dispose();
    }//GEN-LAST:event_CloseButtonActionPerformed

    private void addUserButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addUserButtonActionPerformed
        CriteriaBuilder builder=em.getCriteriaBuilder();
        CriteriaQuery query=builder.createQuery(User.class);
        Root<User> root=query.from(User.class);
        query.select(root);
        query.where(builder.equal(root.get(User_.rights), 3));
        List<User> userList=em.createQuery(query).getResultList();
        userList.removeAll(project.getUserList());
        addUserDialog dialog = new addUserDialog(new javax.swing.JFrame(), true,userList,this);
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }//GEN-LAST:event_addUserButtonActionPerformed

    private void cancelProjectButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelProjectButtonActionPerformed
        selectProject();
    }//GEN-LAST:event_cancelProjectButtonActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        CriteriaBuilder builder=em.getCriteriaBuilder();
        CriteriaQuery query=builder.createQuery(User.class);
        Root<User> root=query.from(User.class);
        query.select(root);
        query.where(builder.equal(root.get(User_.rights), 3));
        List<User> userList=em.createQuery(query).getResultList();
        NewProjectDialog dialog = new NewProjectDialog(new javax.swing.JFrame(), true,userList,this);
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if(projectjList.getSelectedIndex()!=-1){
            int position=0;
            if(projectjList.getSelectedIndex()>0&&projectjList.getSelectedIndex()<projectList.size()-1)
                position=projectjList.getSelectedIndex();
            em.getTransaction().begin();
            Project forDelete=em.createNamedQuery("Project.findById",Project.class).setParameter("id", project.getId()).getSingleResult();
            forDelete.setUserList(new ArrayList<User>());
            em.remove(forDelete);
            em.getTransaction().commit();
            
            projectList.remove(project);
            
            projectjList.setModel(initProjectsModel());
            projectjList.setSelectedIndex(position);
        }else
            JOptionPane.showMessageDialog(this, "Укажите проект, который хотите удалить!", "Проект не выбран", JOptionPane.PLAIN_MESSAGE);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void deleteUserButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteUserButtonActionPerformed
        if(userjList.getSelectedIndex()!=-1){
            project.getUserList().remove(userjList.getSelectedIndex());
            userjList.setModel(initUsersModel());
        }else
            JOptionPane.showMessageDialog(this, "Укажите учасника, которого хотите удалить!", "Учасник проекта не выбран", JOptionPane.PLAIN_MESSAGE);
        checkSaveAndCancelBuuton();
    }//GEN-LAST:event_deleteUserButtonActionPerformed

    private void closeCheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeCheckActionPerformed
        System.out.println(evt);
        if(closeCheck.isSelected())
            project.setStopTime(closeData.getCurrent().getTime());
        else
            project.setStopTime(null);
        checkSaveAndCancelBuuton();
    }//GEN-LAST:event_closeCheckActionPerformed
    
    private void closeDataOnCommit(datechooser.events.CommitEvent evt) {//GEN-FIRST:event_closeDataOnCommit
        closeCheckActionPerformed(null);
    }//GEN-LAST:event_closeDataOnCommit

    private void creatingDataOnCommit(datechooser.events.CommitEvent evt) {//GEN-FIRST:event_creatingDataOnCommit
        project.setStartTime(creatingData.getCurrent().getTime());
        checkSaveAndCancelBuuton();
    }//GEN-LAST:event_creatingDataOnCommit

    private void saveProjectButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveProjectButtonActionPerformed
        em.getTransaction().begin();
        em.merge(project);
        em.getTransaction().commit();
        
        int index=projectjList.getSelectedIndex();
        projectList.remove(index);
        projectList.add(index, project);
        
        projectjList.setModel(initProjectsModel());
        projectjList.setSelectedIndex(index);
    }//GEN-LAST:event_saveProjectButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton CloseButton;
    private javax.swing.JList<String> actionjList;
    private javax.swing.JButton addUserButton;
    private javax.swing.JButton cancelProjectButton;
    private javax.swing.JCheckBox closeCheck;
    private datechooser.beans.DateChooserCombo closeData;
    private datechooser.beans.DateChooserCombo creatingData;
    private javax.swing.JLabel creatorNameLabel;
    private javax.swing.JButton deleteUserButton;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTextField nameTextField;
    private javax.swing.JList<String> projectjList;
    private javax.swing.JButton saveProjectButton;
    private javax.swing.JList<String> userjList;
    // End of variables declaration//GEN-END:variables

    @Override
    public void dispose() {
        super.dispose(); //To change body of generated methods, choose Tools | Templates.
    }
    
    private void setAllButtons(boolean enable){
        nameTextField.setEnabled(enable);
        creatingData.setEnabled(enable);
        closeData.setEnabled(enable);
        closeCheck.setEnabled(enable);
        addUserButton.setEnabled(enable);
        cancelProjectButton.setEnabled(enable);
        saveProjectButton.setEnabled(enable);
        
        if(Values.user.getRights()!=1)
            deleteUserButton.setEnabled(false);
        else
            deleteUserButton.setEnabled(enable);
    }

    @Override
    public void onSelectedUser(User user) {
        project.getUserList().add(user);
        setDataToViews();
        checkSaveAndCancelBuuton();
    }

    @Override
    public void addNewproject(Project project) {
        int position=-1;
        if(projectjList.getSelectedIndex()!=-1)
            position=projectjList.getSelectedIndex();
        
        em.getTransaction().begin();
        em.merge(project);
        em.getTransaction().commit();
        
        projectList.add(project);
        
        projectjList.setModel(initProjectsModel());
        
        if(position!=-1)
            projectjList.setSelectedIndex(position);
    }
    
    private void checkSaveAndCancelBuuton(){
        /**System.out.println((!project.equals(selectedProject))+" "+(!project.getName().equals(selectedProject.getName()))+
                " "+(project.getStartTime().getTime()!=selectedProject.getStartTime().getTime())+" "+
                (!project.getUserList().equals(selectedProject.getUserList()))+" "+((project.getStopTime() == null && selectedProject.getStopTime() != null) || (project.getStopTime() != null && !project.getStopTime().equals(selectedProject.getStopTime()))));
        System.out.println(project.getStartTime().getTime()+" "+selectedProject.getStartTime().getTime());*/
        if((!project.equals(selectedProject))||(!project.getName().equals(selectedProject.getName()))||(!project.getStartTime().equals(selectedProject.getStartTime()))||
                (!project.getUserList().equals(selectedProject.getUserList()))||((project.getStopTime() == null && selectedProject.getStopTime() != null) || (project.getStopTime() != null && !project.getStopTime().equals(selectedProject.getStopTime())))){
            cancelProjectButton.setEnabled(true);
            saveProjectButton.setEnabled(true);
        }else{
            cancelProjectButton.setEnabled(false);
            saveProjectButton.setEnabled(false);
        }
    }
    
}
