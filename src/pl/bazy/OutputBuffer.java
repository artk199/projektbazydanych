package pl.bazy;

import pl.bazy.data.FieldType;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by Art on 2014-11-03.
 */
public class OutputBuffer {

    private File f;
    private FileWriter fw;
    private int position;
    private FieldType[] buffer;
    private int bufferSize;
    private int counter;

    public OutputBuffer(File f,int bufferSize){
        this.f = f;
        this.counter = 0;
        this.bufferSize = bufferSize;
        this.buffer = new FieldType[bufferSize];

    }

    public void open(){
        position = 0;
        try {
            this.fw = new FileWriter(f);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveItem(FieldType s){
        buffer[position] = s;
        position++;
        if(position >= bufferSize)
            forceSave();
    }

    public void forceSave(){

        for(int i=0;i<position;i++){
            try {
                fw.write(buffer[i].serialize()+"\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if(Settings.DEBUG)
            System.out.println("Zapisałem " + position + " pozycji.");

        if(position > 0 && position == bufferSize)
            counter++;

        position = 0;
    }

    public void close(){
        this.forceSave();
        try {
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public File getFile() {
        return f;
    }

    public void setFile(File f) {
        this.f = f;
    }
}
