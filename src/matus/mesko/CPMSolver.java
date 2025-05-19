package matus.mesko;

class CPMSolver {
    private final GraphData data;

    public CPMSolver(GraphData data) {
        this.data = data;
    }

    public void cpm() {
        monotonneOcislovanie();
        vypocitajZaciatky();
        vypocitajKonce();
    }

    private void monotonneOcislovanie() {
        smernikovyVektor();
        int[] ideg = new int[data.n + 1];
        new GraphSorter().shellSortH(data, 0);

        for (int j = 1; j < data.n + 1; j++) {
            ideg[j] = 0;
        }

        for (int j = 1; j < data.m + 1; j++) {
            int vv = data.HlavnePole[j][1];
            ideg[vv]++;
        }

        data.k = 0;
        for (int j = 1; j < data.n + 1; j++) {
            if (ideg[j] == 0) {
                data.k++;
                data.monotonneUsporiadanie[data.k] = j;
            }
        }

        for (int i = 1; i < data.n + 1; i++) {
            for (int j = data.Smernik[data.monotonneUsporiadanie[i]];
                 j < data.Smernik[data.monotonneUsporiadanie[i] + 1]; j++) {
                int w = data.HlavnePole[j][1];
                ideg[w]--;
                if (ideg[w] == 0) {
                    data.k++;
                    data.monotonneUsporiadanie[data.k] = w;
                }
            }
            if (data.k == data.n) break;
        }

        if (data.k != data.n) {
            System.out.print("Graf nie je acyklicky");
        } else {
            System.out.print("Monotonne ocislovanie: ");
            for (int i = 1; i < data.monotonneUsporiadanie.length; i++) {
                System.out.print(data.monotonneUsporiadanie[i] + " -> ");
            }
        }
        System.out.println();
    }

    private void vypocitajZaciatky() {
        for (int i = 1; i < data.n + 1; i++) {
            data.x[i] = 0;
            data.zaciatokCinnosti[i] = 0;
        }

        for (int i = 1; i < data.n; i++) {
            int r = data.monotonneUsporiadanie[i];
            for (int j = data.Smernik[r]; j < data.Smernik[r + 1]; j++) {
                int w = data.HlavnePole[j][1];
                if (data.zaciatokCinnosti[w] < data.zaciatokCinnosti[r] + data.P[r]) {
                    data.zaciatokCinnosti[w] = data.zaciatokCinnosti[r] + data.P[r];
                    data.x[w] = r;
                }
            }
        }

        data.trvanieProjektu = 0;
        for (int i = 1; i < data.n + 1; i++) {
            if (data.trvanieProjektu < data.zaciatokCinnosti[i] + data.P[i]) {
                data.trvanieProjektu = data.zaciatokCinnosti[i] + data.P[i];
            }
        }
    }

    private void vypocitajKonce() {
        for (int i = 1; i < data.n + 1; i++) {
            data.koniecCinnosti[i] = data.trvanieProjektu;
            data.y[i] = 0;
        }

        for (int i = data.n - 1; i > 0; i--) {
            int r = data.monotonneUsporiadanie[i];
            for (int j = data.Smernik[r]; j < data.Smernik[r + 1]; j++) {
                int w = data.HlavnePole[j][1];
                if (data.koniecCinnosti[r] > data.koniecCinnosti[w] - data.P[w]) {
                    data.koniecCinnosti[r] = data.koniecCinnosti[w] - data.P[w];
                    data.y[r] = w;
                }
            }
        }
    }

    private void smernikovyVektor() {
        for (int i = 0; i < data.n + 2; i++) {
            data.Smernik[i] = 0;
        }

        for (int k = 1; k <= data.m; k++) {
            int i = data.HlavnePole[k][0];
            if (data.Smernik[i] == 0) {
                data.Smernik[i] = k;
            }
        }
        data.Smernik[data.n + 1] = data.m + 1;

        for (int i = data.n; i >= 1; i--) {
            if (data.Smernik[i] == 0) {
                data.Smernik[i] = data.Smernik[i + 1];
            }
        }
    }
}
