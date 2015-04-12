package com.sandoval.glenn.dothething;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import greendao.Task;

public class TaskAdapter extends BaseAdapter {

    List<Task> m_tasks;

    public TaskAdapter(List<Task> tasks) {
        super();
        m_tasks = tasks;
    }

    @Override
    public int getCount() {
        return m_tasks.size();
    }

    @Override
    public View getView(int i, View v, ViewGroup vg) {
        Task t = (Task)getItem(i);
        TextView tv;
        if(v == null) {
            tv = new TextView(vg.getContext());
        } else {
            tv = (TextView)v;
        }
        tv.setText(t.getMessage());
        return tv;
    }

    @Override
    public long getItemId(int i) {
        Task item = (Task)getItem(i);
        return item.getId();
    }

    @Override
    public Object getItem(int position) {
        return m_tasks.get(position);
    }
}
