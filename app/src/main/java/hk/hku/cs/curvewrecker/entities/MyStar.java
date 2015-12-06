package hk.hku.cs.curvewrecker.entities;

import java.io.Serializable;

/**
 * Created by LZ on 15/12/3.
 */
public class MyStar implements Serializable {
    private MyTime myTime;

    public MyStar(){
        this.myTime = new MyTime();
    }
    public MyStar(MyTime myTime) {
        this.myTime = myTime;
    }

    public MyTime getMyTime() {
        return myTime;
    }

    public void setMyTime(MyTime myTime) {
        this.myTime = myTime;
    }

    public MyStar copy(){
        MyStar newStar = new MyStar(this.myTime.copy());
        return newStar;
    }
}
