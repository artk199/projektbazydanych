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
    private BufferedWriter bw;
    private int position;
    private Student[] buffer;
    private int bufferSize;

    public OutputBuffer(File f,int bufferSize){
        this.f = f;
        this.bufferSize = bufferSize;
        this.buffer = new Student[bufferSize];
        try {
            this.fw = new FileWriter(f);
            this.bw = new BufferedWriter(fw);
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
                bw.write(buffer[i].serialize());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        position = 0;
    }
}
