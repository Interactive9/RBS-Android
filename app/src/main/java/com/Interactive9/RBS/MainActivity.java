package com.Interactive9.RBS;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void play(View view)
    {
        //String path = writeToFile("R1 G2 B3 Y4");
        Intent intent = new Intent(this, SelectActivity.class);
        final MediaPlayer mp = MediaPlayer.create(this, R.raw.button_19);
        mp.start();
        startActivity(intent);
    }

    public void activityNotImplemented(View view)
    {


        String message = "This function(s) has not been implemented yet";
        new AlertDialog.Builder(MainActivity.this)
                .setTitle("Sorry")
                .setMessage(message)
                .setCancelable(false)
                .setNeutralButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
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

    private String writeToFile(String text) {

        File file = new File(Environment.getExternalStorageDirectory(), "/Android/data/com.Interactive9.RBS/files/Code.txt");
        FileOutputStream outputStream;
        try {

            outputStream = new FileOutputStream(file);
            outputStream.write(text.getBytes());

            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
            return "file not created";
        }
        return Environment.getExternalStorageDirectory().toString();
    }

    public String readFromFile() {
        File file = new File(Environment.getExternalStorageDirectory(), "/Android/data/com.Interactive9.RBS/files/Code.txt");
        String ret = "";

        try {
            FileInputStream inputStream = new FileInputStream(file);

            if ( inputStream != null ) {

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

        return ret;
    }

    /* Checks if external storage is available for read and write */

    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    public void onBackPressed() {
        finish();
        System.exit(0);
    }

}
