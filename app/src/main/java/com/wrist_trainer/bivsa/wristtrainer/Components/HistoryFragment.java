package com.wrist_trainer.bivsa.wristtrainer.Components;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.wrist_trainer.bivsa.wristtrainer.Adapter.HistoryList;
import com.wrist_trainer.bivsa.wristtrainer.R;
import com.wrist_trainer.bivsa.wristtrainer.Utils.ReadWriteObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class HistoryFragment extends Fragment{

    private View fragment;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragment = inflater.inflate(R.layout.fragment_history, container, false);
        ListView list = fragment.findViewById(R.id.historyList);
        ArrayList history = ReadWriteObject.readFromContext(getContext(), "history");
        Collections.reverse(history);
        list.setAdapter(new HistoryList(history, getContext()));
        return fragment;
    }
}
