package matus.mesko;

import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {

        System.out.println("------------------------------------------------------");
        System.out.print("Zadaj nazov suboru pre ktory chces vypocitat CPM: ");

        CPM cpm = CPM.nacitajSubor("src/matus/mesko/data/CPM_midi.hrn");
        cpm.trvanieCinnosti("src/matus/mesko/data/CPM_midi.tim");
        cpm.cpm();
        cpm.vypisKritickuCestu();
        cpm.printInfo();
    }
}
