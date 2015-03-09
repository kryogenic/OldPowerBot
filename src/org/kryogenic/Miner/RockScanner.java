package org.kryogenic.Miner;

import org.kryogenic.util.Lists;
import org.powerbot.game.api.methods.Calculations;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.util.Filter;
import org.powerbot.game.api.util.Time;
import org.powerbot.game.api.wrappers.node.SceneObject;

import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Author: kryo
 * Date: 28/07/11
 */
public class RockScanner implements Runnable {

    public static final SortedSet<Rock> rocks = new TreeSet<Rock>();
    private final double distance;
    private final Session session;

    public RockScanner(Session session, double distance) {
        this.session = session;
        this.distance = distance;
    }

    @Override
    public void run() {
        while(true) {
            try {
                SceneObject[] newRocks = SceneEntities.getLoaded(new Filter<SceneObject>() {
                    @Override
                    public boolean accept(SceneObject go) {
                        return Lists.toObjectList(session.rockIDs).contains(go.getId()) && Calculations.distanceTo(go) <= distance;
                    }
                });
                for (SceneObject nr : newRocks)
                    rocks.add(new Rock(nr.getLocation(), System.currentTimeMillis()));
                SortedSet<Rock> rocksCopy = new TreeSet<>();
                synchronized (this) {
                    rocksCopy.addAll(rocks);
                }
                for (Rock r : rocksCopy) {
                    rocks.remove(r);
                    if (r.getTime() < 60000) {
                        rocks.add(r);
                        if (r.getObj() != null)
                            if (Lists.toObjectList(session.rockIDs).contains(r.getObj().getId()))
                                r.setTime(System.currentTimeMillis());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            Time.sleep(500);
        }
    }
    
    public Rock next() {
        double distance = Double.MAX_VALUE;
        Rock closestRock = null;
        for (Rock r : rocks) {
            if (Calculations.distanceTo(r.getObj()) < distance &&
                    Lists.toObjectList(session.rockIDs).contains(r.getObj().getId()) &&
                    !r.isOccupado()) {
                distance = Calculations.distanceTo(r.getObj());
                closestRock = r;
            }
        }
        return closestRock == null ? getClosestValidRock() == null ? null : getClosestValidRock() : closestRock; //lolwut
    }

    public synchronized Rock getBestRock() {
        return rocks.isEmpty() ? null : rocks.last(); // return the rock that has been empty the longest
    }

    public synchronized Rock getClosestValidRock() {
        double distance = Double.MAX_VALUE;
        Rock closestRock = null;
        for (Rock r : rocks) {
            if (Calculations.distanceTo(r.getObj()) < distance && Lists.toObjectList(session.rockIDs).contains(r.getObj().getId())) {
                distance = Calculations.distanceTo(r.getObj());
                closestRock = r;
            }
        }
        return closestRock;
    }
}