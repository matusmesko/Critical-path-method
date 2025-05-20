package matus.mesko.idk;

import java.util.ArrayList;

public class Monotonne {

    public ArrayList<Vrchol> getTopologicalOrdering(ArrayList<Vrchol> vrcholy) {
        ArrayList<Vrchol> list = new ArrayList<>();
        // pole pre vsetky vrcholy
        int[] inDeg = new int[vrcholy.size()];

        for (int i = 0; i < inDeg.length; i++) {
            // priradujem kazdemu vrcho jeho ideg
            inDeg[i] = vrcholy.get(i).getVchadzajuceHrany().size();
        }

        while (true) {
            Vrchol vrchol = null;
            for (int i = 0; i < inDeg.length; i++) {
                if (inDeg[i] == 0) { // najdem vrchol ktory ma nulovy ideg
                    inDeg[i] = -1000; // vykaslem sa na vrchol
                    vrchol = vrcholy.get(i);
                    break;
                }
            }

            if (vrchol == null) {
                break;
            }

            list.add(vrchol);

            // znizujem indegy do ktorych idem
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
