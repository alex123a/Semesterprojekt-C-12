package data.logger;

import java.io.File;
import java.io.OutputStream;

public class LogHandler {

    private LogHandler logHandler = null;

    private OutputStream outputStream = null;

    private File file = null;

    private LogHandler(){
        //todo create file and output stream here
    }

    //singleton getInstance architecture
    public LogHandler getInstance(){

        if(logHandler == null){
            logHandler = new LogHandler();
        }
        return logHandler;

    }

    //todo method to write to file

}
