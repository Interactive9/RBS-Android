package com.Interactive9.RBS;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
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
import java.util.concurrent.TimeUnit;


public class puzzleActivity extends Activity {

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

        final MediaPlayer btn_Click = MediaPlayer.create(this, R.raw.bombbeep);
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

        CountDownTimer timer = new CounterClass(30000,1000);
        timer.start();

    }


    private void Over(){
        Intent intent = new Intent(this, GameOver.class);
        startActivity(intent);
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
            StartOver();
        }else{
            Over();
        }
        textView.setText(disarmed);

    }

    public void StartOver(){
        String message = "Bomb has been successfully disarmed";
        new AlertDialog.Builder(puzzleActivity.this)
                .setTitle("Bomb disarmed")
                .setMessage(message)
                .setCancelable(false)
                .setNeutralButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        disarmed();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();

    }
    private void disarmed(){
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public class CounterClass extends CountDownTimer {
        public CounterClass (long millisInFuture, long countDownInterval){
            super(millisInFuture,countDownInterval);

        }

        @Override
        public void onTick(long millisUntilFinished) {
            long millis = millisUntilFinished;
            String ms = String.format("%02d:%02d", TimeUnit.MILLISECONDS.toMinutes(millis),
                    TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
            System.out.println(ms);
            textView.setText(ms);
            textView.setTextColor(Color.RED);

        }

        @Override
        public void onFinish() {
            Over();
        }
    }

}

