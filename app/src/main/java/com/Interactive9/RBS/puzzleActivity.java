package com.Interactive9.RBS;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class puzzleActivity extends MainActivity {

    NumberPicker noPickR;
    NumberPicker noPickG;
    NumberPicker noPickB;
    NumberPicker noPickY;
    Button btn;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puzzle);

        noPickR = get_NP_by_Id(R.id.noPick1, Color.RED);
        noPickG = get_NP_by_Id(R.id.noPick2, Color.GREEN);
        noPickB = get_NP_by_Id(R.id.noPick3, Color.BLUE);
        noPickY = get_NP_by_Id(R.id.noPick4, Color.YELLOW);
        btn = (Button)findViewById(R.id.button2);
        textView = (TextView)findViewById(R.id.textView);

        final MediaPlayer btn_Click = MediaPlayer.create(this, R.raw.beep_24);
        final MediaPlayer np_Click = MediaPlayer.create(this, R.raw.beep_21);

        /* Extra ljud överflödigt? Bara om man har touch ljud är på*/
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

    private NumberPicker get_NP_by_Id(int np_id, int color){
        NumberPicker np = (NumberPicker)findViewById(np_id);
        np.setMaxValue(9);
        np.setMinValue(0);
        //np.setBackgroundColor(color);
        np.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        np.setWrapSelectorWheel(true);
        return np;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_puzzle, menu);
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

    public void GameOver(View view){
        String code = "R" + noPickR.getValue()+ " G"+ noPickG.getValue()+  " B"+ noPickB.getValue() + " Y" + noPickY.getValue();
        String RealCode = readFromFile();
        String disarmed = "KABOOM!!!";
        if(code.equals(RealCode)){
            disarmed = "Bomb as been disarmed";
        }else{
            Intent intent = new Intent(this, GameOver.class);
            startActivity(intent);
        }
        textView.setText(disarmed);

    }
}
