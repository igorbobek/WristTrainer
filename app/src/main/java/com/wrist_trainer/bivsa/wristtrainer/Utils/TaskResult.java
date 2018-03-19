package com.wrist_trainer.bivsa.wristtrainer.Utils;

import com.wrist_trainer.bivsa.wristtrainer.TaskModel;

import java.io.Serializable;


public class TaskResult implements Serializable {

    private TaskModel task;
    private String comment;

    public TaskResult(TaskModel task, String comment){
        this.comment = comment;
        this.task = task;
    }

    public TaskModel getTask() {
        return task;
    }

    public String getComment() {
        return comment;
    }
}
