package com.wrist_trainer.bivsa.wristtrainer.Utils;

import android.content.Context;
import android.support.annotation.Nullable;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;


public class ReadWriteObject {

    public static void writeToContext(Context context, String key, Object object){
        try{
            try {
                ArrayList objects = readFromContext(context, key);
                FileOutputStream fos = context.openFileOutput(key, Context.MODE_PRIVATE);
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                objects.add(object);
                oos.writeObject(objects);
                oos.close();
                fos.close();
            }catch (FileNotFoundException e){
                context.openFileOutput(key, Context.MODE_PRIVATE).close();
            }
        }
        catch (Exception e){
            System.err.println(e.getMessage());
        }

    }

    public static ArrayList readFromContext(Context context, String key){
        ArrayList objects = null;
        try {
            FileInputStream fis = context.openFileInput(key);
            ObjectInputStream ois = new ObjectInputStream(fis);
            objects = (ArrayList) ois.readObject();
        }catch (IOException | ClassNotFoundException | ClassCastException e){
            objects = new ArrayList();
            System.err.println(e.getMessage());
        }

        return objects == null ? new ArrayList() : objects;
    }
}
