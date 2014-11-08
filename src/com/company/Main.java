package com.company;

import pl.bazy.ProjektBazy;
import pl.bazy.services.Generator;
import pl.bazy.services.PolyphaseSort;

import java.io.File;

public class Main {

    public static void main(String[] args) {
        //PolyphaseSort sort = new PolyphaseSort(Generator.generateStudentFile());
        ProjektBazy projekt = new ProjektBazy(Generator.generateStudentFile());
        //ProjektBazy projekt = new ProjektBazy(new File("plik.txt"));
        projekt.sort();
    }
}
