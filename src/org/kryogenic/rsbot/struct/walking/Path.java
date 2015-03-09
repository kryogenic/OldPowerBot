package org.kryogenic.rsbot.struct.walking;

import org.powerbot.game.api.methods.Calculations;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.wrappers.Tile;

/**
 * @author: Kale
 * @date: 24/08/12
 * @version: 0.0
 */
public class Path extends Traversable {
    
    private final Tile start, end;
    
    public Path(Tile start, Tile end) {
        this.start = start;
        this.end = end;
    }

    @Override
    public boolean traverse() {
        return Walking.walk(end); // TODO don't leave it like this
    }
}
