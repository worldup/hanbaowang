package com.upbest.mvc.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by lili on 15-2-8.
 */
@Entity
@Table(name="bk_common_words")
public class BCommonWords  implements Serializable{
    @Id
    @Column(name = "id")
    @GeneratedValue
    private Integer id;
    @Column(name = "task_type_id")
    private Integer taskTypeId;
    @Column(name = "content")
    private String content;
    @Column(name = "create_time")
    private Date createTime;
    @Column(name = "create_user")
    private String createUser;
    @Column(name = "update_time")
    private Date updateTime;
    @Column(name = "update_user")
    private String updateUser;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTaskTypeId() {
        return taskTypeId;
    }

    public void setTaskTypeId(Integer taskTypeId) {
        this.taskTypeId = taskTypeId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }



    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }
}
