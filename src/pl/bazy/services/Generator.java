package pl.bazy.services;

import pl.bazy.data.Student;

import java.io.*;
import java.util.Random;

/**
 * Created by Art on 2014-10-21.
 */
public class Generator {

    public static Student student(int index){
        Student s = new Student();
        s.setIndex(index);
        for(int i=0;i<Student.NUM_OF_RATINGS;i++){
            s.setRating(i,randomRating());
        }

        return s;
    }

    public static File generateStudentFile(){
        File f = new File("plik.txt");
        FileWriter os = null;
        try {
            os = new FileWriter(f);

        int j = 1;
        Student s = student(j);
        StringBuilder sb = new StringBuilder();
        sb.append(s.getIndex());
        sb.append("\n");
        for(int i=0;i<Student.NUM_OF_RATINGS;i++)
            sb.append(s.getRating(i)+"\n");

            os.write(sb.toString());
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return f;
    }

    private static double randomRating(){
        Random r = new Random();
        return r.nextDouble()*6;
    }
}
