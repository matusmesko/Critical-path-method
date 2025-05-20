package matus.mesko.idk;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final String NAZOV_SUBORU = "CPM_midi";

    public static void main(String[] args) {
        List<Hrana> zoznamHran = new ArrayList<>();
        List<Vrchol> zoznamVrcholov = new ArrayList<>();
        int pocetVrcholov = 0;

        // Cesta k súboru s hranami
        String subor = "src/matus/mesko/data/" + NAZOV_SUBORU + ".hrn";
        try (Scanner citac = new Scanner(new File(subor))) {
            while (citac.hasNextInt()) {
                int u = citac.nextInt();
                int v = citac.nextInt();
                int cena = citac.nextInt();

                zoznamHran.add(new Hrana(u, v, cena));
                pocetVrcholov = Math.max(pocetVrcholov, Math.max(u, v));
            }
        } catch (Exception e) {
            throw new RuntimeException("Nepodarilo sa načítať súbor: " + subor);
        }

        // Cesta k súboru s časmi činností
        String subor2 = "src/matus/mesko/data/" + NAZOV_SUBORU + ".tim";
        List<Integer> list = new ArrayList<>();
        list.add(0); // Index 0 je rezervovaný pre "virtuálny" počiatočný vrchol

        try (Scanner citac = new Scanner(new File(subor2))) {
            while (citac.hasNextInt()) {
                list.add(citac.nextInt());
            }
        } catch (Exception e) {
            throw new RuntimeException("Nepodarilo sa načítať súbor: " + subor2);
        }

        // Vytvorenie zoznamu vrcholov
        for (int i = 0; i <= pocetVrcholov; i++) {
            Vrchol vrchol = new Vrchol(i);
            if (i < list.size()) {
                vrchol.setTrvanie(list.get(i));
            }
            zoznamVrcholov.add(vrchol);
        }

        // Spustenie výpočtu CPM
        Graf graf = new Graf(new ArrayList<>(zoznamHran), new ArrayList<>(zoznamVrcholov));
        graf.vypocitajCPM();
    }
}
