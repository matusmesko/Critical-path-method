package matus.mesko;

class GraphSorter {
    public void shellSortH(GraphData data, int s) {
        for (int gap = data.m / 2 + 1; gap >= 1; gap = 5 * gap / 11) {
            if (gap == 2) gap = 3;
            for (int i = 1; i + gap <= data.m; i++) {
                if (data.HlavnePole[i][s] > data.HlavnePole[i + gap][s]) {
                    swapH(data, i, i + gap);
                    for (int k = i; k - gap >= 1; k -= gap) {
                        if (data.HlavnePole[k - gap][s] <= data.HlavnePole[k][s]) break;
                        swapH(data, k - gap, k);
                    }
                }
            }
        }
    }

    private void swapH(GraphData data, int i, int j) {
        int tmp = 0;
        for (int k = 0; k <= 2; k += 1) {
            tmp = data.HlavnePole[i][k];
            data.HlavnePole[i][k] = data.HlavnePole[j][k];
            data.HlavnePole[j][k] = tmp;
        }
    }
}
