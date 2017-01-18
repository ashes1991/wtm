/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kovtun.WorkTimeMap.Models.db;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author kovtun
 */
@Entity
@Table(name = "work_action")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Action.findAll", query = "SELECT a FROM Action a"),
    @NamedQuery(name = "Action.findById", query = "SELECT a FROM Action a WHERE a.id = :id"),
    @NamedQuery(name = "Action.findByName", query = "SELECT a FROM Action a WHERE a.name = :name"),
    @NamedQuery(name = "Action.findByDescription", query = "SELECT a FROM Action a WHERE a.description = :description"),
    @NamedQuery(name = "Action.findByComment", query = "SELECT a FROM Action a WHERE a.comment = :comment"),
    @NamedQuery(name = "Action.findByStartTimeAndUser", query = "SELECT a FROM Action a JOIN a.userId u WHERE u.id=:id AND FUNC('YEAR',a.startTime)=:year "
                        + "AND FUNC('MONTH',a.startTime)=:month AND FUNC('DAY',a.startTime)=:day"),
    @NamedQuery(name = "Action.findByStartTimeAndUserANDProject", query = "SELECT a FROM Action a JOIN a.userId u JOIN a.projectId p WHERE u.id=:id AND p.id=:idp AND FUNC('YEAR',a.startTime)=:year "
                        + "AND FUNC('MONTH',a.startTime)=:month AND FUNC('DAY',a.startTime)=:day"),
    @NamedQuery(name = "Action.findByUserId", query = "SELECT a FROM Action a JOIN a.userId u WHERE u.id = :id ORDER BY a.startTime DESC"),
    @NamedQuery(name = "Action.findByUserIdAndStopTimeNull", query = "SELECT a FROM Action a JOIN a.userId u WHERE u.id = :id AND a.stopTime IS NULL ORDER BY a.startTime DESC"),
    @NamedQuery(name = "Action.findByUserIdAndStopTimeNotNull", query = "SELECT a FROM Action a JOIN a.userId u WHERE u.id = :id AND a.stopTime IS NOT NULL ORDER BY a.startTime DESC"),
    @NamedQuery(name = "Action.findByUserIdAndStartTime", query = "SELECT a FROM Action a JOIN a.userId u WHERE u.id = :id AND a.startTime>:fromTime AND a.startTime<:toTime ORDER BY a.startTime DESC"),
    @NamedQuery(name = "Action.findByUserIdAndProject", query = "SELECT a FROM Action a JOIN a.userId u JOIN a.projectId p WHERE u.id = :idu AND p.id=:idp ORDER BY a.startTime DESC"),
    @NamedQuery(name = "Action.findByStopTime", query = "SELECT a FROM Action a WHERE a.stopTime = :stopTime")})
public class Action implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @Column(name = "description")
    private String description;
    @Column(name = "comment")
    private String comment;
    @Basic(optional = false)
    @Column(name = "start_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startTime;
    @Column(name = "stop_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date stopTime;
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private User userId;
    @JoinColumn(name = "project_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Project projectId;

    public Action() {
    }

    public Action(Integer id) {
        this.id = id;
    }

    public Action(Integer id, String name, String description, Date startTime) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.startTime = startTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getStopTime() {
        return stopTime;
    }

    public void setStopTime(Date stopTime) {
        this.stopTime = stopTime;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public Project getProjectId() {
        return projectId;
    }

    public void setProjectId(Project projectId) {
        this.projectId = projectId;
    }
    public String getStringDate(){
        SimpleDateFormat sdf=new SimpleDateFormat("dd.MM.yy HH:mm");
        return  "С:"+sdf.format(startTime)+" По:"+
        (stopTime!=null?sdf.format(stopTime):"Еще не закончино!");
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Action)) {
            return false;
        }
        Action other = (Action) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return name+" ("+new SimpleDateFormat("dd.MM.yy").format(startTime)+": "+
                (stopTime==null?"Не завершено!":((stopTime.getTime()-startTime.getTime())/(1000*60))/60+" ч. "+((stopTime.getTime()-startTime.getTime())/(1000*60)%60+" м."))+")";
    }
    
}
