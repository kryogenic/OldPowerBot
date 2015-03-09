package org.kryogenic.rsbot.struct.walking;

import java.util.ArrayList;

/**
 * @author: Kale
 * @date: 24/08/12
 * @version: 0.0
 */
public abstract class WalkPath extends ArrayList<Traversable> {
    public WalkPath() {
        super();
        init();
    }

    public abstract void init();
}
