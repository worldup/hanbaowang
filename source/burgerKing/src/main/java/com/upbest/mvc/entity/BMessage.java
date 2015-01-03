package com.upbest.mvc.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name = "bk_message")
public class BMessage implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    @GeneratedValue
    private Integer id;

    @Column(name = "push_content")
    @Size(max = 1000)
    private String pushContent;

    @Column(name = "push_title")
    @Size(max = 1000)
    private String pushTitle;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "state")
    private String state;

    @Column(name = "user_id")
    private Integer userId;
    
    @Column(name = "push_time")
    private Date pushTime;
    
    @Column(name = "sender_id")
    private Integer senderId;
    
    @Column(name = "receiver_id")
    private Integer receiverId;
    
    @Column(name = "is_read")
    private String isRead;
    
    @Column(name = "is_push_imme")
    private String isPushImme;

    @Column(name = "task_id")
    private Integer taskId;
    
    @Column(name = "message_type")
    private String messageType;
    
    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPushContent() {
        return pushContent;
    }

    public void setPushContent(String pushContent) {
        this.pushContent = pushContent;
    }

    public String getPushTitle() {
        return pushTitle;
    }

    public void setPushTitle(String pushTitle) {
        this.pushTitle = pushTitle;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getPushTime() {
        return pushTime;
    }

    public void setPushTime(Date pushTime) {
        this.pushTime = pushTime;
    }

    public Integer getSenderId() {
        return senderId;
    }

    public void setSenderId(Integer senderId) {
        this.senderId = senderId;
    }

    public Integer getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(Integer receiverId) {
        this.receiverId = receiverId;
    }

    public String getIsRead() {
        return isRead;
    }

    public void setIsRead(String isRead) {
        this.isRead = isRead;
    }

    public String getIsPushImme() {
        return isPushImme;
    }

    public void setIsPushImme(String isPushImme) {
        this.isPushImme = isPushImme;
    }

    @Override
    public String toString() {
        return "BMessage [id=" + id + ", pushContent=" + pushContent + ", pushTitle=" + pushTitle + ", createTime=" + createTime + ", state=" + state + ", userId=" + userId + ", pushTime=" + pushTime
                + ", senderId=" + senderId + ", receiverId=" + receiverId + ", isRead=" + isRead + ", isPushImme=" + isPushImme + ", taskId=" + taskId + ", messageType=" + messageType + "]";
    }

}
