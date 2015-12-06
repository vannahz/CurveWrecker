package hk.hku.cs.curvewrecker.entities;

import java.io.Serializable;

/**
 * Created by LZ on 15/12/3.
 */
public class MySubRank implements Serializable {
    private int uid;
    private String name;
    private int mark;
    private int rank;

    public MySubRank(){
        this.uid = 0;
        this.name = "";
        this.mark = 0;
        this.rank = 0;
    }

    public MySubRank(int uid, String name, int mark, int rank) {
        this.uid = uid;
        this.name = name;
        this.mark = mark;
        this.rank = rank;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public MySubRank copy(){
        MySubRank newSubRank = new MySubRank(this.uid, this.name, this.mark, this.rank);
        return newSubRank;
    }
}

