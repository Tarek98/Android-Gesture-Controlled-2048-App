package lab4_204_06.uwaterloo.ca.lab4_204_06;

import android.content.Context;
import android.widget.ImageView;

/**
 * Created by Tarek on 28/06/2017.
 */

public abstract class GameBlockTemplate extends ImageView{
    public abstract void setBlockDirection(GameLoopTask.directions Direction);
    public abstract void move();

    public GameBlockTemplate(Context gbCTX){
        super(gbCTX);
    }
}
