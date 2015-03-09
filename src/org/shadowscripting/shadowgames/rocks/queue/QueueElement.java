package org.shadowscripting.shadowgames.rocks.queue;

import org.powerbot.game.api.wrappers.node.SceneObject;

import java.sql.Time;

/**
 * Author: Kale
 * Date: 25/07/12
 * Time: 10:12 AM
 */
public class QueueElement implements Comparable {
    public SceneObject obj;
    public int points;
    public long attemptedTime;
    public boolean success;
    QueueElement(SceneObject obj, int points) {
        this.obj = obj;
        this.points = points;
    }

    @Override
    public int compareTo(Object o) {
        if(o instanceof QueueElement) {
            return this.points - ((QueueElement) o).points;
        }
        return -1;
    }
}