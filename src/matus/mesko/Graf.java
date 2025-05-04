package matus.mesko;

import java.util.*;

/**
 * Implementation of the Critical Path Method (CPM) algorithm for project scheduling.
 * This class analyzes a project network to determine earliest start times, latest finish times,
 * time reserves, and the critical path.
 */
public class Graf {
    private final Map<Vrchol, List<Hrana>> graph;
    private final List<Vrchol> sortedVrcholy;
    private int projectDuration;

    /**
     * Creates a new CPM analyzer with the specified project network.
     *
     * @param graph The project network represented as a map from activities to their outgoing edges
     */
    public Graf(Map<Vrchol, List<Hrana>> graph) {
        this.graph = graph;
        this.sortedVrcholy = new ArrayList<>();
        performTopologicalSort();
    }

    /**
     * Performs a topological sort of the activities in the network.
     * This is a prerequisite for the CPM algorithm.
     */
    private void performTopologicalSort() {
        Map<Vrchol, Integer> inDegreeMap = new HashMap<>();
        
        // Initialize in-degree map
        for (Vrchol vrchol : graph.keySet()) {
            inDegreeMap.put(vrchol, 0);
        }
        
        // Calculate in-degrees
        for (List<Hrana> hranas : graph.values()) {
            for (Hrana hrana : hranas) {
                Vrchol target = hrana.getTarget();
                inDegreeMap.put(target, inDegreeMap.getOrDefault(target, 0) + 1);
            }
        }

        // Queue activities with no incoming edges
        Queue<Vrchol> queue = new LinkedList<>();
        for (Map.Entry<Vrchol, Integer> entry : inDegreeMap.entrySet()) {
            if (entry.getValue() == 0) {
                queue.add(entry.getKey());
            }
            entry.getKey().setInDegree(entry.getValue());
        }

        // Process activities in topological order
        while (!queue.isEmpty()) {
            Vrchol current = queue.poll();
            sortedVrcholy.add(current);
            
            List<Hrana> outgoingHranas = graph.get(current);
            if (outgoingHranas != null) {
                for (Hrana hrana : outgoingHranas) {
                    Vrchol neighbor = hrana.getTarget();
                    int newInDegree = inDegreeMap.get(neighbor) - 1;
                    inDegreeMap.put(neighbor, newInDegree);
                    neighbor.setInDegree(newInDegree);
                    
                    if (newInDegree == 0) {
                        queue.add(neighbor);
                    }
                }
            }
        }
    }

    /**
     * Calculates the earliest start times for all activities in the network.
     *
     * @return A formatted string describing the earliest start times
     */
    public String calculateEarliestStartTimes() {
        for (Vrchol vrchol : sortedVrcholy) {
            List<Hrana> outgoingHranas = graph.get(vrchol);
            if (outgoingHranas != null) {
                for (Hrana hrana : outgoingHranas) {
                    Vrchol target = hrana.getTarget();
                    int proposedStart = vrchol.getEarliestStart() + hrana.getDuration();
                    
                    if (proposedStart > target.getEarliestStart()) {
                        target.setEarliestStart(proposedStart);
                    }
                }
            }
        }

        StringBuilder result = new StringBuilder("Najskôr možný začiatok:\n");
        for (Vrchol vrchol : sortedVrcholy) {
            result.append("Vrchol ").append(vrchol.getId())
                  .append(" - ").append(vrchol.getEarliestStart()).append("\n");
        }
        return result.toString();
    }

