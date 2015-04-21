package com.sandoval.glenn.dothething;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
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
        Task t = (Task) getItem(i);

        Context c = vg.getContext();

        LinearLayout ll;
        TextView tv;
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) c.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            ll = (LinearLayout) inflater.inflate(R.layout.task_list_item, null);
        } else {
            ll = (LinearLayout) v;
        }

        tv = (TextView) ll.getChildAt(1);
        tv.setText(t.getMessage());

        return ll;
    }

    @Override
    public long getItemId(int i) {
        Task item = (Task) getItem(i);
        return item.getId();
    }

    @Override
    public Object getItem(int position) {
        return m_tasks.get(position);
    }
}
