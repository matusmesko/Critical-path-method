package matus.mesko;

import java.io.File;
import java.util.*;

/**
 * Main application class for the Critical Path Method project scheduling tool.
 * This class reads project data from a file and performs CPM analysis.
 */
public class Main {
    public static void main(String[] args) {
        // Path to the input file containing project network data
        String dataFilePath = "src/matus/mesko/data/CPM_mini.hrn";
        
        // Parse the input file and build the project network
        Map<Integer, Vrchol> vrcholy = new HashMap<>();
        Map<Vrchol, List<Hrana>> graf = new HashMap<>();
        
        try (Scanner scanner = new Scanner(new File(dataFilePath))) {
            while (scanner.hasNextInt()) {
                int sourceId = scanner.nextInt();
                int targetId = scanner.nextInt();
                int duration = scanner.nextInt();
                
                // Create activities if they don't exist
                vrcholy.putIfAbsent(sourceId, new Vrchol(sourceId));
                vrcholy.putIfAbsent(targetId, new Vrchol(targetId));
                
                // Increment in-degree of target activity
                vrcholy.get(targetId).incrementInDegree();
                
                // Get the activity objects
                Vrchol source = vrcholy.get(sourceId);
                Vrchol target = vrcholy.get(targetId);
                
                // Add the edge to the network
                graf.putIfAbsent(source, new ArrayList<>());
                graf.get(source).add(new Hrana(source, target, duration));
            }
        } catch (Exception e) {
            throw new RuntimeException("Error reading file " + dataFilePath + ": " + e.getMessage(), e);
        }
        
        // Create the CPM analyzer and run the analysis
        Graf cpm = new Graf(graf);
        
        print(cpm);
    }

    private static void print(Graf graf) {
        System.out.println(graf.calculateEarliestStartTimes());
        System.out.println("-----------------------------------------");
        System.out.println(graf.calculateLatestFinishTimes());
        System.out.println("-----------------------------------------");
        System.out.println(graf.calculateTimeReserves());
        System.out.println("-----------------------------------------");
        System.out.println(graf.findCriticalPath());
        System.out.println("-----------------------------------------");
        System.out.println("Trvanie projektu: " + graf.getProjectDuration());
    }
}