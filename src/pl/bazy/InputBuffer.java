package pl.bazy;

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
    private Student[] buffer;
    private int position;
    private int max;
    private int counter;

    public InputBuffer(File f,int bufferSize){
        this.f = f;

        this.bufferSize = bufferSize;

        this.buffer = new Student[bufferSize];

        this.position = bufferSize;

        this.counter = 0;

        try {
            this.scanner = new Scanner(f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Student getItem(){
        //Jeżeli buffer się zrobił pusty wczytaj kolejne rekordy z pliku
        if(position >= bufferSize){
            position = 0;
            max = 0;
            while(scanner.hasNext() && max < bufferSize) {
                Student s = new Student();
                s.setIndex(scanner.nextInt());
                int number = 0;
                while(number < Student.NUM_OF_RATINGS){
                    s.setRating(number, Double.parseDouble(scanner.next()));
                    number++;
                }
                buffer[max] = s;
                max++;
            }
            if(ProjektBazy.DEBUG){
                System.out.println("Wczytałem " + max + " pozycji: ");
                for(int i=0;i<max;i++)
                    System.out.println(i+" "+buffer[i]);
            }
            counter++;
        }

        Student s = null;
        if(position < max){
            s = buffer[position];
            position++;
        }
        return s;
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
