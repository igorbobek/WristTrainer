package com.wrist_trainer.bivsa.wristtrainer.Adapter;

import android.content.Context;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ArrayAdapter;
import android.widget.ImageView;

import android.widget.TextView;


import com.wrist_trainer.bivsa.wristtrainer.Animations;
import com.wrist_trainer.bivsa.wristtrainer.R;
import com.wrist_trainer.bivsa.wristtrainer.TaskModel;
import com.wrist_trainer.bivsa.wristtrainer.Utils.OnTaskSelectedListener;

import java.util.ArrayList;


public class TaskList extends ArrayAdapter<TaskModel> implements View.OnTouchListener{

    private ArrayList<TaskModel> data;
    private Context context;
    private OnTaskSelectedListener callBack;

    public TaskList(ArrayList<TaskModel> data, Context context,OnTaskSelectedListener callBack ){
        super(context, R.layout.list_item ,data);
        this.callBack = callBack;
        this.context = context;
        this.data = data;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View rowView= inflater.inflate(R.layout.list_item, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.name);
        ImageView image = (ImageView) rowView.findViewById(R.id.imageView2);
        txtTitle.setText(data.get(position).getName());
        image.setImageResource(R.drawable.start);
        image.setOnTouchListener(this);
        image.setTag(position);
        return rowView;
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch(motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                System.out.println(data.get((int)view.getTag()).getName());
                Animations.clickAnimation(view,1.2f);
                break;
            case MotionEvent.ACTION_UP:
                System.out.println(data.get((int)view.getTag()).getName());
                Animations.clickAnimation(view,1.0f);
                callBack.onTaskSelected((int)view.getTag());
        }
        return true;
    }
}
