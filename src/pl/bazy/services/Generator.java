package pl.bazy.services;

import pl.bazy.Settings;
import pl.bazy.data.Student;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;

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

    public static File generateStudentFile(Settings.GenerationWay generationWay){
        switch (generationWay){
            case CONSOLE:
                return generateStudentFileFromConsole();
            case AUTO:
                return generateStudentFile();
            case OLD:
                return new File(Settings.FILE_NAME[0]);
            default:
                return generateStudentFile();
        }
    }

    private static File generateStudentFileFromConsole() {
        File f = new File(Settings.FILE_NAME[0]);
        FileWriter os = null;
        Random r = new Random();
        Scanner scanner = new Scanner(System.in);
        System.out.print("Podaj ilość elementów: ");
        int amount_of_students = scanner.nextInt();
        try {
            os = new FileWriter(f);
            for (int k = 0; k < amount_of_students; k++) {
                System.out.println("Podaj indeks studenta i jego ostatnie " + Settings.NUM_OF_RATINGS + "ocen z kolokwiów: ");
                int j = scanner.nextInt();
                Student s = student(j);
                for (int i = 0; i < NUM_OF_RATINGS; i++)
                    s.setRating(i,scanner.nextDouble());

                os.write(s.serialize() + "\n");


            }
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Date data = new Date();
        try {
            Files.copy(new File(data.getTime()+"-copy.txt").toPath(),f.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return f;
    }

    public static File generateStudentFile() {
        File f = new File(Settings.FILE_NAME[0]);
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
        Date data = new Date();
        File f2 = new File(data.getTime()+"-copy.txt");
        try {
            f2.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            Files.copy(f.toPath(),f2.toPath(), StandardCopyOption.REPLACE_EXISTING);
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
        double l = r.nextDouble() * 199;
        l = Math.ceil(l+1.00001);
        l/=4;
        return l;
    }
}
