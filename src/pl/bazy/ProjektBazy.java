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

        InputBuffer buffer = new InputBuffer(file,10);
        OutputBuffer tapes[] = new OutputBuffer[2];
        tapes[0] = new OutputBuffer(new File("tape1.txt"),10);
        tapes[1] = new OutputBuffer(new File("tape2.txt"),10);

        Student s;
        int l = 0;
        Student lastOne = null;
        int activeTape = 0;
        while((s = buffer.getItem()) != null){
            if(s.compareTo(lastOne)<0){
                activeTape++;
                activeTape%=2;
            }
            tapes[activeTape].saveItem(s);
            lastOne = s;
        }
        tapes[0].close();
        tapes[1].close();

        /*
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
*/
    }
}
