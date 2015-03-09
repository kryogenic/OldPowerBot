package org.kryogenic.util.astar;

import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.wrappers.Tile;

import java.util.ArrayList;

/**
 * Author: Kale
 * Date: 25/07/12
 * Time: 6:54 PM
 */
public class AStar {
    
    public static Tile[] getSequence(Tile start, Tile[] steps, double[] scores) {
        if(steps.length != scores.length) {
            throw new IllegalArgumentException("Must have a score for each tile step");
        }
        Goal[] goals = new Goal[steps.length];
        for(int i = 0; i < steps.length; i++) {
            goals[i] = new Goal(steps[i], scores[i]);
        }
        return getSequence(start, goals);
    }
    
    private static Tile[] getSequence(Tile start, Goal[] goals) {
        ArrayList<Tile> path = new ArrayList<>();
        path.add(start);

        boolean noGoalsLeft;
        do {
            double lowestScore = Double.MAX_VALUE;
            int index = -1;
            Goal bestGoal = null;
            for(int i = 0; i < goals.length; i++) {
                Goal g = goals[i];
                if (Walking.findPath(goals[i].getTile()).getTilePath() == null) // this is true. what the fuck fuck fuck ufkc fudkfxkjkfx
                    // make own a* i guess
                    System.out.println("g is null");
                double score = Walking.findPath(goals[i].getTile()).getTilePath().toArray().length / goals[i].getScore();
                if(score < lowestScore) {
                    lowestScore = score;
                    bestGoal = goals[i];
                    index = i;
                }
            }
            goals[index] = null;
            
            path.add(bestGoal.getTile());
            
            noGoalsLeft = true;
            for(Goal goal : goals) {
                if(goal != null) {
                    noGoalsLeft = false;
                    break;
                }
            }
        } while (!noGoalsLeft);
        
        for(Tile t : path) {
            System.out.println(t);
        }
        return (Tile[]) path.toArray();
    }

    /**
     * Generates the path from one tile to another, using the A* algorithm
     * @param t1 the start tile
     * @param t2 the end tile
     * @return an array of tiles, including the start and end tiles
     */
    public static Tile[] path(Tile t1, Tile t2) {
        Node start = new Node(t1, null);
        Node end;

        ArrayList<Tile> closed = new ArrayList<>();
        NodeList open = new NodeList();
        open.add(start);

        int i = 0;
        while((end = open.getBest(t2)) == null) {
            //if(i++ > 200) {
            //    return null;
            //}
            Node parent = null;
            if(!open.isEmpty()) {
                double bestF = Double.MAX_VALUE;
                for(Node n : open) {
                    if(n.getF() < bestF) {
                        bestF = n.getF();
                        parent = n;
                    }
                }
                if(parent != null) {
                    System.out.println("Removing " + parent.getTile().toString());
                    System.out.println("parent removal: " + open.remove(parent));
                    closed.add(parent.getTile());

                    for(int xMod = -1; xMod <= 1; xMod++) {
                        for(int yMod = -1; yMod <= 1; yMod++) {
                            if(!(xMod == 0 && yMod == 0)) {
                                Node n = new Node(new Tile(parent.getTile().getX() + xMod, parent.getTile().getY() + yMod, parent.getTile().getPlane()), parent);
                                if(!closed.contains(n.getTile()) && SceneEntities.getAt(n.getTile(), SceneEntities.TYPE_BOUNDARY | SceneEntities.TYPE_INTERACTIVE) == null) {
                                    n.calcH(t2);
                                    if(open.contains(n.getTile())) {
                                        if(n.getG() < open.getBest(n.getTile()).getG()) {
                                            System.out.println("Replacing " + open.getBest(n.getTile()).toString() + " with " + n.getTile().toString());
                                            System.out.println("Removal: " + open.removeAll(n.getTile()) + " Insertion: " +  open.add(n));
                                        }
                                    } else {
                                        System.out.println("Adding " + n.getTile().toString());
                                        System.out.println("Adding n: " + open.add(n));
                                    }
                                }
                            }
                        }
                    }
                }
            } else {
                return null;
            }
        }

        return end.getTileArray();
    }

    /**
     * Generates the path from the player's location to a specified destination, using the A* algorithm
     * @param destination the desired end location
     * @return an array of tiles, including the start and end tiles
     */
    public static Tile[] pathTo(Tile destination) {
        return path(Players.getLocal().getLocation(), destination);
    }
}