    /**
     * Calculates the latest finish times for all activities in the network.
     *
     * @return A formatted string describing the latest finish times
     */
    public String calculateLatestFinishTimes() {
        // Find the project duration (maximum earliest start time)
        projectDuration = sortedVrcholy.stream()
                .mapToInt(Vrchol::getEarliestStart)
                .max()
                .orElse(0);

        // Initialize all latest finish times to the project duration
        for (Vrchol vrchol : sortedVrcholy) {
            vrchol.setLatestFinish(projectDuration);
        }

        // Process activities in reverse topological order
        List<Vrchol> reversedActivities = new ArrayList<>(sortedVrcholy);
        Collections.reverse(reversedActivities);

        for (Vrchol vrchol : reversedActivities) {
            List<Hrana> outgoingHranas = graph.get(vrchol);
            if (outgoingHranas != null) {
                for (Hrana hrana : outgoingHranas) {
                    Vrchol target = hrana.getTarget();
                    int proposedFinish = target.getLatestFinish() - hrana.getDuration();
                    
                    if (proposedFinish < vrchol.getLatestFinish()) {
                        vrchol.setLatestFinish(proposedFinish);
                    }
                }
            }
        }

        StringBuilder result = new StringBuilder("Najneskôr nutný koniec:\n");
        for (Vrchol vrchol : sortedVrcholy) {
            result.append("Vrchol ").append(vrchol.getId())
                  .append(" - ").append(vrchol.getLatestFinish()).append("\n");
        }
        return result.toString();
    }

    /**
     * Gets the total duration of the project.
     *
     * @return The project duration
     */
    public int getProjectDuration() {
        return projectDuration;
    }

    /**
     * Calculates the time reserves for all activities in the network.
     * The time reserve is the difference between the latest finish time and the earliest start time.
     *
     * @return A formatted string describing the time reserves
     */
    public String calculateTimeReserves() {
        StringBuilder result = new StringBuilder("Časová rezerva:\n");
        for (Vrchol vrchol : sortedVrcholy) {
            int reserve = vrchol.getLatestFinish() - vrchol.getEarliestStart();
            result.append("Vrchol ").append(vrchol.getId())
                  .append(" - ").append(reserve).append("\n");
        }
        return result.toString();
    }

    /**
     * Finds the critical path in the project network.
     * The critical path consists of activities with zero time reserve.
     *
     * @return A formatted string describing the critical path
     */
    public String findCriticalPath() {
        StringBuilder result = new StringBuilder("Kritická cesta:\n");
        
        // Start from activities with no incoming edges and zero time reserve
        for (Vrchol vrchol : sortedVrcholy) {
            if (vrchol.getInDegree() == 0 &&
                vrchol.getEarliestStart() == vrchol.getLatestFinish()) {
                if (exploreCriticalPath(vrchol, result, new ArrayList<>())) {
                    break;  // Stop after finding one critical path
                }
            }
        }
        
        return result.toString().trim();
    }

    /**
     * Recursively explores the critical path starting from the given activity.
     *
     * @param vrchol The current activity
     * @param result   The string builder to append the path to
     * @param path     The current path
     * @return True if a complete critical path was found, false otherwise
     */
    private boolean exploreCriticalPath(Vrchol vrchol, StringBuilder result, List<Integer> path) {
        path.add(vrchol.getId());

        // If we've reached an activity with earliest start equal to project duration,
        // we've found a complete critical path
        if (vrchol.getEarliestStart() == projectDuration) {
            for (int i = 0; i < path.size(); i++) {
                result.append(path.get(i));
                if (i < path.size() - 1) {
                    result.append(" → ");
                }
            }
            result.append("\n");
            return true;
        }

        // Explore outgoing edges
        List<Hrana> outgoingHranas = graph.get(vrchol);
        if (outgoingHranas != null) {
            for (Hrana hrana : outgoingHranas) {
                Vrchol next = hrana.getTarget();
                
                // An activity is on the critical path if it has zero time reserve
                // and its earliest start is exactly after the current activity
                if (next.getEarliestStart() == next.getLatestFinish() &&
                    next.getEarliestStart() == vrchol.getEarliestStart() + hrana.getDuration()) {
                    if (exploreCriticalPath(next, result, new ArrayList<>(path))) {
                        return true;
                    }
                }
            }
        }
        
        return false;
    }
}