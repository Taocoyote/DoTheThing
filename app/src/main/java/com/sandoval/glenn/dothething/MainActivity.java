package com.sandoval.glenn.dothething;

import android.app.Activity;
import android.app.ListActivity;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import greendao.DaoMaster;
import greendao.DaoSession;
import greendao.Task;


public class MainActivity extends Activity {

    ArrayList<Task> listItems = new ArrayList<>();

    TaskAdapter adapter;

    int clickCounter = 0;

    ListView tasksView;

    public DaoSession daoSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupDatabase();

        List<Task> existingTasks = daoSession.getTaskDao().loadAll();

        for(Task t: existingTasks){
            listItems.add(t);
        }

        adapter = new TaskAdapter(listItems);
        tasksView = (ListView) findViewById(R.id.list);
        tasksView.setAdapter(adapter);
    }

    private void setupDatabase() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "tasks-db", null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void buttonClick(View v) {
        Task t = new Task();
        t.setMessage("Clicked : " + clickCounter++);
        getDaoSession().getTaskDao().insert(t);
        listItems.add(t);
        adapter.notifyDataSetChanged();
    }
}
