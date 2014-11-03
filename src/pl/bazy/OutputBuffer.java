package pl.bazy;

import pl.bazy.data.Student;

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
    private Student[] buffer;
    private int bufferSize;

    public OutputBuffer(File f,int bufferSize){
        this.f = f;
        this.bufferSize = bufferSize;
        this.buffer = new Student[bufferSize];
        try {
            this.fw = new FileWriter(f);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveItem(Student s){
        buffer[position] = s;
        position++;
        if(position >= bufferSize)
            forceSave();
    }

    public void forceSave(){
        for(int i=0;i<position;i++){
            try {
                fw.write(buffer[i].serialize()+"\n");
                if(ProjektBazy.DEBUG){
                    System.out.println("Zapsiuje do pliku:\n " + buffer[i].serialize());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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
}
