package hk.hku.cs.curvewrecker.entities;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by LZ on 15/12/3.
 */
public class MyTarget implements Serializable {

    private int type;               // 0 for sleep, 1 for study
    private MyTime targetTime;
    private MyTime actualTime;
    private MyTime date;
    private int status;             // to indicate if the target is done, 1 means done

    public MyTarget(){
        this.type = 0;
        this.date = new MyTime();
        this.targetTime = new MyTime();
        this.actualTime = new MyTime();
        this.status = 0;

    }

    public MyTarget(int type){
        this.type = type;
        this.date = new MyTime();
        this.date.getCurrentTime();
        this.targetTime = new MyTime();
        this.actualTime = new MyTime();
        this.status = 0;
    }

    public MyTarget( int type, MyTime targetTime) {
        this.type = type;
        this.date = new MyTime();
        this.date.getCurrentTime();
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
        if(this.actualTime.getTotalHour()>this.targetTime.getTotalHour()){
            this.status = 1;
        }
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

    public MyTarget copy(){
        MyTarget newTarget = new MyTarget();
        newTarget.setActualTime(this.actualTime.copy());
        newTarget.setDate(this.date.copy());
        newTarget.setStatus(this.status);
        newTarget.setTargetTime(this.targetTime.copy());
        newTarget.setType(this.type);
        return newTarget;
    }
}
