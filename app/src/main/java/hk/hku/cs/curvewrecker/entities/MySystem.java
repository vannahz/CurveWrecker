package hk.hku.cs.curvewrecker.entities;

import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Created by LZ on 15/12/3.
 */
public class MySystem implements Serializable {
    final private int basicExp = 50;

    private MyUser myUser;
    private MyTime lastLoginDate;
    private File filePath;
    private MyTime firstLoginDate;

    public MySystem(){
        myUser = new MyUser();
        lastLoginDate = new MyTime();
        lastLoginDate.getCurrentTime();
    }

    public MySystem(MyUser newUser){
        myUser = newUser.copy();
        lastLoginDate = new MyTime();
        lastLoginDate.getCurrentTime();
    }

    public MySystem(File tempPath){
        myUser = new MyUser();
        lastLoginDate = new MyTime();
        lastLoginDate.getCurrentTime();
        this.filePath = tempPath;
        firstLoginDate = new MyTime();
        firstLoginDate.getCurrentTime();
    }

    public void initialFakeData(){
        myUser.initialFakeData();
        updateTotalStudyTime();
    }


    public MyTime getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(MyTime lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public void updateLoginData(){
        this.lastLoginDate.getCurrentTime();
    }

    public MyUser getMyUser() {
        return myUser;
    }

    public void setMyUser(MyUser myUser) {
        this.myUser = myUser.copy();
    }

    public boolean loadFile(){
        File fileCheck = new File(this.filePath+"/","data.bin");
        if(!fileCheck.exists()){
            return false;
        }
        else{
            try {
                FileInputStream fileInputStream = new FileInputStream(fileCheck);
                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
                MyUser newUser = (MyUser) objectInputStream.readObject();
                this.myUser = newUser.copy();
                objectInputStream.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e){
                e.printStackTrace();
            } catch (ClassNotFoundException e){
                e.printStackTrace();
            }

            updateTotalDay();
            return true;
        }
    }

    public void saveFile() {
        try{
            File fileCheck = new File(this.filePath+"/","data.bin");
            if(!fileCheck.exists()){
                fileCheck.createNewFile();
            }
            FileOutputStream fileOutputStream = new FileOutputStream(fileCheck);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(this.myUser);
            objectOutputStream.close();
        }catch(FileNotFoundException e){
            e.printStackTrace();

        }catch(IOException e){
            e.printStackTrace();
        }

    }


    //the total hour for sleep + study
    public int getTotalHour(){
        int totalHour = 0;
        int totalMin = 0;
        totalHour =myUser.getSleepTarget().getActualTime().getHour() + myUser.getStudyTarget().getActualTime().getHour();
        totalMin = myUser.getSleepTarget().getActualTime().getMinute() +  myUser.getStudyTarget().getActualTime().getMinute();
        for(MyTarget tempT :myUser.getMyTargetList()){

            totalHour += tempT.getActualTime().getHour();
            totalMin += tempT.getActualTime().getMinute();
        }

        return totalHour+totalMin/60;
    }

    public int getCurrentMark(){
        int mark = 0;
        int targetSleepTime = this.myUser.getSleepTime().getHour() * 60 + this.myUser.getSleepTime().getMinute();
        int targetStudyTime = this.myUser.getStudyTime().getHour() * 60 + this.myUser.getStudyTime().getMinute();
        //Log.d("######MySystem:check1", String.format("sleepHour:%d, sleepMin:%d, tarSleep:%d", this.myUser.getSleepTime().getHour(), this.myUser.getSleepTime().getMinute(),targetSleepTime));
        //Log.d("######MySystem:check1", String.format("StudyHour:%d, StudyMin:%d, tarStudy:%d", this.myUser.getStudyTime().getHour(), this.myUser.getStudyTime().getMinute(),targetStudyTime));
        int currentSleepTime = this.myUser.getSleepTarget().getActualTime().getHour() * 60
                                + this.myUser.getSleepTarget().getActualTime().getMinute();
        int currentStudyTime = this.myUser.getStudyTarget().getActualTime().getHour() * 60
                                + this.myUser.getStudyTarget().getActualTime().getMinute();
        //Log.d("######MySystem:check2", String.format("sleepHour:%d, sleepMin:%d, tarSleep:%d", this.myUser.getSleepTime().getHour(), this.myUser.getSleepTime().getMinute(),targetSleepTime));
        //Log.d("######MySystem:check2", String.format("StudyHour:%d, StudyMin:%d, tarStudy:%d", this.myUser.getStudyTime().getHour(), this.myUser.getStudyTime().getMinute(),targetStudyTime));
        if((currentSleepTime > targetSleepTime -30) && (currentSleepTime < targetSleepTime +30)){
            mark+=10;
        }

        mark += currentStudyTime/60;

        //Log.d("######MySystem:check3", String.format("sleepHour:%d, sleepMin:%d, tarSleep:%d", this.myUser.getSleepTime().getHour(), this.myUser.getSleepTime().getMinute(),targetSleepTime));
        //Log.d("######MySystem:check3", String.format("StudyHour:%d, StudyMin:%d, tarStudy:%d", this.myUser.getStudyTime().getHour(), this.myUser.getStudyTime().getMinute(),targetStudyTime));
        if(currentStudyTime >= targetStudyTime){
            mark += 10;
        }

        //Log.d("######MySystem:", String.format("mark:%d, crtStudy:%d, crtSleep:%d", mark, currentStudyTime, currentSleepTime));
        //Log.d("######MySystem:", String.format("tarStudy:%d, tarSleep:%d",targetStudyTime, targetSleepTime));
        return mark;
    }


    public long getCurrentMaxExp(){
        return ((long) (basicExp * Math.pow(2,myUser.getMyAttributes().getLevel())));
    }

    public boolean checkLevelUp(){
        long currentExp = myUser.getMyAttributes().getExp();
        int currentLevel = myUser.getMyAttributes().getLevel();
        if( currentExp >= getCurrentMaxExp()){
            myUser.getMyAttributes().setExp(currentExp-getCurrentMaxExp());
            myUser.getMyAttributes().setLevel(currentLevel+1);
            return true;
        }
        return false;
    }

    public void checkLevelInfo(){
        double exp = 0;
        int level = 0;
        long tempMax = basicExp;
        for(MyTarget tempT: this.myUser.getMyTargetList()){
            if(tempT.getStatus() == 1){
                exp += 10;
            }
            if(tempT.getType() == 1){
                exp += tempT.getActualTime().getTotalHour();
            }
        }

      //  Log.d("MySystem: ", String.format("%f",(exp/tempMax)));
        while((int)(exp/tempMax) != 0){

            exp -= tempMax;
            level++;
            tempMax = ((long) (basicExp * Math.pow(2,level)));

        }

        myUser.getMyAttributes().setExp((long) exp);
        myUser.getMyAttributes().setLevel(level);

    }

    public void updateTotalStudyTime(){
        float tempTotal = 0;
        for(MyTarget tempT:this.getMyUser().getMyTargetList()){
            if(tempT.getType() == 1){
                tempTotal += tempT.getActualTime().getTotalHour();
            }
        }

        this.getMyUser().setTotalStudyTime(tempTotal);
    }

    public void addTotalStudyTime(MyTarget studyTarget){
        float tempTotal = this.getMyUser().getTotalStudyTime();
        if(studyTarget.getType() == 1){
            tempTotal += studyTarget.getActualTime().getTotalHour();
        }

        this.getMyUser().setTotalStudyTime(tempTotal);
    }

    public float getAverageStudyTime(){
        return this.getMyUser().getTotalStudyTime()/this.getMyUser().getTotalDay();
    }

    private void addExp(int addedV){
        this.myUser.getMyAttributes().setExp(this.myUser.getMyAttributes().getExp() + addedV);
        checkLevelUp();
    }

    private void convertMission(MyMission myMission){
        int preS = 0;
        int crtS = 0;
        if(myMission.getType() == 0){
          //  Log.d("####MySystem:", "0");
            preS = this.getMyUser().getSleepTarget().getActualTime().getTotalSeconds();
            if(myMission.isDone()) {
          //      Log.d("####MySystem:", "00");
                crtS = myMission.getTargetTime().getTotalSeconds();
            }
            else{
          //      Log.d("####MySystem:", "01");
                crtS = myMission.getTargetTime().getTotalSeconds() - myMission.getRemainTime().getTotalSeconds();

            }

            this.getMyUser().getSleepTarget().getActualTime().resetTimeBySec(preS + crtS);
        }
        else{
         //   Log.d("MySystem:", "1");
            preS = this.getMyUser().getStudyTarget().getActualTime().getTotalSeconds();

            if(myMission.isDone()) {
         //       Log.d("####MySystem:", "10");
                crtS = myMission.getTargetTime().getTotalSeconds();
            }
            else{
         //       Log.d("####MySystem:", "11");
                crtS = myMission.getTargetTime().getTotalSeconds() - myMission.getRemainTime().getTotalSeconds();
            }

            this.getMyUser().getStudyTarget().getActualTime().resetTimeBySec(preS+crtS);
         //   Log.d("####MySystem: total-", String.format("%d", this.getMyUser().getStudyTarget().getActualTime().getTotalSeconds()));
        }
    }

    public void addMissionToTarget(MyMission myMission){
        int preMark = getCurrentMark();
        int crtMark = 0;

        if(myMission.getStrTime().equalDate(myMission.getEndTime())){
            convertMission(myMission);
            crtMark = getCurrentMark();
            int newExp = crtMark - preMark;
            addExp(newExp);
        }
        else{
            if(myMission.getType() == 0){
                this.myUser.getMyTargetList().add(this.myUser.getSleepTarget().copy());
                this.myUser.setSleepTarget(new MyTarget(0, this.myUser.getSleepTime().copy()));
                convertMission(myMission);
                crtMark = getCurrentMark();
                int newExp = crtMark;
                addExp(newExp);
            }
            else{
                this.myUser.getMyTargetList().add(this.myUser.getStudyTarget().copy());
                this.myUser.setStudyTarget(new MyTarget(1, this.myUser.getStudyTime().copy()));
                convertMission(myMission);
                crtMark = getCurrentMark();
                int newExp = crtMark;
                addExp(newExp);
            }
            updateTotalDay();

        }
    }

    public MyTime getAverageSleep(){
        MyTime aveSleep = new MyTime();

        for(MyTarget tempT: this.getMyUser().getMyTargetList()){
            if(tempT.getType() == 0) {
                aveSleep.resetTimeBySec(aveSleep.getTotalSeconds() + tempT.getActualTime().getTotalSeconds());
            }
        }
        aveSleep.resetTimeBySec(aveSleep.getTotalSeconds()/this.getMyUser().getTotalDay());
        return aveSleep;
    }

    public void updateTotalDay(){
        updateLoginData();
        int tempTD = 0;
        MyTime compareT = this.firstLoginDate.copy();

        //compare year
        while(compareT.getYear() < this.lastLoginDate.getYear()) {
            //lear year
            if (((compareT.getYear() + 1) % 400 == 0)
                    || (((compareT.getYear() + 1) % 4 == 0) && ((compareT.getYear() + 1) % 100 != 0))) {
                compareT.setYear(compareT.getYear()+1);
                tempTD += 366;
            }
            //normal year
            else {
                compareT.setYear(compareT.getYear()+1);
                tempTD += 365;
            }
        }

        tempTD -= (compareT.getDayOfYear() - this.lastLoginDate.getDayOfYear());

        this.getMyUser().setTotalDay(tempTD+1);


    }


    public void addTotalDay() {
        this.getMyUser().setTotalDay(this.getMyUser().getTotalDay()+1);
    }

    public boolean connectServer(){
        return false;
    }

    //uid need to be signed by the system, so need to check database to get the uid value

}
