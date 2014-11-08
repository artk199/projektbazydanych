package pl.bazy;

import pl.bazy.data.FieldType;
import pl.bazy.data.Student;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.Scanner;

/**
 * Created by Art on 2014-11-03.
 */
public class InputBuffer {

    private File f;
    private Scanner scanner;
    private int bufferSize;
    private FieldType[] buffer;
    private int position;
    private int max;
    private int counter;

    public InputBuffer(File f,int bufferSize){
        this.f = f;

        this.bufferSize = bufferSize;

        this.buffer = new Student[bufferSize];

        this.counter = 0;
    }

    public FieldType getItem(){
        //Jeżeli buffer się zrobił pusty wczytaj kolejne rekordy z pliku
        if(position >= bufferSize){
            position = 0;
            max = 0;
            while(scanner.hasNextLine() && max < bufferSize) {
                FieldType s = new Student();
                s.deserialize(scanner.nextLine());
                buffer[max] = s;
                max++;
            }
            if(Settings.DEBUG){
                System.out.println("Wczytałem " + max + " pozycji: ");
                //for(int i=0;i<max;i++)
               //     System.out.println(i+" "+buffer[i]);
            }
            if(max > 0)
                counter++;
        }

        FieldType s = null;
        if(position < max){
            s = buffer[position];
            position++;
        }
        return s;
    }

    public void open(){
        this.position = bufferSize;
        try {
            this.scanner = new Scanner(f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void close(){
        scanner.close();
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
