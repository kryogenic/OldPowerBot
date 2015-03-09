package org.kryogenic.util.astar;

import org.powerbot.game.api.wrappers.Tile;

/**
 * @author: Kale
 * @date: 01/08/12
 * @version: 0.0
 */
public class Goal {
    private final Tile tile;
    private final double score;
    Goal(Tile tile, double score) {
        this.tile = tile;
        if(score < 0 || score > 1) {
            throw new IllegalArgumentException("Score must be between 0 and 1");
        }
        this.score = score;
    }
    public Tile getTile() {
        return tile;
    }
    public double getScore() {
        return score;
    }
}
