package org.kryogenic.rsbot.struct.walking.obstacles;

import org.kryogenic.rsbot.struct.walking.Traversable;
import org.kryogenic.util.Calc;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.util.Time;
import org.powerbot.game.api.util.Timer;
import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.api.wrappers.node.SceneObject;

/**
 * @author: Kale
 * @date: 24/08/12
 * @version: 0.0
 */
public class Door extends Traversable {
    
    private final int closedId, openId;
    private final String openAction;
    
    public Door(int closedId, int openId, String openAction) {
        this.closedId = closedId;
        this.openId = openId;
        this.openAction = openAction;
    }

    @Override
    public boolean traverse() {
        Timer mainTimeout = new Timer(Random.nextInt(25000, 35000));
        while(mainTimeout.isRunning()) {
        boolean failed = false;
        // detect the door
        SceneObject openDoor = null, closedDoor = null;
        Timer timeout = new Timer(Random.nextInt(1500, 3000));
        while(openDoor == null && closedDoor == null && !(failed = !timeout.isRunning())) {
            openDoor = SceneEntities.getNearest(openId);
            closedDoor = SceneEntities.getNearest(closedId);
            Time.sleep(200, 500);
        }
        // detect the tile on the other side of the door
        Tile otherSide = null;
        if(timeout.isRunning())
            timeout = new Timer(Random.nextInt(500, 1500));
        while(otherSide == null && !(failed = failed || !timeout.isRunning())) {
            Tile t;
            if(openDoor == null) {
                t = closedDoor.getLocation();
            } else {
                t = openDoor.getLocation();
            }
            Tile p = Players.getLocal().getLocation();
            int mask = SceneEntities.TYPE_BOUNDARY | SceneEntities.TYPE_INTERACTIVE;
            if(SceneEntities.getAt(t.derive(0, 1), mask) == null && SceneEntities.getAt(t.derive(0, -1), mask) == null) { // north/south
                otherSide = t.derive(0, Calc.lessThan(p.getY(), t.getY()));
            } else if(SceneEntities.getAt(t.derive(1, 0), mask) == null && SceneEntities.getAt(t.derive(-1, 0), mask) == null) { // east/west
                otherSide = t.derive(0, Calc.lessThan(p.getX(), t.getX()));
            } else {
                return false;
            }
        }
        // open the door
        if(timeout.isRunning())
            timeout = new Timer(Random.nextInt(3000, 5000));
        while(openDoor == null && !(failed = failed || !timeout.isRunning())) {
            closedDoor.interact(openAction);
            Time.sleep(200, 1000);
        }
        // walk through (not always necessary)
        if(timeout.isRunning())
            timeout = new Timer(Random.nextInt(4500, 7000));
        while(openDoor != null && !Players.getLocal().getLocation().equals(otherSide) && !(failed = failed || !timeout.isRunning())) {
            otherSide.click(true);
            Time.sleep(200, 1500);
        }
        return Players.getLocal().getLocation().equals(otherSide);
        }
        return false; // TODO WAt;
    }
}
