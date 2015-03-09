package org.shadowscripting.shadowgames.strategies.resourcerace;

import org.powerbot.concurrent.Task;
import org.powerbot.concurrent.strategy.Strategy;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.tab.Skills;
import org.powerbot.game.api.util.Time;
import org.shadowscripting.shadowgames.C;
import org.shadowscripting.shadowgames.Session;

/**
 * Author: Kale
 * Date: 25/07/12
 * Time: 12:18 PM
 */
public class ResourceListener extends Strategy implements Task {
    
    private long lastScan = -1;
    
    public boolean validate() {
        return System.currentTimeMillis() - lastScan > 50;
    }
    
    @Override
    public void run() {
        boolean mining = Session.resourceRace == Session.ResourceRaceSkill.MINING;
        int[] red = mining ? C.ROCKS_RED : C.TREES_RED;
        int[] green = mining ? C.ROCKS_GREEN : C.TREES_GREEN;
        int[] black = mining ? C.ROCKS_BLACK : C.TREES_BLACK;
        int[] gold = mining ? C.ROCKS_GOLD : C.TREES_GOLD;
        int[] blue = mining ? C.ROCKS_BLUE : C.TREES_BLUE;
        int skillIdx = mining ? Skills.MINING : Skills.WOODCUTTING;

        if(SceneEntities.getNearest(red) != null && Skills.getLevel(skillIdx) >= C.LEVEL_RED) {
            Session.queue.add(SceneEntities.getNearest(red), C.POINTS_RED);
        } else if(SceneEntities.getNearest(green) != null && Skills.getLevel(skillIdx) >= C.LEVEL_GREEN) {
            Session.queue.add(SceneEntities.getNearest(green), C.POINTS_GREEN);
        } else if(SceneEntities.getNearest(black) != null && Skills.getLevel(skillIdx) >= C.LEVEL_BLACK) {
            Session.queue.add(SceneEntities.getNearest(black), C.POINTS_BLACK);
        } else if(SceneEntities.getNearest(gold) != null && Skills.getLevel(skillIdx) >= C.LEVEL_GOLD) {
            Session.queue.add(SceneEntities.getNearest(gold), C.POINTS_GOLD);
        } else if(SceneEntities.getNearest(blue) != null && Skills.getLevel(skillIdx) >= C.LEVEL_BLUE) {
            Session.queue.add(SceneEntities.getNearest(blue), C.POINTS_BLUE);
        }
        lastScan = System.currentTimeMillis();
    }
}
