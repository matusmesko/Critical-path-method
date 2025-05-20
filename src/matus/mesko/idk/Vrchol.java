package matus.mesko.idk;

import java.util.ArrayList;

public class Vrchol {

    private int ocislovanieVrchola;
    private int trvanie;
    private ArrayList<Hrana> vchadzajuceHrany;
    private ArrayList<Hrana> vychadzajuceHrany;

    public Vrchol(int ocislovanieVrchola) {
        this.ocislovanieVrchola = ocislovanieVrchola;
        this.vchadzajuceHrany = new ArrayList<>();
        this.vychadzajuceHrany = new ArrayList<>();
    }

    public int getOcislovanieVrchola() {
        return this.ocislovanieVrchola;
    }


    public int getTrvanie() {
        return this.trvanie;
    }

    public void setTrvanie(int trvanie) {
        this.trvanie = trvanie;
    }

    public ArrayList<Hrana> getVchadzajuceHrany() {
        return this.vchadzajuceHrany;
    }

    public void pridajVchadzajucuHranu(Hrana e) {
        this.vchadzajuceHrany.add(e);
    }

    public ArrayList<Hrana> getVychadzajuceHrany() {
        return this.vychadzajuceHrany;
    }

    public void pridajVychdazajucuHranu(Hrana e) {
        this.vychadzajuceHrany.add(e);
    }
}
