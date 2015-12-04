package hk.hku.cs.curvewrecker.entities;

import java.util.List;

/**
 * Created by LZ on 15/12/3.
 */
public class MyUser {
    private int uid;
    private String name;
    private int gender;
    private String imgPath;
    private int title;
    private MyTime sleepTime;
    private MyTime studyTime;
    private int targetFinished;
    private int targetNotFinished;
    private List<MyTarget> myTargetList;
    private List<MyStar> myStarList;
    private MyRank myRank;
    private MyDress myDress;
    private List<MyPlan> myPlanList;
    private MyMission crtMission;


    public MyUser(){
        uid = 0;
        name = "";
        gender = 0;
        imgPath = "";
        title = 0;
        sleepTime = new MyTime();
        studyTime = new MyTime();
        targetFinished = 0;
        targetNotFinished = 0;
        myRank = new MyRank();
        myDress = new MyDress();
        crtMission = new MyMission();
    }

    public int getUid(){
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getName(){
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTitle() {
        return title;
    }

    public void setTitle(int title) {
        this.title = title;
    }

    public MyTime getSleepTime() {
        return sleepTime;
    }

    public void setSleepTime(MyTime sleepTime) {
        this.sleepTime = sleepTime;
    }

    public MyTime getStudyTime() {
        return studyTime;
    }

    public void setStudyTime(MyTime studyTime) {
        this.studyTime = studyTime;
    }

    public int getTargetFinished() {
        return targetFinished;
    }

    public void setTargetFinished(int targetFinished) {
        this.targetFinished = targetFinished;
    }

    public int getTargetNotFinished() {
        return targetNotFinished;
    }

    public void setTargetNotFinished(int targetNotFinished) {
        this.targetNotFinished = targetNotFinished;
    }

    public List<MyTarget> getMyTargetList() {
        return myTargetList;
    }

    public void setMyTargetList(List<MyTarget> myTargetList) {
        this.myTargetList = myTargetList;
    }

    public List<MyStar> getMyStarList() {
        return myStarList;
    }

    public void setMyStarList(List<MyStar> myStarList) {
        this.myStarList = myStarList;
    }

    public MyRank getMyRank() {
        return myRank;
    }

    public void setMyRank(MyRank myRank) {
        this.myRank = myRank;
    }

    public MyDress getMyDress() {
        return myDress;
    }

    public void setMyDress(MyDress myDress) {
        this.myDress = myDress;
    }

    public List<MyPlan> getMyPlanList() {
        return myPlanList;
    }

    public void setMyPlanList(List<MyPlan> myPlanList) {
        this.myPlanList = myPlanList;
    }

    public MyMission getCrtMission() {
        return crtMission;
    }

    public void setCrtMission(MyMission crtMission) {
        this.crtMission = crtMission;
    }
}
