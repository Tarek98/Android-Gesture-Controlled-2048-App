package lab4_204_06.uwaterloo.ca.lab4_204_06;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Random;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;
import static lab4_204_06.uwaterloo.ca.lab4_204_06.MainActivity.array;

public class GameBlock extends GameBlockTemplate{

    private float IMAGE_SCALE = 0.45f;

    private int myCoordX;
    private int myCoordY;
    private int targetX;
    private int targetY;
    private GameLoopTask myGLT;
    private RelativeLayout myRL;
    private Random randIntGenerator = new Random();

    private final int initialVel = 30;
    private int velocity = 20;
    private int acceleration = 10;
    // CORNERS (lUP, lDWN, rUP, rDwn) = (-130, -130) ; (-130, 680) ; (680, -130) ; (680, 680)
    private final int upperBound = -130;
    private final int lowerBound = 680;
    private final int rightBound = 680;
    private final int leftBound = -130;
    private final int textViewOffset = 250; //Offset(px) between textView and corner of Block
    
    TextView numInBlock;
    private int blockNum;
    private int blockColor = Color.BLUE;

    private boolean changeable = true;
    private boolean markDelete = false;
    private boolean isMultiplyBy2 = false;

    public GameBlock(Context gbCTX, RelativeLayout gLoopRL, GameLoopTask glTask, int CoordX, int CoordY){
        super(gbCTX);

        myCoordX = CoordX;
        myCoordY = CoordY;
        targetX = myCoordX;
        targetY = myCoordY;
        myGLT = glTask;
        myRL = gLoopRL;

        setImageResource(R.drawable.gameblock);
        setScaleX(IMAGE_SCALE);
        setScaleY(IMAGE_SCALE);
        setX(myCoordX);
        setY(myCoordY);

        numInBlock = new TextView(gbCTX);
        // Generates random int either 2 or 4
        blockNum = (randIntGenerator.nextInt(2)+1)*2;
        //blockNum = 1024;
        Log.d("blockNum", String.format("GameBlock: %d", blockNum));

        numInBlock.setText(Integer.toString(blockNum));
        gLoopRL.addView(this);
        gLoopRL.addView(numInBlock);
        numInBlock.bringToFront();
        numInBlock.setX(CoordX+textViewOffset);
        numInBlock.setY(CoordY+textViewOffset);
    }

