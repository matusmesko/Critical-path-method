package matus.mesko;

import java.io.FileNotFoundException;

public class CPM {
    final GraphData graphData;
    private final CPMSolver solver;
    private final ResultPrinter printer;
    private long casovac;

    public CPM(int pocetVrcholov, int pocetHran) {
        this.graphData = new GraphData(pocetVrcholov, pocetHran);
        this.solver = new CPMSolver(graphData);
        this.printer = new ResultPrinter(graphData, solver);
        this.casovac = System.nanoTime();
    }

    public static CPM nacitajSubor(String nazovSuboru) throws FileNotFoundException {
        GraphLoader loader = new GraphLoader();
        return loader.nacitajSubor(nazovSuboru);
    }

    public void trvanieCinnosti(String nazov) throws FileNotFoundException {
        DurationLoader.loadDurations(nazov, graphData);
    }

    public void cpm() {
        solver.cpm();
        casovac = (System.nanoTime() - casovac);
    }

    public void vypisKritickuCestu() {
        printer.vypisKritickuCestu();
    }

    public void printInfo() {
//        printer.printInfo(casovac);
        printer.vypisZoradeneVTabulke();
    }
}
