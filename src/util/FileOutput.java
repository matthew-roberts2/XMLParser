package util;

import java.io.*;

public class FileOutput
{
    private BufferedWriter writer;

    public FileOutput(){}

    public boolean setFile(String fileName){
        try{
            FileWriter fw = new FileWriter(fileName);
            writer = new BufferedWriter(fw);
        }catch (IOException e){
            return false;
        }
        return true;
    }

    public void writeLine(String s){
        try{
            writer.write(s);
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void close(){
        try{
            writer.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
