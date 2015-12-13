package hk.hku.cs.curvewrecker.entities;

import java.io.Serializable;

/**
 * Created by LZ on 15/12/3.
 */
public class MyFriend implements Serializable {
    //the class store all the information about friends
    private int uid;
    private String name;
    private int gender;
    private int imgPath;
    private int title;
    private int targetFinished;
    private int targetNotFinished;
    private MyTime sleepTime;
    private MyTime studyTime;
    private MyAttributes myAttributes;
    private int mark;

    public  MyFriend(){

        uid = 0;
        name = "";
        gender = 0;
        imgPath = 0;
        title = 0;
        sleepTime = new MyTime();
        studyTime = new MyTime();
        targetFinished = 0;
        targetNotFinished = 0;
        myAttributes = new MyAttributes();
        mark = 0;

    }

    public MyFriend(int uid, String name, int gender, int imgPath, int title, int targetFinished, int targetNotFinished, MyTime sleepTime, MyTime studyTime, MyAttributes myAttributes, int mark) {
        this.uid = uid;
        this.name = name;
        this.gender = gender;
        this.imgPath = imgPath;
        this.title = title;
        this.targetFinished = targetFinished;
        this.targetNotFinished = targetNotFinished;
        this.sleepTime = sleepTime;
        this.studyTime = studyTime;
        this.myAttributes = myAttributes;
        this.mark = mark;
    }

    public MyFriend(int uid, String name, int mark){
        this.uid = uid;
        this.name = name;
        this.gender = 0;
        this.imgPath = 0;
        this.title = 0;
        this.sleepTime = new MyTime();
        this.studyTime = new MyTime();
        this.targetFinished = 0;
        this.targetNotFinished = 0;
        this.myAttributes = new MyAttributes();
        this.mark = mark;
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

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getImgPath() {
        return imgPath;
    }

    public void setImgPath(int imgPath) {
        this.imgPath = imgPath;
    }

    public int getTitle() {
        return title;
    }

    public void setTitle(int title) {
        this.title = title;
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

    public MyAttributes getMyAttributes() {
        return myAttributes;
    }

    public void setMyAttributes(MyAttributes myAttributes) {
        this.myAttributes = myAttributes;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    public MyFriend copy(){
        MyFriend newFriend = new MyFriend(this.uid, this.name, this.gender, this.imgPath,
                                            this.title, this.targetFinished, this.targetNotFinished,
                                            this.sleepTime.copy(),this.studyTime.copy(),this.myAttributes.copy(), this.mark);
        return newFriend;
    }


}
