package matus.mesko.cpm;

import java.util.ArrayList;

public class Monotonne {

    public ArrayList<Vrchol> monotonneOcisluj(ArrayList<Vrchol> vrcholy) {
        ArrayList<Vrchol> list = new ArrayList<>();
        int[] inDeg = new int[vrcholy.size()];

        for (int i = 0; i < inDeg.length; i++) {
            inDeg[i] = vrcholy.get(i).getVchadzajuceHrany().size();
        }

        while (true) {
            Vrchol vrchol = null;
            for (int i = 0; i < inDeg.length; i++) {
                if (inDeg[i] == 0) {
                    inDeg[i] = -1000;
                    vrchol = vrcholy.get(i);
                    break;
                }
            }

            if (vrchol == null) {
                break;
            }

            list.add(vrchol);

            for (Hrana e : vrchol.getVychadzajuceHrany()) {
                inDeg[e.getV()]--;
            }
        }

        if (list.size() != vrcholy.size()) {
            System.out.println("Graf nieje acyklicky!");
            return list;
        }

        return list;
    }
}
