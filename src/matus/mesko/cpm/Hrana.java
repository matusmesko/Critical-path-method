package matus.mesko.cpm;

public class Hrana {
    private int u;
    private int v;
    private int cena;

    public Hrana(int u, int v, int cena) {
        this.u = u;
        this.v = v;
        this.cena = cena;
    }

    public int getU() {
        return this.u;
    }

    public int getV() {
        return this.v;
    }

    public int getCena() {
        return this.cena;
    }
}
