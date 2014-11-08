package pl.bazy;

import pl.bazy.data.FieldType;

import java.io.File;
import java.io.IOException;

import static pl.bazy.Settings.INPUT_BUFFER_SIZE;
import static pl.bazy.Settings.NUM_OF_ELEMENTS;

/**
 * Created by Art on 2014-10-26.
 */
public class ProjektBazy {

    //Liczba dostepów do dysku
    private int diskAccesses = 0;

    private int diskAccessesIn = 0;
    private int diskAccessesOut = 0;

    private Tape mainTape;

    private Tape[] subsidiaryTapes = new Tape[Settings.NUM_OF_TAPES];

    int runs = 1;

    private int numberOfFaze = 0;

    private int predictedDiskAccesses = 0;

    boolean init = true;

    public ProjektBazy(File file) {
        mainTape = new Tape(file);
        subsidiaryTapes[0] = new Tape(new File(Settings.FILE_NAME[1]));
        subsidiaryTapes[1] = new Tape(new File(Settings.FILE_NAME[2]));
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
       // if (init)
        //    System.out.println("W pliku jest "+ runs + " serii.");
        init = false;
    }

    public void merge(){

        mainTape.getOutputBuffer().open();
        for(Tape t : subsidiaryTapes)
            t.getInputBuffer().open();

        FieldType[] lastItem = new FieldType[Settings.NUM_OF_TAPES];

        FieldType item[] = new FieldType[Settings.NUM_OF_TAPES];
        for (int i = 0; i < item.length; i++) {
            item[i] = subsidiaryTapes[i].getInputBuffer().getItem();
            lastItem[i] = null;
        }
        boolean[] changeSeries = new boolean[2];
        
        while (item[0] != null && item[1] != null) {

            //jeżeli jest zmiana serii na taśmie 1
            for (int i = 0; i < changeSeries.length; i++) {
                changeSeries[i] = (item[i].compareTo(lastItem[i]) < 0);
            }
            //Jeżeli serie na obydwu ciągle trwają to wal dalej
            if (!changeSeries[0] && !changeSeries[1]) {
                if (item[0].compareTo(item[1]) > 0) {
                    mainTape.getOutputBuffer().saveItem(item[1]);
                    lastItem[1] = item[1];
                    item[1] = subsidiaryTapes[1].getInputBuffer().getItem();
                } else {
                    mainTape.getOutputBuffer().saveItem(item[0]);
                    lastItem[0] = item[0];
                    item[0] = subsidiaryTapes[0].getInputBuffer().getItem();
                }
            } else {
                //Jeżeli zmiana na 1 taśmie to wypisz całą 2 taśmę
                if (changeSeries[0]) {
                    //Wypisz do końca serię nr 2;
                    item[1] = flushSeries(mainTape,subsidiaryTapes[1],item[1]);
                } else {
                    item[0] =flushSeries(mainTape,subsidiaryTapes[0],item[0]);
                }
                for (int i = 0; i < lastItem.length; i++) {
                    lastItem[i] = null;
                }
            }
        }
        while (item[0] != null) {
            mainTape.getOutputBuffer().saveItem(item[0]);
            item[0] = subsidiaryTapes[0].getInputBuffer().getItem();
        }
        while (item[1] != null) {
            mainTape.getOutputBuffer().saveItem(item[1]);
            item[1] = subsidiaryTapes[1].getInputBuffer().getItem();
        }
        mainTape.getOutputBuffer().close();
        for(Tape t : subsidiaryTapes)
            t.getInputBuffer().close();
    }

    private FieldType flushSeries(Tape dest,Tape source,FieldType lastOne){
        FieldType ft = lastOne;
        FieldType temp = lastOne;
        while (temp.compareTo(ft) <= 0 && ft != null) {
            dest.getOutputBuffer().saveItem(ft);
            temp = ft;
            ft = source.getInputBuffer().getItem();
        }
        return ft;
    }

