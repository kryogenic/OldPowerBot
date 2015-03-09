package org.kryogenic.util.astar;

import org.powerbot.game.api.methods.Calculations;
import org.powerbot.game.api.wrappers.Tile;

import java.util.ArrayList;

/**
 * @author: Kale
 * @date: 01/08/12
 * @version: 0.0
 */
public class Node {

    private double g = -1;
    private double h = -1;
    private final Node parent;
    private final Tile tile;

    public static int instances = 0;
    
    /**
     * Creates a new node
     * Note that a node's parent can also have a parent, and so on, therefore making Node a type of list
     * @param tile the tile of the child being created
     * @param parent the node object of the child's parent
     */
    public Node(Tile tile, Node parent) {
        instances++;
        this.tile = tile;
        this.parent = parent;
    }

    @Override
    public boolean equals(Object o) {
        if(o != null && o instanceof Node) {
            Node n = (Node) o;
            return n.getF() == this.getF() &&
                    n.getG() == this.getG() &&
                    n.getTile().equals(this.getTile()) &&
                    (n.getParent() == null && this.getParent() == null ||
                            n.getParent().equals(this.getParent()));
        }
        return false;
    }
    
    public Tile[] getTileArray() {
        ArrayList<Tile> backwardsTileList = new ArrayList<>();
        Node n = this;
        while(n != null) {
            backwardsTileList.add(n.getTile());
            n = n.getParent();
        }
        Tile[] tileArray = new Tile[backwardsTileList.size()]; // reverse the ArrayList and store in an array
        for(int i = 0, j = backwardsTileList.size() - 1; i < backwardsTileList.size(); i++, j--) {
            tileArray[i] = backwardsTileList.get(j);
        }
        return tileArray;
    }

    /**
     * Calculates the "f" score
     * @return the sum of getG() and getH()
     */
    public double getF() {
        return getG() + getH();
    }

    /**
     * Calculates (if not already calculated) the "g" score of this node
     * @return the "g" score
     */
    public double getG() {
        if(g == -1) {
            if(getParent() == null) {
                return g = 0;
            } else {
                return g = getParent().getG() + Calculations.distance(getParent().getTile(), getTile());
            }
        } else {
            return g;
        }
    }

    /**
     * Just a getter for the h score
     * @return the "h" score if it has been calculated, -1 otherwise
     */
    public double getH() {
        return h;
    }

    /**
     * If not already done, calculates the "h" score using the diagonal method
     * @param destination the goal for the path being calculated
     * @return the "h" score
     */
    public void calcH(Tile destination) {
        if(h == -1) {
            double diagonalSteps = Math.min(Math.abs(getTile().getX() - destination.getY()), Math.abs(getTile().getY() - destination.getY()));
            double straightSteps = Math.abs(getTile().getX() - destination.getX()) + Math.abs(getTile().getY() - destination.getY());
            h = Math.sqrt(2) * diagonalSteps + 1 * (straightSteps - 2 * diagonalSteps);
        }
    }

    /**
     * Getter for the parent of this node
     * @return the node object representing the parent
     */
    public Node getParent() {
        return parent == null ? null : parent;
    }

    /**
     * Getter for this node's tile
     * @return the tile
     */
    public Tile getTile() {
        return tile == null ? null : tile;
    }
}
