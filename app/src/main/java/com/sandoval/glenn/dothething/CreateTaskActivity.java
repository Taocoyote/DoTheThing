package com.sandoval.glenn.dothething;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.ToggleButton;

import java.io.FileNotFoundException;
import java.io.InputStream;


public class CreateTaskActivity extends Activity {

    private Uri selectedImage;
    static final int SELECT_PHOTO = 1;
    private Boolean scheduled = false;
    private DataManager _manager = DataManager.getInstance();
    EditText _editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);

        Spinner spTimes = (Spinner) findViewById(R.id.spinnerInterval);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.times, android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spTimes.setAdapter(adapter);
        _editText = (EditText)findViewById(R.id.editText);
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

    public void buttonOkClick(View v) {
        _manager.addTask(_editText.getText().toString());
        finish();
    }

    public void buttonCancelClick(View v) {
        finish();
    }

    public void selectImage(View v) {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, SELECT_PHOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        switch (requestCode) {
            case SELECT_PHOTO:
                if (resultCode == RESULT_OK) {
                    selectedImage = imageReturnedIntent.getData();
                    try {
                        decodeUri(selectedImage);
                    } catch (FileNotFoundException fnf) {
                        //panic
                    }
                }
        }
    }

    private void decodeUri(Uri selectedImage) throws FileNotFoundException {

        // Decode image size
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(getContentResolver().openInputStream(selectedImage), null, o);

        // The new size we want to scale to
        final int REQUIRED_SIZE = 64;

        // Find the correct scale value. It should be the power of 2.
        int width_tmp = o.outWidth, height_tmp = o.outHeight;
        int scale = 1;
        while (true) {
            if (width_tmp / 2 < REQUIRED_SIZE || height_tmp / 2 < REQUIRED_SIZE) {
                break;
            }
            width_tmp /= 2;
            height_tmp /= 2;
            scale *= 2;
        }

        // Decode with inSampleSize
        BitmapFactory.Options o2 = new BitmapFactory.Options();
        o2.inSampleSize = scale;
        Bitmap bm = BitmapFactory.decodeStream(
                getContentResolver().openInputStream(selectedImage), null, o2
        );

        ImageView iv = (ImageView) findViewById(R.id.taskImageView);
        iv.setImageBitmap(bm);

    }

    public void toggleType(View v) {
        RelativeLayout rvScheduled = (RelativeLayout) findViewById(R.id.scheduled);
        RelativeLayout rvInterval = (RelativeLayout) findViewById(R.id.interval);
        ToggleButton tb = (ToggleButton) findViewById(R.id.toggleButton);

        scheduled = tb.isChecked();

        if (scheduled) {
            rvInterval.setVisibility(View.INVISIBLE);
            rvScheduled.setVisibility(View.VISIBLE);
        } else {
            rvInterval.setVisibility(View.VISIBLE);
            rvScheduled.setVisibility(View.INVISIBLE);
        }

    }

}
