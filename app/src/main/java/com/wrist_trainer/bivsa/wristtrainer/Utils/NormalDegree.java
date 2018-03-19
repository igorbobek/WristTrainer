package com.wrist_trainer.bivsa.wristtrainer.Utils;


public class NormalDegree {

    public static Float getNormalZ(float z) {
        if (z >= 0 && z <= 90){
            z = 90 - z;
            return z;
        }else if (z < 0 && z >= -180){
            z = 90 + z*(-1);
            return z;
        }else if (z > 90 && z <= 180){
            z = 359.9f - (z - 90);
            return z;
        }

        System.err.println(z);
        return null;
    }


}
