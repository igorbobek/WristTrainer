package com.wrist_trainer.bivsa.wristtrainer.Components;

import android.content.Context;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.CountDownTimer;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.method.KeyListener;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;

import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.wrist_trainer.bivsa.wristtrainer.R;
import com.wrist_trainer.bivsa.wristtrainer.TaskModel;
import com.wrist_trainer.bivsa.wristtrainer.Tasks.TaskRotateByZ;
import com.wrist_trainer.bivsa.wristtrainer.Utils.ReadWriteObject;
import com.wrist_trainer.bivsa.wristtrainer.Utils.TaskResult;


import java.text.SimpleDateFormat;


public class ResultFragment extends Fragment implements EditText.OnFocusChangeListener{



    private EditText commentContainer;
    private TextView beginTimeText;
    private TextView durationText;
    private TextView rotateCountText;
    private Button continueButton;
    private View fragment;
    private CountDownTimer timer;
    TaskModel task;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragment = inflater.inflate(R.layout.fragment_result, container, false);

        Bundle bundle = this.getArguments();
        task = (TaskModel) bundle.getSerializable("task");
        bundle.clear();
        initElements();
        if(task instanceof TaskRotateByZ){
            TaskRotateByZ resultTask = (TaskRotateByZ) task;
            rotateCountText.setText(String.valueOf(resultTask.getRotateCount()));
            durationText.setText(String.valueOf(resultTask.getDuration()));
            beginTimeText.setText(String.valueOf(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(resultTask.getBeginTime())));
        }
        return fragment;
    }


    private void initElements(){
        rotateCountText = fragment.findViewById(R.id.resultRotateCount);
        beginTimeText = fragment.findViewById(R.id.beginTime);
        durationText = fragment.findViewById(R.id.duration);
        commentContainer = fragment.findViewById(R.id.commentContainer);
        continueButton = fragment.findViewById(R.id.Continue);

        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timer.cancel();
                saveResult();
            }
        });

        commentContainer.setOnFocusChangeListener(this);

        timer = new CountDownTimer(6000, 1000) {
            private int tickTimes = 5;
            @Override
            public void onTick(long l) {
                continueButton.setText(String.format("Continue in %d", tickTimes));
                tickTimes--;
            }

            @Override
            public void onFinish() {
                saveResult();
            }
        };
        timer.start();

    }

    private void closeResultView(){
        if (getFragmentManager().getBackStackEntryCount() != 0){
            getFragmentManager().popBackStack();
            getFragmentManager().beginTransaction().remove(this).commit();
        }
    }


    private void saveResult(){
        ReadWriteObject.writeToContext(getContext(), "history", new TaskResult(task, commentContainer.getText().toString()));
        closeResultView();
    }

    @Override
    public void onFocusChange(View view, boolean b) {
        if(b){
            timer.cancel();
            System.out.println("FOCUS");
        }else{
            System.out.println("UNFOCUS");
        }
    }
}
