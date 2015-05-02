package com.sandoval.glenn.dothething;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import greendao.DaoMaster;
import greendao.DaoSession;
import greendao.Task;
import greendao.TaskDao;


public class DataManager {

    ArrayList<Task> listItems = new ArrayList<>();
    DaoSession daoSession;
    Boolean _dbSetup = false;
    TaskAdapter _adapter;

    private static final DataManager manager = new DataManager();

    public static DataManager getInstance() {
        return manager;
    }

    public synchronized void setupDatabase(Context c) {
        if (_dbSetup) {
            return;
        }
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(c, "tasks-db", null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
        _dbSetup = true;
    }

    public TaskDao getDaoSession() {
        return daoSession.getTaskDao();
    }

    public TaskAdapter getAdapter() {
        if (_adapter == null) {
            List<Task> existingTasks = getDaoSession().loadAll();

            for (Task t : existingTasks) {
                listItems.add(t);
            }

            _adapter = new TaskAdapter(listItems);
        }
        return _adapter;
    }

    public void addTask(String message) {
        Task t = new Task();
        t.setMessage(message);
        getDaoSession().insert(t);
        listItems.add(t);
        _adapter.notifyDataSetChanged();
    }

}
