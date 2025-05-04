package matus.mesko;

/**
 * Represents a directed edge in a project network for Critical Path Method analysis.
 * Each edge connects a source activity to a target activity and has a duration.
 */
public class Hrana {
    private final Vrchol source;
    private final Vrchol target;
    private final int duration;

    /**
     * Creates a new edge with the specified source, target, and duration.
     *
     * @param source   The source activity
     * @param target   The target activity
     * @param duration The duration of the activity represented by this edge
     */
    public Hrana(Vrchol source, Vrchol target, int duration) {
        this.source = source;
        this.target = target;
        this.duration = duration;
    }

    /**
     * Gets the source activity of this edge.
     *
     * @return The source activity
     */
    public Vrchol getSource() {
        return source;
    }

    /**
     * Gets the target activity of this edge.
     *
     * @return The target activity
     */
    public Vrchol getTarget() {
        return target;
    }

    /**
     * Gets the duration of the activity represented by this edge.
     *
     * @return The duration
     */
    public int getDuration() {
        return duration;
    }
    
    @Override
    public String toString() {
        return source + " -> " + target + " (" + duration + ")";
    }
}