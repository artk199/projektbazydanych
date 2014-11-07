package pl.bazy.services;

import pl.bazy.data.Student;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import static pl.bazy.Settings.NUM_OF_ELEMENTS;
import static pl.bazy.Settings.NUM_OF_RATINGS;

/**
 * Created by Art on 2014-10-21.
 */
public class Generator {

    public static Student student(int index) {
        Student s = new Student();
        s.setIndex(index);
        for (int i = 0; i < NUM_OF_RATINGS; i++) {
            s.setRating(i, randomRating());
        }

        return s;
    }

    public static File generateStudentFile() {
        File f = new File("plik.txt");
        FileWriter os = null;
        Random r = new Random();
        int amount_of_students = NUM_OF_ELEMENTS;
        try {
            os = new FileWriter(f);
            for (int k = 0; k < amount_of_students; k++) {
                int j = k;
                Student s = student(j);
                StringBuilder sb = new StringBuilder();
                sb.append(s.getIndex() + " ");
                for (int i = 0; i < NUM_OF_RATINGS; i++)
                    sb.append(s.getRating(i) + " ");

                os.write(sb.toString() + "\n");


            }
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return f;
    }

    public static File generateStudentFileForDebug(){
        File f = new File("plik.txt");
        FileWriter os = null;
        int amount_of_students = 100;
        try {
            StringBuilder sb;
            os = new FileWriter(f);

            Student s = new Student();
            s.setRatings(new double[]{2,2,2});
            s.setIndex(1);

            sb = new StringBuilder();
            sb.append(s.getIndex() + " ");
            for (int i = 0; i < NUM_OF_RATINGS; i++)
                sb.append(s.getRating(i) + " ");
            os.write(sb.toString() + "\n");

            s = new Student();
            s.setRatings(new double[]{1,1,1});
            s.setIndex(1);

            sb = new StringBuilder();
            sb.append(s.getIndex() + " ");
            for (int i = 0; i < NUM_OF_RATINGS; i++)
                sb.append(s.getRating(i) + " ");
            os.write(sb.toString() + "\n");
            s = new Student();
            s.setRatings(new double[]{2,2,2});
            s.setIndex(1);

            sb = new StringBuilder();
            sb.append(s.getIndex() + " ");
            for (int i = 0; i < NUM_OF_RATINGS; i++)
                sb.append(s.getRating(i) + " ");
            os.write(sb.toString() + "\n");
            s = new Student();
            s.setRatings(new double[]{4,4,4});
            s.setIndex(1);

            sb = new StringBuilder();
            sb.append(s.getIndex() + " ");
            for (int i = 0; i < NUM_OF_RATINGS; i++)
                sb.append(s.getRating(i) + " ");
            os.write(sb.toString() + "\n");
            s = new Student();
            s.setRatings(new double[]{6,6,6});
            s.setIndex(1);

            sb = new StringBuilder();
            sb.append(s.getIndex() + " ");
            for (int i = 0; i < NUM_OF_RATINGS; i++)
                sb.append(s.getRating(i) + " ");
            os.write(sb.toString() + "\n");

            s = new Student();
            s.setRatings(new double[]{3,3,3});
            s.setIndex(1);

            sb = new StringBuilder();
            sb.append(s.getIndex() + " ");
            for (int i = 0; i < NUM_OF_RATINGS; i++)
                sb.append(s.getRating(i) + " ");
            os.write(sb.toString() + "\n");

            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return f;
    }

    private static double randomRating() {
        Random r = new Random();
        return r.nextDouble() * 6;
    }
}
