package com.company;

import pl.bazy.services.Generator;

public class Main {

    public static void main(String[] args) {
        Generator.generateStudentFile();

        System.out.println(Generator.student(1));
    }
}
