package org.kryogenic.rsbot.script.yLimpwurt.strat;

import org.kryogenic.rsbot.script.yLimpwurt.Session;
import org.kryogenic.rsbot.struct.walking.Traversable;
import org.kryogenic.rsbot.struct.kStrategy;

import java.util.ArrayList;

/**
 * @author: Kale
 * @date: 24/08/12
 * @version: 0.0
 */
public class Walk extends kStrategy {
    private final Session session;
    private final ArrayList<Traversable> tilePath = new ArrayList<>();

    public Walk(Session session, kStrategy... dependencies) {
        super(dependencies);
        this.session = session;
    }

    @Override
    public boolean validate() {
        return false;
    }

    @Override
    public void run() {
        
    }
}