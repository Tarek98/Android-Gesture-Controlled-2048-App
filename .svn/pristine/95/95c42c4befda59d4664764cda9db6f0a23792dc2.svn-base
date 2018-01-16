package lab4_204_06.uwaterloo.ca.lab4_204_06;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.LinkedList;
import java.util.Random;
import java.util.TimerTask;

import static android.R.attr.id;

/**
 * Created by Tarek on 25/06/2017.
 */

public class GameLoopTask extends TimerTask {

    public enum directions {LEFT, RIGHT, UP, DOWN, UNKNOWN};       //Can be called anywhere in the scope of public
    private LinkedList<GameBlock> gbList = new LinkedList<>();
    private Activity myActivity;
    private Context gameLoopCTX;
    private RelativeLayout gameLoopRL;
    private Random randIntGenerator = new Random();
    public TextView gameStatusText;

    public final int slotSeperation = 270;

    private boolean creating = false;
    private boolean isAllMoving = false;
    private boolean hasAnyBlockMoved = false;
    public boolean endGame = false;

    public GameLoopTask (Activity myActivity, RelativeLayout myRL, Context myContext, TextView gameStatus){
        this.myActivity = myActivity;
        this.gameLoopCTX = myContext;
        this.gameLoopRL = myRL;
        this.gameStatusText = gameStatus;

        createBlock();
    }

    @Override
    public void run() {
        this.myActivity.runOnUiThread(
                new Runnable() {
                    @Override
                    public void run() {
                        // Periodic tasks inserted here...
                        isAllMoving = false;
                        for (GameBlock gb: gbList) {
                            gb.move();
                            if(gb.isMoving()==true) {isAllMoving = true;}
                        }
                        if(!isAllMoving) Log.d("checkCreate", "AllStopped");
                        if(creating) Log.d("checkCreate", "creating block...");
                        //If block reaches target, create block, delete merged GB, doubleValues
                        if(!isAllMoving && creating == true){
                            LinkedList<GameBlock> blocksToDelete = new LinkedList<GameBlock>();
                            //Go through each gameblock to update (delete merged blocks & update number)
                            for(GameBlock gb : gbList){
                                gb.updateBlockNumber();
                                if(gb.isMarkedDelete() == true){
                                    gb.delete();    //delete block and textview from relative layout
                                    blocksToDelete.add(gb);
                                }
                            }
                            for(GameBlock deletingBlock: blocksToDelete){
                                Log.d("deleting", "Before "+ Integer.toString(gbList.size()));
                                gbList.remove(deletingBlock);
                                Log.d("deleting", Integer.toString(gbList.size()));
                            }


                            createBlock();
                            creating = false;
                        }
                    }
                }
        );
    }

    private void createBlock(){
        // 4x4 boolean elements; default is initialized to false for all elements
        boolean cellOccupied[][] = new boolean[4][4];
        int cellXY [] = new int[2];     //Store GB's x and y value
        int numEmptySlots;
        int numOccupiedSlots = 0;
        int randomNum;
        int emptySlotIndex = 0;         //Increment to determine newBlockPlacement
        int xPos; int yPos;             //x y Position: translate slotCoordinate back to pixelCoordinate

        for (GameBlock gb: gbList) {
            cellXY = gb.getTargetCoordinates();
            cellXY[0] = (cellXY[0] + 130)/slotSeperation;   //Get block's X slot position
            cellXY[1] = (cellXY[1] + 130)/slotSeperation;   //Get block's Y slot position
            cellOccupied[cellXY[0]][cellXY[1]] = true;      //set slot coordinate to be occupied
            numOccupiedSlots++;
        }
        numEmptySlots = 16 - numOccupiedSlots;             //Calculate number of empty slots

        if(numEmptySlots>1){
            randomNum = randIntGenerator.nextInt(numEmptySlots-1);
        }
        else{
            if(numEmptySlots==0){
                boolean canAnyBlockMerge = false;
                //try all direction
                for(GameBlock gb: gbList){
                    if(gb.testMergable()==true){
                        canAnyBlockMerge = true;
                    }
                }
                if(canAnyBlockMerge == false){
                    endGame = true;
                    gameStatusText.setText("GAME OVER");
                    Log.d("GAME_STATUS", "END - GAMEOVER");
                }
                return; //Just wait for next instruction
            }
            randomNum = 0;
        }

        for(int i = 0 ; i < 4; i++){
            for (int j = 0; j < 4; j++){
                if (cellOccupied[i][j] == false){
                    if (emptySlotIndex == randomNum){
                        // subtracting offset due to block size scaling (upper most corner has dimensions (-130,-130))
                        xPos = (i*slotSeperation) - 130;
                        yPos = (j*slotSeperation) - 130;
                        GameBlock newBlock = new GameBlock(gameLoopCTX, gameLoopRL, this, xPos, yPos);
                        gbList.add(newBlock);
                    }
                    emptySlotIndex++;
                }
            }
        }
        // CORNERS (lUP, lDWN, rUP, rDwn) = (-130, -130) ; (-130, 680) ; (680, -130) ; (680, 680)
    }

    public void setDirection(directions newDirection){
        if(endGame) return;
        isAllMoving = false;
        for (GameBlock gb: gbList) {
            gb.setBlockDirection(newDirection);
            if(gb.isMoving()) Log.d("isMoving", "Moving");
            if(gb.isMoving()==true) {isAllMoving = true;}
        }
        if(isAllMoving || gbList.size() == 16){
            creating= true; //only create block if at least one block moves\
        }
    }

    public GameBlock isOccupied(int x, int y){
        int xyBlock[] = new int[2];

        for (GameBlock gb: gbList) {
            xyBlock = gb.getCoordinates();
            if (xyBlock[0] == x && xyBlock[1] == y)
                return gb;  //If Block is found, return the gameblock reference
        }
        // parameter spot is empty if foreach loop didn't detect any block in the spot
        return null;
    }
}
