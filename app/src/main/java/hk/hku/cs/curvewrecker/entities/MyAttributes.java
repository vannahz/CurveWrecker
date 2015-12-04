package hk.hku.cs.curvewrecker.entities;

/**
 * Created by LZ on 15/12/3.
 */
public class MyAttributes {
    private int profession;
    private long exp;
    private int level;
    private int intelligence;
    private int endurance;
    private int force;



    public MyAttributes(){
        profession = 0;
        exp = 0;
        level = 0;
        intelligence = 0;
        endurance = 0;
        force = 0;
    }


    public MyAttributes(int profession, long exp, int level, int intelligence, int endurance, int force) {
        this.profession = profession;
        this.exp = exp;
        this.level = level;
        this.intelligence = intelligence;
        this.endurance = endurance;
        this.force = force;
    }


    public int getProfession() {
        return profession;
    }

    public void setProfession(int profession) {
        this.profession = profession;
    }

    public long getExp() {
        return exp;
    }

    public void setExp(long exp) {
        this.exp = exp;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getIntelligence() {
        return intelligence;
    }

    public void setIntelligence(int intelligence) {
        this.intelligence = intelligence;
    }

    public int getEndurance() {
        return endurance;
    }

    public void setEndurance(int endurance) {
        this.endurance = endurance;
    }

    public int getForce() {
        return force;
    }

    public void setForce(int force) {
        this.force = force;
    }
}
