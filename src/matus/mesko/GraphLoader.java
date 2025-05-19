package matus.mesko;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

class GraphLoader {
    public static CPM nacitajSubor(String nazovSuboru) throws FileNotFoundException {
        Scanner s = new Scanner(new FileInputStream(nazovSuboru));
        int pocetVrcholov = 1;
        int pocetHran = 0;

        while (s.hasNext()) {
            int u = s.nextInt();
            int v = s.nextInt();
            s.nextInt();
            pocetHran++;
            pocetVrcholov = Math.max(pocetVrcholov, Math.max(u, v));
        }
        s.close();

        CPM g = new CPM(pocetVrcholov, pocetHran);
        s = new Scanner(new FileInputStream(nazovSuboru));

        for (int j = 1; j <= pocetHran; j++) {
            int u = s.nextInt();
            int v = s.nextInt();
            int c = s.nextInt();
            g.graphData.HlavnePole[j][0] = u;
            g.graphData.HlavnePole[j][1] = v;
            g.graphData.HlavnePole[j][2] = c;
        }

        new GraphSorter().shellSortH(g.graphData, 0);
        return g;
    }
}
