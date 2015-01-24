package com.upbest.mvc.vo;

import java.io.Serializable;

/**
 * Created by lili on 2015/1/24.
 */
public class CommonWordsVO implements Serializable {
    private String id;
    private String taskTypeId;
    private String content;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTaskTypeId() {
        return taskTypeId;
    }

    public void setTaskTypeId(String taskTypeId) {
        this.taskTypeId = taskTypeId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
