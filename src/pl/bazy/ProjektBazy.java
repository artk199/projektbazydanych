package pl.bazy;

import pl.bazy.data.Student;

import java.io.File;

/**
 * Created by Art on 2014-10-26.
 */
public class ProjektBazy {

    public static boolean DEBUG = true;
    public static final int INPUT_BUFFER_SIZE = 10;
    public static final int OUTPUT_BUFFER_SIZE = 10;

    //Liczba dostepów do dysku
    private int diskAccesses = 0;
    private File file;

    public ProjektBazy(File file) {
        this.file = file;
    }

    public void sort() {
        boolean koniec = false;
        int i = 0;
        while (!koniec) {
            i++;
            InputBuffer buffer = new InputBuffer(file, 10);
            OutputBuffer tapes[] = new OutputBuffer[2];
            tapes[0] = new OutputBuffer(new File("tape1.txt"), 10);
            tapes[1] = new OutputBuffer(new File("tape2.txt"), 10);

            Student s;
            int l = 0;
            Student lastOne = null;
            int activeTape = 0;
            while ((s = buffer.getItem()) != null) {
                if (s.compareTo(lastOne) < 0) {
                    activeTape++;
                    activeTape %= 2;
                }
                tapes[activeTape].saveItem(s);
                lastOne = s;
            }
            buffer.close();
            tapes[0].close();
            tapes[1].close();
            if (tapes[1].getFile().length() == 0) {
                koniec = true;
                i--;
                continue;

            }
            diskAccesses += buffer.getCounter();
            diskAccesses += tapes[0].getCounter();
            diskAccesses += tapes[1].getCounter();


            //Podzielenie taśm na taśmę wynikową
            OutputBuffer outputTape = new OutputBuffer(new File("tape3.txt"), OUTPUT_BUFFER_SIZE);
            InputBuffer[] input = new InputBuffer[2];

            input[0] = new InputBuffer(tapes[0].getFile(), INPUT_BUFFER_SIZE);
            input[1] = new InputBuffer(tapes[1].getFile(), INPUT_BUFFER_SIZE);

            Student lastTape1 = null;
            Student lastTape2 = null;
            Student s1 = input[0].getItem();
            Student s2 = input[1].getItem();
            boolean[] changeSeries = new boolean[2];
            while (s1 != null && s2 != null) {
                System.out.println(s1 + " hehe " + s2);
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
                        outputTape.saveItem(s2);
                        lastTape2 = s2;
                        s2 = input[1].getItem();
                    } else {
                        outputTape.saveItem(s1);
                        lastTape1 = s1;
                        s1 = input[0].getItem();
                    }
                } else {
                    //Jeżeli zmiana na 1 taśmie to wypisz całą 2 taśmę
                    if (changeSeries[0]) {
                        //Wypisz do końca serię nr 2;
                        Student temp = s2;
                        while (temp.compareTo(s2) <= 0 && s2 != null) {
                            outputTape.saveItem(s2);
                            temp = s2;
                            s2 = input[1].getItem();
                        }
                    } else {
                        Student temp = s1;
                        while (temp.compareTo(s1) <= 0 && s1 != null) {
                            outputTape.saveItem(s1);
                            temp = s1;
                            s1 = input[0].getItem();
                        }
                    }
                    lastTape2 = null;
                    lastTape1 = null;
                }
            }
            while (s1 != null) {
                outputTape.saveItem(s1);
                s1 = input[0].getItem();
            }
            while (s2 != null) {
                outputTape.saveItem(s2);
                s2 = input[1].getItem();
            }
            outputTape.close();
            input[0].close();
            input[1].close();
            if (input[1].getFile().length() == 0)
                koniec = true;
            diskAccesses += outputTape.getCounter();
            diskAccesses += input[0].getCounter();
            diskAccesses += input[1].getCounter();


            //przepisanie pliku wyjsciowego na wejsciowy
            InputBuffer inputBufferprzepisanie = new InputBuffer(outputTape.getFile(), INPUT_BUFFER_SIZE);
            OutputBuffer outputBufferprzepisanie = new OutputBuffer(this.file, OUTPUT_BUFFER_SIZE);
            Student st;
            while ((st = inputBufferprzepisanie.getItem()) != null) {
                outputBufferprzepisanie.saveItem(st);
            }
            inputBufferprzepisanie.close();
            outputBufferprzepisanie.close();
        }
        System.out.println(i + " diskAccesses : " + diskAccesses);
    }
}
