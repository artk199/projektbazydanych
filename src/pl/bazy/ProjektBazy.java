package pl.bazy;

import pl.bazy.data.FieldType;

import java.io.File;
import static pl.bazy.Settings.INPUT_BUFFER_SIZE;
import static pl.bazy.Settings.NUM_OF_ELEMENTS;
import static pl.bazy.Settings.OUTPUT_BUFFER_SIZE;

/**
 * Created by Art on 2014-10-26.
 */
public class ProjektBazy {

    //Liczba dostepów do dysku
    private int diskAccesses = 0;

    private Tape mainTape;
    private Tape[] subsidiaryTapes = new Tape[Settings.NUM_OF_TAPES];

    int runs = 1;
    boolean init = true;
    public ProjektBazy(File file) {
        mainTape = new Tape(file);
        subsidiaryTapes[0] = new Tape(new File("plik1.txt"));
        subsidiaryTapes[1] = new Tape(new File("plik2.txt"));
    }

    public void distribute(){
        FieldType item;
        FieldType lastOne = null;
        int activeTape = 0;

        mainTape.getInputBuffer().open();
        for(Tape t : subsidiaryTapes)
            t.getOutputBuffer().open();

        while ((item = mainTape.getInputBuffer().getItem()) != null) {
            if (item.compareTo(lastOne) < 0) {
                activeTape++;
                activeTape %= Settings.NUM_OF_TAPES;
                if(init)
                    runs++;
            }
            subsidiaryTapes[activeTape].getOutputBuffer().saveItem(item);
            lastOne = item;
        }

        mainTape.getInputBuffer().close();
        for(Tape t : subsidiaryTapes)
            t.getOutputBuffer().close();
        if (init)
            System.out.println("W pliku jest "+ runs + " serii.");
        init = false;
    }

    public void merge(){

        mainTape.getOutputBuffer().open();
        for(Tape t : subsidiaryTapes)
            t.getInputBuffer().open();

        FieldType lastTape1 = null;
        FieldType lastTape2 = null;
        FieldType s1 = subsidiaryTapes[0].getInputBuffer().getItem();
        FieldType s2 = subsidiaryTapes[1].getInputBuffer().getItem();
        boolean[] changeSeries = new boolean[2];
        while (s1 != null && s2 != null) {

            //jeżeli jest zmiana serii na taśmie 1
            if (s1.compareTo(lastTape1) <= 0) {
                changeSeries[0] = true;
            } else {
                changeSeries[0] = false;
            }

            if (s2.compareTo(lastTape2) <= 0) {
                changeSeries[1] = true;
            } else {
                changeSeries[1] = false;
            }

            //Jeżeli serie na obydwu ciągle trwają to wal dalej
            if (!changeSeries[0] && !changeSeries[1]) {
                if (s1.compareTo(s2) > 0) {
                    mainTape.getOutputBuffer().saveItem(s2);
                    lastTape2 = s2;
                    s2 = subsidiaryTapes[1].getInputBuffer().getItem();
                } else {
                    mainTape.getOutputBuffer().saveItem(s1);
                    lastTape1 = s1;
                    s1 = subsidiaryTapes[0].getInputBuffer().getItem();
                }
            } else {
                //Jeżeli zmiana na 1 taśmie to wypisz całą 2 taśmę
                if (changeSeries[0]) {
                    //Wypisz do końca serię nr 2;
                    FieldType temp = s2;
                    while (temp.compareTo(s2) <= 0 && s2 != null) {
                        mainTape.getOutputBuffer().saveItem(s2);
                        temp = s2;
                        s2 = subsidiaryTapes[1].getInputBuffer().getItem();
                    }
                } else {
                    FieldType temp = s1;
                    while (temp.compareTo(s1) <= 0 && s1 != null) {
                        mainTape.getOutputBuffer().saveItem(s1);
                        temp = s1;
                        s1 = subsidiaryTapes[0].getInputBuffer().getItem();
                    }
                }
                lastTape2 = null;
                lastTape1 = null;
            }
        }
        while (s1 != null) {
            mainTape.getOutputBuffer().saveItem(s1);
            s1 = subsidiaryTapes[0].getInputBuffer().getItem();
        }
        while (s2 != null) {
            mainTape.getOutputBuffer().saveItem(s2);
            s2 = subsidiaryTapes[1].getInputBuffer().getItem();
        }
        mainTape.getOutputBuffer().close();
        for(Tape t : subsidiaryTapes)
            t.getInputBuffer().close();
    }

    public boolean nextStep(){
        distribute();
        if (subsidiaryTapes[1].getFile().length() == 0) {
            mainTape.clearConuter();
            for(Tape t : subsidiaryTapes) {
                t.clearConuter();
            }
            return true;
        }
        merge();
        return false;
    }

    public void sort() {
        int i=0;
        while (!nextStep()){
            i++;
            diskAccesses += mainTape.getCounter();
            for(Tape t : subsidiaryTapes) {
                System.out.println("tapes: ");
                System.out.println("zapis:\t" + t.getOutputBuffer().getCounter() + "\todczyt:\t" + t.getInputBuffer().getCounter());
                diskAccesses += t.getCounter();
                t.clearConuter();
            }
            System.out.println("mainTape");
            System.out.println("zapis:\t" + mainTape.getOutputBuffer().getCounter() + "\todczyt:\t" + mainTape.getInputBuffer().getCounter());

            mainTape.clearConuter();
            System.out.println(i + " diskAccesses : " + diskAccesses + " powinno byc " + (4*NUM_OF_ELEMENTS*i/INPUT_BUFFER_SIZE));
        };


    }
}
