package hk.hku.cs.curvewrecker.entities;

/**
 * Created by LZ on 15/12/3.
 */
public class MyDress {
    String name;
    int type;
    int level;
    String filePath;

    public MyDress(){
        name = "";
        type = 0;
        level = 0;
        filePath = "";
    }

    public MyDress(String name, int type, int level, String filePath) {
        this.name = name;
        this.type = type;
        this.level = level;
        this.filePath = filePath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
