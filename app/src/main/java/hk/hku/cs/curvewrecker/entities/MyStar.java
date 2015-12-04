package hk.hku.cs.curvewrecker.entities;

/**
 * Created by LZ on 15/12/3.
 */
public class MyStar {
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
}
