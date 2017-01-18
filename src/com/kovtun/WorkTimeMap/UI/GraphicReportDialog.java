/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kovtun.WorkTimeMap.UI;

import com.kovtun.WorkTimeMap.Models.db.Action;
import com.kovtun.WorkTimeMap.Models.other.GraphicModel;
import com.kovtun.WorkTimeMap.Models.other.HeaderLabel;
import com.kovtun.WorkTimeMap.Models.other.SubLabel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Random;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author kovtun
 */
public class GraphicReportDialog extends javax.swing.JDialog {
    private GraphicModel model;
    /**
     * Creates new form GraphicReportDialog
     */
    public GraphicReportDialog(java.awt.Frame parent, boolean modal,GraphicModel model) {
        super(parent, modal);
        this.model=model;
        initComponents();
        
        
    }

    private JPanel paintGraphic(GraphicModel model) {
        if (model.getListHeaderLabel().size()<1){
            return null;
        }
        JPanel allPanel=new JPanel();
        allPanel.setLayout(new BoxLayout(allPanel, BoxLayout.Y_AXIS));

        int width=950;
        int labelH=30;
        int cellsCount=model.getCellList().size();
        int perfSize=0;
        int panelColorNumb=0;
        boolean isFirst=true;
        int cellWidth=0;
        if(cellsCount<=30){
            cellWidth=750/cellsCount;
        }else{
            cellWidth=24;
            width=(cellWidth*cellsCount)+180;
        }
        SimpleDateFormat sdf=new SimpleDateFormat("dd.MM");
        SimpleDateFormat sdf1=new SimpleDateFormat("dd.MM:yyyy");
        
        width=width+(cellsCount*2)+20;
        for (HeaderLabel data:model.getListHeaderLabel()){
            int height=30;
            
            JPanel panel=new JPanel();
            panel.setLayout(null);

            JLabel head=new JLabel(data.getLabelData());
            head.setToolTipText(data.getLabelData());
            head.setBounds(10,height,120,labelH);
            panel.add(head);
            for (int i=0;i<cellsCount;i++){
                
                    JLabel dLabel=new JLabel(sdf.format(data.getCellList().get(i).getData().getTime()));
                    dLabel.setBounds(120+(i*(cellWidth+2)),5,cellWidth,labelH);
                    if (cellsCount>20) {
                        Font f = new Font("Verdana", Font.PLAIN, (int) (cellWidth / 2.7f));
                        dLabel.setFont(f);
                    }
                    dLabel.setHorizontalAlignment(JLabel.CENTER);
                    dLabel.setToolTipText(sdf1.format(data.getCellList().get(i).getData().getTime()));
                    panel.add(dLabel);

                
                
                if (data.getCellList().get(i).isVisible()){
                    JLabel label=new JLabel();
                    String text="";
                    final List<Action> actionList=data.getCellList().get(i).getActions();
                    
                    for (int index=0;index<actionList.size();index++){
                        if(index==(actionList.size()-1))
                            text+=actionList.get(index).getName()+".";
                        else
                            text+=actionList.get(index).getName()+",\n";
                    }

                    label.setToolTipText(text);
                    label.setBounds(120+(i*(cellWidth+2)),height,cellWidth,labelH);
                    label.setBackground(Color.GREEN);
                    label.setOpaque(true);
                    label.addMouseListener(new MouseListener() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            ActionsDialog dialog = new ActionsDialog(new javax.swing.JFrame(), true,actionList);
                            dialog.setLocationRelativeTo(null);
                            dialog.setVisible(true);
                        }

                        @Override
                        public void mousePressed(MouseEvent e) {

                        }

                        @Override
                        public void mouseReleased(MouseEvent e) {

                        }

                        @Override
                        public void mouseEntered(MouseEvent e) {

                        }

                        @Override
                        public void mouseExited(MouseEvent e) {

                        }
                        
                    });
                    panel.add(label);
                }

            }
            //isFirst=false;
            JLabel lt=new JLabel(data.getTimeInMin()/60+" ч. "+data.getTimeInMin()%60+" м.");
            lt.setBounds(width-60,height,80,labelH);
            panel.add(lt);
            height=height+labelH+10;

            for (SubLabel lg:data.getSublabelList()){
                JLabel label=new JLabel(lg.getLabelData());
                label.setBounds(20,height,110,labelH);
                panel.add(label);
                Color subColor=new Color(new Random().nextInt());
                for (int i=0;i<cellsCount;i++){
                    if (lg.getCellList().get(i).isVisible()){
                        JLabel lab=new JLabel();
                        String text="";
                        final List<Action> actionList=lg.getCellList().get(i).getActions();
                    
                        for (int index=0;index<actionList.size();index++){
                            if(index==(actionList.size()-1))
                                text+=actionList.get(index).getName()+".";
                            else
                                text+=actionList.get(index).getName()+",\n";
                        }
                        lab.setToolTipText(text);
                        lab.setBounds(120+(i*(cellWidth+2)),height,cellWidth,labelH);
                        lab.setBackground(subColor);
                        lab.setOpaque(true);
                        lab.addMouseListener(new MouseListener() {
                            @Override
                            public void mouseClicked(MouseEvent e) {
                                ActionsDialog dialog = new ActionsDialog(new javax.swing.JFrame(), true,actionList);
                                dialog.setLocationRelativeTo(null);
                                dialog.setVisible(true);
                            }

                            @Override
                            public void mousePressed(MouseEvent e) {

                            }

                            @Override
                            public void mouseReleased(MouseEvent e) {

                            }

                            @Override
                            public void mouseEntered(MouseEvent e) {

                            }

                            @Override
                            public void mouseExited(MouseEvent e) {

                            }
                        });
                        panel.add(lab);
                    }
                }
                JLabel lt1=new JLabel(lg.getTimeInMin()/60+" ч. "+lg.getTimeInMin()%60+" м.");
                lt1.setBounds(width-60,height,80,labelH);
                panel.add(lt1);
                height=height+labelH+10;
            }


            panel.setPreferredSize(new Dimension(width+20,height));
            if (panelColorNumb==0){
                panelColorNumb=1;
            }else {
                panel.setBackground(Color.LIGHT_GRAY);
                panelColorNumb=0;
            }

            allPanel.add(panel);
            perfSize+=height;
        }

        return allPanel;
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane(paintGraphic(model));

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jScrollPane1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1024, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 398, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
