package org.shadowscripting.shadowgames.rocks.queue;

import org.powerbot.game.api.wrappers.node.SceneObject;

import java.util.PriorityQueue;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * Author: Kale
 * Date: 25/07/12
 * Time: 10:11 AM
 */
public class SceneObjectQueue extends PriorityBlockingQueue<QueueElement> {

    public boolean add(SceneObject o, int points) {
        return add(new QueueElement(o, points));
    }

    public void attempted() {
        peek().attemptedTime = System.currentTimeMillis();
    }

    public long attemptedTime() {
        return peek().attemptedTime;
    }

    public void success() {
        peek().success = true;
    }
    
    public boolean hadSuccess() {
        return peek().success;
    }
    
}
