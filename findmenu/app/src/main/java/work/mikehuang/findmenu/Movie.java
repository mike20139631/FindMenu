package work.mikehuang.findmenu;

public class Movie {
    private int type;
    private String name;
    private String time;
    public Movie(int type,String name,String time) {
        this.type = type;
        this.name = name;
        this.time = time;
    }
    public int getType(){
        return type;
    }
    public void setType(int type){
        this.type = type;
    }
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getTime(){
        return time;
    }
    public void setTime(String time){
        this.time = time;
    }
}
