package com.wrist_trainer.bivsa.wristtrainer.Utils;



public interface TrainingConnector {
    void stopTraining(boolean flag);
    void changeFragmentColor(int color);
    void setRotateCount(int curCount, int needCount);
    void changeCoordinates(float z);
}
