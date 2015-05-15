package com.Interactive9.RBS;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.NumberPicker;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


public class PlantBomb extends Activity {
    NumberPicker noPickR;
    NumberPicker noPickG;
    NumberPicker noPickB;
    NumberPicker noPickY;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_bomb);
        noPickR = get_NP_by_Id(R.id.noPick1, Color.RED);
        noPickG = get_NP_by_Id(R.id.noPick2, Color.GREEN);
        noPickB = get_NP_by_Id(R.id.noPick3, Color.BLUE);
        noPickY = get_NP_by_Id(R.id.noPick4, Color.YELLOW);

        final MediaPlayer np_Click = MediaPlayer.create(this, R.raw.beep_21);

        NumberPicker.OnValueChangeListener vcl = new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker np, int oldVal, int newVal) {
                np_Click.start();
            }
        };
        noPickR.setOnValueChangedListener(vcl);
        noPickG.setOnValueChangedListener(vcl);
        noPickB.setOnValueChangedListener(vcl);
        noPickY.setOnValueChangedListener(vcl);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_plant_bomb, menu);
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
    private NumberPicker get_NP_by_Id(int np_id, int color){
        NumberPicker np = (NumberPicker)findViewById(np_id);
        np.setMaxValue(9);
        np.setMinValue(1);
        //np.setBackgroundColor(color);
        np.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        np.setWrapSelectorWheel(true);
        return np;
    }
    private void writeToFile(String text) {

        File file = new File(Environment.getExternalStorageDirectory(), "/Android/data/com.Interactive9.RBS/files/Code.txt");
        FileOutputStream outputStream;
        try {

            outputStream = new FileOutputStream(file);
            outputStream.write(text.getBytes());

            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();

        }

    }

    public void plant(View view){
        StringBuilder stringBuilder = new StringBuilder("R" + noPickR.getValue());
        stringBuilder.append(" G" + noPickG.getValue());
        stringBuilder.append(" B" + noPickB.getValue());
        stringBuilder.append(" Y" + noPickY.getValue());
        writeToFile(stringBuilder.toString());
        final MediaPlayer mp = MediaPlayer.create(this, R.raw.bombhasbeenplanted);
        mp.start();

        String message = "Bomb has been successfully planted";
        new AlertDialog.Builder(PlantBomb.this)
                .setTitle("Bomb planted")
                .setMessage(message)
                .setCancelable(false)
                .setNeutralButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        planted();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();

    }
    private void planted(){
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        startActivity(intent);
    }
}
