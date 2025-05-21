package matus.mesko.cpm;

import java.util.ArrayList;

public class Graf {

    private ArrayList<Hrana> hrany;
    private ArrayList<Vrchol> vrcholy;

    public Graf(ArrayList<Hrana> hrany, ArrayList<Vrchol> vrcholy) {

        this.hrany = hrany;
        this.vrcholy = vrcholy;

        for (Hrana hrana : this.hrany) {
            this.vrcholy.get(hrana.getV()).pridajVchadzajucuHranu(hrana);
            this.vrcholy.get(hrana.getU()).pridajVychdazajucuHranu(hrana);
        }

    }

    public void vypocitajCPM() {
        int najvacsieTrvanie = 0;
        Monotonne monotonne = new Monotonne();
        ArrayList<Vrchol> zoradene = monotonne.monotonneOcisluj(this.vrcholy);
        int[] zaciatok = new int[zoradene.size()];
        int[] koniec = new int[zoradene.size()];
        int[] trvanie = new int[zoradene.size()];
        int[] rezerva = new int[zoradene.size()];


        for (int i = 0; i < zoradene.size(); i++) {
            Vrchol vrchol = zoradene.get(i);

            for (Hrana hrana : vrchol.getVychadzajuceHrany()) {
                if (zaciatok[hrana.getV()] < zaciatok[hrana.getU()] + vrchol.getTrvanie()) {
                    zaciatok[hrana.getV()] = zaciatok[hrana.getU()] + vrchol.getTrvanie();
                }
            }
        }

        for (int i = 0; i < koniec.length; i++) {
            najvacsieTrvanie = Math.max(najvacsieTrvanie, zaciatok[i] + this.vrcholy.get(i).getTrvanie());
        }

        for (int i = 0; i < koniec.length; i++) {
            koniec[i] = najvacsieTrvanie;
        }

        for (int i = zoradene.size() - 1; i >= 0; i--) {
            Vrchol vrchol = zoradene.get(i);

            for (Hrana hrana : vrchol.getVchadzajuceHrany()) {
                if (koniec[hrana.getU()] > koniec[hrana.getV()] - vrchol.getTrvanie()) {
                    koniec[hrana.getU()] = koniec[hrana.getV()] - vrchol.getTrvanie();
                }
            }
        }

        ArrayList<Integer> kritCesta = new ArrayList<>();

        for (int i = 1; i < trvanie.length; i++) {
            rezerva[i] = koniec[i] - (zaciatok[i] + this.vrcholy.get(i).getTrvanie());

            if (rezerva[i] == 0) {
                kritCesta.add(i);
            }
        }


        System.out.println("Trvanie projektu: " + najvacsieTrvanie);

        System.out.print("Kritická cesta: ");
        for (int i = 0; i < kritCesta.size(); i++) {
            System.out.print(kritCesta.get(i));
            if (i < kritCesta.size() - 1) {
                System.out.print(", ");
            } else System.out.println();
        }

        vypisZoradeneVTabulke(zoradene, zaciatok, koniec, rezerva);
//        vypisNezoradeneVTabulke(zaciatok, koniec, rezerva);
    }

    private void vypisZoradeneVTabulke(ArrayList<Vrchol> zoradene, int[] zaciatok, int[] koniec, int[] rezerva) {
        System.out.println("+------------+------------+------------+------------+----------------+");
        System.out.println("| Činnosť    | Trvanie    | Začiatok   | Koniec     | Rezerva        |");
        System.out.println("+------------+------------+------------+------------+----------------+");

        String format = "| %-10d | %-10d | %-10d | %-10d | %-14d |%n";
        for (Vrchol v : zoradene) {
            int ocislovanieVrchola = v.getOcislovanieVrchola();

            if (ocislovanieVrchola == 0) {
                continue;
            }
            int trvanie = v.getTrvanie();
            int z = zaciatok[ocislovanieVrchola];
            int k = koniec[ocislovanieVrchola];
            int r = rezerva[ocislovanieVrchola];
            System.out.printf(format, ocislovanieVrchola, trvanie, z, k, r);
        }

        System.out.println("+------------+------------+------------+------------+----------------+");
    }


    private void vypisNezoradeneVTabulke(int[] zaciatok, int[] koniec, int[] rezerva) {
        System.out.println("+------------+------------+------------+------------+----------------+");
        System.out.println("| Činnosť    | Trvanie    | Začiatok   | Koniec     | Rezerva        |");
        System.out.println("+------------+------------+------------+------------+----------------+");

        String format = "| %-10d | %-10d | %-10d | %-10d | %-14d |%n";
        for (int i = 1; i < vrcholy.size(); i++) {
            Vrchol v = vrcholy.get(i);
            int trvanie = v.getTrvanie();
            int z = zaciatok[i];
            int k = koniec[i];
            int r = rezerva[i];
            System.out.printf(format, i, trvanie, z, k, r);
        }

    }

}
