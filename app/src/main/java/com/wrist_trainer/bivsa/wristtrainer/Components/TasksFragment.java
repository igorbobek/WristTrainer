package com.wrist_trainer.bivsa.wristtrainer.Components;


import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.wrist_trainer.bivsa.wristtrainer.Adapter.TaskList;
import com.wrist_trainer.bivsa.wristtrainer.R;
import com.wrist_trainer.bivsa.wristtrainer.TaskModel;
import com.wrist_trainer.bivsa.wristtrainer.Tasks.TaskRotateByZ;
import com.wrist_trainer.bivsa.wristtrainer.Utils.OnTaskSelectedListener;

import java.util.ArrayList;

public class TasksFragment extends Fragment implements OnTaskSelectedListener{

    private ArrayList<TaskModel> taskList = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View fragment = inflater.inflate(R.layout.activity_test_fragment, container,false);
        ListView listView = fragment.findViewById(R.id.taskList);
        taskList = new ArrayList<>();
        taskList.add(new TaskRotateByZ("Easy", 20, 100, 1));
        taskList.add(new TaskRotateByZ("Normal", 50, 180,2));
        taskList.add(new TaskRotateByZ("Extreme",100, 220,3));
        listView.setAdapter(new TaskList(taskList, fragment.getContext(), this));
        return fragment;
    }

    @Override
    public void onTaskSelected(Integer position) {
        if (getActivity().findViewById(R.id.container) != null) {

            Bundle bundle = new Bundle();
            bundle.putSerializable("task", taskList.get(position));

            TrainingFragment firstFragment = new TrainingFragment();

            firstFragment.setArguments(bundle);

            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, firstFragment).addToBackStack(null).commit();
        }
        System.out.println(position);
    }

}
