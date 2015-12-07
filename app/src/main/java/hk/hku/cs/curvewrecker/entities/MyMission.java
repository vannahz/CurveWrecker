package hk.hku.cs.curvewrecker.entities;

import java.io.Serializable;

/**
 * Created by LZ on 15/12/3.
 */
public class MyMission implements Serializable {
// mission is the object which contains the information about thing you are doing
    String name;
    int type;
    MyTime strTime;
    MyTime endTime;
    MyTime currentTime;

    public MyMission(){
        name = "";
        type = 0;
        strTime = new MyTime();
        endTime = new MyTime();
        currentTime = new MyTime();

    }

    public MyMission(String name, int type, MyTime strTime, MyTime endTime) {
        this.name = name;
        this.type = type;
        this.strTime = strTime;
        this.endTime = endTime;
        this.currentTime = new MyTime();
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

    public MyTime getEndTime() {
        return endTime;
    }

    public void setEndTime(MyTime endTime) {
        this.endTime = endTime;
    }

    public MyTime getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(MyTime currentTime) {
        this.currentTime = currentTime;
    }

    public MyMission copy(){
        MyMission newMission = new MyMission();
        newMission.setName(this.name);
        newMission.setCurrentTime(this.currentTime.copy());
        newMission.setEndTime(this.endTime.copy());
        newMission.setStrTime(this.strTime.copy());
        newMission.setType(this.type);
        return newMission;
    }
}
