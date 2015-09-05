package com.sandoval.glenn.dothething;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import greendao.Task;


public class MainActivity extends Activity {

    DataManager _manager = DataManager.getInstance();
    ListView tasksView;
    TaskAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        _manager.setupDatabase(getApplicationContext());

        adapter = _manager.getAdapter();
        adapter.setParentActivity(this);
        tasksView = (ListView) findViewById(R.id.list);
        tasksView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
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
        Intent intent = new Intent(this, CreateTaskActivity.class);
        startActivity(intent);
    }

    public void deleteButtonClick(View v) {
        _manager.deleteTask((Task)v.getTag());
    }
}
