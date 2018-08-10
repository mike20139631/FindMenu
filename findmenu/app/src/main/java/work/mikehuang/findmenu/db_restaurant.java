package work.mikehuang.findmenu;

import com.google.firebase.database.PropertyName;

import java.util.ArrayList;

public class db_restaurant{
    private String name;
    private String uid;
    private String tag;
    private String menu;
    private String url;
    private String updatetime;
    private ArrayList<String> list_tag;

    public db_restaurant(){}
    public db_restaurant(String menu,String name,String tag,String uid,String updatetime,String url){
        this.menu = menu;
        this.name = name;
        this.tag = tag;
        this.uid = uid;
        this.updatetime = updatetime;
        this.url = url;
    }

    public void setMenu(String menu){
        this.menu = menu;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setTag(String tag){
        this.tag = tag;

    }
    public void setUid(String uid){
        this.uid = uid;

    }
    public void setUpdatetime(String updatetime){
        this.updatetime = updatetime;
    }
    public void setUrl(String url){
        this.url = url;
    }

    @PropertyName("menu")
    public String getMenu() {
        return menu;
    }
    @PropertyName("name")
    public String getName() {
        return name;
    }
    @PropertyName("tag")
    public String getTag() {
        return tag;
    }
    @PropertyName("uid")
    public String getUid() {
        return uid;
    }
    @PropertyName("updatetime")
    public String getUpdatetime() {
        return updatetime;
    }
    @PropertyName("url")
    public String getUrl() {
        return url;
    }
    public ArrayList<String> getList_tag() {
        return Utils.stringSplit2Array(getTag(),",");
    }
}
