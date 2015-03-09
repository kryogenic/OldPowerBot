package org.kryogenic.Miner;

import org.kryogenic.rsbot.struct.kSession;
import org.powerbot.game.api.wrappers.node.SceneObject;

/**
 * @author: Kale
 * @date: 01/08/12
 * @version: 0.0
 */
public class Session extends kSession {
    public int rockIDs[] = {2092, 2093, 5773, 5774, 5775, 6943, 6944, 9717, 9718, 9719, 11954, 11955, 11956, 14856, 14857, 14858, 14913, 14914, 21281, 21282, 29221,
            29222, 31071, 31072, 31073, 32443, 32441, 32442, 37307, 37308, 37309};
    public int miningAnimation = -1;

    public RockScanner rockScanner;

    public SceneObject target;

    public Session() {
        rockScanner = new RockScanner(this, 5);
    }
}
