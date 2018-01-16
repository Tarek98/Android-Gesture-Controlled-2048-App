package lab4_204_06.uwaterloo.ca.lab4_204_06;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.util.Log;
import android.widget.TextView;

import static lab4_204_06.uwaterloo.ca.lab4_204_06.MainActivity.linkedlistArrayX;
import static lab4_204_06.uwaterloo.ca.lab4_204_06.MainActivity.linkedlistArrayY;
import static lab4_204_06.uwaterloo.ca.lab4_204_06.MainActivity.linkedlistArrayZ;

/**
 * Created by Tarek on 03/06/2017.
 */

class SensorListener extends GestureFSM implements SensorEventListener {

    TextView outputGesture; //The gestures
    GameLoopTask gameLoop;

    GameLoopTask.directions previousGesture = GameLoopTask.directions.UNKNOWN;

    //Declare FSM objects for both x and y coordinates
    GestureFSM xfsm = new GestureFSM();
    GestureFSM yfsm = new GestureFSM();

    public SensorListener(GameLoopTask mainGameLoop, TextView gesture) {
        outputGesture = gesture;
        this.gameLoop = mainGameLoop;
    }

    public void onAccuracyChanged(Sensor s, int i) {
    }

    public void onSensorChanged(SensorEvent se) {
        if (se.sensor.getType() == Sensor.TYPE_LINEAR_ACCELERATION) {

            // array[0] stores the previous x-value reading [filteredReading], se.values[0] is the new reading captured [newReading] when OnSensorChanged is called
            // Low Pass Filter implementation
            //MainActivity.array[] stores the old filtered value.
            MainActivity.array[0] += (se.values[0]- MainActivity.array[0])/5;
            MainActivity.array[1] += (se.values[1]- MainActivity.array[1])/5;
            MainActivity.array[2] += (se.values[2]- MainActivity.array[2])/5;

            //Add filtered values to the linkedlist (100 recent data)
            linkedlistArrayX.addFirst(MainActivity.array[0]);
            linkedlistArrayY.addFirst(MainActivity.array[1]);
            linkedlistArrayZ.addFirst(MainActivity.array[2]);

            //Trucate values to 100.
            while(linkedlistArrayX.size()>100){
                linkedlistArrayX.removeLast();
                linkedlistArrayY.removeLast();
                linkedlistArrayZ.removeLast();
            }

            //Run the FSM to get the gesture type (A,B,X) for y and x axis.
            String xSignature = xfsm.currentGesture(MainActivity.array[0]);
            String ySignature = yfsm.currentGesture(MainActivity.array[1]);

            //Decode the type from FSM for x and y to the gesture result.
            GameLoopTask.directions result;
            if (xSignature == "B" && ySignature == "X"){ result = GameLoopTask.directions.LEFT;}
            else if(xSignature == "A" && ySignature == "X"){ result = GameLoopTask.directions.RIGHT;}
            else if(ySignature == "A" && xSignature == "X"){ result = GameLoopTask.directions.UP;}
            else if(ySignature == "B" && xSignature == "X"){ result = GameLoopTask.directions.DOWN;}
            else result = GameLoopTask.directions.UNKNOWN;;

            //Only print out values if it's not unknown
            if(result!= GameLoopTask.directions.UNKNOWN && result!=previousGesture) {
                outputGesture.setText(result.toString());
                gameLoop.setDirection(result);
            }
            previousGesture = result;

        }
    }
}
