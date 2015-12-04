package hk.hku.cs.curvewrecker.entities;

import java.util.Date;

/**
 * Created by LZ on 15/12/3.
 */
public class MyTarget {

    private int type;
    private MyTime targetTime;
    private MyTime actualTime;
    private MyTime date;
    private int status;  // to indicate if the target is done, 1 means done

    public MyTarget(){
        this.type = 0;
        this.date = new MyTime();
        this.targetTime = new MyTime();
        this.actualTime = new MyTime();
        this.status = 0;

    }

    public MyTarget( int type, MyTime date,MyTime targetTime) {
        this.type = type;
        this.date = date;
        this.targetTime = targetTime;
        this.actualTime = new MyTime();
        this.status = 0;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public MyTime getTargetTime() {
        return targetTime;
    }

    public void setTargetTime(MyTime targetTime) {
        this.targetTime = targetTime;
    }

    public MyTime getActualTime() {
        return actualTime;
    }

    public void setActualTime(MyTime actualTime) {
        this.actualTime = actualTime;
    }

    public MyTime getDate() {
        return date;
    }

    public void setDate(MyTime date) {
        this.date = date;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
