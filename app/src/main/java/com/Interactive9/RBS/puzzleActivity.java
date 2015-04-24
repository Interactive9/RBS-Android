package com.Interactive9.RBS;

import android.app.Activity;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class puzzleActivity extends Activity {

    NumberPicker noPick1;
    NumberPicker noPick2;
    NumberPicker noPick3;
    NumberPicker noPick4;
    Button btn;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puzzle);

        noPick1 = get_NP_by_Id(R.id.noPick1, Color.RED);
        noPick2 = get_NP_by_Id(R.id.noPick2, Color.GREEN);
        noPick3 = get_NP_by_Id(R.id.noPick3, Color.BLUE);
        noPick4 = get_NP_by_Id(R.id.noPick4, Color.YELLOW);
        btn = (Button)findViewById(R.id.button2);

        final MediaPlayer btn_Click = MediaPlayer.create(this, R.raw.beep_24);
        final MediaPlayer np_Click = MediaPlayer.create(this, R.raw.beep_21);
        btn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_Click.start();
                textView = (TextView)findViewById(R.id.textView);
                textView.setText(readFromFile());
            }
        });

        NumberPicker.OnValueChangeListener vcl = new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker np, int oldVal, int newVal) {
                np_Click.start();
            }
        };
        noPick1.setOnValueChangedListener(vcl);
        noPick2.setOnValueChangedListener(vcl);
        noPick3.setOnValueChangedListener(vcl);
        noPick4.setOnValueChangedListener(vcl);
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

    private String readFromFile() {
        String retVal = "";
        try {
            InputStream inputStream = openFileInput("test.txt");
            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String text = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ((text = bufferedReader.readLine()) != null) {
                    stringBuilder.append(text);
                }
                inputStream.close();
                retVal = stringBuilder.toString();
            }
        } catch (FileNotFoundException e) {
            Log.e("Read error", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("Read error", "Can not read file: " + e.toString());
        }

        return retVal;
    }
}
