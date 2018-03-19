package com.wrist_trainer.bivsa.wristtrainer.Tasks;


import android.graphics.Color;
import android.os.CountDownTimer;


import com.wrist_trainer.bivsa.wristtrainer.TaskModel;

import java.io.Serializable;
import java.text.DateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;


public class TaskRotateByZ extends TaskModel implements Serializable{

    private int rotateCount;
    private Boolean flag = null;
    private int count = 0;
    private int degree;
    private float minGoalZ;
    private float maxGoalZ;
    private int mode;



    private transient  CountDownTimer  timer = new CountDownTimer(16000,1000) {

        private int lastCount = mode*-1;

        @Override
        public void onTick(long l) {
            if (count >= lastCount ){
                lastCount += mode;
                callBack.changeFragmentColor(Color.argb(255,0,255,0));
            }else{
                lastCount += mode;
                callBack.changeFragmentColor(Color.argb(255,155,0,0));
            }
            System.out.println(lastCount);
            System.out.println(count);
        }

        @Override
        public void onFinish() {
            timer.start();
        }
    };



    public TaskRotateByZ(String name, int count, int degree, int mode){
        super(name);
        this.mode = mode;
        this.degree = degree;
        this.rotateCount = count;
    }

    private void checkProgress(){
        if (count == rotateCount){
            endTime = new Date();
            timer.cancel();
            callBack.stopTraining(true);
        }
    }


    @Override
    public synchronized void rotate(float x, float y, float z) {
        checkProgress();
        try {
            if (flag == null) {
                if (z <= minGoalZ) {
                    flag = true;
                    timer.start();
                    callBack.setRotateCount(0,this.rotateCount);
                }
                if (z >= maxGoalZ) {
                    flag = false;
                    timer.start();
                    callBack.setRotateCount(0, this.rotateCount);
                }
                return;
            } else if (z <= minGoalZ && !flag) {
                flag = true;
                callBack.setRotateCount(++count, this.rotateCount);
            } else if (z >= maxGoalZ && flag) {
                flag = false;
                callBack.setRotateCount(++count, this.rotateCount);
            } else if (z >= maxGoalZ && !flag) {
                minGoalZ = Float.parseFloat(String.format("%.1f", z - degree));
                maxGoalZ = z;
            } else if (z <= minGoalZ && flag) {
                maxGoalZ = Float.parseFloat(String.format("%.1f", z + degree));
                minGoalZ = z;
            }
        }finally {
            return;
        }

    }

    public int getRotateCount(){
        return this.count;
    }

    @Override
    public int getDuration() {
        return (int)Math.abs(this.endTime.getTime() - this.beginTime.getTime()) / 1000;
    }

    @Override
    public void initCoordinates(float x, float y, float z) {
        super.initCoordinates(x, y, z);
        maxGoalZ = Float.parseFloat(String.format("%.1f", z + (float)degree / 2));
        minGoalZ = Float.parseFloat(String.format("%.1f", z - (float)degree / 2));
    }


    @Override
    public String toString() {
        return String.format("Rotate %d\u00B0 %d times", degree, rotateCount);
    }
}
