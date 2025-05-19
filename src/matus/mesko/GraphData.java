package matus.mesko;

public class GraphData {
    final int[][] HlavnePole;
    final int m;
    final int n;

    int[] zaciatokCinnosti;
    int[] koniecCinnosti;
    int[] y;
    int[] x;
    int[] Smernik;
    int[] P;
    int k;

    int[] monotonneUsporiadanie;
    int trvanieProjektu;

    public GraphData(int pocetVrcholov, int pocetHran) {
        this.n = pocetVrcholov;
        this.m = pocetHran;
        this.HlavnePole = new int[1 + this.m][3];
        this.monotonneUsporiadanie = new int[this.n + 1];
        this.zaciatokCinnosti = new int[this.n + 1];
        this.koniecCinnosti = new int[this.n + 1];
        this.y = new int[this.n + 1];
        this.x = new int[this.n + 1];
        this.P = new int[this.n + 1];
        this.Smernik = new int[this.n + 2];
    }
}
