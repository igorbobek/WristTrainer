package com.wrist_trainer.bivsa.wristtrainer.Components;

import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.wrist_trainer.bivsa.wristtrainer.R;
import com.wrist_trainer.bivsa.wristtrainer.TaskModel;
import com.wrist_trainer.bivsa.wristtrainer.Utils.NormalDegree;
import com.wrist_trainer.bivsa.wristtrainer.Utils.TrainingConnector;

public class TrainingFragment extends Fragment implements SensorEventListener, View.OnClickListener, TrainingConnector{

    boolean running = false;

    private float x;
    private float y;
    private float z;

    private ImageView cycle;

    private float gravity[];
    private float magnetic[];
    private float accels[] = new float[3];
    private float mags[] = new float[3];
    private float[] values = new float[3];
    private float cycleX;
    private  float cycleY;

    private TaskModel task;

    private static final int SENSOR_DELAY_MICROS = 16 * 1000;

    private SensorManager sensorManager;

    private TextView rotateCount;
    private View fragment;
    Button startButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        fragment = inflater.inflate(R.layout.training_fragment, container, false);
        Bundle bundle = this.getArguments();
        cycle = fragment.findViewById(R.id.indicator);
        TextView text = fragment.findViewById(R.id.taskName);
        task = (TaskModel) bundle.getSerializable("task");
        text.setText(String.format(task.toString()));
        startButton = fragment.findViewById(R.id.StartTraining);
        startButton.setOnClickListener(this);
        rotateCount = fragment.findViewById(R.id.resultRotateCount);
        sensorManager = (SensorManager)getActivity().getSystemService(Context.SENSOR_SERVICE);
        initSensor();
        bundle.clear();
        return fragment;
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (isResumed()) {
            switch (sensorEvent.sensor.getType()) {
                case Sensor.TYPE_MAGNETIC_FIELD:
                    mags = sensorEvent.values.clone();
                    break;
                case Sensor.TYPE_ACCELEROMETER:
                    accels = sensorEvent.values.clone();
                    break;
            }

            if (mags != null && accels != null) {
                gravity = new float[9];
                magnetic = new float[9];
                SensorManager.getRotationMatrix(gravity, magnetic, accels, mags);
                float[] outGravity = new float[9];
                SensorManager.remapCoordinateSystem(gravity, SensorManager.AXIS_X, SensorManager.AXIS_Z, outGravity);
                SensorManager.getOrientation(outGravity, values);
                x = values[0] * 57.2957795f;
                y = values[1] * 57.2957795f;
                z = values[2] * 57.2957795f;
                if (cycleX != 0 && cycleY != 0)changeCoordinates(z);
                if (running) task.rotate(x, y, z);
                mags = null;
                accels = null;
            }
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        cycle.getViewTreeObserver().addOnDrawListener(new ViewTreeObserver.OnDrawListener() {
            @Override
            public void onDraw() {
                cycleX = cycle.getX();
                cycleY = cycle.getY();
                cycle.getViewTreeObserver().removeOnDrawListener(this);
            }
        });
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    public void setRotateCount(int curCount, int needCount) {
        rotateCount.setText(String.format("%d from %d", curCount, needCount));
    }


    @Override
    public void changeFragmentColor(int color) {
        fragment.setBackgroundColor(color);
    }

    @Override
    public void changeCoordinates(float z) {
        cycle.setX(
                cycleX + (float) Math.cos(Math.toRadians(NormalDegree.getNormalZ(z)))
                    * startButton.getWidth()
        );
        cycle.setY(
                cycleY - (float) Math.sin(Math.toRadians(NormalDegree.getNormalZ(z)))
                    * startButton.getWidth()
        );
    }

    private void initBundleForTransaction(Bundle bundle){
        bundle.putSerializable("task", task);
    }

    @Override
    public void stopTraining(boolean flag) {
        if (flag){
            running = false;
            sensorManager.unregisterListener(this);

            Bundle bundle = new Bundle();

            initBundleForTransaction(bundle);

            ResultFragment resultFragment = new ResultFragment();

            resultFragment.setArguments(bundle);

            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, resultFragment).commit();
        }
        else{
            running = false;
            sensorManager.unregisterListener(this);
            startButton.setBackgroundColor(Color.argb(255,153,204,0));
            if(getFragmentManager().getBackStackEntryCount() != 0) {
                getFragmentManager().popBackStack();
            }
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.StartTraining:
                if (view.getTag() == null || (int)view.getTag() == 0) {
                    view.setTag(1);
                    startTraining();
                } else {
                    view.setTag(0);
                    stopTraining(false);
                }
                break;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }


    private void initSensor(){
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SENSOR_DELAY_MICROS);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD), SENSOR_DELAY_MICROS);
    }

    private void startTraining(){
        startButton.setBackgroundColor(Color.argb(255,200,0,0));
        task.initCoordinates(x,y,z);
        startButton.setText("Stop Training");
        task.setCallBack(this);
        task.initBeginTime();
        running = true;
    }

}
