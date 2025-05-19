package matus.mesko;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

class DurationLoader {
    public static void loadDurations(String nazov, GraphData graphData) throws FileNotFoundException {
        Scanner sc = new Scanner(new FileInputStream(nazov));
        int riadok = 0;
        while (sc.hasNext() && riadok < graphData.n) {
            graphData.P[++riadok] = sc.nextInt();
        }

        if (riadok != graphData.n) {
            System.out.println("Pocet vrcholov sa nerovna poctu elementarnych cinnosti.");
            System.exit(0);
        }
    }
}
