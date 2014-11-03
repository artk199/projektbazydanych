package pl.bazy;

import pl.bazy.data.Student;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * Created by Art on 2014-10-26.
 */
public class ProjektBazy {

    public static boolean DEBUG = true;
    //Liczba dostepów do dysku
    private int diskAccesses = 0;
    private File file;

    private LinkedList<Comparable> seria1 = new LinkedList<Comparable>();
    private LinkedList<Comparable> seria2 = new LinkedList<Comparable>();


    public ProjektBazy(File file) {
        this.file = file;
    }

    public void sort(){
        //Wczytaj plik
        InputStream    fis;
        BufferedReader br;
        String         line;
        LinkedList<Comparable> seria = seria1;
        LinkedList<Comparable> wszystkie = new LinkedList<Comparable>();
        try {
            Scanner scanner = new Scanner(file);

            while (scanner.hasNext()) {
                Student s = new Student();
                s.setIndex(scanner.nextInt());
                int number = 0;
                while(number < Student.NUM_OF_RATINGS){
                    s.setRating(number, Double.parseDouble(scanner.next()));
                    number++;
                }
                wszystkie.add(s);
                //System.out.println(s);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(wszystkie);
        //Dopóki nie wczytasz wszystkiego zapisuj na serie 1 i 2
       Comparable last = null;
        for(Comparable s : wszystkie){

           if(last == null || s.compareTo(last) < 0){
               if(seria == seria1)
                    seria = seria2;
               else
                    seria = seria1;
              System.out.println("Zamieniam serie!");
           }
            System.out.println(s);
            seria.addLast(s);
            last = s;
       }
        System.out.println("SERIA 1");
        System.out.println(seria1);
        System.out.println("SERIA 2");
        System.out.println(seria2);
        System.out.println(seria1.size() + seria2.size());
        wszystkie.clear();

        Comparable item1 = null;
        Comparable item2 = null;

        while(!seria1.isEmpty() || !seria2.isEmpty()){
            if(seria1.isEmpty()){
                if (item2 == null)
                    item2 = seria2.pop();
                wszystkie.add(item2);
                System.out.println("Dodaje: " + item2);
                item2 = null;
                continue;
            }
            if(seria2.isEmpty()){
                if (item1 == null)
                    item1 = seria1.pop();
                wszystkie.add(item1);
                System.out.println("Dodaje: " + item1);
                item1 = null;
                continue;
            }

            if(item1 == null)
                item1 = seria1.pop();
            if(item2 == null)
                item2 = seria2.pop();

            if(item1.compareTo(item2) > 0){
                wszystkie.add(item2);
                System.out.println("Dodaje: " + item2);
                item2 = null;
            }else{
                wszystkie.add(item1);
                System.out.println("Dodaje: " + item1);
                item1 = null;
            }
        }
        System.out.println(wszystkie);
        System.out.println(wszystkie.size());

    }
}
