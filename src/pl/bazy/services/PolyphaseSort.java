package pl.bazy.services;

import pl.bazy.Tape;
import pl.bazy.data.FieldType;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Art on 2014-11-07.
 */
public class PolyphaseSort {

    Tape[] tapes = new Tape[4];
    FibonacciNumbers fibb = new FibonacciNumbers();

    public PolyphaseSort(File f) {
        tapes[0] = new Tape(f);
        for (int i = 1; i < tapes.length; i++) {
            tapes[i] = new Tape(new File(new String("ptape" + i + ".txt")));
        }
        this.distribute();
    }

    public void distribute(){
        FieldType item;
        tapes[0].getInputBuffer().open();
        for (int i = 1; i < tapes.length; i++) {
            tapes[i].getOutputBuffer().open();
        }
        int numberOfTape = 1;
        int numberOfSeries = fibb.getNext();
        int currentElement = 0;
        while((item = tapes[0].getInputBuffer().getItem()) != null){
            if(currentElement >= numberOfSeries){
                currentElement = 0;
                numberOfSeries = fibb.getNext();
                numberOfTape++;
                if(numberOfTape >= tapes.length)
                    numberOfTape = 1;
            }
            tapes[numberOfTape].getOutputBuffer().saveItem(item);
            currentElement++;
        }
        tapes[0].getInputBuffer().close();
        for (int i = 1; i < tapes.length; i++) {
            tapes[i].getOutputBuffer().close();
        }
        tapes[0].clear();
    }

    public void nextStep(){
        int emptyTape = -1;
        //Wyszukanie pustej taśmy
        for (int i = 0; i < tapes.length; i++) {
            if(tapes[i].isEmpty()){
                emptyTape = i;
                break;
            }
        }
        if(emptyTape == -1)
            System.out.println("Nie znaleziono pustej taśmy!");

        //Sprawdzenie czy w pozostałych seriach jest 1 seria, jeżeli tak to scalenie ich lol
        for (int i = 0; i < tapes.length; i++) {
            if(i == emptyTape)
                continue;
        }
        //Doprowadzenie do pustej taśmy
    }

    class FibonacciNumbers{
        int count = 0;
        private ArrayList<Integer> numbers = new ArrayList<Integer>();

        FibonacciNumbers() {
            numbers.add(1);
            numbers.add(1);
            getNumber(20);
        }
        public int getNumber(int i){
            try{
                return numbers.get(i);
            }catch (IndexOutOfBoundsException e){
                numbers.add(getNumber(i-2)+getNumber(i-1));
                return numbers.get(i);
            }

        }
        public int getNext(){
            int anws = getNumber(count);
            count++;
            return anws;
        }
        public void reset(){
            count = 1;
        }
        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            for(Integer i: numbers)
                sb.append(i+"\t");
            return sb.toString();
        }
    }
}
