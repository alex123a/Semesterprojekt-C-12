package data.logger;

import Interfaces.IUser;

import java.io.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class LogHandler {

    private static LogHandler logHandler = null;

    private PrintWriter outputStream = null;

    private File file = null;

    private String fileName = "logfile.txt";

    //format for timestamp
    private static final SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");

    private LogHandler(){

        try{
            file = new File(fileName);
            if(file.createNewFile()){
                System.out.println("File Created");
            } else{
                System.out.println("File already exist");
            }
        } catch (IOException e){
            e.printStackTrace();
        }
        try{
            outputStream = new PrintWriter(new FileOutputStream(file, true));
        } catch(FileNotFoundException e){
            e.printStackTrace();
        }

    }

    //singleton getInstance architecture
    public static LogHandler getInstance(){

        if(logHandler == null){
            logHandler = new LogHandler();
        }
        return logHandler;

    }

    public void writeLog(String logTxt, IUser user) {

        //output written
        outputStream.println(sdf1.format(new Timestamp(System.currentTimeMillis())) + " : " + logTxt + " by: " + user.getUsername());
        outputStream.flush();

    }

}


