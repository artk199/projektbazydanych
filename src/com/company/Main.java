package com.company;

import pl.bazy.ProjektBazy;
import pl.bazy.services.Generator;

public class Main {

    public static void main(String[] args) {
        ProjektBazy projekt = new ProjektBazy(Generator.generateStudentFile());
        projekt.sort();
    }
}
