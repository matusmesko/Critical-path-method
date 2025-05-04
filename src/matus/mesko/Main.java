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
        Map<Integer, Vrchol> activities = new HashMap<>();
        Map<Vrchol, List<Hrana>> network = new HashMap<>();
        
        try (Scanner scanner = new Scanner(new File(dataFilePath))) {
            while (scanner.hasNextInt()) {
                int sourceId = scanner.nextInt();
                int targetId = scanner.nextInt();
                int duration = scanner.nextInt();
                
                // Create activities if they don't exist
                activities.putIfAbsent(sourceId, new Vrchol(sourceId));
                activities.putIfAbsent(targetId, new Vrchol(targetId));
                
                // Increment in-degree of target activity
                activities.get(targetId).incrementInDegree();
                
                // Get the activity objects
                Vrchol source = activities.get(sourceId);
                Vrchol target = activities.get(targetId);
                
                // Add the edge to the network
                network.putIfAbsent(source, new ArrayList<>());
                network.get(source).add(new Hrana(source, target, duration));
            }
        } catch (Exception e) {
            throw new RuntimeException("Error reading file " + dataFilePath + ": " + e.getMessage(), e);
        }
        
        // Create the CPM analyzer and run the analysis
        Graf cpm = new Graf(network);
        
        // Calculate and display earliest start times
        String result = cpm.calculateEarliestStartTimes();
        System.out.println(result);
        
        // Calculate and display latest finish times
        result = cpm.calculateLatestFinishTimes();
        System.out.println(result);
        
        // Calculate and display time reserves
        result = cpm.calculateTimeReserves();
        System.out.println(result);
        
        // Find and display the critical path
        result = cpm.findCriticalPath();
        System.out.println(result);
        
        // Display the total project duration
        int duration = cpm.getProjectDuration();
        System.out.println("Trvanie projektu: " + duration);
    }
}