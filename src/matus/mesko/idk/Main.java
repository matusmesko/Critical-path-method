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
        int maxVrchol = 0;

        // Cesta k súboru s hranami
        String cestaHrany = "src/matus/mesko/data/" + NAZOV_SUBORU + ".hrn";
        try (Scanner citac = new Scanner(new File(cestaHrany))) {
            while (citac.hasNextInt()) {
                int u = citac.nextInt();
                int v = citac.nextInt();
                int cena = citac.nextInt();

                zoznamHran.add(new Hrana(u, v, cena));
                maxVrchol = Math.max(maxVrchol, Math.max(u, v));
            }
        } catch (Exception e) {
            throw new RuntimeException("Nepodarilo sa načítať súbor: " + cestaHrany);
        }

        // Cesta k súboru s časmi činností
        String cestaCasy = "src/matus/mesko/data/" + NAZOV_SUBORU + ".tim";
        List<Integer> trvania = new ArrayList<>();
        trvania.add(0); // Index 0 je rezervovaný pre "virtuálny" počiatočný vrchol

        try (Scanner citac = new Scanner(new File(cestaCasy))) {
            while (citac.hasNextInt()) {
                trvania.add(citac.nextInt());
            }
        } catch (Exception e) {
            throw new RuntimeException("Nepodarilo sa načítať súbor: " + cestaCasy);
        }

        // Vytvorenie zoznamu vrcholov
        for (int i = 0; i <= maxVrchol; i++) {
            Vrchol vrchol = new Vrchol(i);
            if (i < trvania.size()) {
                vrchol.setTrvanie(trvania.get(i));
            }
            zoznamVrcholov.add(vrchol);
        }

        // Spustenie výpočtu CPM
        Graf graf = new Graf(new ArrayList<>(zoznamHran), new ArrayList<>(zoznamVrcholov));
        graf.vypocitajCPM(); // alebo .vypocitajCPM() podľa tvojej finálnej metódy
    }
}
