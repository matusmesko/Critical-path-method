package matus.mesko;

//class ResultPrinter {
//    private final GraphData data;
//    private final CPMSolver solver;
//
//    public ResultPrinter(GraphData data, CPMSolver solver) {
//        this.data = data;
//        this.solver = solver;
//    }
//
//    public void vypisKritickuCestu() {
//        System.out.print("Kritická cesta: ");
//        for (int i = 1; i <= data.n; i++) {
//            int rezerva = data.koniecCinnosti[i] - data.zaciatokCinnosti[i] - data.P[i];
//            if (rezerva == 0) {
//                System.out.print(i);
//                for (int j = data.Smernik[i]; j < data.Smernik[i + 1]; j++) {
//                    int v = data.HlavnePole[j][1];
//                    int rez = data.koniecCinnosti[v] - data.zaciatokCinnosti[v] - data.P[v];
//                    if (rez == 0 && data.zaciatokCinnosti[i] + data.P[i] == data.zaciatokCinnosti[v]) {
//                        System.out.print(", ");
//                        break;
//                    }
//                }
//            }
//        }
//        System.out.println();
//    }
//
//    public void printInfo(long casovac) {
//        System.out.println();
//        System.out.println("Pocet vrcholov: " + data.n);
//        System.out.println("Pocet hran: " + data.m);
//        System.out.println("Celkove trvanie projektu: " + data.trvanieProjektu);
//        System.out.println("Čas výpočtu: " + casovac / 1_000_000_000D + " s");
//
//        System.out.println("\nNezoradene");
//        StringBuilder vypis = new StringBuilder();
//        for (int i = 1; i < data.n + 1; i++) {
//            vypis.append(createActivityString(i));
//        }
//        System.out.println(vypis);
//
//        vypis = new StringBuilder();
//        System.out.println("Zoradene");
//        for (int t = 1; t < data.n + 1; t++) {
//            int i = data.monotonneUsporiadanie[t];
//            vypis.append(createActivityString(i));
//        }
//        System.out.println(vypis);
//    }
//
//    private String createActivityString(int i) {
//        return String.format("Cinnost %d: trvanie cinnosti %d, z[i]: %d, k[i]: %d, rezerva cinnosti: %d%n",
//                i, data.P[i], data.zaciatokCinnosti[i], data.koniecCinnosti[i],
//                data.koniecCinnosti[i] - data.zaciatokCinnosti[i] - data.P[i]);
//    }
//}

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// ResultPrinter.java - Upravená verzia pre požadovaný výstup
public class ResultPrinter {
    private final GraphData data;
    private final CPMSolver solver;

    public ResultPrinter(GraphData data, CPMSolver solver) {
        this.data = data;
        this.solver = solver;
    }

    public void vypisKritickuCestu() {
        System.out.print("\nKritická cesta: ");

        // Zozbierame všetky kritické činnosti
        List<Integer> kritickeCinnosti = new ArrayList<>();
        for (int i = 1; i <= data.n; i++) {
            int rezerva = data.koniecCinnosti[i] - data.zaciatokCinnosti[i] - data.P[i];
            if (rezerva == 0) {
                kritickeCinnosti.add(i);
            }
        }

        // Zoradíme činnosti vzostupne
        Collections.sort(kritickeCinnosti);

        // Vypíšeme oddelené čiarkami
        for (int i = 0; i < kritickeCinnosti.size(); i++) {
            System.out.print(kritickeCinnosti.get(i));
            if (i < kritickeCinnosti.size() - 1) {
                System.out.print(", ");
            }
        }
        System.out.println();
    }

    private void vypisCestuZActivity(int activity, StringBuilder sb) {
        sb.append(activity).append(" -> ");

        // Nájdeme nasledovníkov na kritickej ceste
        for (int j = data.Smernik[activity]; j < data.Smernik[activity + 1]; j++) {
            int nasledovnik = data.HlavnePole[j][1];
            int rezerva = data.koniecCinnosti[nasledovnik] - data.zaciatokCinnosti[nasledovnik] - data.P[nasledovnik];
            if (rezerva == 0 && data.zaciatokCinnosti[nasledovnik] == data.zaciatokCinnosti[activity] + data.P[activity]) {
                vypisCestuZActivity(nasledovnik, sb);
                break; // Predpokladáme, že máme len jedného nasledovníka na kritickej ceste
            }
        }
    }

    public void printInfo(long casovac) {
        // 1. Výpis doby trvania projektu
        System.out.println("Doba trvania projektu: " + data.trvanieProjektu + " jednotiek");

        // 2. Výpis rozvrhu v tvare tabuľky
        System.out.println("\nRozvrh projektu:");
        System.out.println("+------------+------------+------------+------------+------------+");
        System.out.println("| Činnosť    | Trvanie    | Začiatok   | Koniec     | Rezerva    |");
        System.out.println("+------------+------------+------------+------------+------------+");

        for (int i = 1; i <= data.n; i++) {
            int rezerva = data.koniecCinnosti[i] - data.zaciatokCinnosti[i] - data.P[i];
            System.out.printf("| %-10d | %-10d | %-10d | %-10d | %-10d |%n",
                    i, data.P[i], data.zaciatokCinnosti[i],
                    data.koniecCinnosti[i], rezerva);
        }

        System.out.println("+------------+------------+------------+------------+------------+");
    }

    public void vypisZoradeneVTabulke() {
        // Hlavička tabuľky
        System.out.println("\nZoradené činnosti:");
        System.out.println("+------------+------------+------------+------------+----------------+");
        System.out.println("| Činnosť    | Trvanie    | Začiatok   | Koniec     | Rezerva (k-z-p) |");
        System.out.println("+------------+------------+------------+------------+----------------+");

        // Formátovanie riadkov
        String format = "| %-10d | %-10d | %-10d | %-10d | %-14d |%n";

        // Prechádzame činnosti v zoradenom poradí
        for (int t = 1; t < data.n + 1; t++) {
            int i = data.monotonneUsporiadanie[t];
            int rezerva = data.koniecCinnosti[i] - data.zaciatokCinnosti[i] - data.P[i];

            // Zvýraznenie kritických činností (rezerva = 0)
            if (rezerva == 0) {
                System.out.print("\u001B[31m"); // Červená farba pre kritické činnosti
            }

            System.out.printf(format, i, data.P[i], data.zaciatokCinnosti[i],
                    data.koniecCinnosti[i], rezerva);

            if (rezerva == 0) {
                System.out.print("\u001B[0m"); // Reset farby
            }
        }

        // Päta tabuľky
        System.out.println("+------------+------------+------------+------------+----------------+");

        // Vysvetlivky
        System.out.println("Legenda:");
        System.out.println("k - najneskorší možný koniec");
        System.out.println("z - najskorší možný začiatok");
        System.out.println("p - trvanie činnosti");
        System.out.println("Červené riadky - kritické činnosti (rezerva = 0)");
    }
}