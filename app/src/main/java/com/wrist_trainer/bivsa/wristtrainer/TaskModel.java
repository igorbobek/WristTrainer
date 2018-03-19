package com.wrist_trainer.bivsa.wristtrainer;


import com.wrist_trainer.bivsa.wristtrainer.Utils.TrainingConnector;

import java.io.Serializable;

import java.util.Date;



public abstract class TaskModel implements Serializable{

    protected float startX;
    protected float startY;
    protected float startZ;

    protected Date beginTime;
    protected Date endTime;

    protected transient TrainingConnector callBack;

    private String name;


    public TaskModel(String name){
        this.name = name;
    }

    public void initCoordinates(float x, float y, float z){
        startX = x;
        startY = y;
        startZ = z;
    }

    public Date getBeginTime(){
        return beginTime;
    }

    public String getName() {
        return name;
    }


    public void setCallBack(TrainingConnector callBack){
        this.callBack = callBack;
    }

    public void rotate(float x, float y, float z) {

    }

    public void initBeginTime(){
        beginTime = new Date();
    }

    public Date getEndTime(){
        return endTime;
    }

    public int getDuration(){
        return 0;
    }

}
