package util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class FileInput {
    private BufferedReader reader;
    private boolean eof;

    public FileInput(){
        eof = false;
    }

    public boolean setFile(String fileName){
        eof = false;
        try{
            FileReader fr = new FileReader(fileName);
            reader = new BufferedReader(fr);
        }catch (FileNotFoundException e){
            return false;
        }
        return true;
    }

    public String readLine(){
        String s = null;
        try{
            s = reader.readLine();
            if(s==null) eof=true;
        }catch(IOException e){
            e.printStackTrace();
        }
        return s;
    }

    public void close(){
        try{
            reader.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public boolean isDone(){
        return eof;
    }
}
