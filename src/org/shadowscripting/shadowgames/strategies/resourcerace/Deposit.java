package org.shadowscripting.shadowgames.strategies.resourcerace;

import org.powerbot.concurrent.Task;
import org.powerbot.concurrent.strategy.Strategy;
import org.powerbot.game.api.methods.Calculations;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.util.Time;
import org.powerbot.game.api.util.Timer;
import org.shadowscripting.shadowgames.C;

/**
 * Author: Kale
 * Date: 25/07/12
 * Time: 3:42 AM
 */
public class Deposit extends Strategy implements Task {
    
    public boolean validate() {
        return Inventory.isFull();
    }
    
    @Override
    public void run() {
        if(Calculations.distanceTo(C.RR_DEPOSIT_POINT_TILE) > 3 && (SceneEntities.getNearest(C.RR_DEPOSIT_POINT) == null || !SceneEntities.getNearest(C.RR_DEPOSIT_POINT).isOnScreen())) {
            Walking.walk(C.RR_DEPOSIT_POINT_TILE);
        } else {
            SceneEntities.getNearest(C.RR_DEPOSIT_POINT).click(true);
            Time.sleep(100, 600);
        }
    }
}
