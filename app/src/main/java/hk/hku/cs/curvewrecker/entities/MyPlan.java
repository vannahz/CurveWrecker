package hk.hku.cs.curvewrecker.entities;

import java.io.Serializable;

/**
 * Created by LZ on 15/12/3.
 */
public class MyPlan extends MyMission implements Serializable {
    private String desc;

    public MyPlan(){
        super();
        desc = "";
    }

    public MyPlan(String name, int type, MyTime strTime, MyTime endTime, String desc) {
        super(name, type, strTime, endTime);
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public MyPlan copy(){
        MyPlan newPlan = new MyPlan(this.name, this.type, this.strTime, this.endTime, this.desc);
        return newPlan;

    }
}