    public boolean nextStep(){
        distribute();
        if (subsidiaryTapes[1].getFile().length() == 0) {
            mainTape.clearCounter();
            for(Tape t : subsidiaryTapes) {
                t.clearCounter();
            }
            return true;
        }
        merge();
        return false;
    }

    public void sort() {
        numberOfFaze=0;
        while (!nextStep()){
            numberOfFaze++;
            diskAccessesIn += mainTape.getInputBuffer().getCounter();
            diskAccessesOut += mainTape.getOutputBuffer().getCounter();

            for(Tape t : subsidiaryTapes) {
                if(Settings.WRITE_DISK_ACC) {
                    System.out.println("tapes: ");
                    System.out.println("zapis:\t" + t.getOutputBuffer().getCounter() + "\todczyt:\t" + t.getInputBuffer().getCounter());
                }
                diskAccessesIn += t.getInputBuffer().getCounter();
                diskAccessesOut += t.getOutputBuffer().getCounter();
                t.clearCounter();
            }

            if(Settings.WRITE_DISK_ACC) {
                System.out.println("mainTape");
                System.out.println("zapis:\t" + mainTape.getOutputBuffer().getCounter() + "\todczyt:\t" + mainTape.getInputBuffer().getCounter());
            }
            mainTape.clearCounter();

            diskAccesses = diskAccessesIn + diskAccessesOut;
            if(Settings.WRITE_DISK_ACC)
                System.out.println(numberOfFaze + " diskAccesses : " + diskAccesses + " powinno byc " + (4*NUM_OF_ELEMENTS*numberOfFaze/INPUT_BUFFER_SIZE));

            if(Settings.WAIT_FOR_KEY)
                try {
                    System.in.read();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            if(Settings.WRITE_FILES){
                mainTape.writeFile();
                for (Tape tape : subsidiaryTapes) {
                    tape.writeFile();
                }
            }
        };
        predictedDiskAccesses = (4*NUM_OF_ELEMENTS*numberOfFaze/INPUT_BUFFER_SIZE);
    }

    public int getDiskAccessesIn() {
        return diskAccessesIn;
    }

    public void setDiskAccessesIn(int diskAccessesIn) {
        this.diskAccessesIn = diskAccessesIn;
    }

    public int getDiskAccessesOut() {
        return diskAccessesOut;
    }

    public void setDiskAccessesOut(int diskAccessesOut) {
        this.diskAccessesOut = diskAccessesOut;
    }

    public int getRuns() {
        return runs;
    }

    public void setRuns(int runs) {
        this.runs = runs;
    }

    public int getNumberOfFaze() {
        return numberOfFaze;
    }

    public void setNumberOfFaze(int numberOfFaze) {
        this.numberOfFaze = numberOfFaze;
    }

    public int getDiskAccesses() {
        return diskAccesses;
    }

    public void setDiskAccesses(int diskAccesses) {
        this.diskAccesses = diskAccesses;
    }

    public int getPredictedDiskAccesses() {
        return predictedDiskAccesses;
    }

    public void setPredictedDiskAccesses(int predictedDiskAccesses) {
        this.predictedDiskAccesses = predictedDiskAccesses;
    }

    public static String toStringHeader(){
        return "Liczba elementów\tLiczba odczytów\tProcent odczytów\tLiczba zapisów\tProcent zapisów\tSuma dostępów\tPrzewidywana ilość dostępów\tLiczba faz";

    }
    @Override
    public String toString() {
        return Settings.NUM_OF_ELEMENTS+"\t"
                +this.getDiskAccessesIn()+"\t"
                +(100.0*this.getDiskAccessesIn()/this.getDiskAccesses())+"%\t"
                +this.getDiskAccessesOut()+"\t"
                +(100.0*this.getDiskAccessesOut()/this.getDiskAccesses())+"%\t"
                +this.getDiskAccesses()+"\t"
                +this.getPredictedDiskAccesses()+"\t"
                +this.getNumberOfFaze();
    }
}
