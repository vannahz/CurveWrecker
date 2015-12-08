package hk.hku.cs.curvewrecker.entities;

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

    private MyUser myUser;
    private MyTime lastLoginDate;

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


    public void initialFakeData(){
        myUser.initialFakeData();
    }


    public MyTime getlastLoginDate() {
        return lastLoginDate;
    }

    public void setlastLoginDate(MyTime lastLoginDate) {
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
        File fileCheck = new File("./player/data.bin");
        if(!fileCheck.exists()){
            return false;
        }
        else{
            try {
                FileInputStream fileInputStream = new FileInputStream("./player/data.bin");
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

            return true;
        }
    }

    public void saveFile() {
        try{
            FileOutputStream fileOutputStream = new FileOutputStream("./player/data.bin");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(this.myUser);
            objectOutputStream.close();
        }catch(FileNotFoundException e){
            e.printStackTrace();

        }catch(IOException e){
            e.printStackTrace();
        }

    }

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
        return 0;
    }

    

    public boolean connectServer(){
        return false;
    }

    //uid need to be signed by the system, so need to check database to get the uid value

}
