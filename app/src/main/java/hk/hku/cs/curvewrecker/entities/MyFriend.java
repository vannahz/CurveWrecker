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
    private String imgPath;
    private int title;
    private int targetFinished;
    private int targetNotFinished;
    private MyTime sleepTime;
    private MyTime studyTime;
    private MyAttributes myAttributes;

    public  MyFriend(){

        uid = 0;
        name = "";
        gender = 0;
        imgPath = "";
        title = 0;
        sleepTime = new MyTime();
        studyTime = new MyTime();
        targetFinished = 0;
        targetNotFinished = 0;
        myAttributes = new MyAttributes();

    }

    public MyFriend(int uid, String name, int gender, String imgPath, int title, int targetFinished, int targetNotFinished, MyTime sleepTime, MyTime studyTime, MyAttributes myAttributes) {
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

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
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

    public MyFriend copy(){
        MyFriend newFriend = new MyFriend(this.uid, this.name, this.gender, this.imgPath,
                                            this.title, this.targetFinished, this.targetNotFinished,
                                            this.sleepTime.copy(),this.studyTime.copy(),this.myAttributes.copy());
        return newFriend;
    }

    @Override
    public String toString() {
        return "MyFriend{" +
                "uid=" + uid +
                ", name='" + name + '\'' +
                ", gender=" + gender +
                ", imgPath='" + imgPath + '\'' +
                ", title=" + title +
                ", targetFinished=" + targetFinished +
                ", targetNotFinished=" + targetNotFinished +
                ", sleepTime=" + sleepTime +
                ", studyTime=" + studyTime +
                ", myAttributes=" + myAttributes +
                '}';
    }

}
