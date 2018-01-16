package lab4_204_06.uwaterloo.ca.lab4_204_06;

import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Timer;

import sensortoy.LineGraphView;

import static android.R.id.input;
import static lab4_204_06.uwaterloo.ca.lab4_204_06.R.id.parentLayout;

public class MainActivity extends AppCompatActivity {

    static float[] array = new float[3];    //Stores old filtered values
    //Linked List to store the 100 data points for x, y, and z seperately
    static LinkedList<Float> linkedlistArrayX = new LinkedList<>();
    static LinkedList<Float> linkedlistArrayY = new LinkedList<>();
    static LinkedList<Float> linkedlistArrayZ = new LinkedList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final int GAMEBOARD_DIMENSION = 1080;

        //Main Layout and Gameboard settings
        RelativeLayout rl = (RelativeLayout) findViewById(R.id.board);
        RelativeLayout parentLayout = (RelativeLayout) findViewById(R.id.parentLayout);

        //GAME STATUS text
        TextView statusText = new TextView(getApplicationContext()); //Result Gesture TextView
        statusText.setTextColor(Color.BLACK);
        statusText.setTextSize(30);
        statusText.setY(1200);

        Timer GameTimer = new Timer();
        GameLoopTask gameLoop = new GameLoopTask(this, rl, getApplicationContext(), statusText);
        GameTimer.schedule(gameLoop, 50, 50);

        rl.getLayoutParams().width = GAMEBOARD_DIMENSION;
        rl.getLayoutParams().height = rl.getLayoutParams().width;

        rl.setBackgroundResource(R.drawable.gameboard);

        //Begin sensor manager
        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        /*ACCELEROMETER*/
        TextView gestureText = new TextView(getApplicationContext()); //Result Gesture TextView
        gestureText.setTextColor(Color.BLACK);
        gestureText.setTextSize(30);
        gestureText.setGravity(Gravity.CENTER_HORIZONTAL);

        Sensor accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        sensorManager.registerListener(new SensorListener(gameLoop, gestureText), accelerometerSensor, SensorManager.SENSOR_DELAY_GAME);
        /*END ACCELEROMETER*/

        //Add button and texts
        parentLayout.addView(gestureText,1);
        parentLayout.addView(statusText,2);
    }
}



