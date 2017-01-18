/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kovtun.WorkTimeMap;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author kovtun
 */
public class Settings {
    private JSONObject settingsObject=null;
    public Settings() throws IOException {

        
        try {
            settingsObject =(JSONObject) new JSONParser().parse(readSettingsFile());
        } catch (org.json.simple.parser.ParseException ex) {
            Logger.getLogger(Settings.class.getName()).log(Level.SEVERE, null, ex);
        }
        

    }

    private String readSettingsFile() throws FileNotFoundException, IOException {
        File file=new File("Settings.json");
        FileReader reader=new FileReader(file);
        BufferedReader bufferedReader=new BufferedReader(reader);
        String out=bufferedReader.readLine();
        bufferedReader.close();
        return out;
    }

    public String getUserLogin(){
        return (String)settingsObject.get(Constants.USER_LOGIN);
    }

    public String getUserPassword(){
        return (String)settingsObject.get(Constants.USER_PASSWORD);
    }

    public String getRepeatTime(){
        return (String)settingsObject.get(Constants.INTERVAL);
    }

    public void setUserLogin(String userName){
        settingsObject.put(Constants.USER_LOGIN,userName);
        saveToFile();
    }

    public void setUserPassword(String userPassword){
        settingsObject.put(Constants.USER_PASSWORD,userPassword);
        saveToFile();
    }

    public void setRepeatTime(int repeatTime){
        settingsObject.put(Constants.INTERVAL,String.valueOf(repeatTime));
        saveToFile();
    }

    private void saveToFile(){

        try {
            File file=new File("Settings.json");
            FileWriter reader=new FileWriter(file);
            BufferedWriter buffer=new BufferedWriter(reader);
            buffer.write(settingsObject.toString());
            buffer.flush();
            buffer.close();
        } catch (IOException e) {
            System.out.println("Can't write settings file");
        }
    }
}
