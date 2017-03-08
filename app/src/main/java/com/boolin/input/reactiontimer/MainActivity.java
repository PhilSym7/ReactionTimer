package com.boolin.input.reactiontimer;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    TextView timerTextView;
    long startTime = 0;

    long millis;
    int seconds;
    int minutes;
    long yourTime;
    boolean tapNow;


    //attempt to make wait time different on each test
    public static int getWaitTime(){
        //makes wait time
        Random rand = new Random();
        int waitTime = rand.nextInt(5) + 2;
        return waitTime;
    }

    int fakeWaitTime = getWaitTime();

    //runs without a timer by reposting this handler at the end of the runnable
    Handler timerHandler = new Handler();
    Runnable timerRunnable = new Runnable() {
        @Override
        public void run() {
            //makes the second system
            millis = System.currentTimeMillis() - startTime;
            seconds = (int) (millis / 1000);
            minutes = seconds / 60;
            seconds = seconds % 60;
            tapNow = true;

            //sets up text view to display time
            //to be removed
            timerTextView.setText(String.format("%d.%02d", seconds, millis));
            timerTextView.setTextColor(getResources().getColorStateList(R.color.colorBaseText));

            timerHandler.postDelayed(this, 10);

            TableLayout main = (TableLayout) findViewById(R.id.activity_main);

            getWaitTime();
            if (seconds == fakeWaitTime && tapNow == true){
                setContentView(R.layout.green_tap);
                tapNow = false;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerTextView = (TextView) findViewById(R.id.timerTextView);

        //makes start button
        //to be replaced with 'touch screen'
        TextView b = (TextView) findViewById(R.id.textFull);
        b.setText("Tap anywhere to start. Tap again when the screen turns green.");
        b.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                TextView b = (TextView) v;
                if (b.getText().equals("stop")) {
                    timerHandler.removeCallbacks(timerRunnable);
                    b.setText("start");
                    getWaitTime();
                } else {
                    startTime = System.currentTimeMillis();
                    timerHandler.postDelayed(timerRunnable, 0);
                    if (seconds < 1){
                        b.setText("Wait...");
                    }
                    else if (seconds >= 1){
                        b.setText("Wait for it...");
                    }
                    else if (seconds >= 2){
                        b.setText("Does this even work?");
                    }
                    getWaitTime();
                }
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        timerHandler.removeCallbacks(timerRunnable);
        Button b = (Button)findViewById(R.id.button);
        b.setText("start");
        getWaitTime();
    }

    //generate new toString method

    @Override
    public String toString() {
        return "time:" + yourTime;
    }

    public void getReactionTime(View view){
        TextView txtFinal = (TextView) findViewById(R.id.urTime);
    }

    public void finish(){
        timerHandler.removeCallbacks(timerRunnable);

//        setContentView(R.layout.finish_tap);
//        System.out.println("Test");
        TextView txtFinal = (TextView) findViewById(R.id.urTime);
        txtFinal.setText("PBEBNEIBSI");

    }
}
