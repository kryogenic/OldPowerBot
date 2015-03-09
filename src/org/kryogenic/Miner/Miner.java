package org.kryogenic.Miner;

import org.kryogenic.rsbot.struct.kPaint;
import org.kryogenic.rsbot.struct.kScript;
import org.kryogenic.rsbot.struct.kStrategy;
import org.kryogenic.util.Lists;
import org.kryogenic.util.astar.AStar;
import org.powerbot.game.api.methods.Calculations;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.util.Filter;
import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.api.wrappers.interactive.Player;
import org.powerbot.game.api.wrappers.node.SceneObject;

/**
 * Author: Kale
 * Date: 31/07/12
 * Time: 4:15 PM
 */
public class Miner extends kScript {
    
    private final Session session = new Session();

    @Override
    public void updatePaint(kPaint paint) {

    }

    @Override
    protected void setup() {
        
    }
}
class FindRock extends kStrategy {

    private final Session session;
    
    public FindRock(Session session) {
        this.session = session;
    }

    protected Session getSession() {
        return (Session) session;
    }

    @Override
    public boolean validate() {
        return true;
    }

    @Override
    public void run() {
        if(Players.getLocal().getAnimation() == getSession().miningAnimation) {
            SceneObject[] rocks = SceneEntities.getLoaded(getSession().rockIDs);
            SceneObject[] closestRocks = new SceneObject[5];

            for (int i = 0; i < 5; i++) {
                double lowest = Calculations.distanceTo(rocks[0]);
                int idx = 0;
                for(int j = 1; j < rocks.length; j++) {
                    double dist = rocks[j] == null ? Double.MAX_VALUE : Calculations.distanceTo(rocks[j]);
                    if(dist < lowest) {
                        lowest = dist;
                        idx = j;
                    }
                }
                closestRocks[i] = rocks[idx];
                rocks[idx] = null;
            }

            SceneObject bestRock = closestRocks[0];
            for(int i = 1; i < 5; i++) {
                int crap = 0;
                for(int xMod = -1; xMod <= 1; xMod++) {
                    for(int yMod = -1; yMod <= 1; yMod++) {
                        final Tile t = new Tile(bestRock.getLocation().getX() + xMod, bestRock.getLocation().getY() + yMod, bestRock.getPlane());
                        Player[] players = Players.getLoaded(new Filter<Player>() {
                            @Override
                            public boolean accept(Player p) {
                                return p.getLocation().equals(t);
                            }
                        });
                        for(Player p : players) {
                            int theta = p.getOrientation();
                            boolean b = false;
                            if(xMod == -1 && yMod == -1) {
                                b = theta == 45;
                            } else if(xMod == -1 && yMod == 0) {
                                b = theta == 0;
                            } else if(xMod == -1 && yMod == 1) {
                                b = theta == 315;
                            } else if(xMod == 0 && yMod == -1) {
                                b = theta == 90;
                            } else if(xMod == 0 && yMod == 1) {
                                b = theta == 270;
                            } else if(xMod == 1 && yMod == -1) {
                                b = theta == 235;
                            } else if(xMod == 1 && yMod == 0) {
                                b = theta == 0;
                            } else if(xMod == 1 && yMod == 1) {
                                b = theta == 135;
                            } // todo check if these are working
                        }
                    }
                }
                bestRock = AStar.pathTo(closestRocks[i].getLocation()).length < AStar.pathTo(bestRock.getLocation()).length ? rocks[i] : bestRock;
            }
        } else {
            if(!Players.getLocal().isMoving()) {
                SceneObject[] rocks = SceneEntities.getLoaded(getSession().rockIDs);
                SceneObject[] closestRocks = new SceneObject[5];

                for (int i = 0; i < 5; i++) {
                    double lowest = Calculations.distanceTo(rocks[0]);
                    int idx = 0;
                    for(int j = 1; j < rocks.length; j++) {
                        double dist = rocks[j] == null ? Double.MAX_VALUE : Calculations.distanceTo(rocks[j]);
                        if(dist < lowest) {
                            lowest = dist;
                            idx = j;
                        }
                    }
                    closestRocks[i] = rocks[idx];
                    rocks[idx] = null;
                }
                
                SceneObject bestRock = closestRocks[0];
                for(int i = 1; i < 5; i++) {
                    bestRock = AStar.pathTo(closestRocks[i].getLocation()).length < AStar.pathTo(bestRock.getLocation()).length ? rocks[i] : bestRock;
                }
                getSession().target = bestRock;
            }
        }
    }
}

class MineRock extends kStrategy {

    private final Session session;
    
    public MineRock(Session session) {
        this.session = session;
    }

    @Override
    public boolean validate() {
        return session.target != null && Lists.arrayContains(session.rockIDs, session.target.getId()) && !Inventory.isFull();
    }

    @Override
    public void run() {

    }
}

class WalkRock extends kStrategy {
    
    Tile walkTo;

    private final Session session;

    public WalkRock(Session session) {
        this.session = session;
    }

    @Override
    public boolean validate() {
        return Calculations.distanceTo(session.target) > 3 && Calculations.distance(walkTo, session.target) > 3 * Math.sqrt(2); // antiban, lol
    }

    @Override
    public void run() {
        int error = (int) Math.floor(Calculations.distanceTo(session.target) * 0.5);
        while(AStar.pathTo(walkTo = session.target.getLocation().randomize(error, error)) != null);
        walkTo.clickOnMap();
    }
}

class CameraRock extends kStrategy {
    
    private Session session;

    public CameraRock(Session session) {
        this.session = session;
    }

    @Override
    public boolean validate() {
        return !session.target.isOnScreen();
    }

    @Override
    public void run() {
        Camera.turnTo(session.target, 10);
    }
}