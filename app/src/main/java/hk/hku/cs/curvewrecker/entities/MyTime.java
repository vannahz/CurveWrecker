package hk.hku.cs.curvewrecker.entities;

import org.joda.time.DateTime;

import java.io.Serializable;

/**
 * Created by LZ on 15/12/3.
 */
public class MyTime implements Serializable {
    private int second;
    private int minute;
    private int hour;
    private int day;
    private int month;
    private int year;



    public MyTime(){
        second = 0;
        minute = 0;
        hour = 0;
        day = 0;
        month = 0;
        year = 0;
    }

    public MyTime(int minute, int hour){
        this.minute = minute;
        this.hour = hour;
        this.second = 0;
        this.day = 0;
        this.month = 0;
        this.year = 0;
    }

    public MyTime(int day, int month, int year){
        this.day = day;
        this.month = month;
        this.year = year;
        second = 0;
        minute = 0;
        hour = 0;
    }

    public MyTime(int second, int minute, int hour, int day, int month, int year) {
        this.second = second;
        this.minute = minute;
        this.hour = hour;
        this.day = day;
        this.month = month;
        this.year = year;
    }


    public void getCurrentTime(){

        DateTime newTime = new DateTime();
        this.second = newTime.getSecondOfMinute();
        this.minute = newTime.getMinuteOfHour();
        this.hour = newTime.getHourOfDay();
        this.day = newTime.getDayOfMonth();
        this.month = newTime.getMonthOfYear();
        this.year = newTime.getYear();

    }

    public int getDayOfYear(){

        DateTime newTime = new DateTime(this.year,this.month,this.day,this.hour,this.minute,this.second);
        return newTime.getDayOfYear();

    }

    public int getSecond() {
        return second;
    }

    public void setSecond(int second) {
        this.second = second;
    }

    public int getTotalSeconds(){
        return (this.hour * 60 + this.minute)*60 + this.second;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getTotalMinute(){
        return this.hour*60 + this.minute;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public float getTotalHour(){


        return (getHour()+getMinute()/60);
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }


    public int getMonth() {
        return month;
    }


    public void setMonth(int month) {
        this.month = month;
    }

    public void resetTimeBySec(int newSec){
        this.hour = newSec/3600;
        this.minute = newSec/60 - this.hour * 60;
        this.second = newSec%60;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }


    public boolean equal(MyTime tempT){

        return (this.getYear() == tempT.getYear()
                && this.getMonth() == tempT.getMonth()
                && this.getDay() == tempT.getDay()
                && this.getHour() == tempT.getHour()
                && this.getMinute() == tempT.getMinute()
                && this.getSecond() == tempT.getSecond());
    }

    public boolean equalDate(MyTime tempT){
        return (this.getYear() == tempT.getYear()
                && this.getMonth() == tempT.getMonth()
                && this.getDay() == tempT.getDay());
    }
    public MyTime copy(){
        MyTime newTime = new MyTime();
        newTime.setSecond(this.second);
        newTime.setMinute(this.minute);
        newTime.setHour(this.hour);
        newTime.setDay(this.day);
        newTime.setMonth(this.month);
        newTime.setYear(this.year);
        return newTime;
    }


}
