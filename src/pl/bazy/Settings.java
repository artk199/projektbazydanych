package pl.bazy;

/**
 * Created by Art on 2014-11-07.
 */
public class Settings {



    private Settings(){};


    // MAGICAL NUMBERS, BOOLEANS AND STRINGS
    public static final int NUM_OF_RATINGS = 1;
    public static final int INPUT_BUFFER_SIZE = 10;
    public static final int OUTPUT_BUFFER_SIZE = 10;
    public static int NUM_OF_ELEMENTS = 20;
    public static final int NUM_OF_TAPES = 2;

    //WYPISUJE ZŁOO NA KONSOLE
    public static final boolean DEBUG = false;

    //CZEKAJ NA KLAWISZ
    public static final boolean WAIT_FOR_KEY = false;

    //WYPISZ ZAWARTOSC PLIKU PO KAZDEJ FAZIE NA KONSOLE
    public static final boolean WRITE_FILES = false;

    //WYPISZ TAKIE TAM COS
    public static final boolean WRITE_DISK_ACC = false;

    //TYP GENERACJI ŹRÓDŁA
    public static final GenerationWay GENERATION_WAY = GenerationWay.AUTO;

    public static final String[] FILE_NAME = {"mainTape.csv","tape1.csv","tape2.csv"};

    public enum GenerationWay {
        AUTO,
        OLD,
        CONSOLE
    }
}