    public void setBlockDirection(GameLoopTask.directions gameDirection){

        if(changeable == true) {

            int blockCount = 0;         // tracks number of blocks and slots along the path of travel
            int slotCount = 0;
            GameBlock previousGB = null;
            int prevBlockNum = 0;

            switch (gameDirection) {
                //----------------------------------  LEFT ----------------------------
                case LEFT:
                    targetX = leftBound;

                    //While Loop Checks blockCount and slotCount
                    while(targetX != myCoordX){ //Traverse through the slots
                        GameBlock gbInCheck = myGLT.isOccupied(targetX, targetY);   //Reference to the block ahead
                        if(gbInCheck!=null){    //if slot is Occupied
                            blockCount++;   

                            int gbInCheck_num = gbInCheck.blockNum;

                            if(gbInCheck_num == prevBlockNum){  //gbInCheck and prevBlock will be merged
                                Log.d("Match", "CALLED");
                                blockCount--;
                                gbInCheck.markDelete = true;
                                if(previousGB!=null){
                                    previousGB.isMultiplyBy2 = true;
                                }
                            }
                            int currentNum = gbInCheck.markDelete? 0 : gbInCheck.blockNum; //Don't let nextBlock merge
                            prevBlockNum = currentNum;
                            previousGB = gbInCheck;
                        }
                        targetX += myGLT.slotSeperation;    //Traverse to closer slot
                        slotCount++;
                    }

                    Log.d("Match", "prevBlockNum "+prevBlockNum + " and currentNumber " + blockNum);
                    if(blockNum == prevBlockNum){   //Compare with 'this' block
                        Log.d("Match", "thisCALLED");
                        blockCount--;
                        this.markDelete = true;
                        if(previousGB!=null){
                            previousGB.isMultiplyBy2 = true;
                        }
                    }

                    targetX = myCoordX - (slotCount - blockCount)*myGLT.slotSeperation;
                    break;

                //---------------------------------- RIGHT ----------------------------
                case RIGHT:
                    targetX = rightBound;
                    while(targetX != myCoordX){
                        GameBlock gbInCheck = myGLT.isOccupied(targetX, targetY);
                        if(gbInCheck!=null){
                            blockCount++;
                            //adjacentNumbers[numberIndex++] = myGLT.getBlockNumber(targetX,targetY);
                            int gbInCheck_num = gbInCheck.blockNum;
                            Log.d("Match", "prevBlockNum "+prevBlockNum + " and currentNumber " + gbInCheck_num);
                            if(gbInCheck_num == prevBlockNum){
                                Log.d("Match", "CALLED");
                                blockCount--;
                                gbInCheck.markDelete = true;
                                if(previousGB!=null){
                                    previousGB.isMultiplyBy2 = true;
                                }
                            }
                            int currentNum = gbInCheck.markDelete? 0:gbInCheck.blockNum;
                            prevBlockNum = currentNum;
                            previousGB = gbInCheck;
                        }
                        targetX -= myGLT.slotSeperation;
                        slotCount++;
                    }
                    Log.d("Match", "prevBlockNum "+prevBlockNum + " and currentNumber " + blockNum);
                    if(blockNum == prevBlockNum){
                        Log.d("Match", "thisCALLED");
                        blockCount--;
                        this.markDelete = true;
                        if(previousGB!=null){
                            previousGB.isMultiplyBy2 = true;
                        }
                    }
                    targetX = myCoordX + (slotCount - blockCount)*myGLT.slotSeperation;
                    break;

                //---------------------------------- UP ----------------------------
                case UP:
                    targetY = upperBound;
                    while(targetY != myCoordY){
                        GameBlock gbInCheck = myGLT.isOccupied(targetX, targetY);
                        if(gbInCheck!=null){
                            blockCount++;
                            //adjacentNumbers[numberIndex++] = myGLT.getBlockNumber(targetX,targetY);
                            int gbInCheck_num = gbInCheck.blockNum;
                            Log.d("Match", "prevBlockNum "+prevBlockNum + " and currentNumber " + gbInCheck_num);
                            if(gbInCheck_num == prevBlockNum){
                                Log.d("Match", "CALLED");
                                blockCount--;
                                gbInCheck.markDelete = true;
                                if(previousGB!=null){
                                    previousGB.isMultiplyBy2 = true;
                                }
                            }
                            int currentNum = gbInCheck.markDelete? 0:gbInCheck.blockNum;
                            prevBlockNum = currentNum;
                            previousGB = gbInCheck;
                        }
                        targetY += myGLT.slotSeperation;
                        slotCount++;
                    }
                    Log.d("Match", "prevBlockNum "+prevBlockNum + " and currentNumber " + blockNum);
                    if(blockNum == prevBlockNum){
                        Log.d("Match", "thisCALLED");
                        blockCount--;
                        this.markDelete = true;
                        if(previousGB!=null){
                            previousGB.isMultiplyBy2 = true;
                        }
                    }
                    targetY = myCoordY - (slotCount - blockCount)*myGLT.slotSeperation;
                    break;

                //----------------------------------  DOWN ----------------------------
                case DOWN:
                    targetY = lowerBound;
                    while(targetY != myCoordY){
                        GameBlock gbInCheck = myGLT.isOccupied(targetX, targetY);
                        if(gbInCheck!=null){
                            blockCount++;
                            //adjacentNumbers[numberIndex++] = myGLT.getBlockNumber(targetX,targetY);
                            int gbInCheck_num = gbInCheck.blockNum;
                            Log.d("Match", "prevBlockNum "+prevBlockNum + " and currentNumber " + gbInCheck_num);
                            if(gbInCheck_num == prevBlockNum){
                                Log.d("Match", "CALLED");
                                blockCount--;
                                gbInCheck.markDelete = true;
                                if(previousGB!=null){
                                    previousGB.isMultiplyBy2 = true;
                                }
                            }
                            int currentNum = gbInCheck.markDelete? 0:gbInCheck.blockNum;
                            prevBlockNum = currentNum;
                            previousGB = gbInCheck;
                        }
                        targetY -= myGLT.slotSeperation;
                        slotCount++;
                    }
                    Log.d("Match", "prevBlockNum "+prevBlockNum + " and currentNumber " + blockNum);
                    if(blockNum == prevBlockNum){
                        Log.d("Match", "thisCALLED");
                        blockCount--;
                        this.markDelete = true;
                        if(previousGB!=null){
                            previousGB.isMultiplyBy2 = true;
                        }
                    }
                    targetY = myCoordY + (slotCount - blockCount)*myGLT.slotSeperation;
                    break;
                default:
                    break;
            }

            if(myCoordX != targetX || myCoordY != targetY){
                changeable = false;
            }
        }
    }

