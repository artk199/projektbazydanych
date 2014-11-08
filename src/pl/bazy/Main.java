package pl.bazy;

import pl.bazy.ProjektBazy;
import pl.bazy.Settings;
import pl.bazy.services.Generator;

import java.io.File;

public class Main {

    public static void main(String[] args) {
        Settings.NUM_OF_ELEMENTS = 100;
        ProjektBazy projekt = new ProjektBazy(Generator.generateStudentFile(Settings.GENERATION_WAY));;
        System.out.println(projekt.toStringHeader());
        //for (int i = 1; i < 1; i++) {
            Settings.NUM_OF_ELEMENTS = Settings.NUM_OF_ELEMENTS+100;
            projekt = new ProjektBazy(Generator.generateStudentFile(Settings.GENERATION_WAY));
            projekt.sort();
            System.out.println(projekt.toString());
        //}
    }
}
