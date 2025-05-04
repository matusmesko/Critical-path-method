package matus.mesko;

import java.util.Objects;

/**
 * Represents an activity in a project network for Critical Path Method analysis.
 * Each activity has an ID, earliest start time, latest finish time, and in-degree count.
 */
public class Vrchol {
    private final int id;
    private int earliestStart = 0;
    private int latestFinish = 0;
    private int inDegree = 0;

    /**
     * Creates a new activity with the specified ID.
     *
     * @param id The unique identifier for this activity
     */
    public Vrchol(int id) {
        this.id = id;
    }

    /**
     * Gets the in-degree (number of incoming edges) of this activity.
     *
     * @return The in-degree count
     */
    public int getInDegree() {
        return inDegree;
    }

    /**
     * Sets the in-degree of this activity.
     *
     * @param inDegree The new in-degree value
     */
    public void setInDegree(int inDegree) {
        this.inDegree = inDegree;
    }

    /**
     * Increments the in-degree count by one.
     */
    public void incrementInDegree() {
        this.inDegree++;
    }

    /**
     * Gets the ID of this activity.
     *
     * @return The activity ID
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the earliest start time of this activity.
     *
     * @return The earliest start time
     */
    public int getEarliestStart() {
        return earliestStart;
    }

    /**
     * Sets the earliest start time of this activity.
     *
     * @param earliestStart The new earliest start time
     */
    public void setEarliestStart(int earliestStart) {
        this.earliestStart = earliestStart;
    }

    /**
     * Gets the latest finish time of this activity.
     *
     * @return The latest finish time
     */
    public int getLatestFinish() {
        return latestFinish;
    }

    /**
     * Sets the latest finish time of this activity.
     *
     * @param latestFinish The new latest finish time
     */
    public void setLatestFinish(int latestFinish) {
        this.latestFinish = latestFinish;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vrchol)) return false;
        Vrchol vrchol = (Vrchol) o;
        return id == vrchol.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    
    @Override
    public String toString() {
        return "Vrchol " + id;
    }
}