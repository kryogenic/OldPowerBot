package org.kryogenic.rsbot.struct.walking.obstacles;

import org.kryogenic.rsbot.struct.walking.Traversable;

/**
 * @author: Kale
 * @date: 24/08/12
 * @version: 0.0
 */
public class Portal extends Traversable {
    
    private final int inId, outId;
    private final String action;
    
    public Portal(int inId, int outId, String action) {
        this.inId = inId;
        this.outId = outId;
        this.action = action;
    }
    
    @Override
    public boolean traverse() {
        return false;
    }
}
