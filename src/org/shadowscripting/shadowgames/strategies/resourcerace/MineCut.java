package org.shadowscripting.shadowgames.strategies.resourcerace;

import org.powerbot.concurrent.Task;
import org.powerbot.concurrent.strategy.Strategy;
import org.powerbot.game.api.methods.Calculations;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.input.Mouse;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.tab.Skills;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.util.Time;
import org.powerbot.game.api.wrappers.Area;
import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.api.wrappers.node.SceneObject;
import org.shadowscripting.shadowgames.C;
import org.shadowscripting.shadowgames.Session;
import org.kryogenic.util.RSBot;

import java.awt.*;
import java.util.*;

/**
 * Author: Kale
 * Date: 24/07/12
 * Time: 8:36 PM
 */
public class MineCut extends Strategy implements Task {

    

    // Move to session?
    private boolean mining = Session.resourceRace == Session.ResourceRaceSkill.MINING;
    private Area area = mining ? C.ROCKS_AREA : C.TREES_AREA;
    private ArrayList<Integer> all = mining ? C.ROCKS : C.TREES;
    private int animation = mining ? C.ROCKS_ANIMATION : C.TREES_ANIMATION;

    public boolean validate() {
        return (Session.resourceRace == Session.ResourceRaceSkill.MINING || Session.resourceRace == Session.ResourceRaceSkill.WOODCUTTING)
                && !Session.queue.isEmpty()
                && !(Session.queue.hadSuccess() && Players.getLocal().getAnimation() == animation);
    }

    @Override
    public void run() {

        if(area.contains(Players.getLocal())) {
            SceneObject obj = Session.queue.peek().obj;
            Tile tile = obj.getLocation();

            if(System.currentTimeMillis() - Session.queue.attemptedTime() > 30000 || !all.contains(SceneEntities.getAt(tile).getId())) {
                Session.queue.remove();
            } else if (Players.getLocal().getAnimation() == animation) {
                Session.queue.success();
            } else if(Calculations.distanceTo(obj) < 3) {
                if(Camera.getPitch() > 270) {
                    Walking.walk(obj.getLocation());
                }
                if(obj.isOnScreen()) {
                    int i = Random.nextInt(0, 100);
                    if(i < 80) {
                        obj.click(true);
                    } else {
                        obj.interact((mining ? "Mine" : "Chop"));
                    }
                    Session.queue.attempted();
                } else {
                    Camera.turnTo(obj);
                }
            } else {
                Tile rockTile = RSBot.shake(Walking.getClosestOnMap(obj.getLocation()));
                Point m = RSBot.shake(Calculations.worldToMap(rockTile.getX(), rockTile.getY()));
                while(Mouse.getLocation() != m) {
                    while(Mouse.getLocation() != m) {
                        Mouse.move(m);
                    }
                    Time.sleep(100, 400);
                }
                Mouse.click(true);
            }
        } else {
            Walking.walk(RSBot.randTileFromArea(area));
        }
    }
}
