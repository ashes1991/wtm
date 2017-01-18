/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kovtun.WorkTimeMap.UI;

import com.kovtun.WorkTimeMap.Constants;
import com.kovtun.WorkTimeMap.Models.db.User;
import com.kovtun.WorkTimeMap.Settings;
import com.kovtun.WorkTimeMap.Values;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.JOptionPane;

/**
 *
 * @author kovtun
 */
public class LogIn extends javax.swing.JFrame {
    private static final String ADMIN_LOGIN="admin";
    private static final String ADMIN_PASSWORD="admin";
    private String UserLog=null;
    private String UserPass=null;
    private EntityManagerFactory emf;
    /**
     * Creates new form LogIn
     */
    public LogIn() {
        initComponents();
        setKeyListener();
        getUserData();
        if(isUserSaved()){
            textField1.setText(UserLog);
            passwordField1.setText(UserPass);
        }else {

        }
        try {
            Values.repeatTime=Integer.valueOf(new Settings().getRepeatTime());
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, Values.IOException,"Ошибка", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        textField1 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        passwordField1 = new javax.swing.JPasswordField();
        rememberCheckBox = new javax.swing.JCheckBox();
        cancelButton = new javax.swing.JButton();
        singInButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("LogIn");
        setMaximumSize(new java.awt.Dimension(400, 300));

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N

        jLabel1.setText("Логин:");
        jLabel1.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N

        textField1.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N

        jLabel2.setText("Пароль:");
        jLabel2.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N

        passwordField1.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N

        rememberCheckBox.setText("Запомнить");
        rememberCheckBox.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N

        cancelButton.setText("Отмана");
        cancelButton.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        singInButton.setText("ОК");
        singInButton.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        singInButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                singInButtonActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout jPanel1Layout = new org.jdesktop.layout.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1Layout.createSequentialGroup()
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel1Layout.createSequentialGroup()
                        .add(12, 12, 12)
                        .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jLabel1)
                            .add(jLabel2)
                            .add(rememberCheckBox)))
                    .add(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jPanel1Layout.createSequentialGroup()
                                .add(cancelButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 130, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(singInButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 130, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                            .add(passwordField1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 266, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(textField1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 266, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .add(jLabel1)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(textField1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 35, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(jLabel2)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(passwordField1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 35, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(rememberCheckBox)
                .add(18, 18, 18)
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(cancelButton)
                    .add(singInButton))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, java.awt.BorderLayout.PAGE_START);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void singInButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_singInButtonActionPerformed
        SingIn();
    }//GEN-LAST:event_singInButtonActionPerformed

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        System.exit(0);
    }//GEN-LAST:event_cancelButtonActionPerformed

private void setKeyListener(){
        textField1.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getExtendedKeyCode()==KeyEvent.VK_ENTER){
                    SingIn();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
        passwordField1.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getExtendedKeyCode()==KeyEvent.VK_ENTER){
                    SingIn();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
        rememberCheckBox.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getExtendedKeyCode()==KeyEvent.VK_ENTER){
                    SingIn();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
        cancelButton.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getExtendedKeyCode()==KeyEvent.VK_ENTER){
                    SingIn();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
        singInButton.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getExtendedKeyCode()==KeyEvent.VK_ENTER){
                    SingIn();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

    }

private boolean CheckString(String data,String spec){
        Pattern p = null;
        if (spec.equals(Constants.USER_LOGIN)){
            p=Pattern.compile("^[A-Za-z0-9_-]{4,20}$");
        }else if (spec.equals(Constants.USER_PASSWORD)){
            p=Pattern.compile("^[A-Za-z0-9_-]{4,15}$");
        }
        Matcher m = p.matcher(data);
        return m.matches();
    }

private boolean isUserSaved(){
        return (!UserLog.isEmpty()&&!UserPass.isEmpty());
    }

private void getUserData(){
        try {
            Settings settings=new Settings();
            UserLog=settings.getUserLogin();
            UserPass=settings.getUserPassword();
        } catch (IOException e1) {
            JOptionPane.showMessageDialog(this, Values.IOException,"Ошибка", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void saveUserData(){

        try {
            Settings settings = new Settings();
            settings.setUserLogin(textField1.getText());
            settings.setUserPassword(passwordField1.getText());
        } catch (IOException e1) {
            JOptionPane.showMessageDialog(this, Values.IOException,"Ошибка", JOptionPane.ERROR_MESSAGE);
        }
    }    

private void startHeaderFrame(){
        
    HeaderForm headerForm=new HeaderForm(this);
    headerForm.setVisible(true);
    headerForm.setLocationRelativeTo(null);
            
    this.setVisible(false);
}

private void startMainForm(){
    MainForm mainForm=new MainForm(this);
    mainForm.setVisible(true);
    mainForm.setLocationRelativeTo(null);
    this.setVisible(false);
}

private void SingIn(){
    /**
     * @NamedQuery(name = "Project.findByUserId", query = "SELECT p FROM Project p WHERE p.creatorId.id=:id")})
    //SELECT p FROM Project p JOIN p.creatorId u JOIN p.userList u1 WHERE u.id= :id AND u1.id=:id1
     */
    emf=Persistence.createEntityManagerFactory("Work_Time_MapPU");
    EntityManager em=emf.createEntityManager();
        if (!textField1.getText().isEmpty()){
            if(textField1.getText().equals(ADMIN_LOGIN)){
                if (!String.copyValueOf(passwordField1.getPassword()).isEmpty()){
                    if(String.copyValueOf(passwordField1.getPassword()).equals(ADMIN_PASSWORD)){
                        /**
                         *
                         * Логин под админом
                         *
                         */
                        Values.user=new User(0,"admin",ADMIN_LOGIN,ADMIN_PASSWORD,1);
                        startHeaderFrame();
                    }else JOptionPane.showMessageDialog(this, "Вы ввели неправельный пароль","Ошибка", JOptionPane.ERROR_MESSAGE);
                }else JOptionPane.showMessageDialog(this, "Введите пароль","Ошибка", JOptionPane.ERROR_MESSAGE);
            }else{
                if(CheckString(textField1.getText(),Constants.USER_LOGIN)){
                    List<User> userList=em.createNamedQuery("User.findByLogin", User.class).setParameter("login", textField1.getText()).getResultList();
                    if(userList.size()>0){
                        if (CheckString(passwordField1.getText(),Constants.USER_PASSWORD)){
                            for(User u:userList){
                                if (passwordField1.getText().equals(u.getPassword())){
                                    Values.user=u;
                                    switch (u.getRights()){
                                        case 1:
                                            break;
                                        case 2:
                                            startHeaderFrame();
                                            
                                            break;
                                        case 3:
                                            startMainForm();
                                            break;
                                        default:
                                            break;
                                    }
                                    if (rememberCheckBox.isSelected()){
                                        saveUserData();
                                    }
                                    this.setVisible(false);
                                    em.close();
                                    emf.close();
                                    return;
                                }
                            }
                            JOptionPane.showMessageDialog(this, "Вы ввели не правельный пароль","Ошибка", JOptionPane.PLAIN_MESSAGE);
                        }else {
                            if (textField1.getText().length()>4&&textField1.getText().length()<21){
                                JOptionPane.showMessageDialog(this, "Пароль должен содержать от 5 до 20 стмволов","Ошибка", JOptionPane.PLAIN_MESSAGE);
                            }else {
                                JOptionPane.showMessageDialog(this, "В пароль имеются некоректные символы","Ошибка", JOptionPane.PLAIN_MESSAGE);
                            }
                        }
                    }else JOptionPane.showMessageDialog(this, "Пользователь не найден","Ошибка", JOptionPane.PLAIN_MESSAGE);
                }else {
                    if (textField1.getText().length()>4&&textField1.getText().length()<21){
                        JOptionPane.showMessageDialog(this, "Логин должен содержать от 5 до 20 стмволов","Ошибка", JOptionPane.PLAIN_MESSAGE);
                    }else {
                        JOptionPane.showMessageDialog(this,"В логине имеются некоректные символы","Ошибка", JOptionPane.PLAIN_MESSAGE);
                    }
                }
            }
        }else {
            JOptionPane.showMessageDialog(this, "Неправельный логин","Ошибка", JOptionPane.PLAIN_MESSAGE);
        }
        
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancelButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPasswordField passwordField1;
    private javax.swing.JCheckBox rememberCheckBox;
    private javax.swing.JButton singInButton;
    private javax.swing.JTextField textField1;
    // End of variables declaration//GEN-END:variables
}
