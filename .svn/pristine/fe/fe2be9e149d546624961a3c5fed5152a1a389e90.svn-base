package lab4_204_06.uwaterloo.ca.lab4_204_06;

import android.util.Log;

/**
 * Created by Tarek on 06/06/2017.
 */

public class GestureFSM {
    enum gestureStates{WAIT, riseB, fallB, riseA, fallA, DETERMINED};   //Possible states
    enum TYPEAXB{A, X, B};                                              //Gesture Type(gets returned)

    //Default values for type and state
    TYPEAXB currentType = TYPEAXB.X;
    gestureStates currentState = gestureStates.WAIT;

    //thresholds in order: dA, min point, max point
    final public float thresholdB[] = {-0.4f,-3f,3f};
    final public float thresholdA[] = {+0.4f,-3f,3f};

    //Current MinMax for type B graph
    public float TypeBMin = 0;
    public float TypeBMax = 0;

    //Current MinMax for type A graph
    public float aMin = 0;
    public float aMax = 0;

    public int sampleCount = 0;
    //Use prevValue to calculate for change in acceleration(dA) from previous filtered value
    //preValue stores previous filteredValue
    public float prevValue = 0;

    public String currentGesture(Float current){
        //Update the change in acceleration, and update prevValue
        final float dAy = current-prevValue;
        prevValue = current;

        //Sample count that limits the number of samples untiil it goes back to WAIT state
        if(currentState!=gestureStates.WAIT){
            sampleCount++;
            if(sampleCount == 30){
                currentState = gestureStates.WAIT;
                sampleCount = 0;
            }
        }else{
            sampleCount=0;
        }

        //Begin Switch case
        switch (currentState){
            case WAIT:
                // reset min and max
                TypeBMin = 0;
                TypeBMax = 0;

                if(dAy < thresholdB[0]){
                    currentState = gestureStates.fallB;
                }
                if(dAy > thresholdA[0]){
                    currentState = gestureStates.riseA;
                }
                break;
            case riseA:
                if(dAy <= 0){
                    //If graph starts to fall(dAy<0), record the max value
                    //Compare this maxValue with our threshold to see which state to go next

                    aMax = current;

                    Log.d("maxMinA: ", String.format("Check Max Y: %f", aMax));

                    if (aMax >= thresholdA[2]){
                        currentState = gestureStates.fallA;
                    }
                    else if (aMax < thresholdA[2]){
                        currentType = TYPEAXB.X;
                        currentState = gestureStates.DETERMINED;
                    }
                }
                break;
            case fallA:
                if(dAy >= 0){
                    //If graph starts to rise(dAy>0), record the min value
                    //Compare this minValue with our threshold to see which state to go next

                    aMin = current;

                    Log.d("maxMinA: ", String.format("Check Min Y: %f", aMin));

                    //Ending gesture - update Type X or A
                    if (aMin <= thresholdA[1]){
                        currentType = TYPEAXB.A;
                        currentState = gestureStates.DETERMINED;
                    }
                    else if (aMin > thresholdA[1]){
                        currentType = TYPEAXB.X;
                        currentState = gestureStates.DETERMINED;
                    }
                }
                break;
            case fallB:
                if(dAy >=0){
                    //If graph starts to rise(dAy>0), record the min value
                    //Compare this minValue with our threshold to see which state to go next

                    TypeBMin = current;

                    Log.d("maxMinB: ", String.format("Check Min Y: %f", TypeBMin));

                    if (TypeBMin <= thresholdB[1]){
                        currentState = gestureStates.riseB;
                    }
                    else if (TypeBMin > thresholdB[1]){
                        currentType = TYPEAXB.X;
                        currentState = gestureStates.DETERMINED;
                    }
                }
                break;
            case riseB:
                if (dAy <= 0){
                    //If graph starts to fall(dAy<0), record the max value
                    //Compare this maxValue with our threshold to see which state to go next

                    TypeBMax = current;

                    Log.d("maxMinB: ", String.format("Check Max Y: %f", TypeBMax));

                    //Ending gesture - update Type X or B
                    if (TypeBMax >= thresholdB[2]){
                        currentType = TYPEAXB.B;
                        currentState = gestureStates.DETERMINED;
                    }
                    else if (TypeBMax < thresholdB[2]){
                        currentType = TYPEAXB.X;
                        currentState = gestureStates.DETERMINED;
                    }
                }
                break;

            case DETERMINED:
                //Reset state to WAIT, and return TYPE (X,A,B)
                currentState = gestureStates.WAIT;
                return currentType.toString();

            default:
                break;
        }
        return currentType.toString();
    }
}