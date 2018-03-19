package com.wrist_trainer.bivsa.wristtrainer.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;


import com.wrist_trainer.bivsa.wristtrainer.R;
import com.wrist_trainer.bivsa.wristtrainer.TaskModel;
import com.wrist_trainer.bivsa.wristtrainer.Utils.TaskResult;

import java.text.SimpleDateFormat;
import java.util.ArrayList;


public class HistoryList extends ArrayAdapter<Object> {

    private ArrayList data;
    private Context context;

    public HistoryList(ArrayList<Object> data, Context context) {
        super(context, R.layout.history_item, data);
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View rowView = inflater.inflate(R.layout.history_item, null, true);
        initViewElements(rowView, position);
        return rowView;
    }

    private void initViewElements(View view, int position) {
        if (data.get(position) instanceof TaskResult) {
            TaskResult taskResult = (TaskResult) data.get(position);
            TaskModel task = taskResult.getTask();


            ((TextView) view.findViewById(R.id.history_duration)).setText(String.format("Duration %d", task.getDuration()));

            ((TextView) view.findViewById(R.id.history_begin)).setText(
                    String.valueOf(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(task.getBeginTime()))
            );

            ((TextView) view.findViewById(R.id.history_name)).setText(task.toString());

            ((EditText) view.findViewById(R.id.history_comment)).setText(taskResult.getComment());
        }
    }

}

