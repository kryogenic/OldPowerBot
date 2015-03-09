package org.kryogenic.rsbot.struct;

import org.powerbot.concurrent.Task;
import org.powerbot.concurrent.strategy.Strategy;

import java.util.ArrayList;
import java.util.Collections;

/**
 * @author: Kale
 * @date: 01/08/12
 * @version: 0.0
 */
public abstract class kStrategy extends Strategy implements Task {
    
    private ArrayList<kStrategy> dependencies = new ArrayList<>();
    
    public kStrategy(kStrategy... dependencies) {
        Collections.addAll(this.dependencies, dependencies);
    }
    
    public final boolean canRun() {
        boolean b = true;
        for(kStrategy strat : dependencies) {
            b = strat.validate() && b;
        }
        return validate() && b;
    }

    public abstract boolean validate();
}
