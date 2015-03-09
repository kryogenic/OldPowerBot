package org.shadowscripting.shadowgames;

import org.shadowscripting.shadowgames.rocks.queue.SceneObjectQueue;

/**
 * Author: Kale
 * Date: 24/07/12
 * Time: 10:51 PM
 */
public class Session {

    // Cache
    public static SceneObjectQueue queue = new SceneObjectQueue();

    // Settings
    public static enum ResourceRaceSkill {
        FARMING, NONE, MINING, WOODCUTTING
    }
    public static ResourceRaceSkill resourceRace = ResourceRaceSkill.NONE;
}
