package hk.hku.cs.curvewrecker.entities;

import java.util.ArrayList;
import java.util.Date;


/**
 * Created by LZ on 15/12/3.
 */
public class MyRank {
    final int maxSize = 5;
    ArrayList<MySubRank> mySubRankList;
    MyTime myTime;

    public MyRank(){
        mySubRankList = new ArrayList<MySubRank>(maxSize);
        myTime = new MyTime();
    }

    public MyRank(MyTime newTime) {
        mySubRankList = new ArrayList<MySubRank>(maxSize);
        this.myTime = newTime;
    }

    public ArrayList<MySubRank> getMySubRankList() {
        return mySubRankList;
    }

    public void setMySubRankList(ArrayList<MySubRank> mySubRankList) {
        this.mySubRankList = mySubRankList;
    }

    public boolean addMyRankList(MySubRank newSubRank){

        return this.mySubRankList.add(newSubRank);

    }

    public boolean addMyRankList(MySubRank newSubRank, int index){

        if(index < this.maxSize) {
            this.mySubRankList.add(index, newSubRank);
            return true;
        }else{
            return false;
        }

    }

    public MyTime getMyDate() {
        return myTime;
    }

    public void setMyDate(MyTime myDate) {
        this.myTime = myTime;
    }


    public int getMaxSize() {
        return maxSize;
    }
}
