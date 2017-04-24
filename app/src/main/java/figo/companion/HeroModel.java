package figo.companion;

import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Hashtable;

/**
 * Created by figo on 1/10/17.
 */

public class HeroModel implements Serializable{
    private String name;
    private String biography;
    private String damage;
    private String imagePath;
    private Hashtable<Integer, String> skills;

    private Integer STR;
    private Integer DEX;
    private Integer CON;
    private Integer INT;
    private Integer WIS;
    private Integer CHA;

    HeroModel()  {
        STR = DEX = CON = INT = WIS = CHA = 0;

        skills = new Hashtable<Integer, String>();
    }

    public void setDamage(String damage) {
        this.damage = damage;
    }

    public String getDamage() {
        return damage;
    }

    public ArrayList<String> getSkillsList(){
        return null;
    }

    public String getSkill(int n){
        return skills.get(n);
    }

    public void addSkill(Integer n, String description){
        skills.put(n, description);
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setBiography(String b){
        this.biography = b;
    }

    public String getName() {
        return name;
    }

    public String getBiography() {
        return biography;
    }

    public void setStat(String stat, Integer value){
        switch (stat){
            case "STR":
                STR = value;
                break;
            case "DEX":
                DEX = value;
                break;
            case "CON":
                CON = value;
                break;
            case "INT":
                INT = value;
                break;
            case "WIS":
                WIS = value;
                break;
            case "CHA":
                CHA = value;
                break;
        }

    }

    public Integer getState(String stat){
        switch (stat){
            case "STR":
                return STR;
            case "DEX":
                return DEX;
            case "CON":
                return CON;
            case "INT":
                return INT;
            case "WIS":
                return WIS;
            case "CHA":
                return CHA;
            default:
                return 0;
        }
    }

    public Boolean saveHero(HeroModel obj){
        final File suspend_f = new File(Environment.getExternalStorageDirectory(), "myHero");

        FileOutputStream   fos  = null;
        ObjectOutputStream oos  = null;
        boolean            keep = true;

        try {
            fos = new FileOutputStream(suspend_f);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(obj);

        } catch (Exception e) {
            keep = false;
            Log.v("Save", e.toString());
        } finally {
            try {
                if (oos != null)   oos.close();
                if (fos != null)   fos.close();
                if (keep == false) suspend_f.delete();
            } catch (Exception e) { /* do nothing */ }
        }

        return keep;
    }

    public static HeroModel loadHero(){
        final File suspend_f=new File(Environment.getExternalStorageDirectory(), "myHero");

        HeroModel simpleClass= null;
        FileInputStream fis = null;
        ObjectInputStream is = null;

        try {
            fis = new FileInputStream(suspend_f);
            is = new ObjectInputStream(fis);
            simpleClass = (HeroModel) is.readObject();
        } catch(Exception e) {
            String val= e.getMessage();
        } finally {
            try {
                if (fis != null)   fis.close();
                if (is != null)   is.close();
            } catch (Exception e) { }
        }

        if (simpleClass == null){
            Log.v("Load", "NotLoaded");
            return new HeroModel();
        }else{
            Log.v("Load", "Loaded");

            return simpleClass;
        }
    }
}
