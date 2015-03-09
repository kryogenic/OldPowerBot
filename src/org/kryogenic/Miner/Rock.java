package org.kryogenic.Miner;

import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.api.wrappers.node.SceneObject;

import java.util.Objects;

public class Rock implements Comparable {

    private long minedTime; // Time in millis the rock was last seen at an unmined state
    private final Tile loc;
    private volatile int hashCode;

    Rock(Tile loc, long minedTime) {
        this.loc = loc;
        this.minedTime = minedTime;
    }

    public boolean isOccupado() {
        Players.getLocal().getOrientation();
        return true; // TODO figure out what the hell getOrientation() does
    }

    /**
     * Retrieves the location of the rock
     *
     * @return the rock's location
     */
    public Tile getLoc() {
        return loc;
    }

    /**
     * Finds the rock's object
     *
     * @return the rock's object
     */
    public SceneObject getObj() {
        return SceneEntities.getAt(loc);
    }

    /**
     * Retrieves the time the rock has been empty
     *
     * @return the current time - the time the rock was last seen full
     */
    public long getTime() {
        return System.currentTimeMillis() - minedTime;
    }

    /**
     * Sets the time the rock was last seen in an un-mined state
     *
     * @param time the time in milliseconds the rock was seen in an un-mined state
     */
    public void setTime(long time) {
        minedTime = time;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        Rock r;
        if (o instanceof Rock)
            r = (Rock) o;
        else
            return false;
        return getLoc().equals(r.getLoc()) /*&& r.minedTime == minedTime*/;
    }

    @Override
    public int hashCode() {
        int result = hashCode;
        if (result == 0) {
            result = 17;
            //result = 31 * result + (int) (minedTime ^ (minedTime >>> 32));
            result = 31 * result + getLoc().hashCode();
            hashCode = result;
        }
        return result;
    }

    @Override
    public String toString() {
        return "Rock Location: " + getLoc() + " Time: " + getTime();
    }

    @Override
    public int compareTo(Object o) {
        if (o instanceof Comparable) {
            if (o instanceof Rock) {
                Rock r = (Rock) o;
                int time = (int) (this.getTime() - r.getTime());
                // int distance = Calculations.distanceTo(this.getObj()) - Calculations.distanceTo(r.getObj());
                // 0.3 per tile, 0.5 base
                return time;
            }
        }
        return 0;
    }
}