    public void move(){
        if(myCoordX > targetX) {
            myCoordX -= velocity;
            if(myCoordX < targetX){
                myCoordX = targetX;
            }
        }
        else if(myCoordX < targetX){
            myCoordX += velocity;
            if(myCoordX > targetX){
                myCoordX = targetX;
            }
        }
        if(myCoordY > targetY) {
            myCoordY -= velocity;
            if(myCoordY < targetY){
                myCoordY = targetY;
            }
        }
        else if(myCoordY < targetY){
            myCoordY += velocity;
            if(myCoordY > targetY){
                myCoordY = targetY;
            }
        }

        if(myCoordX == targetX && myCoordY == targetY){
            velocity = initialVel;
            changeable = true;
        }
        else{
            velocity += acceleration;
        }

        setX(myCoordX);
        setY(myCoordY);
        numInBlock.setX(myCoordX+textViewOffset);
        numInBlock.setY(myCoordY+textViewOffset);
    }

    // getter method, reports its x and y coords
    public int[] getCoordinates(){
        int xy[] = new int[2];
        xy[0] = myCoordX;
        xy[1] = myCoordY;
        return xy;
    }

    // getter method, reports its target x and y coords while moving
    public int[] getTargetCoordinates(){
        int xy[] = new int[2];
        xy[0] = targetX;
        xy[1] = targetY;
        return xy;
    }

    public boolean isMoving(){
        return !changeable;
    }
    public boolean isMarkedDelete(){
        return markDelete;
    }
    public void delete(){
            Log.d("adjacent Numbers", "DELETING");
            myRL.removeView(this);
            myRL.removeView(numInBlock);
    }
    public void updateBlockNumber(){
        if(isMultiplyBy2){
            blockNum*=2;
            numInBlock.setText(Integer.toString(blockNum));
            isMultiplyBy2= false;
        }
        if(blockNum ==2048){
            myGLT.endGame = true;
            myGLT.gameStatusText.setText("YOU WIN IT'S 2048!!");
            Log.d("GAME_STATUS", "WIN IT'S 2048!!");
        };
        if(blockNum ==2) numInBlock.setBackgroundColor(Color.rgb(202,225,255));
        if(blockNum ==4) numInBlock.setBackgroundColor(Color.rgb(180,238,180));
        if(blockNum ==8) numInBlock.setBackgroundColor(Color.rgb(238,232,170));
        if(blockNum ==16) numInBlock.setBackgroundColor(Color.rgb(205,155,155));
        if(blockNum ==32) numInBlock.setBackgroundColor(Color.rgb(205,150,205));
        if(blockNum ==64) numInBlock.setBackgroundColor(Color.rgb(255,182,193));
        if(blockNum ==128) numInBlock.setBackgroundColor(Color.rgb(255,160,122));
        if(blockNum ==256) numInBlock.setBackgroundColor(Color.rgb(255,215,0));
        if(blockNum ==512) numInBlock.setBackgroundColor(Color.rgb(176,196,222));
        if(blockNum ==1024) numInBlock.setBackgroundColor(Color.rgb(192,192,192));
        if(blockNum ==2048) numInBlock.setBackgroundColor(Color.rgb(255,255,255));
    }

    public boolean testMergable(){

        int leftBlockCoordX = myCoordX-myGLT.slotSeperation;
        int rightBlockCoordX = myCoordX+myGLT.slotSeperation;
        int upBlockCoordY = myCoordY-myGLT.slotSeperation;
        int downBlockCoordY = myCoordY+myGLT.slotSeperation;
        boolean canMerge = false;

        GameBlock gbInCheck = myGLT.isOccupied(leftBlockCoordX, targetY);

        if(gbInCheck!=null){
            if(blockNum == gbInCheck.blockNum){   //Compare with 'this' block
                //MergeHappens
                canMerge = true;
            }
        }
        gbInCheck = myGLT.isOccupied(rightBlockCoordX, targetY);
        if(gbInCheck!=null){
            if(blockNum == gbInCheck.blockNum){   //Compare with 'this' block
                //MergeHappens
                canMerge = true;
            }
        }
        gbInCheck = myGLT.isOccupied(targetX, upBlockCoordY);
        if(gbInCheck!=null){
            if(blockNum == gbInCheck.blockNum){   //Compare with 'this' block
                //MergeHappens
                canMerge = true;
            }
        }
        gbInCheck = myGLT.isOccupied(targetX, downBlockCoordY);
        if(gbInCheck!=null){
            if(blockNum == gbInCheck.blockNum){   //Compare with 'this' block
                //MergeHappens
                canMerge = true;
            }
        }

        return canMerge;
    }


}
