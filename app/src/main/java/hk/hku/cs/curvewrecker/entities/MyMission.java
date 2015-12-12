package hk.hku.cs.curvewrecker.entities;

import java.io.Serializable;

/**
 * Created by LZ on 15/12/3.
 */
public class MyMission implements Serializable {
// mission is the object which contains the information about thing you are doing
    String name;
    int type;
    MyTime strTime;                 //the start time of the current mission
    MyTime targetTime;              // the target time for the current mission
    MyTime remainTime;              // the remain time for the current mission
    MyTime endTime;                 // the actual finish time for the mission
    boolean status;

    public MyMission(){
        name = "";
        type = 0;
        strTime = new MyTime();
        strTime.getCurrentTime();
        targetTime = new MyTime();
        remainTime = this.targetTime.copy();
        endTime = new MyTime();
        status = false;

    }

    public MyMission(int type, MyTime targetTime){
        this.name = "";
        this.type = type;
        this.strTime = new MyTime();
        strTime.getCurrentTime();
        this.targetTime = targetTime;
        this.remainTime = this.targetTime.copy();
        this.endTime = new MyTime();
        status = false;
    }

    public MyMission(String name, int type, MyTime strTime, MyTime targetTime) {
        this.name = name;
        this.type = type;
        this.strTime = strTime;
        this.targetTime = targetTime;
        this.remainTime = this.targetTime.copy();
        this.endTime = new MyTime();
        status = false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public MyTime getStrTime() {
        return strTime;
    }

    public void setStrTime(MyTime strTime) {
        this.strTime = strTime;
    }

    public MyTime getTargetTime() {
        return targetTime;
    }

    public void setTargetTime(MyTime targetTime) {
        this.targetTime = targetTime;
    }

    public MyTime getRemainTime()
    {
        return remainTime;
    }

    public void setRemainTime(MyTime remainTime) {
        this.remainTime = remainTime;
    }


    //when time is over, it will return false, otherwise it will return true
    public boolean decreaseRemainTime(){
    /*        //time is 00:00:00
        if(this.remainTime.getHour() == 0 && this.remainTime.getMinute() == 0 && this.remainTime.getSecond() == 0){
            status = true;
            return false;
        }

        else if(this.remainTime.getSecond() == 0){
            //time is 00:01:00
            if(this.remainTime.getMinute() > 0) {
                this.remainTime.setSecond(59);
                this.remainTime.setMinute(this.remainTime.getMinute() - 1);
            }
            //time is 01:00:00
            else{
                this.remainTime.setSecond(59);
                this.remainTime.setMinute(59);
                this.remainTime.setHour(this.remainTime.getHour() - 1);
            }
        }

        //time is 01:01:32
        else{
            this.remainTime.setSecond(this.remainTime.getSecond() - 1);
        }*/
        int totalS = this.getRemainTime().getTotalSeconds();
        totalS--;
        if(totalS < 0){
            return false;
        }

        if(totalS == 0){
            status = true;
            this.getRemainTime().setHour(0);
            this.getRemainTime().setMinute(0);
            this.getRemainTime().setSecond(0);
        }else{
            this.getRemainTime().setHour(totalS/3600);
            this.getRemainTime().setMinute(totalS/60 - this.getRemainTime().getHour() * 60);
            this.getRemainTime().setSecond(totalS%60);
        }


        return true;

    }

    public boolean isDone(){
        return (this.remainTime.getHour() == 0 && this.remainTime.getMinute() == 0
                && this.remainTime.getSecond() == 0 && status == true);
    }

    public MyTime getEndTime() {
        return endTime;
    }

    public void setEndTime(MyTime newEndTime){
        this.endTime = newEndTime;
    }

    public void updateEndTime() {
        this.endTime.getCurrentTime();
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public MyMission copy(){
        MyMission newMission = new MyMission();
        newMission.setName(this.name);
        newMission.setRemainTime(this.remainTime.copy());
        newMission.setTargetTime(this.targetTime.copy());
        newMission.setStrTime(this.strTime.copy());
        newMission.setType(this.type);
        newMission.setEndTime(this.endTime.copy());
        newMission.setStatus(this.status);

        return newMission;
    }

}
