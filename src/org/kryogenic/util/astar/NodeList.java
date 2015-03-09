package org.kryogenic.util.astar;

import org.powerbot.game.api.wrappers.Tile;

import java.util.ArrayList;

/**
 * A wrapper for an ArrayList of nodes, provides methods for dealing with just the tiles of the nodes
 * @author: Kale
 * @date: 01/08/12
 * @version: 0.0
 */
public class NodeList extends ArrayList<Node> {

    /**
     * Checks if there exists a node with the specified tile
     * @param t the tile to look for
     * @return true if the list contains one or more nodes with that tile, false otherwise
     */
    public boolean contains(Tile t) {
        for(Node n : this) {
            if(n.getTile().equals(t))
                return true;
        }
        return false;
    }

    /**
     * Gets the node with the best "g" score with a specified tile
     * @param t the tile to look for
     * @return the node if it's found, null otherwise
     */
    public Node getBest(Tile t) {
        double bestG = Double.MAX_VALUE;
        Node bestNode = null;
        for(Node n : this) {
            if(n.getTile().equals(t) && n.getG() < bestG) {
                bestG = n.getG();
                bestNode = n;
            }
        }
        return bestNode;
    }

    /**
     * Removes all entries with a specified tile
     * @param t the tile to remove
     * @return true if all possible nodes were removed, false otherwise
     */
    public boolean removeAll(Tile t) {
        boolean b = true;
        for(int i = size() - 1; i >= 0; i--) {
            if(get(i).getTile().equals(t)) {
                b = remove(get(i)) && b;
            }
        }
        return b;
    }
}